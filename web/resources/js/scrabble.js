/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
	//$('.divContainer').droppable({accept: '.nondraggable'});
	$('#newjoingame').click(function(){displayNewForm();});
	$('#btnNewGame').click(function(){createNewGame();});
	$('#btnJoinGame').click(function(){joinGame();});
	$('#btnCancel').click(function(){hideForm();});
	$('#submittiles').click(function(){submitPlay();});
	$('#returntiles').click(function(){returnTiles();});
	$('#startgame').click(function(){startGame();});
	$('#endgame').click(function(){endGame();});
	$('#btnChallengeNo').click(function(){challengeNo();})
	$('#btnChallengeYes').click(function(){challengeYes();});
	$('#showBoard').click(function(){showBoard()});
	$('#showStats').click(function(){showStats()});        
        
	var replaceHand = JSON.parse('{"replacement tiles":{"hand":[{"isDraggable":true,"isInPool":false,"letter":"L","tileNumber":58,"isPlacedOnBoard":false},{"isDraggable":true,"isInPool":false,"letter":"L","tileNumber":61,"isPlacedOnBoard":false},{"isDraggable":true,"isInPool":false,"letter":"D","tileNumber":72,"isPlacedOnBoard":false},{"isDraggable":true,"isInPool":false,"letter":"D","tileNumber":73,"isPlacedOnBoard":false},{"isDraggable":true,"isInPool":false,"letter":"P","tileNumber":83,"isPlacedOnBoard":false},{"isDraggable":true,"isInPool":false,"letter":"K","tileNumber":95,"isPlacedOnBoard":false},{"isDraggable":true,"isInPool":false,"letter":"G","tileNumber":76,"isPlacedOnBoard":false}]}}');
	$('#scrabblemenu').menu();
	$( '.draggable' ).draggable({ revert: "invalid" });
	$('.draggable').draggable({
			//clickedItem = this;
			drag: function(event,ui){
				/*clickedItem = $(this);
				if(clickedItem == null) 
					alert('null item clicked');
				$('#status').html(clickedItem.innerHTML);
				$(this).parent().droppable('enable');
				parentTileHolder = $(this).parent();*/
				dragFunction(event,ui,$(this));
			}


			
	});
	$('.draggable').draggable({
		stop: function(event,ui){
			var reverter = $(this).draggable('option','revert');
			//alert('dragging stopped, reverter: '+reverter);
		}
	});
	
	$('[class*="TileHolder"]').droppable({
		drop: function(event, ui){
			var numChildren = $(this).children().length;
			//alert('*tileholder* has '+numChildren+' child(ren)');
			if (numChildren > 0){
				$(parentTileHolder).append(clickedItem);
				$(clickedItem).offset({top: $(parentTileHolder).offset().top+3, left: $(parentTileHolder).offset().left+3});
				$(parentTileHolder).droppable('disable');
				return false;
				
			} else {
				var parenttop = $(this).offset().top;
				var parentleft = $(this).offset().left;
				$(clickedItem).offset().top = parenttop+2;
				$(clickedItem).offset().left = parentleft+2;
				$(clickedItem).attr('isPlacedOnBoard',true);
				$(this).append(clickedItem);
				$(clickedItem).offset({ top: parenttop+3, left: parentleft+3 });
				$(this).droppable('disable');
				
				var tileHolderClass = $(this).attr('class');
				var parentID = $(parentTileHolder).attr('Id');
				if (tileHolderClass.split(' ')[0] === 'boardTileHolder') {
					var key = $(this).attr('Id');
					var tileLetter = $(clickedItem).attr('letter');
					var tileID = $(clickedItem).attr('Id');
					var tileIsInPool = $(clickedItem).attr('isInPool');
					var tileIsPlacedOnBoard = $(clickedItem).attr('isPlacedOnBoard');
					var tileIsDraggable = $(clickedItem).attr('isDraggable');
					var jsonObject = JSON.parse('{}');
					jsonObject.tileNumber = tileID;
					jsonObject.letter = tileLetter;
					jsonObject.isInPool = tileIsInPool;
					jsonObject.isPlacedOnBoard = tileIsPlacedOnBoard;
					jsonObject.isDraggable = tileIsDraggable;
					playedTilesMap.set(key,jsonObject)
					jsonPlayedTilesMap[key] = jsonObject;
					playedTilesMap.delete(parentID);
					delete jsonPlayedTilesMap[parentID];
					clickedItem = null;
				} else {
					$(clickedItem).attr('isPlacedOnBoard',false);
					playedTilesMap.delete(parentID);
					delete jsonPlayedTilesMap[parentID];
				}
				//alert('tileHolderclass: '+tileHolderClass+'\ntile: '+JSON.stringify(jsonObject));
//				$('#status').html(mapToString(playedTilesMap)+'<br><br>'+JSON.stringify(jsonPlayedTilesMap));
				return true;
			}
			
		}	
		
	});
	
        
        
});
function updateLastBoardUpdateTiles(){
//    alert('updating last updated board tiles: '+JSON.stringify(lastBoardUpdateTiles));
    for (var key in lastBoardUpdateTiles){
        var keyString = '#'+key;
        var tileHolder = $(keyString);
        $(tileHolder).children().first().removeClass('tempnondraggable');
        $(tileHolder).children().first().addClass('nondraggable');
    }
    //clear list to prevent concatenating future json objects for updating
    lastBoardUpdateTiles = {};
}
function clearLastBoardUpdateTilesHolders(){
    alert('clearLastBoardUpdateTilesHolders: removing last board update tiles'+JSON.stringify(lastBoardUpdateTiles));
    for (var key in lastBoardUpdateTiles){
        var keyString = '#'+key;
        var tileHolder = $(keyString);
        $(tileHolder).empty();
        $(tileHolder).droppable('enable');
    }
    lastBoardUpdateTiles = {};
}
function deactivateMapTiles(mode){
//    alert('deactivating mapped tiles with '+playedTilesMap.size+' tiles and mode '+mode);
    playedTilesMap.forEach(function(value,key){
        var keyString = '#'+key;
        var child = $(keyString).children().first();//document.getElementById(key).children();
        if (child === null || child === 'undefined')
                alert('no child found');
        //alert('key: '+key+'\nattempting to deactivate '+playedTilesMap.size+' tiles \nnumber of child(ren) '+$(child).attr('name'));
        $(child).removeClass('draggable');
        $(child).removeClass('tempnondraggable');
        if (mode === 0){
            $(child).addClass('nondraggable');
        } else{
            $(child).addClass('tempnondraggable');
        }
        $(child).draggable('disable');
    });
    if (mode === 0){
        playedTilesMap.clear();
        jsonPlayedTilesMap = {};
    }
}

