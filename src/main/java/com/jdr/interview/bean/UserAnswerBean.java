package com.jdr.interview.bean;

import javax.persistence.Column;

public class UserAnswerBean {
	private Integer questionId;

    private String answer;

    private String analysis;

    private Double trueRate;
    
    private boolean isCollect;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public Double getTrueRate() {
		return trueRate;
	}

	public void setTrueRate(Double trueRate) {
		this.trueRate = trueRate;
	}

	public boolean isCollect() {
		return isCollect;
	}

	public void setCollect(boolean isCollect) {
		this.isCollect = isCollect;
	}
    
    
}
