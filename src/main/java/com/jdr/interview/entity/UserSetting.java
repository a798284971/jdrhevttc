package com.jdr.interview.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "user_setting")
public class UserSetting implements Serializable {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "exam_num")
    private Integer examNum;

    @Column(name = "exam_type")
    private String examType;

    private static final long serialVersionUID = 1L;

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
     * @return exam_num
     */
    public Integer getExamNum() {
        return examNum;
    }

    /**
     * @param examNum
     */
    public void setExamNum(Integer examNum) {
        this.examNum = examNum;
    }

    /**
     * @return exam_type
     */
    public String getExamType() {
        return examType;
    }

    /**
     * @param examType
     */
    public void setExamType(String examType) {
        this.examType = examType == null ? null : examType.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", examNum=").append(examNum);
        sb.append(", examType=").append(examType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}