function getStandardRequestJson(){
    var jsonRequest = JSON.parse('{}');
    jsonRequest['game name'] = gameName;
    jsonRequest['player name'] = playerName;
    return jsonRequest;
}

function loadNewTiles(newTileString){
    var arrTileHolders = $('.handTileHolder');
    var replacedTilesIndex = 0;
    var numTiles = newTileString['tiles remaining'];
    var jsonReplacementHand = newTileString['replacement tiles'];
    var len = jsonReplacementHand.hand.length;
    if (len > 0){
        for (var index = 0; index < arrTileHolders.length; index++){
            if (arrTileHolders[index].children.length === 0){
                var jsonTile = newTileString['replacement tiles'].hand[replacedTilesIndex];
                var tile = $('<div></div>');
                tile.attr('isDraggable',jsonTile.isDraggable);
                tile.attr('isPlacedOnBoard',jsonTile.isPlacedOnBoard);
                tile.attr('isInPool',jsonTile.isInPool);
                tile.attr('letter',jsonTile.letter);
                tile.attr('Id',jsonTile.tileNumber);
                tile.html(jsonTile.letter);
                tile.addClass('draggable');
    //		arrTileHolders[index].appendChild(tile);
                var tileHolderOffset = $(arrTileHolders[index]).offset();
                $(arrTileHolders[index]).append(tile);
                tile.offset({top: tileHolderOffset.top+3, left:tileHolderOffset.left+3});
                //initDynamicTile(tile);
                replacedTilesIndex++;
                if (replacedTilesIndex >= len)
                    break;
            } else {
                    //alert('error adding new tiles');
            }

        //	tile.addEventListener('mousedown',labelClicked);
        //	tile.addEventListener('mousemove',dragged);
        }
    }
    if (replacedTilesIndex !== len)
        alert('replacedTilesIndex: '+replacedTilesIndex+'\nreplacement tiles size:'+newTileString['replacement tiles'].hand.length);

//    if (playerName !== newTileString['current player name']){
        initDynamicTile('blank');
//    } else {
//        alert('player names do not match, cannot initalize tiles\tplayerName: '+playerName+'\tcurrent player: '+newTileString['current player']);
//    }
}
function updateBoard(jsonResponse){
        //var jsonUpdateMap = jsonResponse['update board'];
    lastBoardUpdateTiles = jsonResponse['update board'];
    clearBoard();
    for (var key in lastBoardUpdateTiles){
        var jsonTile = lastBoardUpdateTiles[key];
        var newDiv = createTileFromJson(jsonTile);
        $(newDiv).addClass('tempnondraggable');
        var keyString = '#'+key;
        var tileHolder = document.getElementById(key);//$(keyString);
        var tileHolderOffset = $(tileHolder).offset();
        var thID = $(tileHolder).attr('Id');
        var tID = $(newDiv).attr('Id');
        var numChildren = $(tileHolder).children().length;
        if(numChildren === 0 && ( tileHolder !== null || tileHolder !== 'undefined')){
//            alert('adding child '+tID + ' for key '+keyString+' and tileholder '+thID+' top position '+tileHolderOffset.top);
            $(tileHolder).append(newDiv);
            newDiv.offset({top: tileHolderOffset.top+3, left: tileHolderOffset.left+3});
            $(tileHolder).droppable('disable');
        } else {
            alert('error updating board');
        }
    }
}

