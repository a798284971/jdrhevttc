package com.jdr.interview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jdr.interview.entity.TbSchool;
import tk.mybatis.mapper.common.Mapper;

public interface TbSchoolMapper extends Mapper<TbSchool> {
	public List<TbSchool> getByUniversityName(@Param(value = "name") String name);
}