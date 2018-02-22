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

public class User {
    private int id;
    private String username;
    private String password;
    private Timestamp createDate;
    private Timestamp lastseen;
    private String role;
    private boolean enabled;
    private boolean isLocked;
    private int failedAttempts;

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
    
    public User(){
        this.id = 0;
        this.username = null;
        this.password = null;
        this.role = null;
        this.enabled = false;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastseen() {
        return lastseen;
    }

    public void setLastseen(Timestamp lastseen) {
        this.lastseen = lastseen;
    }
}
