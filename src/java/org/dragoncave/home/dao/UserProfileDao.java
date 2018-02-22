/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.dao;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.dragoncave.home.models.UserProfile;
import org.dragoncave.home.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.FileCopyUtils;
/**
 *
 * @author Rider1
 */
@Repository
public class UserProfileDao {
    private MultipartFile mFile;
    private JdbcTemplate jdbcTemplate;
    private static final String UPLOAD_LOCATION = "C:/Users/Rider1/Documents/NetBeansProjects/DragonWorld/web/resources/images/profile/";
    @Autowired
    UserDao uDao;
    
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Map<String,Object>> getProfileByNickname(String nickname){
        String sql = "select * from AppUserProfile where nickname like '%"+nickname+"%'";
//        System.out.println(sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    public List<Map<String,Object>> getProfile(String username){
    //public String getProfile(String username){
        String sql = "select aup.* from AppUser au join AppUserProfile aup on au.userID = aup.userID where au.username = '"+username+"'";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
        //return sql;
    }
    @Transactional
    public String createProfile(UserProfile up,String uname,MultipartFile file) throws IOException{
//    public String createProfile(UserProfile up,String uname) {
        String result = "";
        User u = uDao.getUser(uname);
        String sql = "insert into AppUserProfile (nickname,avatar,userID,aboutMe) values('"+up.getNickname()+"','"+file.getOriginalFilename()+"',"+u.getId()+",'"+up.getAboutMe()+"')";
        //String sql = "insert into AppUserProfile (nickname,user_id,about_me) values('"+up.getNickname()+"',"+u.getId()+",'"+up.getAboutMe()+"')";
        jdbcTemplate.update(sql);
        FileCopyUtils.copy(file.getBytes(), new File(UPLOAD_LOCATION+file.getOriginalFilename()));
        return result;
    }
    @Transactional
    public String updateProfile(UserProfile up, String username,MultipartFile file) throws IOException {
        String result = "Cannot find profile";
        String sql = "";
        UserProfile p = getUserProfile(username);
        
        if (p != null){
            try {
                String fName = up.getAvatar();
                if(!file.isEmpty()){  // new avatar image uploaded, set avatar to new filename after saving file
                    FileCopyUtils.copy(file.getBytes(), new File(UPLOAD_LOCATION+file.getOriginalFilename()));
                    up.setAvatar(file.getOriginalFilename());
                    sql = "update AppUserProfile aup join AppUser au on au.userID = aup.userID set aboutMe = '"+up.getAboutMe()+"', avatar = '"+up.getAvatar()+"', nickname = '"+up.getNickname()+"' where au.username = '"+username+"'";
                    System.out.println("source: UserProfileDao - updateProfile - file clause\n"+sql);
                } else {
                    sql = "update AppUserProfile aup join AppUser au on au.userID = aup.userID set aboutMe = '"+up.getAboutMe()+"', nickname = '"+up.getNickname()+"' where au.username = '"+username+"'";
                    System.out.println("source: UserProfileDao - updateProfile - else clause\n"+sql);
                }
                jdbcTemplate.update(sql);
                result = "profile updated.";
            } catch (DataAccessException e){
                result = "An internal error occurred, cannot update profile";
            }
        } 
        return result;

    }
    
    private UserProfile getUserProfile(String username){
        String sql = "select up.* from AppUserProfile up join AppUser u on u.userID = up.userID where u.username = '"+username+"'";
        try {
            UserProfile profile = jdbcTemplate.query(sql, (ResultSet rs)-> {
                UserProfile aProfile = new UserProfile();
                if (rs.first()){
                    aProfile.setProfileID(rs.getInt("profileID"));
                    aProfile.setNickname(rs.getString("nickname"));
                    aProfile.setAvatar(rs.getString("avatar"));
                    aProfile.setUserID(rs.getInt("userID"));
                    aProfile.setAboutMe(rs.getString("aboutMe"));
                }
                return aProfile;
            });
            return profile;
        } catch (DataAccessException e){
            UserProfile nullprofile = null;
            return nullprofile;
        }
    }

}
