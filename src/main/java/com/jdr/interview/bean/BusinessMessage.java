package com.jdr.interview.bean;

import lombok.Data;

/**
 * Created by SongpoLiu on 2017/7/10.
 */
@Data
public class BusinessMessage<T> {

    private Integer code; //返回 值

    private String msg; //返回消息

    private Boolean success = false; // 返回数据成功或者失败

    private T data; //数据

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

    

}
