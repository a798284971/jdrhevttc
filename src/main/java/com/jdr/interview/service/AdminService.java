package com.jdr.interview.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.jdr.interview.bean.AdminQuestionBean;
import com.jdr.interview.bean.BusinessMessageBuilder;
import com.jdr.interview.bean.SystemStatusBean;
import com.jdr.interview.conf.WebMvcConfig;
import com.jdr.interview.entity.Adminer;
import com.jdr.interview.entity.Answer;
import com.jdr.interview.entity.ChooseQuestion;
import com.jdr.interview.entity.Feedback;
import com.jdr.interview.entity.Lunbo;
import com.jdr.interview.entity.Message;
import com.jdr.interview.entity.UserStatus;
import com.jdr.interview.entity.ZxExercise;
import com.jdr.interview.mapper.AdminerMapper;
import com.jdr.interview.mapper.AnswerMapper;
import com.jdr.interview.mapper.ChooseQuestionMapper;
import com.jdr.interview.mapper.FeedbackMapper;
import com.jdr.interview.mapper.LunboMapper;
import com.jdr.interview.mapper.MessageMapper;
import com.jdr.interview.mapper.UserMapper;
import com.jdr.interview.mapper.UserStatusMapper;
import com.jdr.interview.mapper.ZxExerciseMapper;
import com.jdr.interview.utils.DateUtil;
import com.jdr.interview.utils.OssUtils;

import tk.mybatis.mapper.entity.Example;

@Service
public class AdminService {
	private final Logger log = LoggerFactory.getLogger(AdminService.class);
	@Autowired
	private AdminerMapper adminMapper;
	@Autowired
	private ZxExerciseMapper exerciseMapper;
	@Autowired
	private ChooseQuestionMapper questionMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AnswerMapper answerMapper;
	@Autowired
	private LunboMapper lunboMapper;
	@Autowired
	private MessageMapper msgMapper;
	@Autowired
	private UserStatusMapper userStatusMapper;
	@Autowired
	private FeedbackMapper feedMapper;
	public BusinessMessageBuilder<String> check(String user, String password, HttpSession session) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		Adminer adminer = new Adminer();
		adminer.setName(user);
		adminer.setPassword(password);
		List<Adminer> select = adminMapper.select(adminer);
		if(select==null || select.size()==0) {
			builder.msg("账号或密码错误")
			.success(false);
		}else {
			builder.msg("登录成功")
			.success(true);
			// 设置session
			session.setAttribute(WebMvcConfig.SESSION_KEY, user);
		}
		
