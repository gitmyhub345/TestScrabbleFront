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
import org.dragoncave.home.dao.UserDao;
import org.dragoncave.home.dao.UserPostDao;
import org.dragoncave.home.dao.UserProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestPostController {
    
    @Autowired
    UserDao uDao;
    
    @Autowired
    UserPostDao upDao;
    
    @Autowired
    UserProfileDao uProfDao;
    
    @RequestMapping(value={"posts/ajax/api/getUsers/{uname}","posts/ajax/api/getUsers"})
    public List<Map<String,Object>> getUser(@PathVariable(required=false) String uname){
//        System.out.println("source RestPostController - getUser():\n"+uname);
//        User up = uDao.getUser("lan@yahoo");
//        List<Map<String,Object>> list = upDao.getAllPost();
        List<Map<String,Object>> list = uProfDao.getProfileByNickname(uname);
        return list;
    }
    
    @RequestMapping(value="posts/ajax/api/delete/{postid}")
    public String deletePost(@PathVariable(required=true) Integer postid){
        boolean result = upDao.deletePost(postid);
        return "your post has been deleted: ";
        
    }
    
    @RequestMapping(value="posts/ajax/api/edit/{postid}")
    public String editPost(@PathVariable(required=true) Integer postid){
        return "post "+postid.toString()+" has been updated";
        
    }   

    @RequestMapping(value="posts/ajax/api/getUserPosts/{userid}")
    public List<Map<String,Object>> getUserPosts(@PathVariable Integer userid){
        List<Map<String,Object>> list = upDao.getUserPost(userid);
//        System.out.println("user post userid: "+userid);
        return list;
    }
    
}
