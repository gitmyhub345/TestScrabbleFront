/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.models;

/**
 *
 * @author Rider1
 */
import java.sql.Timestamp;

public class UserPost {
    private int userID;
    private int postID;
    private String postBody;
    private String language;
    private Timestamp timeStamp;
    private boolean isprivate;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getPost() {
        return postBody;
    }

    public void setPost(String post) {
        this.postBody = post;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public void setIsPrivate(boolean isprivate){
        this.isprivate = isprivate;
    }
    
    public boolean getIsPrivate(){
        return isprivate;
    }
    
}
