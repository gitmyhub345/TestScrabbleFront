/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import org.dragoncave.home.scrabble_2.*;
import org.dragoncave.home.web.DCScrabbleVerifier;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author Rider1
 */

public class ScrabbleGameHandler {
    Map<String,WebSocketSession> mapPlayerSession; // game name, player session
    Map<String,DCScrabbleGame> mapGame;
//    private List<String> gameName;
    Map<String,List<String>> mapGamePlayer;
    private boolean debug;
    
    String gameName;
    String player;
    
    @Autowired
    RandID randGen;
    
    public ScrabbleGameHandler(){
        mapPlayerSession = new HashMap<String,WebSocketSession>();
//        gameName = new ArrayList<String>();
        mapGame = new HashMap<String,DCScrabbleGame>();
        mapGamePlayer = new HashMap<String,List<String>>();
        debug = true;
    }
    public void setDebug(boolean debug){
        this.debug = debug;
    }
    public void handleMessage(String jsonMessage,WebSocketSession session){
        
        JSONObject jsonResponse = new JSONObject();
        JSONObject jsonRequest = new JSONObject(jsonMessage);
        
        gameName = jsonRequest.getString("game name");
        player = jsonRequest.getString("player name");
        
        if (debug){
            System.out.println("ScrabbleGameHandler: handleMessage() -> \n\tjsonRequest: "+jsonRequest.getString("request type"));
        }
        switch (jsonRequest.getString("request type")){
            case "new game":
                jsonResponse = processNewGame(jsonRequest,session);
                break;
            case "join game":
                jsonResponse = processJoinGame(jsonRequest, session);
                break;
            case "start game":
                jsonResponse = processStartGame(jsonRequest,session);
                break;
//            case "get hand":
            case "submit tiles":
                jsonResponse = processSubmit(jsonRequest,session);
                break;
            case "return tiles":
                jsonResponse = processReturn(jsonRequest,session);
                break;
            case "end game":
                jsonResponse = processEnd(jsonRequest,session);
                break;
            case "challenge":
                jsonResponse = processChallenge(jsonRequest,session);
                break;
            default:
                jsonResponse.put("success",true);
                jsonResponse.put("request type", jsonRequest.getString("request type"));
                jsonResponse.put("randomID",randGen.getRandAppID());
        }
        if (debug){
            System.out.println("\tbefore return: "+jsonResponse.toString());
        }
        //return jsonResponse.toString();
    }
    
    private JSONObject processNewGame(JSONObject jsonRequest, WebSocketSession session){
        if (debug) {
            System.out.println("ScrabbleGameHandler: processNewGame() ->\n\tprocessing request for new game"+jsonRequest.toString());
        }
        JSONObject jsonNewGame = new JSONObject();
        if (!mapGame.containsKey(gameName)){
            // create new game, add to the map
            List<String> listPlayers = new ArrayList<>();
            if(debug){
                System.out.println("\tcreating new game... game name="+gameName+" and player="+player );
            }
            DCScrabbleGame game = new DCScrabbleGame(gameName,player);
            game.setDebug(this.debug);
            listPlayers.add(game.getPlayers().get(0));
            if(debug){
                System.out.println("new game created with name "+game.getGameName()+" and player name "+game.getPlayers().get(0));
            }
            WebSocketSession put = mapPlayerSession.put(game.getPlayers().get(0), session);
            mapGame.put(game.getGameName(), game);
            mapGamePlayer.put(game.getGameName(), listPlayers);
//            mapGamePlayer.put(game.getGameName(), game.getPlayers());
            if(debug){
                System.out.println("\t\tmapGame items:");
                for (Entry<String,DCScrabbleGame> entry: mapGame.entrySet()){
                    System.out.println("\t\t\tgame name:"+entry.getKey());
                }
            }
//            gameName.add(game.getGameName());
            jsonNewGame.put("success", true);
            jsonNewGame.put("mode",0);
            jsonNewGame.put("message","New game created");
            jsonNewGame.put("number players", game.getPlayers().size());
            jsonNewGame.put("request type", jsonRequest.getString("request type"));
            jsonNewGame.put("player name",game.getPlayers().get(0));
            jsonNewGame.put("game name",game.getGameName());
            
        } else {
            // game with this name already exists, cannot create new game with the same name
            // not processing necessary
            jsonNewGame.put("success", false);
            jsonNewGame.put("message","A game with the same name already exists, do you want to join?");
            jsonNewGame.put("number players",mapGame.get(gameName).getPlayers().size() );
            jsonNewGame.put("request type", jsonRequest.getString("request type"));
        }
        
//        try{
//            session.sendMessage(new TextMessage(jsonNewGame.toString()));
            boolean send = sendJSONMessage(jsonNewGame,session);
            if (!send){
                System.out.println("\tError sending newGame message.");
            }
//        } catch (IOException e){
//            System.out.println("Error sending message: " + e);
//        }
        return jsonNewGame;
    }
    
