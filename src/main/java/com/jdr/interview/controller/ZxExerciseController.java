package com.jdr.interview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdr.interview.bean.BusinessMessage;
import com.jdr.interview.bean.ExericiseListBean;
import com.jdr.interview.bean.UserAnswerBean;
import com.jdr.interview.entity.Answer;
import com.jdr.interview.entity.ChooseQuestion;
import com.jdr.interview.service.ExerciseService;


@RestController
@RequestMapping("exercise")
public class ZxExerciseController {
	@Autowired
    private ExerciseService exerciseServicew;
	
	/**
	 * 
	 * @return    获取题目一级和二级列表
	 */
	@GetMapping("getExerciseListByUid")
	public BusinessMessage<List<ExericiseListBean>> getExeriseListByUid(String uid){
		return exerciseServicew.getExeriseListByUid(uid).build();
	}
	@GetMapping("getExerciseList")
	public BusinessMessage<List<ExericiseListBean>> getExerciseList(){
		return exerciseServicew.getExeriseList().build();
	}
	
	/**
	 * 
	 * @param id  上级列表ID
	 * @return  根据二级列表获取题目列表详情
	 */
	@GetMapping("exerciseExamList")
	public BusinessMessage<List<ChooseQuestion>> getExericiseExamList(String id){
		return exerciseServicew.getExericiseExamList(id).build();
	}
	/**
	 * 
	 * @param id 题目ID（获取多个用'，' 分隔）
	 * @return   根据题目ID获取题目详情
	 */
	@GetMapping("exercisebyId")
	public BusinessMessage<List<ChooseQuestion>> getExerciseById(String... id){
		String[] data = id;
		return exerciseServicew.getExeRiciseById(data).build();
	}
	/**
	 * 查询答案
	 * @param uid    用户ID
	 * @param id     题目ID
	 * @return
	 */
	@GetMapping("checkAnswer")
	public BusinessMessage<List<UserAnswerBean>> getAnswer(String uid,String... id){
		String[] data = id;
		return exerciseServicew.getAnswer(uid,data).build();
	}
	@GetMapping("getExamexercise")
	public BusinessMessage<List<ChooseQuestion>> getExamexercise(String uid,String superioe){
		return exerciseServicew.getExamexercise(uid,superioe).build();
	}
}

