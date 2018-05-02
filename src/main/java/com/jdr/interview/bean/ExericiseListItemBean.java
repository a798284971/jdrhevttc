package com.jdr.interview.bean;

public class ExericiseListItemBean {
	private Integer id;

    private String title;

    private Integer superioe;
    
    private Integer questionNum;
    
    private Integer isStudyNum;
    
    private double rightRate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSuperioe() {
		return superioe;
	}

	public void setSuperioe(Integer superioe) {
		this.superioe = superioe;
	}

	public Integer getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	public Integer getIsStudyNum() {
		return isStudyNum;
	}

	public void setIsStudyNum(Integer isStudyNum) {
		this.isStudyNum = isStudyNum;
	}

	public double getRightRate() {
		return rightRate;
	}

	public void setRightRate(double rightRate) {
		this.rightRate = rightRate;
	}
    
}