    private JSONObject processJoinGame(JSONObject jsonRequest, WebSocketSession session){
        JSONObject jsonResponse = new JSONObject();
        if(mapGame.containsKey(gameName)){
            jsonResponse = processGame(jsonRequest, session);
            if (jsonResponse.getBoolean("success") == true){
                // successfully joined game, add to game,player mapping
                mapGamePlayer.get(gameName).add(player);
                mapPlayerSession.put(player, session);

                if(debug){
                    int numberPlayers = mapGame.get(gameName).getPlayers().size();
                    for (int index = 0; index < numberPlayers; index++){
                        String listPlayerName = mapGamePlayer.get(gameName).get(index);
                        String gamePlayerName = mapGame.get(gameName).getPlayers().get(index);
                        if (!listPlayerName.equals(gamePlayerName)){
                            System.out.println("ScrabbleGameHandler: processJoinGame()->\n\tError: player lists do not synch with one another");
                        }
                    }
                }
            }
        } else {
            jsonResponse.put("request type", "join game");
            jsonResponse.put("success", false);
            jsonResponse.put("message","unable to join an nonexistent game");
            jsonResponse.put("player name", player);
            jsonResponse.put("game name",gameName);
        }
        boolean send = sendJSONMessage(jsonResponse,session);
        if (!send){
            System.out.println("\tError sending joinGame message.");
        }
        
        for (String p : mapGamePlayer.get(gameName)){
            if(!p.equals(player)){
                jsonResponse.remove("request type");
                jsonResponse.put("message",player+" has joined the game");
                send = sendJSONMessage(jsonResponse,mapPlayerSession.get(p));
            }
        }
        return jsonResponse;
    }
 
    private JSONObject processGame(JSONObject jsonRequest, WebSocketSession session){
        //JSONObject jsonResponse = new JSONObject();
        DCScrabbleGame game = mapGame.get(gameName);
//        JSONObject jsonResponse = new JSONObject(game.submitMainRequest(jsonRequest.toString()));
        JSONObject jsonResponse = game.submitMainRequest(jsonRequest);
        return jsonResponse;
    }

