package com.jdr.interview.bean;

import java.util.List;

import com.jdr.interview.entity.ZxExercise;

public class ExericiseListBean {
	private Integer id;

    private String title;

    private List dataList;

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

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
    
}
