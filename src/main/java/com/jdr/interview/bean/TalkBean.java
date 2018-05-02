package com.jdr.interview.bean;

public class TalkBean {
	private Integer talkId;
	private String nickName;
	private String headImg;
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	private String talkText;
	private String createTime;
	private Integer status;
	public Integer getTalkId() {
		return talkId;
	}
	public void setTalkId(Integer talkId) {
		this.talkId = talkId;
	}
	public String getTalkText() {
		return talkText;
	}
	public void setTalkText(String talkText) {
		this.talkText = talkText;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