function initDynamicTile(tile){
    var handTH = $('.handTileHolder');
    for (var index = 0; index < handTH.length; index++){
        var tile1 = $(handTH[index]).children();
        if (tile1.length === 1){
//            $(tile1).draggable({ revert: "invalid" });
//            $(tile1).draggable({
//                drag: function(event,ui){
//                        dragFunction(event,ui,$(this));
//                }
//            });
            addDragHandlers(tile1);
        }
    }
}

function addDragHandlers(tile){
    $(tile).draggable({ revert: "invalid" });
    $(tile).draggable({
        drag: function(event,ui){
                dragFunction(event,ui,$(this));
        }
    });

}

function dragFunction(event,ui,tile){
    clickedItem = tile;
    if(clickedItem === null) 
            alert('null item clicked');
//    $('#status').html(clickedItem.innerHTML);
    $(tile).parent().droppable('enable');
    parentTileHolder = $(tile).parent();
}

function clearBoard(){
    //playedTilesMap
    var numTilesOnBoard = playedTilesMap.size;
    var handTileHolders = $('.handTileHolder');
    var numChildrenReturned = 0;

    playedTilesMap.forEach(function(value,key){
        for(var index = 0; index < handTileHolders.length; index++){
            var numChildren = $(handTileHolders[index]).children().length;
            if (numChildren === 0){
                var keyString = '#'+key;
                var child = $(keyString).children().first();
                $(child).removeClass('nondraggable');
                $(child).removeClass('tempnondraggable');
                $(child).addClass('draggable');
                $(child).draggable('enable');
                addDragHandlers(child);
                $(handTileHolders[index]).append($(keyString).children().first());
                $(keyString).empty();
                $(keyString).droppable('enable');
                numChildrenReturned++;
                break;
            }

        };

    });
    if (numChildrenReturned !== numTilesOnBoard)
            $('#status').html('error returning unplayed tiles to hand');

    playedTilesMap.clear();
    jsonPlayedTilesMap = {};
}


function createTileFromJson(jsonTile){
    var tile = $('<div></div>');
    tile.attr('isDraggable',jsonTile.isDraggable);
    tile.attr('isPlacedOnBoard',jsonTile.isPlacedOnBoard);
    tile.attr('isInPool',jsonTile.isInPool);
    tile.attr('letter',jsonTile.letter);
    tile.attr('Id',jsonTile.tileNumber);
    tile.html(jsonTile.letter);

    return tile;
}

