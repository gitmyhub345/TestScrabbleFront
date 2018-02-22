/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.dragoncave.home.configuration.DCDataSourceConfig;
import org.dragoncave.home.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.dragoncave.home.models.UserPost;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;


@Repository
public class UserPostDao {
    
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDao uDao;
    
    @Autowired
    User theUser;
    
    @Autowired
    public void setDataSource(DataSource dataSource){
        if (dataSource != null){
            this.jdbcTemplate = new JdbcTemplate(dataSource);
            System.out.println("dataSource found");
//            try {
//                dataSource.getConnection();
//            } catch (SQLException ex) {
//                Logger.getLogger(DCDataSourceConfig.class.getName()).log(Level.SEVERE, null, ex);
//            }

        } else
            System.out.println("no dataSource");
    }

    @Transactional
    public boolean deletePost(int id){
        boolean result = false;
        try{
            String sql = "update UserPost set isActive = false where postid = "+id;
            jdbcTemplate.update(sql);
            result = true;
        }catch(DataAccessException e){
            System.out.println("Source: UserPostDao - deletePost:\nDataAccessException");
        }
        return result;
    }
    @Transactional
    public boolean addPost(UserPost up, String uName){
        boolean result = false;
        try{
//            uDao = new UserDao();
            int uID = uDao.getUser(uName).getId();
//            System.out.println("source: UserPostDao - addPost: String replace ' with \'"+up.getPost().replace("'", "\\'"));
            String sql = String.format("insert into UserPost(postBody,userID,language,isprivate)values('%s',%s,'%s',%s)",up.getPost().replace("'", "\\'"),uID,up.getLanguage(),up.getIsPrivate());
//            System.out.println("source: UserPostDao - addPost:\n"+sql);
            jdbcTemplate.update(sql);
            result = true;
        }catch(DataAccessException e){
            System.out.println(e);
        }
        return result;
    }
    
    @Transactional
    public boolean editPost(UserPost up, int postid){
        boolean result = false;
        int uID = theUser.getId();
        String sql = "update UserPost set postBody = '"+up.getPost()+"', language = '"+up.getLanguage()+"', isPrivate = "+up.getIsPrivate()+ " where postid = "+postid+" and userID = " + uID ;
        try{
            jdbcTemplate.update(sql);
            result = true;
        } catch (DataAccessException e){
            System.out.println("source: UserPostDao - editPost:\n"+"uID:\t"+theUser.getId()+"\n"+sql);
        }
        return result;
    }
    
    public List<Map<String,Object>> getUserPost(int id){
        String sql = "select p.userID as u, p.postID, up.nickname, up.avatar, p.postBody, language from AppUserProfile up join UserPost p on up.userID = p.userID where up.userID = "+ id;
        //System.out.println("source UserPostDao - getUserPost(int id)\n"+sql);        
        //List<UserPost> userPost = getPosts(sql);
        List<Map<String,Object>> userPost = getFullPosts(sql);
        return userPost;
        
    }

    public List<Map<String,Object>> getPublicPosts(){
        String sql = "select p.userID as u, p.postID, up.nickname, up.avatar, p.postBody, language from AppUserProfile up join UserPost p on up.userID = p.userID where p.isprivate = 0";
        List<Map<String,Object>> list = getFullPosts(sql);
        return list;
    }
    
    
    public List<Map<String,Object>> getAllPost(){
        String sql = "select p.userID as u, p.postID, up.nickname, up.avatar, p.postBody, language from AppUserProfile up join UserPost p on up.userID = p.userID";
        //List<UserPost> userPost = getPosts(sql);
        List<Map<String,Object>> userPost = getFullPosts(sql);
        return userPost;
    }
    
    private List<UserPost> getPosts(String sql){
        List<UserPost> userPost;
        userPost = jdbcTemplate.query(sql, new RowMapper<UserPost>(){
            @Override
            public UserPost mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserPost aUser = new UserPost();
                aUser.setPostID(rs.getInt("postID"));
                aUser.setLanguage(rs.getString("language"));
                aUser.setPost(rs.getString("postBody"));
                aUser.setTimeStamp(rs.getTimestamp("timestamp"));
                aUser.setUserID(rs.getInt("userID"));
                return aUser;
            }
        });
        return userPost;
    }
    
    private List<Map<String,Object>> getFullPosts(String sql){
        //String sql = "select up.nickname, up.avatar, p.postBody, language from AppUserProfile up join UserPost p on up.userID = p.userID";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
}