    private JSONObject processStartGame(JSONObject jsonRequest, WebSocketSession session){
        if (debug){
            System.out.println("ScrabbleGameHandler: processStartGame()->");
        }
        DCScrabbleGame game = mapGame.get(gameName);
        // get hand
        /**
         * getting hand and sending to each player who joined game
         */
            JSONObject jsonRequestGetHand = new JSONObject();
            jsonRequestGetHand.put("request type", "get hand");
            List<String> gamePlayers = game.getPlayers();
            for (int index = 0; index < gamePlayers.size(); index++){
                String handPlayer = gamePlayers.get(index);
                WebSocketSession s = mapPlayerSession.get(handPlayer);
//                JSONObject jsonResponseHand = new JSONObject(game.getHand());
                JSONObject jsonResponseHand = processGame(jsonRequestGetHand,session);
                jsonResponseHand.put("request type", "get hand");
                if(debug){
                    System.out.println("ScrabbleGameHander: processStartGame()->getHand() for "+handPlayer+": response:"+jsonResponseHand.toString());
                }
//                s.sendMessage(new TextMessage(jsonResponseHand.toString()));
                boolean send = sendJSONMessage(jsonResponseHand,s);
                if (!send){
                    System.out.println("\tError sending results from getHand() function.");
                }
            }
        /**
         * end getting hand section
         */

        JSONObject jsonResponse = new JSONObject();

        /**
         * Start game 
         */
        jsonResponse = game.submitMainRequest(jsonRequest);
        /**
         * end Start game process - message to be sent to players after populating player hands
         */
        /**
         * need to send start game message for all players to synch.
         */
//        try{
//            session.sendMessage(new TextMessage(jsonResponse.toString()));
            if (debug){
                System.out.println("\tSending start game message..."+jsonResponse.toString());
            }
            boolean mainPlayerSend = sendJSONMessage(jsonResponse, session);
            List<String> listPlayers = game.getPlayers();
            if (!mainPlayerSend){
                System.out.println("\tError sending start game message to originating player");
            }
            for(String thisplayer: listPlayers){
                if (!thisplayer.equals(jsonRequest.getString("player name"))){
                    if (mapPlayerSession.get(thisplayer).getId() != session.getId()){
                        jsonResponse.put("message", "game started by "+player);
//                        mapPlayerSession.get(player).sendMessage(new TextMessage(""));
                        if (debug){
                            System.out.println("\tSending start game message to player"+thisplayer+"..."+jsonResponse.toString());
                        }
                        boolean otherPlayersSend = sendJSONMessage(jsonResponse,mapPlayerSession.get(thisplayer));
                        if (!otherPlayersSend){
                            System.out.println("\tError sending start game message to player: "+thisplayer);
                        }
                    }
                }
            }
//        }catch(IOException e){
//            System.out.println("ERROR: ScrabbleHanlder: processStartGame()-> sendMessage error in starting game");
//        }
                        
        /**
         * end message to first player
         */

        return jsonResponse;
    }

    private JSONObject processSubmit(JSONObject jsonRequest, WebSocketSession session){
        JSONObject jsonResponse = processGame(jsonRequest,session);
        JSONObject updateBoard = new JSONObject();
        JSONObject replacements = new JSONObject();
        if (jsonResponse.keySet().contains("update board")){
            updateBoard = jsonResponse.getJSONObject("update board");
            jsonResponse.remove("update board");
           
        }
        if(debug){
            System.out.println("sending message to submitting player: "+player+"\n\t"+jsonResponse.toString());
        }
        boolean send = sendJSONMessage(jsonResponse,session);
        if (!send){
            System.out.println("\tError sending submit message to submitting player: "+player);
        }
        
        if (jsonResponse.keySet().contains("replacement tiles")){
            replacements = jsonResponse.getJSONObject("replacement tiles");
            jsonResponse.remove("replacement tiles");
        }
        
        jsonResponse.put("update board",updateBoard);
        List<String> gamePlayers = mapGame.get(gameName).getPlayers();
        for (String p: gamePlayers){
            if (!p.equals(player)){
                if(debug){
                    System.out.println("\tsending message to other players: "+p+"\n\tresponse: "+jsonResponse.toString());
                }
                boolean pSend = sendJSONMessage(jsonResponse,mapPlayerSession.get(p));
                if (!pSend){
                    System.out.println("\tError sending submit message to player: "+p);
                }
            }
        }
        return jsonResponse;
    }

    private JSONObject processReturn(JSONObject jsonRequest, WebSocketSession session){
        JSONObject jsonResponse = processGame(jsonRequest,session);
        if(debug){
            System.out.println("ScrabbleGameHandler: processReturn()-> response: "+jsonResponse.toString());
        }
        boolean send = sendJSONMessage(jsonResponse,session);
        if (!send){
            System.out.println("\tError sending submit message to returning player: "+player);
        }
        
        if (jsonResponse.keySet().contains("replacement tiles"))
            jsonResponse.remove("replacement tiles");
        List<String> gamePlayers = mapGame.get(gameName).getPlayers();
        for (String p: gamePlayers){
            if (!p.equals(player)){
                boolean pSend = sendJSONMessage(jsonResponse,mapPlayerSession.get(p));
                if (!pSend){
                    System.out.println("\tError sending submit message to player: "+p);
                }
            }
        }
        
        return jsonResponse;
    }