function removeLastBoardUpdate(){
    alert('removing last board update tiles'+JSON.stringify(lastBoardUpdateTiles));
    for (var key in lastBoardUpdateTiles){
        var keyString = '#'+key;
        $(keyString).empty();
        $(keyString).droppable('enable');
    }
}

function initBoard(){
    $('.boardTileHolder').empty();
    $('.boardTileHolder').droppable('enable');
    $('.handTileHolder').empty();
    $('.handTileHolder').droppable('enable');
}

function createNewGame(){
    var messagespace = $('#newGameStatus');
    var newPlayerName = $('#newPlayerName').val();
    var newGameName = $('#newGameName').val();
    var request = getStandardRequestJson();
    myscore = 0;
    if (newPlayerName === null || newGameName === null || newPlayerName === '' || newGameName === ''){
        $(messagespace).html('game or player must have a name');
        hideForm();
    }else{
        request['player name'] = newPlayerName;
        request['game name'] = newGameName;
        request['request type'] = 'new game';
        $(messagespace).html('new game request'+JSON.stringify(request));
        socket.send(JSON.stringify(request));
        hideForm();
    }
    initBoard();

}
function startGame(){
    var request = getStandardRequestJson();
    request['request type'] = 'start game';
    socket.send(JSON.stringify(request));
}
function endGame(){
    var request = getStandardRequestJson();
    request['request type'] = 'end game';
    socket.send(JSON.stringify(request));
};
function joinGame(){
    var messagespace = $('#newGameStatus');
    var newPlayerName = $('#newPlayerName').val();
    var newGameName = $('#newGameName').val();
    var request = getStandardRequestJson();
    if (newPlayerName === null || newGameName === null || newPlayerName === '' || newGameName === ''){
            $(messagespace).html('game or player must have a name');
    }else{
            request['player name'] = newPlayerName;
            request['game name'] = newGameName;
            request['request type'] = 'join game';
            $(messagespace).html('new game request'+JSON.stringify(request));
    }
    socket.send(JSON.stringify(request));
    hideForm();
    initBoard();
}


function submitPlay(){

    var jsonRequest = getStandardRequestJson();
    jsonRequest['request type'] = 'submit tiles';
    jsonRequest['tiles'] = jsonPlayedTilesMap;
    jsonRequest['remaining'] = getTilesRemainingOnHand();
    socket.send(JSON.stringify(jsonRequest));

}

function getTilesRemainingOnHand(){
    var handTileHolders = $('.handTileHolder');
    var remainingTiles = 0;
    for (var index = 0; index < handTileHolders.length; index++){
        remainingTiles += $(handTileHolders[index]).children().length;
    }
    return remainingTiles;
}

function returnTiles(){
    var mapReturnTiles = new Map();
    var jsonReturnTile = JSON.parse('{}');
    var arrHandTileHolders = $('.handTileHolder');
    for (var index = 0; index < arrHandTileHolders.length; index++){
        var numChild = $(arrHandTileHolders[index]).children().length;
        if(numChild === 1){
            var key = $(arrHandTileHolders[index]).attr('Id');
            var child = $(arrHandTileHolders[index]).children().first();
            var jsonChild = JSON.parse('{}');
            jsonChild.letter = $(child).attr('letter');
            jsonChild.tileNumber = $(child).attr('Id');
            jsonChild.isPlacedOnBoard = $(child).attr('isPlacedOnBoard');
            jsonChild.isInPool = $(child).attr('isInPool');
            jsonChild.isDraggable = $(child).attr('isDraggable');
            mapReturnTiles.set(key,jsonChild);
            jsonReturnTile[key] = jsonChild;
            $(arrHandTileHolders[index]).empty();
            //alert('tileholder, ID: '+$(arrHandTileHolders[index]).attr('Id')+'\n\nkey: '+key+'\n'+JSON.stringify(jsonReturnTile));
            //jsonReturnTile.arrHandTileHolders[index].id$(arrHandTileHolders[index]).attr(id) = 
        } else if (numChild > 1){
                // error
            alert('error with this tileholder, ID: '+arrHandTileHolders[index].Id);
        }

    }
//    $('#status').html('returned tiles map: '+mapToString(mapReturnTiles)+'<br><br>'+JSON.stringify(jsonReturnTile));

    var jsonRequest = getStandardRequestJson();
    jsonRequest['request type'] = 'return tiles';
    jsonRequest['tiles'] = jsonReturnTile;

    socket.send(JSON.stringify(jsonRequest));

}

