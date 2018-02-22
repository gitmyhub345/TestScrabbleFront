/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';
var clickedItem;
var parentTileHolder;
var playedTilesMap = new Map();
var jsonPlayedTilesMap = JSON.parse('{}');
var playerName;
var gameName;
var lastBoardUpdateTiles;
//window.onload = init;
var socket = new WebSocket("ws://192.168.0.9:8084/DragonWorld/scrabble/game/req");

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
    var resp = JSON.parse(event.data);
    $('#status').html(resp['request type']);
    if (resp.success === true) {
        $('#status').html(JSON.stringify(resp));

        if (resp['request type'] === 'new game'){
            gameName = resp['game name'];
            playerName = resp['player name'];
            
            $('#gameName').html(gameName);
            $('#playerName').html(playerName);
            $('#message').html(resp['message']);
            $('#startgame').removeClass('ui-state-disabled');
            $('#newjoingame').addClass('ui-state-disabled');

        }

        if (resp['request type'] === 'start game'){
            $('#endgame').removeClass('ui-state-disabled');
            $('#startgame').addClass('ui-state-disabled');
            $('#newjoingame').addClas('ui-state-disabled');
        }
        
        if (resp['request type'] === 'end game'){
            $('#endgame').addClass('ui-state-disabled');
//            $('#startgame').removeClass('ui-state-disabled');
            $('#newjoingame').removeClass('ui-state-disabled');
        }

        if (resp['request type']=== 'submit tiles'){
            
            for(var key in resp){
                if (key === 'replacement tiles'){
                    loadNewTiles(resp);
                }
            }
            deactivateMapTiles();
        }

        if (resp['request type']=== 'get hand'){
            
            for(var key in resp){
                if (key === 'replacement tiles'){
                    loadNewTiles(resp);
                }
            }
        }

        if (resp['request type']=== 'return tiles'){
            
            for(var key in resp){
                if (key === 'replacement tiles'){
                    loadNewTiles(resp);
                }
            }
        }

        if (resp['request type']=== 'join game'){
            gameName = resp['game name'];
            playerName = resp['player name'];
            $('#gameName').html(gameName);
            $('#playerName').html(playerName);
            $('#message').html(resp['message']);
            $('#startgame').removeClass('ui-state-disabled');
            $('#newjoingame').addClass('ui-state-disabled');
        }

        if(resp['current player name'] === playerName){
            activatePlayFunction(true);
        } else {
            activatePlayFunction(false);
        }

    }

    if (resp.success === false) {
        $('#status').html("failed "+resp['request type'] +"\n"+resp['message']);
        if(resp['request type'] ==='submit tiles'){
            clearBoard();
        }
    }

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
/*
function addDevice(name, type, description) {
    var DeviceAction = {
        action: "add",
        name: name,
        type: type,
        description: description
    };
    socket.send(JSON.stringify(DeviceAction));
}

function removeDevice(element) {
    var id = element;
    var DeviceAction = {
        action: "remove",
        id: id
    };
    socket.send(JSON.stringify(DeviceAction));
}

function toggleDevice(element) {
    alert("toggle device");
    var id = element;
    var DeviceAction = {
        action: "toggle",
        id: id
    };
    socket.send(JSON.stringify(DeviceAction));
}

function printDeviceElement(device) {
    var content = document.getElementById("content");
    
    var deviceDiv = document.createElement("div");
    deviceDiv.setAttribute("id", device.id);
    deviceDiv.setAttribute("class", "device " + device.type);
    content.appendChild(deviceDiv);

    var deviceName = document.createElement("span");
    deviceName.setAttribute("class", "deviceName");
    deviceName.innerHTML = device.name;
    deviceDiv.appendChild(deviceName);

    var deviceType = document.createElement("span");
    deviceType.innerHTML = "<b>Type:</b> " + device.type;
    deviceDiv.appendChild(deviceType);

    var deviceStatus = document.createElement("span");
    if (device.status === "On") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn off</a>)";
    } else if (device.status === "Off") {
        deviceStatus.innerHTML = "<b>Status:</b> " + device.status + " (<a href=\"#\" OnClick=toggleDevice(" + device.id + ")>Turn on</a>)";
        //deviceDiv.setAttribute("class", "device off");
    }
    deviceDiv.appendChild(deviceStatus);

    var deviceDescription = document.createElement("span");
    deviceDescription.innerHTML = "<b>Comments:</b> " + device.description;
    deviceDiv.appendChild(deviceDescription);

    var removeDevice = document.createElement("span");
    removeDevice.setAttribute("class", "removeDevice");
    removeDevice.innerHTML = "<a href=\"#\" OnClick=removeDevice(" + device.id + ")>Remove device</a>";
    deviceDiv.appendChild(removeDevice);
}

function showForm() {
    document.getElementById("addDeviceForm").style.display = '';
}

function hideForm() {
    document.getElementById("addDeviceForm").style.display = "none";
}

function formSubmit() {
    var form = document.getElementById("addDeviceForm");
    var name = form.elements["device_name"].value;
    var type = form.elements["device_type"].value;
    var description = form.elements["device_description"].value;
    hideForm();
    document.getElementById("addDeviceForm").reset();
    addDevice(name, type, description);
}*/
/*
function init() {
    hideForm();
}
*/