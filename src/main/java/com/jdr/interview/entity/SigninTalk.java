package com.jdr.interview.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "signin_talk")
public class SigninTalk implements Serializable {
    @Id
    @Column(name = "talk_id")
    private Integer talkId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "talk_text")
    private String talkText;

    @Column(name = "create_time")
    private String createTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return talk_id
     */
    public Integer getTalkId() {
        return talkId;
    }

    /**
     * @param talkId
     */
    public void setTalkId(Integer talkId) {
        this.talkId = talkId;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return talk_text
     */
    public String getTalkText() {
        return talkText;
    }

    /**
     * @param talkText
     */
    public void setTalkText(String talkText) {
        this.talkText = talkText == null ? null : talkText.trim();
    }

    /**
     * @return create_time
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", talkId=").append(talkId);
        sb.append(", userId=").append(userId);
        sb.append(", talkText=").append(talkText);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}