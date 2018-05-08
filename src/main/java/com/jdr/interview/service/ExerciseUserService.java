package com.jdr.interview.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.jdr.interview.bean.BusinessMessageBuilder;
import com.jdr.interview.bean.CollectBean;
import com.jdr.interview.bean.ExericiseListBean;
import com.jdr.interview.bean.WrongTitleBean;
import com.jdr.interview.entity.Answer;
import com.jdr.interview.entity.ChooseQuestion;
import com.jdr.interview.entity.CorrectRate;
import com.jdr.interview.entity.User;
import com.jdr.interview.entity.UserColllect;
import com.jdr.interview.entity.UserWrong;
import com.jdr.interview.entity.ZxExercise;
import com.jdr.interview.mapper.AnswerMapper;
import com.jdr.interview.mapper.ChooseQuestionMapper;
import com.jdr.interview.mapper.CorrectRateMapper;
import com.jdr.interview.mapper.UserColllectMapper;
import com.jdr.interview.mapper.UserWrongMapper;
import com.jdr.interview.mapper.ZxExerciseMapper;
import com.jdr.interview.utils.DateUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class ExerciseUserService {
	private final Logger log = LoggerFactory.getLogger(ExerciseUserService.class);
	@Autowired
	private UserColllectMapper userCollectMapper;
	@Autowired
	private ChooseQuestionMapper chooseQuestionMapper;
	@Autowired
	private UserWrongMapper userWrongMapper;
	@Autowired
	private ZxExerciseMapper exerciseMapper;
	@Autowired
	private CorrectRateMapper correctRateMapper;
	@Autowired
	private AnswerMapper answerMapper;
	
	public BusinessMessageBuilder<List<CollectBean>> getCollectList(String uid, String type) {
		BusinessMessageBuilder<List<CollectBean>> builder = new BusinessMessageBuilder<>();
		
		UserColllect userCollect = new UserColllect();
		userCollect.setUserId(Integer.parseInt(uid));
		userCollect.setType(Integer.parseInt(type));
		List<UserColllect> collectList = userCollectMapper.select(userCollect);
		ArrayList<CollectBean> arrayList = new ArrayList<CollectBean>();
		if(StringUtils.equals(type,"0") || StringUtils.equals(type,"1")) {
			for(UserColllect b:collectList) {
				CollectBean collectBean = new CollectBean();
				collectBean.setId(b.getId());
				collectBean.setCreateTime(b.getCreatetime());
				ChooseQuestion key = chooseQuestionMapper.selectByPrimaryKey(b.getQuestionId());
				collectBean.setTitle(key.getQuestion());
				collectBean.setQuestion(key);
				arrayList.add(collectBean);	
			}
			builder.data(arrayList)
			.msg("查询成功")
			.success(true);
		}
		return builder;
	}
	public BusinessMessageBuilder<List<ExericiseListBean>> getWrongList(String uid, String type) {
		BusinessMessageBuilder<List<ExericiseListBean>> builder = new BusinessMessageBuilder<>();
		List<String> findWrongSuper = userWrongMapper.findWrongSuper(uid,type);
		ArrayList<ExericiseListBean> data = new ArrayList<ExericiseListBean>();
		for(String s:findWrongSuper) {
			ZxExercise key = exerciseMapper.selectByPrimaryKey(Integer.parseInt(s));
			UserWrong userWrong = new UserWrong();
			userWrong.setUserId(Integer.parseInt(uid));
//			userWrong.setType(Integer.parseInt(type));
			userWrong.setSuperioe(Integer.parseInt(s));
			List<UserWrong> select = userWrongMapper.select(userWrong);
			ArrayList<WrongTitleBean>  isData = new ArrayList<WrongTitleBean>();
			for (UserWrong userWrongBean : select) {
				ChooseQuestion selectByPrimaryKey = chooseQuestionMapper.selectByPrimaryKey(userWrongBean.getQuestionId());
				WrongTitleBean wrongTitleBean = new WrongTitleBean();
				wrongTitleBean.setCreatetime(userWrongBean.getCreatetime());
				wrongTitleBean.setId(userWrongBean.getId());
				wrongTitleBean.setQuestionId(userWrongBean.getQuestionId());
				wrongTitleBean.setSuperioe(userWrongBean.getSuperioe());
				wrongTitleBean.setTitle(selectByPrimaryKey.getQuestion());
				wrongTitleBean.setType(userWrongBean.getType());
				wrongTitleBean.setUserId(userWrongBean.getUserId());
				wrongTitleBean.setWrongAnswer(userWrongBean.getWrongAnswer());
				isData.add(wrongTitleBean);
			}
			ExericiseListBean listBean = new ExericiseListBean();
			listBean.setId(key.getId());
			listBean.setTitle(key.getTitle());
			listBean.setDataList(isData);
			data.add(listBean);
		}
		builder.success(true)
		.msg("查询成功")
		.data(data);
		return builder;
	}
	
	
	@Transactional
	public BusinessMessageBuilder<String> collectQuestion(String uid, String questionId, String type) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		UserColllect userCollect = new UserColllect();
		userCollect.setUserId(Integer.parseInt(uid));
		userCollect.setQuestionId(Integer.parseInt(questionId));
		userCollect.setType(Integer.parseInt(type));
		List<UserColllect> select = userCollectMapper.select(userCollect);
		if(select.size()==0) {
			userCollect.setCreatetime(DateUtil.getStringDate(new Date()));
			int insert = userCollectMapper.insert(userCollect);
			if(insert!=1) 
				builder.success(false)
				.msg("收藏失败");
			else 
				builder.success(true)
				.msg("收藏成功");
		}else {
			builder.success(false)
			.msg("已经收藏过了");
		}
		
		return builder;
	}
	@Transactional
	public BusinessMessageBuilder<String> deleteCollection(String id) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		int dele = userCollectMapper.deleteByPrimaryKey(Integer.parseInt(id));
		if(dele==1)
			builder.success(true)
			.msg("删除收藏成功");
		else 
			builder.success(false)
			.msg("删除收藏失败");
		return builder;
	}
	@Transactional
	public BusinessMessageBuilder<String> CommitWrong(String uid, String questionId, String type,String wrongAnswer) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		UserWrong userWrong = new UserWrong();
		userWrong.setUserId(Integer.parseInt(uid));
		userWrong.setQuestionId(Integer.parseInt(questionId));
		userWrong.setType(Integer.parseInt(type));
		userWrong.setCreatetime(DateUtil.getStringDate(new Date()));	
		userWrong.setSuperioe(chooseQuestionMapper.selectByPrimaryKey(Integer.parseInt(questionId)).getSuperioe());
		userWrong.setWrongAnswer(wrongAnswer);
		CorrectRate correctRate = correctRateMapper.selectByPrimaryKey(Integer.parseInt(questionId));
		correctRate.setWrong(correctRate.getWrong()+1);
		correctRateMapper.updateByPrimaryKey(correctRate);
		if (correctRate.getAllNum()!=0) {
			Answer answer = answerMapper.selectByPrimaryKey(Integer.parseInt(questionId));
			answer.setTrueRate((double)(1-correctRate.getWrong()/correctRate.getAllNum()));
			answerMapper.updateByPrimaryKeySelective(answer);
		}
		int insert = userWrongMapper.insert(userWrong);
		
		//练习成功率。
		if(insert!=1) 
			builder.success(false)
			.msg("提交失败");
		else 
			builder.success(true)
			.msg("提交成功");
		return builder;
	}
	
}
