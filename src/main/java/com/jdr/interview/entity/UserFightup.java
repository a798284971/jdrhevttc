package com.jdr.interview.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "user_fightup")
public class UserFightup implements Serializable {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "talk_id")
    private Integer talkId;

    @Column(name = "create_time")
    private String createTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
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
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", talkId=").append(talkId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}