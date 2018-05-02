package com.jdr.interview.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Answer implements Serializable {
    @Id
    @Column(name = "question_id")
    private Integer questionId;

    private String answer;

    private String analysis;

    @Column(name = "true_rate")
    private Double trueRate;

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
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * @return analysis
     */
    public String getAnalysis() {
        return analysis;
    }

    /**
     * @param analysis
     */
    public void setAnalysis(String analysis) {
        this.analysis = analysis == null ? null : analysis.trim();
    }

    /**
     * @return true_rate
     */
    public Double getTrueRate() {
        return trueRate;
    }

    /**
     * @param trueRate
     */
    public void setTrueRate(Double trueRate) {
        this.trueRate = trueRate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", questionId=").append(questionId);
        sb.append(", answer=").append(answer);
        sb.append(", analysis=").append(analysis);
        sb.append(", trueRate=").append(trueRate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}