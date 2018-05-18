package com.jdr.interview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdr.interview.bean.BusinessMessage;
import com.jdr.interview.bean.CollectBean;
import com.jdr.interview.bean.ExericiseListBean;
import com.jdr.interview.service.ExerciseUserService;


@RestController
@RequestMapping("exerciseUser")
public class CollectAndWrongaController {
	@Autowired
    private  ExerciseUserService exeUserService;
	/**
	 * 获取收藏列表的接口
	 * @param uid      用户ID
	 * @param type     收藏类型
	 * @return
	 */
	@GetMapping("getCollectList")
	public BusinessMessage<List<CollectBean>> getCollectList(String uid,String type){
		return exeUserService.getCollectList(uid, type).build();	
	}
	/**
	 * 获取错题列表的接口
	 * @param uid
	 * @param type
	 * @return
	 */
	@GetMapping("getWrongList")
	public BusinessMessage<List<ExericiseListBean>> getWrongList(String uid,String type){
		return exeUserService.getWrongList(uid,type).build();
	}
	/**
	 * 收藏题目接口
	 * @param uid           用户ID
	 * @param questionId    题目ID
	 * @param type			题目类型
	 * @return
	 */
	@GetMapping("collectQuestion")
	public BusinessMessage<String> collectQuestion(String uid,String questionId,String type){
		return exeUserService.collectQuestion(uid,questionId,type).build();
	}
	/**
	 * 删除用户收藏接口
	 * @param id         收藏ID
	 * @return
	 */
	@GetMapping("deleColl")
	public BusinessMessage<String> deleteCollection(String uid,String id){
		return exeUserService.deleteCollection(uid,id).build();
	}
	/**
	 * 提交错题接口
	 * @param uid
	 * @param questionId
	 * @param type
	 * @return
	 */
	@GetMapping("commitWrong")
	public BusinessMessage<String> CommitWrong(String uid,String questionId,String type,String wrongAnswer){
		return exeUserService.CommitWrong(uid,questionId,type,wrongAnswer).build();
	}
}