    private JSONObject processEnd(JSONObject jsonRequest, WebSocketSession session){
        JSONObject jsonResponse = processGame(jsonRequest,session);
        List<String> gamePlayers = mapGame.get(gameName).getPlayers();
        for (String p: gamePlayers){
//            if (!p.equals(player)){
                boolean pSend = sendJSONMessage(jsonResponse,mapPlayerSession.get(p));
                if (!pSend){
                    System.out.println("\tError sending submit message to player: "+p);
                }
//            }
        }
        
        mapGamePlayer.get(gameName).clear();
        mapGamePlayer.remove(gameName);
        mapGame.remove(gameName);

        return jsonResponse;
    }
    
    private boolean sendJSONMessage(JSONObject jsonResponse, WebSocketSession session){
        boolean result = false;
        try{
            session.sendMessage(new TextMessage(jsonResponse.toString()));
            result = true;
        } catch (IOException e){
            System.out.println("Error send message to userid "+session.getId()+"\n\t"+e);
        }
        
        return result;
    }
    
    private JSONObject processChallenge(JSONObject jsonRequest,WebSocketSession session){
        JSONObject jsonResponse = processGame(jsonRequest,session);
        /**
         * {"current player":1,"player stats":{"mp1name2":[],"mp1name1":[{"word":"RAKE","value":16}]},"total words":1,"points gained":16,
         * "replacement tiles":{"hand":[{"isDraggable":true,"isInPool":false,"letter":"O","tileNumber":36,"isPlacedOnBoard":false},
         *                          {"isDraggable":true,"isInPool":false,"letter":"S","tileNumber":65,"isPlacedOnBoard":false},
         *                          {"isDraggable":true,"isInPool":false,"letter":"N","tileNumber":43,"isPlacedOnBoard":false},
         *                          {"isDraggable":true,"isInPool":false,"letter":"O","tileNumber":32,"isPlacedOnBoard":false}]},
         * "message":"mp1name1 successfully submitted tiles","next player":2,"mode":0,"number of players":2,
         * "current player name":"mp1name1","success":true,"tiles remaining":82,"number of played words":1,
         * "request type":"submit tiles","played words":["RAKE"],"next player name":"mp1name2"}
         * */
        
        // need to give replacement tile to the correct player: currentplayer
        String challengedPlayer = jsonResponse.getString("challenged player name");
        List<String> players = mapGame.get(gameName).getPlayers();
        // send message to challenged player
        if(debug){
            System.out.println("ScrabbleGameHandler: processChallenge()-> sending message to challenged player "+challengedPlayer+": "+jsonResponse.toString());
        }
        sendJSONMessage(jsonResponse,mapPlayerSession.get(challengedPlayer));
        
        // remove replacement tiles for all other messages
        jsonResponse.remove("replacement tiles");
        for (String p: players){
            if (!p.equals(challengedPlayer)){
                if(debug){
                    System.out.println("ScrabbleGameHandler: processChallenge()-> sending message to "+p+": "+jsonResponse.toString());
                }
                sendJSONMessage(jsonResponse,mapPlayerSession.get(p));
            }
        }
//        sendJSONMessage(jsonResponse,session);
        return jsonResponse;
    }
    
    public boolean sendMessageFromGame(JSONObject jsonResponse, String game, String player){
        boolean result = false;
        List<String> listPlayers = mapGamePlayer.get(game);
        if (listPlayers.contains(player)){
            WebSocketSession s = mapPlayerSession.get(player);
            sendJSONMessage(jsonResponse,s);
        }
        return result;
    }
}
