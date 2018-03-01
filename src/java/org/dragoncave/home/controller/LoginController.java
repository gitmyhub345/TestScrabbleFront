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

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dragoncave.home.dao.UserPostDao;


import org.springframework.context.annotation.Bean;

@Controller
public class LoginController {
    @Autowired
    UserPostDao upDao;
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String home(){
//        return new ModelAndView("welcome");
        return "redirect:/welcome";
    }
    /**    
    @RequestMapping(value="/index", method=RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("index");
    }
    */
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(HttpServletRequest req, HttpServletResponse res ){ 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(req, res, auth);
        }
        return "redirect:/welcome?logout";
    }

    /*@RequestMapping(value="/login", method=RequestMethod.GET)
    public ModelAndView Login(){
        ModelAndView model = new ModelAndView("Login");
        model.addObject("title", "Admministrator Control Panel");
        model.addObject("message", "This page demonstrates how to use Spring security.");
         
        return model;
    }
    */
    
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "logout", required = false) String logout,
        @RequestParam(value = "msg", required = false) String msg) {
        List<Map<String,Object>> list = upDao.getPublicPosts();
        ModelAndView model = new ModelAndView("welcome");
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
//        if (msg != null)
        model.addObject("posts",list);
        return model;
    }
}