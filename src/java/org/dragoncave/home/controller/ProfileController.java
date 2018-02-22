/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dragoncave.home.dao.UserProfileDao;
import org.dragoncave.home.models.User;
import org.dragoncave.home.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Rider1
 */

@Controller
public class ProfileController {
    @Autowired
    UserProfileDao upDao;
    
    @RequestMapping(value="/profile", method = RequestMethod.GET)
    public ModelAndView profile(){
        //String uname = getUname();
        List<Map<String,Object>> list = upDao.getProfile(getUname());
        UserProfile up = new UserProfile();
        if(!list.isEmpty()){
            Map<String,Object> map = list.get(0);
            up.setAboutMe((String)map.get("aboutMe"));
            up.setNickname((String)map.get("nickname"));
            up.setAvatar((String)map.get("avatar"));
            up.setProfileID((Integer)map.get("profileID"));
        }
//        ModelAndView model = new ModelAndView("profile","command",new UserProfile());
        ModelAndView model = new ModelAndView("profile","command",up);
//        model.addObject("ava", up.getAvatar());
        return model; 
    }
    /**
     * create profile for logged in user
     * @param up
     * @param model
     * @param file
     * @return 
     */
    @RequestMapping(value="/profile/create", method = RequestMethod.POST)
    public String createProfile(@ModelAttribute("DragonWorld") UserProfile up, ModelMap model,
            @RequestParam("avatarupload") MultipartFile file){
        String uname = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            upDao.createProfile(up, uname, file);
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "redirect:/profile";
    }
    /**
     * update profile for logged in user
     */
    @RequestMapping(value="/profile/update", method=RequestMethod.POST)
    public String updateProfile(@ModelAttribute("DragonWorld") UserProfile up, ModelMap model,
            @RequestParam("avatarupload") MultipartFile file){
        try{
            String result = upDao.updateProfile(up, getUname(), file);
            System.out.println("source: ProfileController - updateProfile:\n"+getUname()+"\nresult:\t"+result);
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/profile";
    }
    
    /**
     * disable profile for logged in user
     */
    @RequestMapping(value="/profile/delete", method=RequestMethod.POST)
    public String disableProfile(@ModelAttribute("DragonWorld") UserProfile up, ModelMap model,
            @RequestParam("avatarupload") MultipartFile file){
        return "redirect:/profile";
    }
    
    private String getUname(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
