package com.jdr.interview.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jdr.interview.bean.AdminQuestionBean;
import com.jdr.interview.bean.BusinessMessage;
import com.jdr.interview.bean.BusinessMessageBuilder;
import com.jdr.interview.bean.SystemStatusBean;
import com.jdr.interview.conf.WebMvcConfig;
import com.jdr.interview.entity.Feedback;
import com.jdr.interview.entity.ZxExercise;
import com.jdr.interview.service.AdminService;

@RestController
@RequestMapping("adminer")
public class AdminController {
	@Autowired
    private AdminService adminService;
	/**
	 * 管理员登录
	 * @param user
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("loginadmin")
	public BusinessMessage<String> check(String user,String password, HttpSession session){
		BusinessMessageBuilder<String> builder = adminService.check(user, password,session);
		return builder.build();
	}
	/**
	 * 退出登录
	 * @param session
	 * @return
	 */
	 @RequestMapping(value ="logout",method = RequestMethod.GET)
	 public String logout(HttpSession session) {
		// 移除session
		session.removeAttribute(WebMvcConfig.SESSION_KEY);
	    return "login";
	 }
	 /**
	  * 添加题目标题
	  * @param title  标题
	  * @param superioe  上级标题ID
	  * @return
	  */
	 @PostMapping("addtitle")
	 public BusinessMessage<String> addTitle(String title,String superioe){
		 BusinessMessageBuilder<String> builder = adminService.addTitle(title,superioe);
		return builder.build(); 
	 }
	 /**
	  * 修改题目标题
	  * @param id   标题ID
	  * @param title   标题文字
	  * @return
	  */
	 @PostMapping("updatetitle")
	 public BusinessMessage<String> updateTitle(String id,String title){
		 BusinessMessageBuilder<String> builder = adminService.updateTitle(id,title);
		return builder.build(); 
	 }
	 /**
	  * 删除标题
	  * @param id   标题ID
	  * @return
	  */
	 @GetMapping("deletitle")
	 public BusinessMessage<String> deleTitle(String id){
		 BusinessMessageBuilder<String> builder = adminService.deleTitle(id);
		return builder.build(); 
	 }
	 /**
	  * 获取系统状态
	  * @return
	  */
	 @GetMapping("getStatus")
	 public BusinessMessage<SystemStatusBean> getStatus(){
		 return adminService.getStatus().build();
	 }
	 /**
	  * 获取上级ID获取下级标题列表
	  * @param superiod  上级ID
	  * @return
	  */
	 @GetMapping("getExe")
	 public BusinessMessage<List<ZxExercise>> getExe(String superiod){
		 return adminService.getExe(superiod).build();
	 }
	 /**
	  * 获取问题列表
	  * @param superioe  上级标题ID
	  * @return
	  */
	 @GetMapping("getQuestion")
	 public BusinessMessage<List<AdminQuestionBean>> getQuestion(String superioe){
		 return adminService.getQuestion(superioe).build();
	 }
	 /**
	  * 删除问题
	  * @param id  问题ID
	  * @return
	  */
	 @GetMapping("deleQuestion")
	 public BusinessMessage<String> deleQuestion(String id){
		 return adminService.deleQuestion(id).build();
	 }
	 /**
	  * 添加问题
	  * @param superioe  上级标题
	  * @param question  问题详情	
	  * @param a        
	  * @param b
	  * @param c
	  * @param d
	  * @param type      问题类型（0:单选  1:多选）
	  * @param answer	   问题大胆
	  * @param analysis  问题解析
	  * @return
	  */
	 @PostMapping("addQuestion")
	 public BusinessMessage<String> addQuestion(String superioe,String question,String a,String b,String c,String d,String type,String answer,String analysis){
		 return adminService.addQuestion(superioe,question,a,b,c,d,type,answer,analysis).build(); 
	 }
	 /**
	  * 添加轮播图信息
	  * @param content   轮播图详情
	  * @param file		  轮播图图片
	  * @return
	  */
	 @PostMapping("addLunbo")
	 public BusinessMessage<String> addLunbo(String content,MultipartFile file ){
		 return adminService.addLunbo(content,file).build(); 
	 }
	 /**
	  * 修改轮播图信息
	  * @param id       轮播图ID
	  * @param content  轮播图详情
	  * @param file     轮播图图片
	  * @return
	  */
	 @PostMapping("updateLunbo")
	 public BusinessMessage<String> updateLunbo(String id,String content,MultipartFile file){
		return adminService.updateLunbo(id,content,file).build(); 
	 }
	 /**
	  * 删除轮播图
	  * @param id      轮播图ID
	  * @return
	  */
	 @PostMapping("deleLunbo")
	 public BusinessMessage<String> deleLunbo(String id){
			return adminService.deleLunbo(id).build(); 
	}
	@PostMapping("addMessage")
	public BusinessMessage<String> addMessage(String title,String content){
		return adminService.addMessage(title,content).build();
	}
	@PostMapping("deleMessage")
	public BusinessMessage<String> deleMessage(String id){
		return adminService.deleMessage(id).build();
	}
	@PostMapping("getAllFeedBack")
	public BusinessMessage<List<Feedback>> getAllFeedBack(){
		return adminService.getAllFeedBack().build();
	}
}
