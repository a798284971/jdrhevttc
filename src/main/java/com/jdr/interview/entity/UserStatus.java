package com.jdr.interview.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "user_status")
public class UserStatus implements Serializable {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "question_num")
    private Integer questionNum;

    @Column(name = "sign_num")
    private Integer signNum;

    @Column(name = "study_num")
    private Integer studyNum;

    @Column(name = "time_stamp")
    private String timeStamp;

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
     * @return question_num
     */
    public Integer getQuestionNum() {
        return questionNum;
    }

    /**
     * @param questionNum
     */
    public void setQuestionNum(Integer questionNum) {
        this.questionNum = questionNum;
    }

    /**
     * @return sign_num
     */
    public Integer getSignNum() {
        return signNum;
    }

    /**
     * @param signNum
     */
    public void setSignNum(Integer signNum) {
        this.signNum = signNum;
    }

    /**
     * @return study_num
     */
    public Integer getStudyNum() {
        return studyNum;
    }

    /**
     * @param studyNum
     */
    public void setStudyNum(Integer studyNum) {
        this.studyNum = studyNum;
    }

    /**
     * @return time_stamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp == null ? null : timeStamp.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", questionNum=").append(questionNum);
        sb.append(", signNum=").append(signNum);
        sb.append(", studyNum=").append(studyNum);
        sb.append(", timeStamp=").append(timeStamp);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}