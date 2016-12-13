package com.example.helloworld;

import java.util.Date;



public class Article {
	Integer id;
	
	User author;
	Date createDate;
	Date editDate;

	String title;
	String text;
	String getAuthorName;
	String getAuthorAvatar;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getGetAuthorName() {
		return getAuthorName;
	}

	public void setGetAuthorName(String getAuthorName) {
		this.getAuthorName = getAuthorName;
	}

	public String getGetAuthorAvatar() {
		return getAuthorAvatar;
	}

	public void setGetAuthorAvatar(String getAuthorAvatar) {
		this.getAuthorAvatar = getAuthorAvatar;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	void onPreUpdate(){
		editDate = new Date();
	}
	
	
	void onPrePersist(){
		createDate = new Date();
		editDate = new Date();
	}
}