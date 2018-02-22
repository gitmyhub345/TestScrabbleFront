/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.controller;

/**
 *
 * @author Rider1
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {
    
    @RequestMapping(value="/hello", method=RequestMethod.GET)
    public String sayHello(ModelMap model) {
        model.addAttribute("greeting", "Hello world");
        return "welcome";
    }
    
    @RequestMapping(value="/helloagain", method=RequestMethod.GET)
    public String sayHelloAgain(ModelMap model){
        model.addAttribute("greeting","Hello again");
        return "welcome";
    }
    /*
    @RequestMapping(value="/welcome", method=RequestMethod.GET)
    public String getWelcome(){
        return "welcome";
    
    }*/
    @RequestMapping(value="/game/Scrabble", method=RequestMethod.GET)
    public String getScrabbleGame(){
        return "scrabble";
    }

}
