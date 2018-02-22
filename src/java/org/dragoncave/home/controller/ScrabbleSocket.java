/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.dragoncave.home.scrabble_2.DCScrabbleGame;
import org.dragoncave.home.service.RandID;
import org.dragoncave.home.service.ScrabbleGameHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
/**
 *
 * @author Rider1
 */
@ComponentScan("org.dragoncave.home")
public class ScrabbleSocket extends TextWebSocketHandler {
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    Map<DCScrabbleGame,WebSocketSession> mapGameSession = new HashMap<>();
    
    @Autowired
    ScrabbleGameHandler gameHandler;
    
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException{
        // can't just reply with hello message to every one.
        
//        String requestMessage = message.getPayload();
        String value = message.getPayload();
        String jsonvalue = value.substring(19);
        System.out.println("message received: " + value);
//        if (gameHandler == null){
//            gameHandler = new ScrabbleGameHandler();
//
//        }
//        String response = 
                gameHandler.handleMessage(value,session);
//        System.out.println("response message: "+response);
//        for (WebSocketSession webSocketSession: sessions){
//            System.out.println("sending message to webSocketSession id "+webSocketSession.getId());
//            if (webSocketSession.isOpen())
//                webSocketSession.sendMessage(new TextMessage(response));
//        } 

    }
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        
        System.out.println("socket connected to client with ID: "+session.getId() + "\nnumber of clients: "+sessions.size());
    }
}
