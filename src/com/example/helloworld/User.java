package com.example.helloworld;

public class User {
	String id;
	String account;
	String passwordHash;
	String name;
	String avatar;
	String email;

	
	public String getAccount() {
		return account;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswordHash() {
		return passwordHash;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
