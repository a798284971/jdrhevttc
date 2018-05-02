package com.jdr.interview.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "user_teststatus")
public class UserTeststatus implements Serializable {
    @Id
    private Integer uid;

    @Id
    @Column(name = "exer_id")
    private Integer exerId;

    @Column(name = "study_num")
    private Integer studyNum;

    @Column(name = "right_rate")
    private Double rightRate;

    private static final long serialVersionUID = 1L;

    /**
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return exer_id
     */
    public Integer getExerId() {
        return exerId;
    }

    /**
     * @param exerId
     */
    public void setExerId(Integer exerId) {
        this.exerId = exerId;
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
     * @return right_rate
     */
    public Double getRightRate() {
        return rightRate;
    }

    /**
     * @param rightRate
     */
    public void setRightRate(Double rightRate) {
        this.rightRate = rightRate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uid=").append(uid);
        sb.append(", exerId=").append(exerId);
        sb.append(", studyNum=").append(studyNum);
        sb.append(", rightRate=").append(rightRate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}