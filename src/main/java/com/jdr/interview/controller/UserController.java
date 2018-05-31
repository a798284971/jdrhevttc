package com.jdr.interview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdr.interview.bean.BusinessMessage;
import com.jdr.interview.bean.CollectAndWrongBean;
import com.jdr.interview.bean.CollectBean;
import com.jdr.interview.bean.OssBean;
import com.jdr.interview.bean.TestStatus;
import com.jdr.interview.bean.UserSignListBean;
import com.jdr.interview.entity.User;
import com.jdr.interview.entity.UserStatus;
import com.jdr.interview.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
    private UserService testService;
    /**
     * 登录接口
     * @param username   用户名
     * @param password   密码
     * @return
     */
	@GetMapping("login")
	public BusinessMessage<User> login(String username,String password) {
		return testService.findUserByUserName(username, password).build();
	}
	/**
	 * 注册用户接口
	 * @param username   用户名
	 * @param password   密码
	 * @param code       接收的短信校验码
	 * @return
	 */
	@GetMapping("regist")
	public BusinessMessage<String> regist(String username,String password,String code){
		return testService.registUser(username, password,code).build();
	}
	/**
	 * 发送短信接口
	 * @param number  手机号
	 * @return
	 */
	@GetMapping("sendMsg")
	public BusinessMessage<String> sendMsg(String number){
		return testService.sendMsg(number).build();
	}
	/**
	 * 修改用户信息接口
	 * @param uid          用户ID
	 * @param nickName     昵称（可选）
	 * @param sex          性别（可选）
	 * @param birth        生日（可选）
	 * @param education    毕业院校（可选）
	 * @param avastar      头像地址（可选）
	 * @return
	 */
	@GetMapping("changeInfo")
	public BusinessMessage<User> changeInfo(@RequestParam(value ="uid") String uid,
											@RequestParam(value ="nickName",required = false) String nickName,
											@RequestParam(value ="sex",required = false) String sex,
											@RequestParam(value ="birth",required=false) String birth,
											@RequestParam(value ="education",required=false) String education,
											@RequestParam(value ="avastar",required = false) String avastar){
		return testService.changeInfo(uid, nickName, sex, birth, education, avastar).build();	
	}
	/**
	 * 修改密码接口
	 * @param uid       用户ID
	 * @param oldpwd    旧密码
	 * @param newpwd    新密码
	 * @return
	 */
	@GetMapping("changePwd")
	public BusinessMessage<String> changePwd(String uid,String oldpwd,String newpwd){
		return testService.changePwd(uid, oldpwd, newpwd).build();
	}
	/**
	 * 获取用户收藏和错题数量的接口
	 * @param uid      用户ID
	 * @return
	 */
	@GetMapping("getCollandWrong")
	public BusinessMessage<CollectAndWrongBean> getCollectTime(String uid){
		return testService.getCollectTime(uid).build();
	}
	/**
	 * 用户签到
	 * @param uid         用户ID
	 * @param timeStamp   时间戳
	 * @return
	 */
	@GetMapping("signIn")
	public BusinessMessage<String> signIn(String uid,String content,String timeStamp){
		return testService.signIn(uid,content, timeStamp).build();
	}
	/**
	 * 获取用户状态      
	 * @param uid    用户ID
	 * @return
	 */
	@GetMapping("getSignStatus")
	public BusinessMessage<UserStatus> getSignStatus(String uid){
		return testService.getSignStatus(uid).build();
	}
	/**
	 * 获取用户签到信息列表  
	 * @param uid    用户ID
	 * @return
	 */
	@GetMapping("getSignList")
	public BusinessMessage<List<UserSignListBean>> getSignList(String uid){
		return testService.getSignList(uid).build();
	}
	@GetMapping("getTestStatus")
	public BusinessMessage<TestStatus> getTestStatus(String uid){
		return testService.getTestStatus(uid).build();
	}
	@GetMapping("getOssID")
	public BusinessMessage<OssBean> getOssid(String uid){
		return testService.getOssid(uid).build();
	}
}
