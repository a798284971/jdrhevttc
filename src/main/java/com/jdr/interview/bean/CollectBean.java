package com.jdr.interview.bean;

public class CollectBean {
	private int id;
	private String createTime;
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private Object question ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Object getQuestion() {
		return question;
	}
	public void setQuestion(Object question) {
		this.question = question;
	}
	
}
