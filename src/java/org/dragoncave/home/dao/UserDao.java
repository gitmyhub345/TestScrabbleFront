/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.dao;

/**
 *
 * @author Rider1
 */

import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.dragoncave.home.models.User;
import org.dragoncave.home.models.UserProfile;
import org.springframework.dao.DataAccessException;

@Repository
public class UserDao {
      
    private JdbcTemplate jdbcTemplate;
    private User user;
    /*
    public UserDao(){
        user.setId(0);
        user.setUsername(null);
        user.setPassword(null);
        user.setLastseen(null);
    }*/
    
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public String getAvatar(){
        if (user.getUsername() == null)
            return "";
        else {
            String sql = "Select p.avatar from AppUserProfile p join AppUser a on a.userID = p.userID where a.username = '" + user.getUsername()+"'";
            String avatar = jdbcTemplate.queryForObject(sql, String.class);
            return avatar;
        }
    }

    public User getUser(String username){
        String sql = "select userID,userName,password,last_seen,enabled from AppUser where userName = '"+ username+"'";
        System.out.println("UserDao: getUser()->"+sql);
        User u = new User();
        this.user = jdbcTemplate.query(sql, (ResultSet rs) -> {
            User aUser = new User();
            if (rs.first()){
                System.out.println("userID: "+rs.getInt("userID")+"\nuserName: "+rs.getString("userName")+"\npassword: "+rs.getString("password")+"\nenabled: "+rs.getBoolean("enabled"));
            aUser.setId(rs.getInt("userID"));
            aUser.setUsername(rs.getString("userName"));
            aUser.setPassword(rs.getString("password"));
        //    aUser.setLastseen(rs.getTimestamp("last_seen"));
            aUser.setEnabled(rs.getBoolean("enabled"));
        //    aUser.setRole(rs.getString("role"));
            }else {
                System.out.println("resultset was null? "+rs.wasNull());
            }
            return aUser;
        });
        return user;
//        return u;
        
    }
    
    public User getUser(){
        return user;
    }
    
    @Transactional
    public String registerUser(User u,String p){
        String result="";
        boolean err = false;
        if(u.getPassword().equals(p)){
            try{
                String sql = "insert into AppUser(username,password,enabled) values ('"+u.getUsername()+"','"+u.getPassword()+"',true)";
                System.out.println("source: userDao - registerUser\n"+sql);
                jdbcTemplate.update(sql);
                result="you are now registered";
                err = true;
            } catch (DataAccessException e) {
                System.out.println("source: UserDao - registerUser:\n"+e.getLocalizedMessage()+"\n---------- end of message ------------");
                System.out.println("shortened message:\t"+e.getLocalizedMessage().substring(e.getLocalizedMessage().lastIndexOf(":")+2,e.getLocalizedMessage().lastIndexOf("for")-1));
                result="Unable to register your account. This user already exists";
            }
            if(err){
                try{
                    String sql = "insert into AppFollowers (followedID,followerID) select userID, userID from AppUser where username = '"+u.getUsername()+"'";
                    jdbcTemplate.update(sql);
                } catch (DataAccessException e) {
                    result = "unable to create followers";
                    err = false;
                }
            }
            if(err){
                try{
                    String sql = "insert into AppUserRole select userID,2 from AppUser where username = '"+u.getUsername()+"'";
                    jdbcTemplate.update(sql);
                } catch (DataAccessException e) {
                    result = "unable to add roles";
                    err = false;
                }
            }
        } else {
            result="please make sure passwords are the same";
        }
        return result;
    }
    
    public String getRoles(){
        String sql = "select ar.roleName from AppUser au join AppUserRole aur on au.userID = aur.userID join AppRole ar on aur.roleID = ar.roleID where au.username = '"+user.getUsername()+"'";
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list.get(0);
    }

    public String updatePassword(User u, String oldPassword, String newPassword){
        String result = "";
        String sql = "update AppUser set password = '"+newPassword+"' where username = '"+u.getUsername()+"'";
        if (u.getPassword().equals(oldPassword) ){
            try{
                jdbcTemplate.update(sql);
                result = "password updated";
            } catch (DataAccessException e){
                result = "unable to change password\n"+e.getLocalizedMessage();
            }
        } else {
            result = "your old password did not match our record.  Please ensure you are authorized by providing the correct password.";
        }
        return result;
    }

    public UserProfile getUserProfile(User u){
        
        String sql = "select up.* from AppUserProfile up join AppUser u on u.id = up.user_id where u.username = '"+u.getUsername()+"'";
        UserProfile profile = jdbcTemplate.query(sql, (ResultSet rs)-> {
            UserProfile aProfile = new UserProfile();
            if (rs.first()){
                aProfile.setProfileID(rs.getInt("id"));
                aProfile.setNickname(rs.getString("nickname"));
                aProfile.setAvatar(rs.getString("avatar"));
                aProfile.setUserID(rs.getInt("userID"));
                aProfile.setAboutMe(rs.getString("about_me"));
            }
            return aProfile;

        });
        return profile;
    }
}
