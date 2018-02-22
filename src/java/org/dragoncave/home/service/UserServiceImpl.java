/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.dragoncave.home.dao.UserDao; 
import org.dragoncave.home.models.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
/**
 *
 * @author Rider1
 */
@Service
public class UserServiceImpl implements  UserDetailsService {
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    User theUser;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        User u = userDao.getUser(string);
        theUser.setUsername(u.getUsername());
        theUser.setId(u.getId());
//        System.out.println("\tUserServiceImpl: loadUserByUsername(string) ->\n\t\ttheUser.username: "+theUser.getUsername()+"\n\t\ttheUser.Id: "+theUser.getId());
        if(u.getId()==0){
            throw new UsernameNotFoundException("Oops!");
        }
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(userDao.getRoles()));

        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), authorities);
        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getAvatar(){
        return userDao.getAvatar();
    }
    
}