function mapToString(map){
    var mapString = '{';
    map.forEach(function(value,key){
            mapString += key+'='+JSON.stringify(value)+',';
    });
    if(map.size > 0)
            mapString = mapString.substring(0,mapString.length-1)+'}';
    else
            mapString += '}';
    return mapString;	
}
/*
$('[class="handTileHolder"]').droppable({
        drop: function(event, ui){
                var parenttop = $(this).offset().top;
                var parentleft = $(this).offset().left;
                $(clickedItem).offset().top = parenttop+2;
                $(clickedItem).offset().left = parentleft+2;
                $(this).append(clickedItem);
                $(clickedItem).offset({ top: parenttop+3, left: parentleft+3 });
        }	

});
*/

function displayNewForm(){
        //alert('testing');
    $('#newgame').show();
}
function hideForm(){
    $('#playerName').val('');
    $('#gameName').val('');
    $('#newGameStatus').html('');
    $('#newgame').hide();
    //loadNewTiles('randomstring');
    $('#submittiles').attr('disabled',false);
    $('#returntiles').attr('disabled',false);
    $('#startgame').attr('disabled',false);
    $('#endgame').attr('disabled',false);

}
/*
 * 
 * the below was originally from scrabblewebsocket.js file
 */
'use strict';
var clickedItem;
var parentTileHolder;
var playedTilesMap = new Map();
var jsonPlayedTilesMap = JSON.parse('{}');
var playerName;
var gameName;
var lastBoardUpdateTiles;
var myscore = 0;
//window.onload = init;
var socket = new WebSocket("ws://your url to socket on server");

socket.onopen = function (evt){
    onOpen(evt);
};

socket.onerror = onError;

function onError(event){
    var output = document.getElementById("status");
    $(output).html('error: unable to connect to server...');
}
function onOpen(evt){
    //var output = document.getElementById("status");
    var output = $('#status');
    //alert('output: '+$(output).html());
    //output.innerHTML = "connected to socket";
    $(output).html('connected to server');
}

socket.onmessage = onMessage;

