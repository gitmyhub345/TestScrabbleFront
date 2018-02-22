/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.controller;

import java.util.List;
import java.util.Map;
import org.dragoncave.home.dao.UserDao;
import org.dragoncave.home.dao.UserPostDao;
import org.dragoncave.home.models.User;
import org.dragoncave.home.models.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Rider1
 */
@Controller
public class PostController {
    @Autowired
    UserPostDao upDao;
    
    @Autowired
    UserDao uDao;
    
    @Autowired
    User theUser;
    
    @RequestMapping(value="/posts")
    public ModelAndView getMyPosts(@RequestParam(value="msg" ,required=false)String message){
        ModelAndView model = new ModelAndView("post","command",new UserPost());
        String uname = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("source: PostController - getMyPost:\n"+theUser.getUsername());
        int uID = uDao.getUser(uname).getId();
        System.out.println("source: PostController - getMyPost()->uID: "+uID);
        List<Map<String,Object>> list = upDao.getUserPost(uID);
        model.addObject("posts",list);
        model.addObject("authNo",theUser.getId());
        model.addObject("msg",message);
        return model;
    }
    
    @RequestMapping(value="/posts/add")
    public String addPost(@ModelAttribute("DragonWorld")UserPost up, ModelMap model){
    //    System.out.println("source: PostController:\n"+up.getPost()+"\n"+up.getIsPrivate()+"\n"+up.getLanguage());
        boolean added = upDao.addPost(up,getUName());
        return "redirect:/posts";
    }
    
    @RequestMapping(value="/posts/edit/{postid}",method=RequestMethod.POST)
    public String editPost(@PathVariable Integer postid, @ModelAttribute("DragonWorld") UserPost p, ModelMap model){
        boolean result = upDao.editPost(p, postid);
        String msg = "";
        if(result)
            msg = "your post has been sucessfully updated. "+postid;
        else
            msg= "unable to update your post. "+postid;
        return "redirect:/posts?msg="+msg;
    }
    private String getUName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