		return builder;
	}
	
	public BusinessMessageBuilder<String> addTitle(String title, String superioe) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		ZxExercise zxExercise = new ZxExercise();
		zxExercise.setTitle(title);
		zxExercise.setSuperioe(Integer.parseInt(superioe));
		int insert = exerciseMapper.insert(zxExercise);
		if(insert!=1) {
			builder.msg("添加失败")
			.success(false);
		}else {
			builder.msg("添加成功")
			.success(true);
		}
		return builder;
	}
	public BusinessMessageBuilder<String> updateTitle(String id, String title) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		ZxExercise zxExercise = new ZxExercise();
		zxExercise.setId(Integer.parseInt(id));
		zxExercise.setTitle(title);
		int insert = exerciseMapper.updateByPrimaryKeySelective(zxExercise);
		if(insert!=1) {
			builder.msg("修改失败")
			.success(false);
		}else {
			builder.msg("修改成功")
			.success(true);
		}
		return builder;
	}

	public BusinessMessageBuilder<String> deleTitle(String id) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		int insert = exerciseMapper.deleteByPrimaryKey(Integer.parseInt(id));
		if(insert!=1) {
			builder.msg("删除失败")
			.success(false);
		}else {
			builder.msg("删除成功")
			.success(true);
		}
		return builder;
	}

	public BusinessMessageBuilder<SystemStatusBean> getStatus() {
		BusinessMessageBuilder<SystemStatusBean> builder = new BusinessMessageBuilder<>();
		SystemStatusBean bean = new SystemStatusBean();
		bean.setUserNum(userMapper.selectAll().size());
		bean.setTestNum(questionMapper.selectAll().size());
		Example example = new Example(UserStatus.class);
		Date date=new Date();  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -2);  
        date = calendar.getTime();  
		example.createCriteria().andBetween("timeStamp", DateUtil.getTimesTamp(date),
													DateUtil.getTimesTamp(new Date()));
		int signNum = userStatusMapper.selectCountByExample(example);
		bean.setSignNum(signNum);
		builder.success(true)
		.msg("查询成功")
		.data(bean);
		return builder;
	}

	public BusinessMessageBuilder<List<ZxExercise>> getExe(String superiod) {
		BusinessMessageBuilder<List<ZxExercise>> builder = new BusinessMessageBuilder<>();
		ZxExercise zxExercise = new ZxExercise();
		zxExercise.setSuperioe(Integer.parseInt(superiod));
		List<ZxExercise> select = exerciseMapper.select(zxExercise);
		builder.data(select)
		.msg("查询成功")
		.success(true);
		return builder;
	}

	public BusinessMessageBuilder<List<AdminQuestionBean>> getQuestion(String superioe) {
		BusinessMessageBuilder<List<AdminQuestionBean>> builder = new BusinessMessageBuilder<>();
		Example example = new Example(ChooseQuestion.class);
		example.createCriteria().andEqualTo("superioe", Integer.parseInt(superioe));
		List<ChooseQuestion> list = questionMapper.selectByExample(example);
		ArrayList<AdminQuestionBean> arrayList = new ArrayList<AdminQuestionBean>();
		for(ChooseQuestion bean:list) {
			AdminQuestionBean adminQuestionBean = new AdminQuestionBean();
			adminQuestionBean.setId(bean.getId());
			adminQuestionBean.setQuestion(bean.getQuestion());
			adminQuestionBean.setA(bean.getA());
			adminQuestionBean.setB(bean.getB());
			adminQuestionBean.setC(bean.getC());
			adminQuestionBean.setD(bean.getD());
			adminQuestionBean.setPic(bean.getPic());
			adminQuestionBean.setType(bean.getType());
			adminQuestionBean.setA(bean.getA());
			Answer answer = answerMapper.selectByPrimaryKey(bean.getId());
			adminQuestionBean.setAnswer(answer.getAnswer());
			adminQuestionBean.setAnalysis(answer.getAnalysis());
			arrayList.add(adminQuestionBean);
		}	
		if(arrayList.size()!=0 || arrayList!=null)
			builder.success(true)
			.msg("查询成功")
			.success(true)
			.data(arrayList);
		else
			builder.success(false)
			.msg("查询失败");
		return builder;
	}

	public BusinessMessageBuilder<String> deleQuestion(String id) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		int key = questionMapper.deleteByPrimaryKey(Integer.parseInt(id));
		if(key!=1) 
			builder.msg("删除失败")
			.success(false);
		else
			builder.msg("删除成功")
			.success(true);
		
		return builder;
	}

	public BusinessMessageBuilder<String> addQuestion(String superioe, String question, String a, String b, String c,
			String d, String type, String answer, String analysis) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		ChooseQuestion chooseQuestion = new ChooseQuestion();
		chooseQuestion.setA(a);
		chooseQuestion.setB(b);
		chooseQuestion.setC(c);
		chooseQuestion.setD(d);
		chooseQuestion.setHaspic(0);
		chooseQuestion.setQuestion(question);
		chooseQuestion.setSuperioe(Integer.parseInt(superioe));
		chooseQuestion.setType(Integer.parseInt(type));
		int insertSelective = questionMapper.insertSelective(chooseQuestion);
		ChooseQuestion key = questionMapper.selectOne(chooseQuestion);
		Answer answer2 = new Answer();
		answer2.setAnswer(answer);
		answer2.setQuestionId(key.getId());
		answer2.setAnalysis(analysis);
		answer2.setTrueRate(0.0);
		int insertSelective2 = answerMapper.insertSelective(answer2);
		if(insertSelective!=0 && insertSelective2!=0) 
			builder.success(true)
			.msg("插入成功");
		else
			builder.success(false)
			.msg("插入失败");
		return builder;
	}
	
	public BusinessMessageBuilder<String> addLunbo(String content, MultipartFile file) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		String putObject = "";
		try {
			putObject = OssUtils.putObject("resouce", file.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(StringUtils.isEmpty(putObject)) {
			builder.success(false)
			.msg("添加失败");
		}else {
			Lunbo lunbo = new Lunbo();
			lunbo.setContent(content);
			lunbo.setImgsrc(putObject);
			int insert = lunboMapper.insert(lunbo);
			if(insert!=0)
				builder.success(true)
				.msg("添加成功");
			else
				builder.success(false)
				.msg("添加失败");
		}
		return builder;
	}

	public BusinessMessageBuilder<String> updateLunbo(String id,String content, MultipartFile file) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		Lunbo lunbo = new Lunbo();
		lunbo.setId(Integer.parseInt(id));
		lunbo.setContent(content);
		if(file!=null) {
			String putObject = "";
			try {
				putObject = OssUtils.putObject("resouce", file.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(StringUtils.isEmpty(putObject)) {
				builder.msg("文件上传失败");
				builder.success(false);
				return builder;
			}else {
				lunbo.setImgsrc(putObject);
			}
		}
		int update = lunboMapper.updateByPrimaryKeySelective(lunbo);
		if(update!=0)
			builder.msg("修改成功")
			.success(true);
		else
			builder.msg("修改失败")
			.success(false);
		return builder;
	}

	public BusinessMessageBuilder<String> deleLunbo(String id) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		int delete = lunboMapper.deleteByPrimaryKey(Integer.parseInt(id));
		if(delete!=0)
			builder.success(true)
			.msg("删除成功");
		else
			builder.msg("删除失败")
			.success(false);
		return builder;
	}

	public BusinessMessageBuilder<String> addMessage(String title, String content) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		Message message = new Message();
		message.setTitle(title);
		message.setContent(content);
		String date = DateUtil.getStringDate(new Date());
		message.setCreateTime(date);
		int insert = msgMapper.insert(message);
		if(insert!=0)
			builder.msg("添加成功")
			.success(true);
		else
			builder.msg("添加失败")
			.success(false);
		return builder;
	}

	public BusinessMessageBuilder<String> deleMessage(String id) {
		BusinessMessageBuilder<String> builder = new BusinessMessageBuilder<>();
		int key = msgMapper.deleteByPrimaryKey(Integer.parseInt(id));
		if(key!=0)
			builder.msg("删除成功")
			.success(true);
		else
			builder.msg("删除失败")
			.success(false);
		return builder;
	}

	public BusinessMessageBuilder<List<Feedback>> getAllFeedBack() {
		BusinessMessageBuilder<List<Feedback>> builder = new BusinessMessageBuilder<>();
		Example example = new Example(Feedback.class);
		example.setOrderByClause("create_time DESC");
		List<Feedback> selectByExample = feedMapper.selectByExample(example);
		builder.data(selectByExample)
				.success(true);
		return builder;
	}
	
}