function onMessage(event) {
//    alert('message received');
    var resp = JSON.parse(event.data);
    var modeGame = 0;
    var modeChallenge = 1;
    $('#status').html(JSON.stringify(resp));
//    $('#score').html(myscore+=resp['points gained']);
    $('#message').html(resp['message']);
    if (resp.mode === modeGame){
        // mode game
        if(resp.success === true){
            // successfully processed request
            // mode: game, success: true
            for(var key in resp){
                    if(key==='points gained'){
                    $('#playScore').html(resp['points gained']);
                }
            }
            switch (resp['request type']){
                case 'new game':
                    gameName = resp['game name'];
                    playerName = resp['player name'];

                    $('#gameName').html(gameName);
                    $('#playerName').html(playerName);
                    $('#message').html(resp['message']);
                    $('#startgame').removeClass('ui-state-disabled');
                    $('#newjoingame').addClass('ui-state-disabled');

                    activatePlayFunction(false);
                    break;
                case 'join game':
                    gameName = resp['game name'];
                    playerName = resp['player name'];
                    $('#gameName').html(gameName);
                    $('#playerName').html(playerName);
                    $('#message').html(resp['message']);
                    $('#startgame').removeClass('ui-state-disabled');
                    $('#newjoingame').addClass('ui-state-disabled');
                    break;
                case 'get hand':
                    loadNewTiles(resp);
                    break;
                case 'start game':
                    $('#endgame').removeClass('ui-state-disabled');
                    $('#startgame').addClass('ui-state-disabled');
                    $('#newjoingame').addClass('ui-state-disabled');
                    $('#currentPlayer').html(resp['current player name']);
                    if(resp['current player name'] === playerName){
                        activatePlayFunction(true);
                    } else {
                        activatePlayFunction(false);
                    }
                    break;
                case 'submit tiles':

                    displayPlayedWords(resp);
                    var iterations = 0;
                    var iterationkeys = '';
                    for(var key in resp){
                        iterations++;
                        iterationkeys+=key+', ';
                        if(key==='update board'){
                            // still need to do work here
                                updateBoard(resp);
                        }
                        if(key==='current player name'){
                            if (resp[key] === playerName){
                                deactivateMapTiles(0);
                                totalMyScore(resp);                            

                            } else {
                                updateLastBoardUpdateTiles();
                            }
                        } else {

                        }
                        if(key==='player stats'){
                            displayStats(resp);
                        }

                        if (key === 'replacement tiles'){
                            loadNewTiles(resp);
                        }

                        if(key==='next player name'){
                            $('#currentPlayer').html(resp[key]);
                        }

                        if(resp['next player name'] === playerName && resp['request type'] !== 'end game'){
                            activatePlayFunction(true);
                        } else {
                            activatePlayFunction(false);
                        }
                        if(key === 'verified'){
                            if (resp[key] === true && resp['current player name'] === playerName ){
//                                totalMyScore(resp);
                            }
                        }

                        if (key === 'winner'){
                            alert('game is over... '+resp[key]+' is the winner!');
                        }
                    }
                    break;
                case 'return tiles':
                    for(var key in resp){
                        if (key === 'replacement tiles'){
                            loadNewTiles(resp);
                        }

                        if(key === 'next player name'){
                            if (resp[key] === playerName){
                                activatePlayFunction(true);
        //                        $('#currentPlayer').html(resp[key]);
                            } else {
                                alert('player names don\'t match'+resp[key]+':'+playerName);
                                activatePlayFunction(false);
                            }
        //                if(key==='next player '){
        //                    $('#currentPlayer').html(resp[key]);
        //                }
                            $('#currentPlayer').html(resp[key]);
                        }
                    }
                    break;
                case 'end game':
                    $('#endgame').addClass('ui-state-disabled');
        //            $('#startgame').removeClass('ui-state-disabled');
                    $('#newjoingame').removeClass('ui-state-disabled');
                    for(var key in resp){
                        if (key === 'winner'){
                            alert('game is over... '+resp[key]+' is the winner!');
                        }
                    }
                    activatePlayFunction(false);
                    break;
                case 'challenge play':
                    break;
                default:
            }
        } else {
            // mode: Game, success: false
            $('#status').html("failed "+resp['request type']);
            $('#message').html(resp['error']);
            $('#currentPlayer').html(resp['next player name']);
            if(resp['request type'] ==='challenge'){
                clearBoard();
            }
            if(resp['current player name'] === playerName){
                activatePlayFunction(true);
            } else {
                activatePlayFunction(false);
            }
            switch (resp['request type']){
                case 'new game':
                    break;
                case 'join game':
                    break;
                case 'get hand':
                    break;
                case 'start game':
                    break;
                case 'challenge':
            // need to return and update board.
                    if (resp['"current player name'] === playerName){
                        // return played tile to hand
                        clearBoard();
                    } else {
                        // remove tiles from updateBoard
                        clearLastBoardUpdateTilesHolders();
                    }
                    // resume play

                    if(resp['next player name'] === playerName){
                        activatePlayFunction(true);
                    } else {
                        activatePlayFunction(false);
                    }
                    break;
                case 'return tiles':
                    break;
                case 'end game':
                    break;
                case 'submit tiles':
                    if (resp['current player name']=== playerName){
                        clearBoard();
                    }
                    if (resp['next player name'] === playerName){
                        activatePlayFunction(true);
                    } else {
                        activatePlayFunction(false);
                    }
                    break;
                default:
            }
        }
    } else {
        // mode challenge
        if (resp.success === true){
            // successfully process request
            if(resp['request type']==='submit tiles'){
                displayPlayedWords(resp);
            }
            for (var key in resp){

//                if(key==='points gained'){
//                $('#playScore').html(resp['points gained']);
//                }

                if(key === 'current player'){
                    if (resp['current player'] !== playerName){
                        updateBoard(resp);
                        showChallengeForm(resp);
                    } else {
                        deactivateMapTiles(1);
                        showChallengeForm(resp);
                    }
                }
                if (key === 'verified'){
                    if (resp['verified'] === true){
                        // challenge verified
                        updateLastBoardUpdateTiles();
                        deactiveMapTiles(0);
                        totalMyScore(resp);
                    } else {
                        // challenge not verified
                        $('#message').html(resp['message']);
                    }
                }

            }
            if(resp['request type']==='challenge'){
                
            }
        } else {
            // mode: challenge, success: false
        }
    }
/*    
    for( var key in resp){
        if(key==='points gained'){
            $('#playScore').html(resp['points gained']);
        }
        if(key==='played words'){
            displayPlayedWords(resp);
        }
        if(key==='player stats'){
            displayStats(resp);
        }
        if(key==='message'){
            $('#message').html(resp['message']);
        }
    }
*/

}

