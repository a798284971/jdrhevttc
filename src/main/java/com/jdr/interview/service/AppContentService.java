package com.jdr.interview.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.jdr.interview.bean.BusinessMessageBuilder;
import com.jdr.interview.bean.TalkBean;
import com.jdr.interview.bean.UserTalkBean;
import com.jdr.interview.entity.Lunbo;
import com.jdr.interview.entity.Message;
import com.jdr.interview.entity.SigninTalk;
import com.jdr.interview.entity.UseTalk;
import com.jdr.interview.entity.User;
import com.jdr.interview.entity.UserFightup;
import com.jdr.interview.entity.UserSetting;
import com.jdr.interview.mapper.LunboMapper;
import com.jdr.interview.mapper.MessageMapper;
import com.jdr.interview.mapper.SigninTalkMapper;
import com.jdr.interview.mapper.UseTalkMapper;
import com.jdr.interview.mapper.UserFightupMapper;
import com.jdr.interview.mapper.UserMapper;
import com.jdr.interview.mapper.UserSettingMapper;
import com.jdr.interview.utils.DateUtil;
import com.jdr.interview.utils.OssUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;
@Service
public class AppContentService {
	private final Logger log = LoggerFactory.getLogger(AppContentService.class);
	@Autowired
	private LunboMapper lunboMapper;
	@Autowired
	private MessageMapper msgMapper;
	@Autowired
	private SigninTalkMapper talkMapper;
	@Autowired
	private UserFightupMapper fightMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UseTalkMapper userTalkMapper;
	@Autowired
	private UserSettingMapper userSettingMapper;
	
	public BusinessMessageBuilder<List<Lunbo>> getLunbo() {
		BusinessMessageBuilder<List<Lunbo>> builder = new BusinessMessageBuilder<List<Lunbo>>();
		List<Lunbo> list = lunboMapper.selectAll();
		if(list.size()!=0 || list!=null) 
			builder.success(true)
			.msg("查询成功");
		else
			builder.success(false)
			.msg("查询失败");
		builder.data(list);
		return builder;
	}
	public BusinessMessageBuilder<List<Message>> getMessage() {
		BusinessMessageBuilder<List<Message>> builder = new BusinessMessageBuilder<List<Message>>();
		List<Message> selectAll = msgMapper.selectAll();
		
		builder.msg("请求成功")
		.success(true)
		.data(selectAll);
		
		return builder;
	}
	public BusinessMessageBuilder<List<TalkBean>> getSignTalk(String uid) {
		BusinessMessageBuilder<List<TalkBean>> builder = new BusinessMessageBuilder<List<TalkBean>>();
		//List<SigninTalk> selectAll = talkMapper.selectAll();
		Example example = new Example(SigninTalk.class);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date=new Date();  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -2);  
        date = calendar.getTime();  
		example.createCriteria().andCondition("create_time>", DateUtil.getTimesTamp(date));
		example.setOrderByClause("create_time DESC");
		List<SigninTalk> selectAll = talkMapper.selectByExample(example);
		
		List<TalkBean> list = new ArrayList<TalkBean>();
		for(SigninTalk bean:selectAll) {
			TalkBean talkBean = new TalkBean();
			talkBean.setTalkId(bean.getTalkId());
			talkBean.setTalkText(bean.getTalkText());
			talkBean.setCreateTime(DateUtil.getDatePoor(new Date(),DateUtil.getDateFromTimeStamp(bean.getCreateTime())));
			//查询用户是否点赞
			if(uid!=null) {
				UserFightup userFightup = new UserFightup();
				userFightup.setTalkId(bean.getTalkId());
				userFightup.setUserId(Integer.parseInt(uid));
				talkBean.setStatus(fightMapper.selectOne(userFightup)==null?0:1);
			}else
				talkBean.setStatus(0);
			//用户信息
			User userKey = userMapper.selectByPrimaryKey(bean.getUserId());
			talkBean.setNickName(userKey.getNickname());
			talkBean.setHeadImg(userKey.getAvastar());
			
			list.add(talkBean);
		}
		builder.data(list)
		.msg("请求成功")
		.success(true);
		return builder;
	}
	public BusinessMessageBuilder<String> talkFighUp(String uid, String talkId) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<String>();
		UserFightup userFightup = new UserFightup();
		userFightup.setTalkId(Integer.parseInt(talkId));
		userFightup.setUserId(Integer.parseInt(uid));
		int selectCount = fightMapper.selectCount(userFightup);
		if(selectCount==0) {
			userFightup.setCreateTime(DateUtil.getTimesTamp(new Date()));
			int insert = fightMapper.insert(userFightup);
			if(insert!=0) {
				userFightup.setUserId(null);
				builder.msg("点赞成功")
				.success(true)
				.data(fightMapper.selectCount(userFightup)+"");
			}else {
				builder.msg("点赞失败")
				.success(false);
			}
			
		}else {
			builder.msg("已经赞过了")
			.success(false);
		}
		return builder;
	}
	public BusinessMessageBuilder<String> addTalk(String uid, String talkId, String text) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		UseTalk useTalk = new UseTalk();
		useTalk.setTalkId(Integer.parseInt(talkId));
		useTalk.setUserId(Integer.parseInt(uid));
		useTalk.setText(text);
		useTalk.setTime(DateUtil.getTimesTamp(new Date()));
		int insert = userTalkMapper.insert(useTalk);
		if(insert!=0)
			builder.msg("评论成功")
			.success(true);
		else 
			builder.msg("评论失败")
			.success(false);
		
		return builder;
	}
	public BusinessMessageBuilder<List<UserTalkBean>> getUserTalkList(String talkId) {
		BusinessMessageBuilder<List<UserTalkBean>> builder = new BusinessMessageBuilder<List<UserTalkBean>>();
		Example example = new Example(UseTalk.class);
		example.createCriteria().andEqualTo("talkId", Integer.parseInt(talkId));
		example.setOrderByClause("time DESC");
		List<UseTalk> selectByExample = userTalkMapper.selectByExample(example);
		List<UserTalkBean> list = new ArrayList<UserTalkBean>();
		for(UseTalk bean:selectByExample) {
			UserTalkBean userTalkBean = new UserTalkBean();
			userTalkBean.setId(bean.getId());
			userTalkBean.setText(bean.getText());
			userTalkBean.setTime(DateUtil.getDatePoor(new Date(),DateUtil.getDateFromTimeStamp(bean.getTime())));
			//用户信息
			User userKey = userMapper.selectByPrimaryKey(bean.getUserId());
			userTalkBean.setNickName(userKey.getNickname());
			userTalkBean.setHeadImg(userKey.getAvastar());
			
			list.add(userTalkBean);
		}
		builder.data(list)
		.msg("请求成功")
		.success(true);
		return builder;
	}
	public BusinessMessageBuilder<String> userSetting(String uid, String examNum, String examType) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<String>();
		UserSetting userSetting = new UserSetting();
		userSetting.setUserId(Integer.parseInt(uid));
		if(!StringUtil.isEmpty(examNum))
			userSetting.setExamNum(Integer.parseInt(examNum));
		userSetting.setExamType(examType);
		int update = userSettingMapper.updateByPrimaryKeySelective(userSetting);
		if(update!=0)
			builder.success(true)
			.msg("修改成功");
		else
			builder.success(false)
			.msg("修改失败");
		return builder;
	}
	
}
