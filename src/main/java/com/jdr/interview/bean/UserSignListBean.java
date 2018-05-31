package com.jdr.interview.bean;

import java.util.List;

public class UserSignListBean {
	private int uid;
	private String content;
	private String createTime;
	private List<UserTalkBean> talkList;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public List<UserTalkBean> getTalkList() {
		return talkList;
	}
	public void setTalkList(List<UserTalkBean> talkList) {
		this.talkList = talkList;
	}
	
}
