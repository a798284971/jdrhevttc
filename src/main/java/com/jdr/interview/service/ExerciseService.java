package com.jdr.interview.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdr.interview.bean.BusinessMessageBuilder;
import com.jdr.interview.bean.ExericiseListBean;
import com.jdr.interview.bean.ExericiseListItemBean;
import com.jdr.interview.bean.UserAnswerBean;
import com.jdr.interview.entity.Answer;
import com.jdr.interview.entity.ChooseQuestion;
import com.jdr.interview.entity.CorrectRate;
import com.jdr.interview.entity.UserColllect;
import com.jdr.interview.entity.UserSetting;
import com.jdr.interview.entity.UserStatus;
import com.jdr.interview.entity.UserTeststatus;
import com.jdr.interview.entity.UserWrong;
import com.jdr.interview.entity.ZxExercise;
import com.jdr.interview.mapper.AnswerMapper;
import com.jdr.interview.mapper.ChooseQuestionMapper;
import com.jdr.interview.mapper.CorrectRateMapper;
import com.jdr.interview.mapper.UserColllectMapper;
import com.jdr.interview.mapper.UserSettingMapper;
import com.jdr.interview.mapper.UserStatusMapper;
import com.jdr.interview.mapper.UserTeststatusMapper;
import com.jdr.interview.mapper.UserWrongMapper;
import com.jdr.interview.mapper.ZxExerciseMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ExerciseService {
	public static final String EXAM_TYPY_ALLEXERCISE= "0";
	public static final String EXAM_TYPE_NEWEXERCISE= "1";
	public static final String Exam_TYPE_WRONGEXERCISE= "2";
	@Autowired
	private ZxExerciseMapper exeriseMapper;
	@Autowired
	private ChooseQuestionMapper chooseMapper;
	@Autowired
	private AnswerMapper answerMapper;
	@Autowired
	private CorrectRateMapper correctMapper;
	@Autowired
	private UserStatusMapper statusMapper;
	@Autowired
	private UserSettingMapper userSettingMapper;
	@Autowired
	private UserWrongMapper wrongMapper;
	@Autowired
	private UserTeststatusMapper userTestStatusMapper;
	@Autowired
	private UserWrongMapper userWrongMapper;
	@Autowired
	private UserColllectMapper userCollectMapper;
	
	public BusinessMessageBuilder<List<ExericiseListBean>> getExeriseList() {
		BusinessMessageBuilder<List<ExericiseListBean>> builder = new BusinessMessageBuilder<List<ExericiseListBean>>();
		Example example = new Example(ZxExercise.class);
		example.createCriteria().andEqualTo("superioe", 0);
		List<ZxExercise> exampleList = exeriseMapper.selectByExample(example);
		ArrayList<ExericiseListBean> data = new ArrayList<ExericiseListBean>();
		for(ZxExercise bean:exampleList) {
			ExericiseListBean exericiseBean = new ExericiseListBean();
			exericiseBean.setId(bean.getId());
			exericiseBean.setTitle(bean.getTitle());
			Example cti = new Example(ZxExercise.class);
			cti.createCriteria().andEqualTo("superioe", bean.getId());
			List<ZxExercise> tempList = exeriseMapper.selectByExample(cti);
			
			exericiseBean.setDataList(tempList);
			data.add(exericiseBean);
		}
		builder.data(data);
		builder.success(true);
		builder.msg("查询成功");
		return builder;
	}
	public BusinessMessageBuilder<List<ExericiseListBean>> getExeriseListByUid(String uid) {
		BusinessMessageBuilder<List<ExericiseListBean>> builder = new BusinessMessageBuilder<List<ExericiseListBean>>();
		Example example = new Example(ZxExercise.class);
		example.createCriteria().andEqualTo("superioe", 0);
		List<ZxExercise> exampleList = exeriseMapper.selectByExample(example);
		ArrayList<ExericiseListBean> data = new ArrayList<ExericiseListBean>();
		for(ZxExercise bean:exampleList) {
			ExericiseListBean exericiseBean = new ExericiseListBean();
			exericiseBean.setId(bean.getId());
			exericiseBean.setTitle(bean.getTitle());
			Example cti = new Example(ZxExercise.class);
			cti.createCriteria().andEqualTo("superioe", bean.getId());
			List<ZxExercise> tempList = exeriseMapper.selectByExample(cti);
			List<ExericiseListItemBean> beanDatas = new ArrayList<ExericiseListItemBean>();
			for (ZxExercise zxExercise : tempList) {
				ChooseQuestion chooseQuestion = new ChooseQuestion();
				chooseQuestion.setSuperioe(zxExercise.getId());
				List<ChooseQuestion> select = chooseMapper.select(chooseQuestion);
				ExericiseListItemBean bean2 = new ExericiseListItemBean();
				bean2.setId(zxExercise.getId());
				bean2.setTitle(zxExercise.getTitle());
				bean2.setQuestionNum(select.size());
				bean2.setSuperioe(zxExercise.getSuperioe());
				
				//查找用户的做题率
				UserTeststatus userTeststatus = new UserTeststatus();
				userTeststatus.setExerId(zxExercise.getId());
				userTeststatus.setUid(Integer.parseInt(uid));
				List<UserTeststatus> select2 = userTestStatusMapper.select(userTeststatus);
			
				if(select2.size()==0) {
					bean2.setIsStudyNum(0);
					bean2.setRightRate(0.0);
				}else {
					UserTeststatus teststatus = select2.get(0);
					bean2.setIsStudyNum(teststatus.getStudyNum());
					bean2.setRightRate(teststatus.getRightRate());
				}
				
				beanDatas.add(bean2);
			}
			exericiseBean.setDataList(beanDatas);
			data.add(exericiseBean);
		}
		builder.data(data);
		builder.success(true);
		builder.msg("查询成功");
		return builder;
	}
	public BusinessMessageBuilder<List<ChooseQuestion>> getExericiseExamList(String id){
		BusinessMessageBuilder<List<ChooseQuestion>> builder = new BusinessMessageBuilder<List<ChooseQuestion>>();
		Example example = new Example(ChooseQuestion.class);
		example.createCriteria().andEqualTo("superioe",Integer.parseInt(id));
		List<ChooseQuestion> data = chooseMapper.selectByExample(example);
		builder.data(data);
		builder.success(true);
		builder.msg("查找成功");
		return builder;
	} 
	public BusinessMessageBuilder<List<ChooseQuestion>> getExeRiciseById(String[] ids){
		BusinessMessageBuilder<List<ChooseQuestion>> builder = new BusinessMessageBuilder<List<ChooseQuestion>>();
		Example example = new Example(ChooseQuestion.class);
		List<String> list = Arrays.asList(ids);
		example.createCriteria().andIn("id", list);
		List<ChooseQuestion> selectByExample = chooseMapper.selectByExample(example);
		builder.data(selectByExample)
		.msg("查找成功")
		.success(true);
		return builder;
	}
	@Transactional
	public BusinessMessageBuilder<List<UserAnswerBean>> getAnswer(String uid, String[] ids) {
		BusinessMessageBuilder<List<UserAnswerBean>> builder = new BusinessMessageBuilder<List<UserAnswerBean>>();
		Example example = new Example(Answer.class);
		List<String> list = Arrays.asList(ids);
		
		example.createCriteria().andIn("questionId", list);
		List<Answer> selectByExample = answerMapper.selectByExample(example);
		Example rateExample = new Example(CorrectRate.class);
		rateExample.createCriteria().andIn("questionId", list);
		List<CorrectRate> rateList = correctMapper.selectByExample(rateExample);
		for(CorrectRate temp:rateList) {
			temp.setAllNum(temp.getAllNum()+1);
			correctMapper.updateByPrimaryKey(temp);
		}
		UserStatus key = statusMapper.selectByPrimaryKey(Integer.parseInt(uid));
		key.setQuestionNum(key.getQuestionNum()+list.size());
		statusMapper.updateByPrimaryKeySelective(key);
		
		//练习状态
		HashMap<Integer,Integer> hashMap = new HashMap<Integer,Integer>();
		for (String data : list) {
			Integer superioe = chooseMapper.selectByPrimaryKey(Integer.parseInt(data)).getSuperioe();
			if(hashMap.get(superioe)==null) {
				hashMap.put(superioe, 1);
			}else {
				hashMap.replace(superioe, hashMap.get(superioe)+1);
			}	
		}
		for(Integer temp: hashMap.keySet()) {
			UserTeststatus userTeststatus = new UserTeststatus();
			userTeststatus.setUid(Integer.parseInt(uid));
			userTeststatus.setExerId(temp);
			List<UserTeststatus> select = userTestStatusMapper.select(userTeststatus);
			
			UserWrong userWrong = new UserWrong();
			userWrong.setUserId(Integer.parseInt(uid));
			userWrong.setSuperioe(temp);
			List<UserWrong> select2 = userWrongMapper.select(userWrong);
			
			//List<ChooseQuestion> questions = chooseMapper.selectByExample(new Example(ChooseQuestion.class).createCriteria().andEqualTo("superioe",temp));
			Example example2 = new Example(ChooseQuestion.class);
			example2.createCriteria().andEqualTo("superioe",temp);
			List<ChooseQuestion> questions  = chooseMapper.selectByExample(example2);
			if(select==null||select.size()==0) {
				userTeststatus.setStudyNum(1);
				if(select2.size()==0)
					userTeststatus.setRightRate(0.0);
				else
					userTeststatus.setRightRate(1.0-(select2.size()/questions.size()));
				userTestStatusMapper.insert(userTeststatus);
			}else {
				userTeststatus.setStudyNum(hashMap.get(temp));
				userTestStatusMapper.updateByPrimaryKeySelective(userTeststatus);
			}
			
		}
		UserColllect userColllect = new UserColllect();
		userColllect.setUserId(Integer.parseInt(uid));
		List<UserColllect> select = userCollectMapper.select(userColllect);
		ArrayList<Integer> tempStrings = new ArrayList<Integer>();
		for (UserColllect userColllect2 : select) {
			tempStrings.add(userColllect2.getQuestionId());
		}
		ArrayList<UserAnswerBean> arrayList = new ArrayList<UserAnswerBean>();
		for (Answer answerBean : selectByExample) {
			UserAnswerBean userAnswerBean = new UserAnswerBean();
			userAnswerBean.setQuestionId(answerBean.getQuestionId());
			userAnswerBean.setAnalysis(answerBean.getAnalysis());
			userAnswerBean.setAnswer(answerBean.getAnswer());
			userAnswerBean.setTrueRate(answerBean.getTrueRate());
			userAnswerBean.setCollect(tempStrings.contains(answerBean.getQuestionId()));
			arrayList.add(userAnswerBean);
		}
		builder.data(arrayList)
		.msg("查找成功")
		.success(true);
		return builder;
	}

	public BusinessMessageBuilder<List<ChooseQuestion>> getExamexercise(String uid,String superioe) {
		BusinessMessageBuilder<List<ChooseQuestion>> builder = new BusinessMessageBuilder<List<ChooseQuestion>>();
		Example example = new Example(ChooseQuestion.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("superioe",Integer.parseInt(superioe));
		
		List<ChooseQuestion> data = chooseMapper.selectByExample(example);
		builder.data(getRandomList(data, 10));
		builder.success(true);
		builder.msg("查找成功");
		return builder;
	}
	/**
     * @function:从list中随机抽取若干不重复元素
     *
     * @param paramList:被抽取list
     * @param count:抽取元素的个数
     * @return:由抽取元素组成的新list
     */
    public  List getRandomList(List paramList,int count){
        if(paramList.size()<count){
            return paramList;
        }
        Random random=new Random();
        List<Integer> tempList=new ArrayList<Integer>();
        List newList=new ArrayList<>();
        int temp=0;
        for(int i=0;i<count;i++){
            temp=random.nextInt(paramList.size());//将产生的随机数作为被抽list的索引
            if(!tempList.contains(temp)){
                tempList.add(temp);
                newList.add(paramList.get(temp));
            }
            else{
                i--;
            }   
        }
        return newList;
    }
	public BusinessMessageBuilder<List<ChooseQuestion>> getExamQuestion(String uid) {
		BusinessMessageBuilder<List<ChooseQuestion>> builder = new BusinessMessageBuilder<List<ChooseQuestion>>();
		UserStatus key = statusMapper.selectByPrimaryKey(Integer.parseInt(uid));
		key.setStudyNum(key.getStudyNum()+1);
		statusMapper.updateByPrimaryKeySelective(key);
		
		Example example = new Example(ChooseQuestion.class);
		Criteria criteria = example.createCriteria();
		
		UserSetting settingKey = userSettingMapper.selectByPrimaryKey(Integer.parseInt(uid));
		
		
		if(settingKey.getExamType().equals(EXAM_TYPE_NEWEXERCISE)) {
			Example wrongExample = new Example(UserWrong.class);
			wrongExample.createCriteria().andEqualTo("userId",Integer.parseInt(uid));
			List<UserWrong> wrongList = wrongMapper.selectByExample(wrongExample);
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(UserWrong bean:wrongList) {
				list.add(bean.getQuestionId());
			}
			criteria.andNotIn("id", list);
		}
		if(settingKey.getExamType().equals(Exam_TYPE_WRONGEXERCISE)) {
			Example wrongExample = new Example(UserWrong.class);
			wrongExample.createCriteria().andEqualTo("userId",Integer.parseInt(uid));
			List<UserWrong> wrongList = wrongMapper.selectByExample(wrongExample);
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(UserWrong bean:wrongList) {
				list.add(bean.getQuestionId());
			}
			criteria.andIn("id", list);
		}
		
		criteria.andEqualTo("type", 0);
		List<ChooseQuestion> data = chooseMapper.selectByExample(example);
		criteria.getAllCriteria().remove(criteria.getAllCriteria().size()-1);
		criteria.andEqualTo("type", 1);
		List<ChooseQuestion> data1 = chooseMapper.selectByExample(example);
		int singleN = settingKey.getExamNum()*4/5;
		int doubleN = settingKey.getExamNum()-singleN;
		List randomList = getRandomList(data, singleN);
		randomList.addAll(getRandomList(data1, doubleN));
		builder.data(randomList);
		builder.success(true);
		builder.msg("查找成功");
		return builder;
	}
}
