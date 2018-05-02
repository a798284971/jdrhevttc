package com.jdr.interview.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "correct_rate")
public class CorrectRate implements Serializable {
    @Id
    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "all_num")
    private Integer allNum;

    private Integer wrong;

    private static final long serialVersionUID = 1L;

    /**
     * @return question_id
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * @return all_num
     */
    public Integer getAllNum() {
        return allNum;
    }

    /**
     * @param allNum
     */
    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    /**
     * @return wrong
     */
    public Integer getWrong() {
        return wrong;
    }

    /**
     * @param wrong
     */
    public void setWrong(Integer wrong) {
        this.wrong = wrong;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", questionId=").append(questionId);
        sb.append(", allNum=").append(allNum);
        sb.append(", wrong=").append(wrong);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}