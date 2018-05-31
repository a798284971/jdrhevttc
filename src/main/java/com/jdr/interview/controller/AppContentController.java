package com.jdr.interview.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdr.interview.bean.BusinessMessage;
import com.jdr.interview.bean.TalkBean;
import com.jdr.interview.bean.UserTalkBean;
import com.jdr.interview.entity.Feedback;
import com.jdr.interview.entity.Lunbo;
import com.jdr.interview.entity.Message;
import com.jdr.interview.entity.SigninTalk;
import com.jdr.interview.entity.TbSchool;
import com.jdr.interview.entity.UserSetting;
import com.jdr.interview.mapper.LunboMapper;
import com.jdr.interview.service.AppContentService;

@RestController
@RequestMapping("app")
public class AppContentController {
	@Autowired
	private AppContentService appContentService;
	
	/**
	 * 获取轮播图列表
	 * @return
	 */
	@GetMapping("getLunbo")
	public BusinessMessage<List<Lunbo>> getLunbo(){
		return appContentService.getLunbo().build();
	}
	/**
	 * 获取消息列表
	 * @return
	 */
	@GetMapping("getMessage")
	public BusinessMessage<List<Message>> getMessage(){
		return appContentService.getMessage().build();
	}
	/**
	 * 获取签到信息列表。。。  
	 * @param uid   可选项：用户ID
	 * @return
	 */
	@GetMapping("getSignTalk")
	public BusinessMessage<List<TalkBean>> getSignTalk(@RequestParam(value ="uid",required = false) String uid){
		return appContentService.getSignTalk(uid).build();
	}
	/**
	 * 点赞   
	 * @param uid       用户ID
	 * @param talkId    说说ID
	 * @return
	 */
	@GetMapping("talkFighUp")
	public BusinessMessage<String> talkFighUp(String uid,String talkId,String type){
		return appContentService.talkFighUp(uid,talkId,type).build();
	}
	/**
	 * 添加评论
	 * @param uid        用户ID
	 * @param talkId     说说ID
	 * @param text		   评论文字
	 * @return
	 */
	@GetMapping("addTalk")
	public BusinessMessage<String> addTalk(String uid,String talkId,String text){
		return appContentService.addTalk(uid,talkId,text).build();
	}
	/**
	 * 获取某 说说的评论列表
	 * @param talkId    评论ID
	 * @return
	 */
	@GetMapping("getUserTalkList")
	public BusinessMessage<List<UserTalkBean>> getUserTalkList(String talkId){
		return appContentService.getUserTalkList(talkId).build();
	}
	/**
	 * 更改用户的设置参数
	 * @param uid       用户ID
	 * @param examNum   抽提个数
	 * @param examType  考试类型。
	 * @return
	 */
	@GetMapping("userSetting")
	public BusinessMessage<String> userSetting(@RequestParam(value ="uid") String uid,
					@RequestParam(value ="examNum",required = false) String examNum,
					@RequestParam(value ="examType",required = false) String examType){
			return appContentService.userSetting(uid,examNum,examType).build();
		
	}
	/**
	 * 获取用户的设置参数
	 * @return
	 */
	@GetMapping("getUserSetting")
	public BusinessMessage<UserSetting> getUserSetting(String uid){
		return appContentService.getUserSetting(uid).build();
	}
	/**
	 * 提交小报告
	 * @param uid       用户ID
	 * @param examNum   小报告内容
	 * 
	 * @return
	 */
	@GetMapping("feedback")
	public BusinessMessage<String> feedback(String uid,String content){
		return appContentService.feedback(uid,content).build();
	}
	/**
	 * 获取用户提交的小报告
	 * @param uid       用户ID
	 * @return
	 */
	@GetMapping("getFeedback")
	public BusinessMessage<List<Feedback>> getFeedback(String uid){
		return appContentService.getFeedback(uid).build();
	}
	@GetMapping("getByUniversityName")
	public BusinessMessage<List<TbSchool>> getByUniversityName(String name){
		return appContentService.getByUniversityName(name).build();
	}
}
