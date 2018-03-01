/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.dao;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.dragoncave.home.models.User;
import org.dragoncave.home.models.UserPost;
import org.dragoncave.home.configuration.DCDataSourceConfig;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
/**
 *
 * @author Rider1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DCDataSourceConfig.class})
//@ContextConfiguration(classes={IContextConfiguration.class})
@WebAppConfiguration
public class UserDaoTest {


//    @Autowired 
//    DataSource dataSource;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    UserPostDao upDao;
       
    public UserDaoTest() {
    }



    /**
     * Test of getUser method, of class UserDao.
     */
    @Test
    public void testGetUser_String() {
        System.out.println("getUser");
        String username = "Test2@gmail";
        UserDao instance = userDao;
        User expResult = new User();
        expResult.setId(1);
        expResult.setUsername(username);
        expResult.setPassword("test2");
        expResult.setEnabled(true);


        User result = instance.getUser(username);
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getUsername(),result.getUsername());
        assertEquals(expResult.getPassword(), result.getPassword());
        assertEquals(expResult.getEnabled(),result.getEnabled());
        // TODO review the generated test code and remove the default call to fail.
        
        assert(instance.getAvatar() != null);
            System.out.println(instance.getAvatar());
            
        UserPostDao upInstance = upDao;
        UserPost expUpResults = new UserPost();
        
        List<Map<String,Object>> results =  upDao.getUserPost(1);
        System.out.println(results.size());
        for (int index = 0; index < results.size(); index++){
            System.out.println(results.get(index));
        }
    }

    
}
