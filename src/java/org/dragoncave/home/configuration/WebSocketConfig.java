/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.configuration;

import org.dragoncave.home.controller.ScrabbleSocket;
import org.dragoncave.home.service.ScrabbleGameHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 *
 * @author Rider1
 */

@Configuration
@EnableWebSocket
class WebSocketConfig implements WebSocketConfigurer{
    
    @Bean(name="gameHandler")
    public ScrabbleGameHandler gameHandler (){
        ScrabbleGameHandler handler = new ScrabbleGameHandler();
        return handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(myScrabbleSocket(), "/scrabble/game/req");
    }
    
    @Bean
    public ScrabbleSocket myScrabbleSocket(){
        return new ScrabbleSocket();
    }

}