function activatePlayFunction(action){
    if (action === true){
        $('#submittiles').attr('disabled',false);
        $('#returntiles').attr('disabled',false);
    } else {
        $('#submittiles').attr('disabled',true);
        $('#returntiles').attr('disabled',true);
        
    }
}

function displayStats(jObj){
    var jObjStats = jObj['player stats'];
//    var numPlayers = jObjStats.keys().length;
//    alert('trying to display stats ' + numPlayers);
    var stringStats = '';
    var playernumb = 1;
    var stringPlayerName = '';
    for(var key in jObjStats){
        stringStats+=key+' : ';
        var keyPlayerNameString = '#p'+playernumb+'name';
        var keyPlayerWordsString = '#p'+playernumb+'words';
        $(keyPlayerNameString).html(key);
        var playerwords = '';
        var totalpoints = 0;
        for (var index = 0; index < jObjStats[key].length; index++){
            var jObjWordStat = jObjStats[key][index];
            totalpoints += jObjWordStat['value'];
            playerwords += jObjWordStat['word'] + ' : '+jObjWordStat['value']+'<br>';
        }
        stringStats+= totalpoints +'<br>';
        playerwords+='<br>';
        $(keyPlayerWordsString).html(playerwords);
        playernumb++;
    }
    $('#playersStats').html(stringStats);
}

function totalMyScore(jObj){
//    alert('totaling my score wit the following: '+jObj['current player name'] + ' : '+playerName);
    if(jObj['current player name']===playerName){
        myscore += jObj['points gained'];
    } else {
//        alert('toatling my score failed with mathcing names');
    }
    $('#score').html(myscore);
}

function displayPlayedWords(jObj){
//    $('#wordsPlayed').html(resp['played words']);
    var jObjArray = jObj['played words'];
//    alert('json array object of played words: '+JSON.stringify(jObjArray));
    var listWords = '';
    for (var index = 0; index < jObjArray.length; index++){
        listWords += jObjArray[index]+'<br>';
    }
    $('#wordsPlayed').html(listWords);
}

function showChallengeForm(jObjResponse){
    var jObjArray = jObjResponse['played words'];
    var listWords = '';
    for (var index = 0; index < jObjArray.length; index++){
        listWords += jObjArray[index]+'<br>';
    }
    $('#words').html(listWords);
    $('#challengeScreen').show();
}

function challengeNo(){
    var jsonRequest = getStandardRequestJson();
    jsonRequest['challenge'] = 'no';
    jsonRequest['request type'] = 'challenge';
    sendRequest(jsonRequest);

    $('#challengeScreen').hide();
}

function challengeYes(){
    var jsonRequest = getStandardRequestJson();
    jsonRequest['challenge'] = 'yes';
    jsonRequest['request type'] = 'challenge';
    sendRequest(jsonRequest);
    $('#challengeScreen').hide();
}

function sendRequest(jsonRequest){
    socket.send(JSON.stringify(jsonRequest));
}
function showBoard(){
    $('#boardcontainer').show();
    $('#statsScreen').hide();
}
function showStats(){
    $('#boardcontainer').hide();
    $('#statsScreen').show();

}