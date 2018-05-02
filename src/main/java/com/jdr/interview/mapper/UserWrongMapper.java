package com.jdr.interview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jdr.interview.entity.UserWrong;
import tk.mybatis.mapper.common.Mapper;

public interface UserWrongMapper extends Mapper<UserWrong> {
	public List<String> findWrongSuper(@Param(value = "user_id") String uid
			,@Param(value = "type") String type);
}