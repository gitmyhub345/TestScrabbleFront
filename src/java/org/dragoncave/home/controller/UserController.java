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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.dragoncave.home.dao.UserDao;
import org.dragoncave.home.dao.UserPostDao;
import org.dragoncave.home.dao.UserProfileDao;
import org.dragoncave.home.models.User;
import org.dragoncave.home.models.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    
    //@Autowired
    //UserDao userDao;
    @Autowired
    HttpServletRequest req;
    
    @Autowired
    UserPostDao userPostDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    UserProfileDao userProfileDao;
    
    @Autowired 
    User theUser;
    
    @RequestMapping(value="/register", method=RequestMethod.GET)
    public ModelAndView getRegistration(@RequestParam(value="msg", required=false) String msg){
        ModelAndView model = new ModelAndView("register","command", new User());
        model.addObject("msg",msg);
        return model;
    }
    
    @RequestMapping(value="/register/add", method=RequestMethod.POST)
    public String registerUser(@ModelAttribute("DragonWorld")User u, ModelMap model,@RequestParam("retype")String retype){
        String result="";
        System.out.println("source: userController - registerUser:\nUsername:\t"+u.getUsername()+"\nPassword:\t"+u.getPassword()+"\nRetype:\t"+retype);
        if(retype.equals(u.getPassword())){
            result=userDao.registerUser(u, retype);
            if (result.contains("Unable")){
                model.addAttribute("msg",result); 
                return "redirect:/register";
            } else {
                boolean authorized = authorizeRegisteredUser(u.getUsername(),u.getPassword());
                return "redirect:/welcome?msg="+result;
            }
        } else {
            model.addAttribute("msg","please make sure passwords are the same");
            return "redirect:/register";
        }
//        model.addAttribute("prm",req.getParameterNames());
//        return "index2";
    }
    @RequestMapping(value="/posts/{userid}", method=RequestMethod.GET)
    public ModelAndView getPosts(@PathVariable Integer userid){
        ModelAndView model = new ModelAndView("posting","command",new UserPost());
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        //User u = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        /*List<UserPost> userPosts = userPostDao.getUserPost(userid);
        model.addObject("postings", userPosts);
        model.addObject("postings",userPostDao.getFullPosts());*/
        model.addObject("postings",userPostDao.getUserPost(userid));
        model.addObject("user",name);
        model.addObject("profile",userProfileDao.getProfile(name).get(0).get("avatar"));
        return model;
    }
    
    private boolean authorizeRegisteredUser(String username, String password){
        boolean result = false;
        //AuthenticationManager am = new SampleAuthenticationManager();
        try{
            User u = userDao.getUser(username);
            List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(userDao.getRoles()));
            Authentication auth = new UsernamePasswordAuthenticationToken(username,password,authorities);
        //    Authentication result = am.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
            theUser.setUsername(u.getUsername());
            theUser.setId(u.getId());
            result = true;
        } catch (AuthenticationException e){
            System.out.println(e);
        }
        return result;
    }
}
