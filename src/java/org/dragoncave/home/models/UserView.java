package org.dragoncave.home.models;

public class UserView {
	
	private String username;
	private String password;
	private String avatar;
	private String aboutMe;
	private String post;

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return avatar;
	}
	public void setAboutMe(String aboutMe){
		this.aboutMe = aboutMe;
	}

	public String getAboutMe(){
		return aboutMe;
	}

	public void setPost(String post){
		this.post = post;
	}

	public String getPost() {
		return post;
	}
}