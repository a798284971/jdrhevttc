package com.jdr.interview.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.util.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.jdr.interview.bean.BusinessMessage;
import com.jdr.interview.bean.BusinessMessageBuilder;
import com.jdr.interview.bean.CollectAndWrongBean;
import com.jdr.interview.bean.CollectBean;
import com.jdr.interview.bean.OssBean;
import com.jdr.interview.bean.TestStatus;
import com.jdr.interview.entity.Answer;
import com.jdr.interview.entity.Checkcode;
import com.jdr.interview.entity.ChooseQuestion;
import com.jdr.interview.entity.CorrectRate;
import com.jdr.interview.entity.SigninTalk;
import com.jdr.interview.entity.User;
import com.jdr.interview.entity.UserColllect;
import com.jdr.interview.entity.UserFightup;
import com.jdr.interview.entity.UserSetting;
import com.jdr.interview.entity.UserStatus;
import com.jdr.interview.entity.UserTeststatus;
import com.jdr.interview.entity.UserWrong;
import com.jdr.interview.entity.ZxExercise;
import com.jdr.interview.mapper.AnswerMapper;
import com.jdr.interview.mapper.CheckcodeMapper;
import com.jdr.interview.mapper.ChooseQuestionMapper;
import com.jdr.interview.mapper.CorrectRateMapper;
import com.jdr.interview.mapper.SigninTalkMapper;
import com.jdr.interview.mapper.UserColllectMapper;
import com.jdr.interview.mapper.UserFightupMapper;
import com.jdr.interview.mapper.UserMapper;
import com.jdr.interview.mapper.UserSettingMapper;
import com.jdr.interview.mapper.UserStatusMapper;
import com.jdr.interview.mapper.UserTeststatusMapper;
import com.jdr.interview.mapper.UserWrongMapper;
import com.jdr.interview.mapper.ZxExerciseMapper;
import com.jdr.interview.utils.DateUtil;
import com.jdr.interview.utils.HttpClientUtil;
import com.jdr.interview.utils.MD5Utils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserService {
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	private final String accountSid = "9013ec5d494b4b2eb0d766016ec2f55d";
	private final String templateid = "110218407";
	private final String authToken = "e48c1e7bb88b4baeaa3eeb3845711982";

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CheckcodeMapper checkCodeMapper;
	@Autowired
	private UserWrongMapper userWrongMapper;
	@Autowired
	private UserColllectMapper userCollectMapper;
	@Autowired
	private ChooseQuestionMapper chooseQuestionMapper;
	@Autowired
	private UserStatusMapper statusMapper;
	@Autowired
	private SigninTalkMapper talkMapper;
	@Autowired
	private UserSettingMapper userSettingMapper;
	@Autowired
	private UserTeststatusMapper userTestStatusMapper;
	@Autowired
	private ZxExerciseMapper zxExeriseMapper;
	
	public BusinessMessageBuilder<User> findUserByUserName(String username, String password) {
		BusinessMessageBuilder<User> builder = new BusinessMessageBuilder<>();
			
		User user2 = new User();
		user2.setUsername(username);
		user2.setPassword(password);
		User user = userMapper.selectOne(user2);
		
		if (user != null) {
			UserStatus status = statusMapper.selectByPrimaryKey(user.getId());
			if(status!=null) {
				Date date1 = DateUtil.getDateFromTimeStamp(status.getTimeStamp());
				Date date = new Date();
				if(date1.getYear()==date.getYear() && date1.getMonth()==date.getMonth() && 
						date1.getDay()==date.getDay()) {
				}else {
					status.setStudyNum(0);
					statusMapper.updateByPrimaryKey(status);
				}
			}			
			user.setPassword(null);
			builder.data(user)
			.msg("Succcess")
			.success(true);
		} else {
			builder.data(null)
			.msg("账号或密码错误")
			.success(false);
		}

		return builder;
	}
	
	@Transactional
	public BusinessMessageBuilder<String> registUser(String username,String password,String code){
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		Checkcode selectKey = checkCodeMapper.selectByPrimaryKey(username);
		if(selectKey ==null) {
			builder.msg("还没发过验证码");
			builder.success(false);
			return builder;
		}
		if(!selectKey.getCode().equals(code)) {
			builder.msg("验证码不正确");
			builder.success(false);
			return builder;
		}
		Date timeStampDate = DateUtil.getDateFromTimeStamp(selectKey.getTimestamp());
		double get2DateMinute = DateUtil.get2DateMinute(timeStampDate, new Date());
		if(get2DateMinute>3) {
			builder.msg("验证码已过期");
			builder.success(false);
			return builder;
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("username",username);
		List<User> selectByExample = userMapper.selectByExample(example);
		if(selectByExample==null || selectByExample.size()==0) {
			int insert = userMapper.insert(user);
			if(insert==1) {
				builder.msg("注册成功");
				builder.success(true);
				User selectOne = userMapper.selectOne(user);
				UserStatus userStatus = new UserStatus();
				userStatus.setUserId(selectOne.getId());
				statusMapper.insertSelective(userStatus);
				
				UserSetting userSetting = new UserSetting();
				userSetting.setUserId(selectOne.getId());
				userSettingMapper.insertSelective(userSetting);
				
				Example example2 = new Example(ZxExercise.class);
				example2.createCriteria().andNotEqualTo("superioe",0);
				List<ZxExercise> superioeList = zxExeriseMapper.selectByExample(example2);
				for (ZxExercise zxExercise : superioeList) {
					UserTeststatus userTeststatus = new UserTeststatus();
					userTeststatus.setUid(selectOne.getId());
					userTeststatus.setExerId(zxExercise.getId());
					userTestStatusMapper.insertSelective(userTeststatus);
				}
				
			}else {
				builder.msg("注册失败")
				.success(false);
			}
		}else {
			builder.msg("账号已存在")
			.success(false);
		}
		builder.data(null);
		return builder;
	}
	@Transactional
	public BusinessMessageBuilder<String> sendMsg(String number){
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("username",number);
		List<User> selectByExample = userMapper.selectByExample(example);
		if(selectByExample==null || selectByExample.size()==0) {
			Checkcode key = checkCodeMapper.selectByPrimaryKey(number);
			if(key!=null && key.getTimes()>=5) {
				if(!DateUtil.getIsLastDay(key.getTimestamp(), new Date())) {
					builder.msg("今天发送次数已达上限");
					builder.success(false);
					return builder;
				}else {
					Checkcode checkcode = new Checkcode();
					checkcode.setTimes(0);
					checkcode.setUsername(number);
					checkCodeMapper.updateByPrimaryKeySelective(checkcode);
				}
			}else if(key==null){
				key = new Checkcode();
				key.setUsername(number);
				key.setTimes(0);
				checkCodeMapper.insert(key);
			}
			int code =(int)((Math.random()*9+1)*10000); 
			String url = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
			String timesTamp = DateUtil.getTimesTamp(new Date());
			HashMap<String,String> param = new HashMap<>();
			param.put("accountSid", accountSid);
			param.put("templateid", templateid);
			param.put("param", code+"");
			param.put("to", number);
			param.put("timestamp",timesTamp);
			param.put("sig", MD5Utils.encode(accountSid+authToken+timesTamp));
			String doPost = HttpClientUtil.doPost(url, param);
			if(!StringUtils.isEmpty(doPost)) {
				try {
					JSONObject jsonObject = new JSONObject(doPost);
					String respCode = jsonObject.getString("respCode");
					if(respCode.equals("00000")) {
						key.setTimes(key.getTimes()+1);
						key.setTimestamp(DateUtil.getTimesTamp(new Date()));
						key.setCode(code+"");
						checkCodeMapper.updateByPrimaryKey(key);
						builder.msg("验证码发送成功");
						builder.success(true);
					}else {
						builder.msg("验证码发送失败");
						builder.success(false);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				builder.msg("验证码发送失败");
				builder.success(false);
			}
		}else {
			builder.msg("手机号已注册");
			builder.success(false);
		}
		return builder;
	}
	public BusinessMessageBuilder<User> changeInfo(String uid,String nickName,String sex, String birth, String education, String avastar){
		BusinessMessageBuilder<User> builder = new BusinessMessageBuilder<>();
		User user = new User();
		user.setId(Integer.parseInt(uid));
		user.setNickname(nickName);
		user.setSex(sex);
		user.setBirth(birth);
		user.setEducation(education);
		user.setAvastar(avastar);
		int keySelective = userMapper.updateByPrimaryKeySelective(user);
		if(keySelective==1) {
			builder.success(true);
			builder.msg("修改成功");
		}else {
			builder.success(false);
			builder.msg("修改失败");
		}
		builder.data(userMapper.selectByPrimaryKey(Integer.parseInt(uid)));
		return builder;
	}
	public BusinessMessageBuilder<String> changePwd(String uid,String oldpwd,String newpwd){
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		User key = userMapper.selectByPrimaryKey(Integer.parseInt(uid));
		if(StringUtils.equals(key.getPassword(), oldpwd)) {
			key.setPassword(newpwd);
			int keySelective = userMapper.updateByPrimaryKeySelective(key);
			if(keySelective==1) {
				builder.msg("修改成功");
				builder.success(true);
			}else {
				builder.msg("修改失败");
				builder.success(false);
			}
		}else {
			builder.msg("旧密码不正确");
			builder.success(false);
		}
		builder.data(null);
		return builder;
	}

	public BusinessMessageBuilder<CollectAndWrongBean> getCollectTime(String uid) {
		BusinessMessageBuilder<CollectAndWrongBean> builder = new BusinessMessageBuilder<>();
		Example example = new Example(UserWrong.class);
		example.createCriteria().andEqualTo("userId", Integer.parseInt(uid));
		int wrongCount = userWrongMapper.selectCountByExample(example);
		Example example1 = new Example(UserColllect.class);
		example1.createCriteria().andEqualTo("userId", Integer.parseInt(uid));
		int collectCount = userCollectMapper.selectCountByExample(example1);
		CollectAndWrongBean collectAndWrongBean = new CollectAndWrongBean();
		collectAndWrongBean.setCollect(collectCount);
		collectAndWrongBean.setWrong(wrongCount);
		builder.data(collectAndWrongBean)
				.msg("查找成功")
				.success(true);
		return builder;
	}

	public BusinessMessageBuilder<String> signIn(String uid, String content,String timeStamp) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		UserStatus key = statusMapper.selectByPrimaryKey(Integer.parseInt(uid));
		if(StringUtils.isEmpty(key.getTimeStamp())) {
			key.setTimeStamp(timeStamp);
			key.setSignNum(key.getSignNum()+1);
		}else {
			Date dayNow = DateUtil.getDateFromTimeStamp(timeStamp);
			boolean isLastDay = DateUtil.getIsLastDay(key.getTimeStamp(), dayNow);
			if(isLastDay) {
				key.setSignNum(key.getSignNum()+1);
				key.setTimeStamp(timeStamp);
				
			}else {
				builder.msg("今天已经签到过了")
				.success(false);
				return builder;
			}
		}
		if(StringUtils.isEmpty(content)) {
			content="我在打卡了"+key.getSignNum()+"天，学习了"+key.getQuestionNum()+"道题";
		}
		SigninTalk signinTalk = new SigninTalk();
		signinTalk.setUserId(key.getUserId());
		signinTalk.setTalkText(content);
		signinTalk.setCreateTime(DateUtil.getTimesTamp(new Date()));
		
		int insert = talkMapper.insertSelective(signinTalk);
		int update = statusMapper.updateByPrimaryKeySelective(key);
		if(update!=0 && insert!=0)
			builder.msg("签到成功")
			.success(true);
		else
			builder.msg("签到失败")
			.success(false);	
		return builder;
	}

	public BusinessMessageBuilder<UserStatus> getSignStatus(String uid) {
		BusinessMessageBuilder<UserStatus> builder = new BusinessMessageBuilder<>();
		UserStatus userStatus = statusMapper.selectByPrimaryKey(Integer.parseInt(uid));
		if(userStatus!=null) {
			String timeStamp = userStatus.getTimeStamp();
			if(!TextUtils.isEmpty(timeStamp)) {
				Date date1 = DateUtil.getDateFromTimeStamp(timeStamp);
				Date date = new Date();
				if(date1.getYear()==date.getYear() && date1.getMonth()==date.getMonth() && 
						date1.getDay()==date.getDay()) {
					userStatus.setTimeStamp("已打卡");
				}else {
					userStatus.setTimeStamp("打卡");
				}
			}else {
				userStatus.setTimeStamp("打卡");
			}
			
			builder.success(true)
			.msg("请求成功")
			.data(userStatus);
		}
			
		else
			builder.success(false)
			.msg("请求失败");
		return builder;
	}
	public BusinessMessageBuilder<TestStatus> getTestStatus(String uid){
		BusinessMessageBuilder<TestStatus> builder = new BusinessMessageBuilder<>();
		UserTeststatus userTeststatus = new UserTeststatus();
		userTeststatus.setUid(Integer.parseInt(uid));
		List<UserTeststatus> select = userTestStatusMapper.select(userTeststatus);
		TestStatus testStatus = new TestStatus();
		double rate = 0.0;
		for (UserTeststatus status : select) {
			testStatus.setTestNum(testStatus.getTestNum()+status.getStudyNum());
			rate += status.getRightRate();
		}
		BusinessMessage<UserStatus> build = getSignStatus(uid).build();
		testStatus.setTestDay(build.getData().getSignNum());
		testStatus.setTestRate(rate/select.size());
		builder.success(true)
				.data(testStatus);
		return builder;
	}

	public BusinessMessageBuilder<OssBean> getOssid(String uid) {
		// TODO Auto-generated method stub
		BusinessMessageBuilder<OssBean> builder = new BusinessMessageBuilder<>();
		User selectByPrimaryKey = userMapper.selectByPrimaryKey(Integer.parseInt(uid));
		if(selectByPrimaryKey!=null) {
			OssBean ossBean = new OssBean();
			ossBean.setId("LTAIrQdpsIaRNbg4");
			ossBean.setPwd("UUTn5SuVpNl52vtGNRH049R7wipMuR");
			builder.success(true)
			.data(ossBean);
		}
		else
			builder.success(false);
		return builder;
	}
}
