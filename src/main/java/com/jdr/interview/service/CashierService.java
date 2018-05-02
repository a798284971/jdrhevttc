/*package com.jdr.interview.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.ceg.domain.*;
import com.songpo.ceg.entity.*;
import com.songpo.ceg.mapper.*;
import com.songpo.ceg.security.MyUserDetailsService;
import com.songpo.ceg.service.exception.ServiceException;
import com.songpo.ceg.utils.SecurityUtil;
import com.songpo.ceg.utils.date.DateStyle;
import com.songpo.ceg.utils.date.DateUtil;
import com.songpo.ceg.utils.utils;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

@Service
@Slf4j
public class CashierService {
	private final Logger log = LoggerFactory.getLogger(CashierService.class);
    @Autowired
    private CommonService commonService;

    @Autowired
    private MedicalReservationMapper medicalReservationMapper;//预约、挂号表

    @Autowired
    private MedicalTreatmentRecordMapper medicalTreatmentRecordMapper;//就诊记录

    @Autowired
    private MedicalTreatmentRecordDetailMapper medicalTreatmentRecordDetailMapper;//治疗明细

    @Autowired
    private MedicalTreatmentTollRecordMapper medicalTreatmentTollRecordMapper;//治疗项与收费单关联关系

    @Autowired
    private MedicalTollRecordMapper medicalTollRecordMapper;//收费单

    @Autowired
    private MedicalRechargeRecordMapper medicalRechargeRecordMapper;//充值单

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;//病历（患者）

    @Autowired
    private MedicalRefundRecordMapper medicalRefundRecordMapper;//退费单

    @Autowired
    private MedicalDrugsMapper medicalDrugsMapper;

    @Autowired
    private ManageUserMapper manageUserMapper;

    @Autowired
    private MedicalDrugsRecordsMapper medicalDrugsRecordsMapper;

    @Autowired
    private MedicalDrugsRecordsDetailMapper medicalDrugsRecordsDetailMapper;
    @Autowired
    private MedicalRemovecardMapper medicalRemovecardMapper;

    @Autowired
    private MedicalPosMapper medicalPosMapper;


    public void findTreatmentTollRecords() {
        List<MedicalTreatmentTollRecord> treatmentTollRecords = medicalTreatmentTollRecordMapper.findTreatmentTollRecords(null);

    }

    *//**
     * 查找历史上收费记录
     *
     * @param value
     * @param date1
     * @param date2
     * @param page
     * @param size
     * @return
     *//*
    public BusinessMessageBuilder<PageInfo<MedicalTollRecordDTO>> findChargeHistory(String value, String date1, String date2, Integer page, Integer size) {
        size = 10;
        BusinessMessageBuilder<PageInfo<MedicalTollRecordDTO>> builder = new BusinessMessageBuilder<>();
        try {
            if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)) {
                date1 = null;
                date2 = null;
                builder.success(false);
                if (StringUtils.isEmpty(value)) {
                    return builder;
                }
            } else {
                date1 += " 00:00:00";
                date2 += " 23:59:59";
            }
            if (StringUtils.isEmpty(value)) {
                value = null;
            } else {
                value = "%" + value + "%";
            }

            List<MedicalTollRecordDTO> list = medicalTollRecordMapper.findChargeHistory(value, date1, date2, null);

            List<MedicalTollRecordDTO> listToSize = new LinkedList<>();
            int endLength = page * size > list.size() ? list.size() : page * size;
            for (int i = (page - 1) * size; i < endLength; i++) {
                listToSize.add(list.get(i));
            }
//
            PageInfo<MedicalTollRecordDTO> pageInfo = new PageInfo<>(listToSize);
            pageInfo.setTotal(list.size());
            pageInfo.setPages(list.size() / size + ((list.size() / size) == 0 ? 0 : 1));
            builder.data(pageInfo);
            builder.msg("Succcess");
            builder.success(true);
        } catch (Exception e) {
            builder.success(false);
            e.printStackTrace();
        }
        return builder;
    }

    *//**
     * 根据就诊记录ID查询所有为收费项目
     *
     * @param id
     * @return
     *//*
    @Transactional
    public BusinessMessageBuilder<JSONObject> findUnpaidDetails(Integer id, Integer flag) {
        BusinessMessageBuilder<JSONObject> builder = new BusinessMessageBuilder<>();
        List<MedicalTreatmentRecordRecordDTO> list = medicalTreatmentRecordMapper.findUnpaidDetails(id, 0);
        if (flag != null) {
            list.removeIf(new Predicate<MedicalTreatmentRecordRecordDTO>() {
                @Override
                public boolean test(MedicalTreatmentRecordRecordDTO medicalTreatmentRecordRecordDTO) {
                    boolean bflag = flag.equals(0);//0代表不显示治疗项、检查项、药品
                    switch (medicalTreatmentRecordRecordDTO.getMedicalTreatmentRecordDetail().getCategory()) {
                        case "耗材":
                            return false;
                        case "手术":
                            return !bflag;
                        case "检查项":
                        case "治疗项":
                        case "药品":
                            return bflag;
                    }
                    return false;
                }
            });
        }
        JSONObject json = new JSONObject();
        json.put("list", list);
        builder.data(json);
        builder.success(true);
        return builder;
    }

    *//**
     * 查找所有未收费项目
     *
     * @param value     姓名、手机号、病历号
     * @param tollState 状态
     * @param date1     开始时间
     * @param date2     结束时间
     * @param page
     * @param size
     * @return
     *//*
    @Transactional
    public BusinessMessageBuilder<PageInfo<MedicalTreatmentRecordRecordDTO>> findUnpaid(String value, Integer tollState, String date1, String date2, Integer page, Integer size) {

        if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)) {
            date1 = DateUtil.DateToString(DateUtil.getDayStartTime(new Date()), DateStyle.YYYY_MM_DD_HH_MM_SS);
            date2 = DateUtil.DateToString(DateUtil.getDayEndTime(new Date()), DateStyle.YYYY_MM_DD_HH_MM_SS);
        } else {
            date1 += " 00:00:00";
            date2 += " 23:59:59";
        }
        if (StringUtils.isEmpty(value)) {
            value = null;
        } else {
            value = "%" + value + "%";
        }
        BusinessMessageBuilder<PageInfo<MedicalTreatmentRecordRecordDTO>> builder = new BusinessMessageBuilder<>();
        try {
     
            List<MedicalTreatmentRecordRecordDTO> list = medicalTreatmentRecordMapper.findUnpaid(value, tollState, date1, date2);
            Predicate<MedicalTreatmentRecordRecordDTO> filter = new Predicate<MedicalTreatmentRecordRecordDTO>() {
                public boolean test(MedicalTreatmentRecordRecordDTO medicalTreatmentRecordDTO) {
                    BigDecimal totalPrice = medicalTreatmentRecordDTO.getTotalPrice();
                    if (totalPrice == null) {
                        return true;
                    }
                    return false;
                }
            };
            list.removeIf(filter);
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
            PageInfo<MedicalTreatmentRecordRecordDTO> pageInfo = new PageInfo<>(list);
            builder.data(pageInfo);
            builder.success(true);
        } catch (Exception e) {
            builder.success(false);
            e.printStackTrace();
        }
        return builder;
    }

    *//**
     * 新增收费单
     * 治疗项与收退费单关联关系
     *
     * @param medicalTollRecord 收费单实体
     * @return
     *//*
    @Transactional
    public BusinessMessage<Integer> saveUnpaid(Integer[] checkboxvalue, MedicalTollRecord medicalTollRecord) {
        BusinessMessage<Integer> business = new BusinessMessage<>();
        business.setSuccess(false);
        business.setMsg("收费单支付失败，请重试");
        try {
            {//获取病例Id
                MedicalTreatmentRecordDetail medicalTreatmentRecordDetail1 = medicalTreatmentRecordDetailMapper.selectByPrimaryKey(checkboxvalue[0]);
                MedicalTreatmentRecord medicalTreatmentRecord = medicalTreatmentRecordMapper.selectByPrimaryKey(medicalTreatmentRecordDetail1.getTreatmentRecordId());
                MedicalRecord medicalRecord = medicalRecordMapper.selectByPrimaryKey(medicalTreatmentRecord.getMedicalRecordId());
                System.out.println("TEST:" + medicalTollRecord.getPaymentBy());
                switch (medicalTollRecord.getPaymentBy()) {
                    case "余额":
                        System.out.println("TEST:" + medicalTollRecord.getPaymentBy());
                        BigDecimal amount = medicalRecord.getAmount();
                        amount = amount.subtract(medicalTollRecord.getAmount());
                        if (amount.doubleValue() < 0) {
                            business.setMsg("余额不足");
                            throw new ServiceException("余额不足");
                        }
                        medicalRecord.setAmount(amount);
                        medicalRecordMapper.updateByPrimaryKey(medicalRecord);
                        break;
                    default:
                        System.out.println("TEST:error");
                }
                medicalTollRecord.setSerialNumber(commonService.getSequence("SEQ_TOLL_SERIAL_NUMBER"));
            }
            medicalTollRecord.setRecordDate(new Date());
            medicalTollRecordMapper.insert(medicalTollRecord);
            MedicalTreatmentTollRecord medicalTreatmentTollRecord;
            BigDecimal sum = new BigDecimal(0);
            for (int i = 0; i < checkboxvalue.length; i++) {
                medicalTreatmentTollRecord = new MedicalTreatmentTollRecord();//关系表
                medicalTreatmentTollRecord.setTreatmentRecordDetailId(checkboxvalue[i]);
                medicalTreatmentTollRecord.setTollRecordId(medicalTollRecord.getId());
                medicalTreatmentTollRecord.setItemFlay(0);
                medicalTreatmentTollRecordMapper.insert(medicalTreatmentTollRecord);//新增关系表
                MedicalTreatmentRecordDetail medicalTreatmentRecordDetail = medicalTreatmentRecordDetailMapper.selectByPrimaryKey(checkboxvalue[i]);
                medicalTreatmentRecordDetail.setTollState(1);
                sum = sum.add(medicalTreatmentRecordDetail.getTotalPrice());
                medicalTreatmentRecordDetailMapper.updateByPrimaryKey(medicalTreatmentRecordDetail);//更新明细表
            }
            medicalTollRecord.setAmount(sum);
            medicalTollRecordMapper.updateByPrimaryKey(medicalTollRecord);//更新收费单
            business.setSuccess(true);
            business.setMsg("支付成功");
        } catch (Exception e) {
            log.error("收费单插入失败，请重试" + e.getMessage());
            e.printStackTrace();
            throw new ServiceException(business.getMsg());
        }
        return business;
    }


    //通过患者姓名、病例ID、手机号获取金额
    public BusinessMessage<JSONObject> getAmount(String idCard) {
        BusinessMessageBuilder<JSONObject> builder = new BusinessMessageBuilder<>();
        builder.success(false);
        builder.msg("该用户信息不存在，请重试");
        try {
            if (!(StringUtils.isEmpty(idCard))) {
                {
                    MedicalRecord oneMedicalRecord = getOneMedicalRecord(idCard);
                    if (oneMedicalRecord.getAmount() == null) {
                        builder.msg("该用户余额信息异常，请重试");
                        throw new ServiceException("该用户余额信息异常，请重试");
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("bean", oneMedicalRecord);
                    builder.data(jsonObject);
                    builder.success(true);
                }
            } else {

                builder.msg("该用户信息不存在，请重试");
                builder.data(null);
            }
        } catch (Exception e) {

            builder.data(null);
        }
        return builder.build();
    }

    private MedicalRecord getOneMedicalRecord(String idCard) {//输入身份证号获取病例信息
        Example example = new Example(MedicalRecord.class);
        example.or().andEqualTo("idCard", idCard);
        List<MedicalRecord> medicalRecords = medicalRecordMapper.selectByExample(example);
        return medicalRecords.get(0);
    }

    private MedicalRecord getOneMedicalRecord(MedicalRecord medicalRecord) {
        Example example = new Example(MedicalRecord.class);
        example.createCriteria()
                .andEqualTo("name", medicalRecord.getName())
                .andEqualTo("medicalRecordNumber", medicalRecord.getMedicalRecordNumber())
                .andEqualTo("phone", medicalRecord.getPhone());
        List<MedicalRecord> medicalRecords = medicalRecordMapper.selectByExample(example);
        return medicalRecords.get(0);
    }

    //添加充值单
    @Transactional
    public BusinessMessageBuilder<JSONObject> addRechargeList(MedicalRecord medicalRecord, MedicalRechargeRecord medicalRechargeRecord) {
        BusinessMessageBuilder<JSONObject> builder = new BusinessMessageBuilder<>();
        if (medicalRechargeRecord.getAmount() == null) {
            builder.msg("请填写充值金额");
            return builder;
        }
        if (medicalRechargeRecord.getAmount().doubleValue() <= 0) {
            builder.msg("金额不能为负数，请重试");
            return builder;
        }
        if (StringUtils.isEmpty(medicalRecord.getName())) {
            builder.msg("缺少患者姓名参数");
            return builder;
        }
        if (StringUtils.isEmpty(medicalRecord.getMedicalRecordNumber())) {
            builder.msg("缺少患者病历编号参数");
            return builder;
        }
        if (StringUtils.isEmpty(medicalRecord.getPhone())) {
            builder.msg("缺少患者手机号码参数");
            return builder;
        }
        MedicalRecord oneMedicalRecord = getOneMedicalRecord(medicalRecord);//根据姓名、病例号、手机号同时查询用户余额
        if (oneMedicalRecord == null) {
            builder.msg("该用户信息不存在，请重试");
            builder.data(null);
        }
        if (oneMedicalRecord.getAmount() == null) {
            builder.msg("该用户余额信息异常，请重试");
            builder.data(null);
        }
        oneMedicalRecord.setAmount(oneMedicalRecord.getAmount().add(medicalRechargeRecord.getAmount()));
        medicalRecordMapper.updateByPrimaryKey(oneMedicalRecord);

        setMedicalRechargeRecord(medicalRechargeRecord, oneMedicalRecord.getId());//设置充值单充值日期、病例号、用户ID
        medicalRechargeRecordMapper.insert(medicalRechargeRecord);

        JSONObject jsObject = new JSONObject();
        jsObject.put("number", medicalRechargeRecord.getSerialNumber());
        builder.data(jsObject);
        builder.success(true);
        return builder;
    }

    //添加退卡单
    @Transactional
    public BusinessMessageBuilder<JSONObject> addRechargeRechargeList(MedicalRecord medicalRecord, MedicalRechargeRecord medicalRechargeRecord, MedicalRemovecard medicalRemovecard) {
        BusinessMessageBuilder<JSONObject> builder = new BusinessMessageBuilder<>();
        builder.success(false);
        //你这个对象时从哪儿获取的  medicalRechargeRecord
        if (medicalRechargeRecord == null || medicalRechargeRecord.getAmount() == null) {
            builder.msg("test");
            builder.data(null);
        }
        //这里空指针异常
        if (medicalRecord.getAmount().doubleValue() <= 0) {
            builder.msg("金额不能为负数，请重试");
            builder.data(null);
        } else {
            double amout = medicalRecord.getAmount().doubleValue();
            String recorderName = medicalRechargeRecord.getRecorderName().toString();
            Integer recorderId = medicalRechargeRecord.getRecorderId();
            String refundMethod = medicalRemovecard.getRefundMethod().toString();
            MedicalRecord oneMedicalRecord = getOneMedicalRecord(medicalRecord);//根据姓名、病例号、手机号同时查询用户余额
            if(oneMedicalRecord==null){
                builder.msg("退卡患者病历信息不存在");
            }else{
                oneMedicalRecord.setAmount(new BigDecimal(0));
                medicalRecordMapper.updateByPrimaryKey(oneMedicalRecord);
                medicalRemovecard.setRecorderId(recorderId);
                medicalRemovecard.setRecorderName(recorderName);
                medicalRemovecard.setMedicalRecordId(oneMedicalRecord.getId());
                medicalRemovecard.setRechargeDate(new Date());
                medicalRemovecard.setRemovecardMoney(new BigDecimal(amout));
                medicalRemovecard.setRemovecardNumber(commonService.getSequence("SEQ_REMOVE_SERIAL_NUMBER"));
                medicalRemovecard.setRemovecardTime(new Date());
                medicalRemovecardMapper.insert(medicalRemovecard);
                builder.success(true);
                builder.data(null);
                builder.msg("退卡成功");
            }
        }
        return builder;
    }

    //查找充值列表
    @Transactional
    public BusinessMessageBuilder<PageInfo<MedicalRechargeRecordDTO>> findRechargeList(String value, String date1, String date2, Integer page, Integer size) {
        BusinessMessageBuilder<PageInfo<MedicalRechargeRecordDTO>> builder = new BusinessMessageBuilder<>();
        builder.success(false);
        try {
            if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)) {
                date1 = DateUtil.DateToString(DateUtil.getDayStartTime(new Date()), DateStyle.YYYY_MM_DD_HH_MM_SS);
                date2 = DateUtil.DateToString(DateUtil.getDayEndTime(new Date()), DateStyle.YYYY_MM_DD_HH_MM_SS);
            } else {
                date1 += " 00:00:00";
                date2 += " 23:59:59";
            }
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
            List<MedicalRechargeRecordDTO> rechargeList = medicalRechargeRecordMapper.findRechargeList(value, date1, date2);
            PageInfo<MedicalRechargeRecordDTO> pageInfo = new PageInfo<>(rechargeList);
            builder.data(pageInfo);
            builder.success(true);

        } catch (Exception e) {
            builder.msg("输入信息错误，请重试");
            builder.data(null);
            log.error("输入信息错误:" + e);
            e.printStackTrace();
            throw new ServiceException("输入信息错误，请重试");
        }
        return builder;
    }

    public MedicalRecord getMedicalRecords(Integer id) {//根据ID获取患者
        Example medicalRecordExample = new Example(MedicalRecord.class);
        medicalRecordExample.createCriteria().andEqualTo("id", id);
        List<MedicalRecord> medicalRecords = medicalRecordMapper.selectByExample(medicalRecordExample);
        if (medicalRecords != null && medicalRecords.size() > 0) {
            //获取患者
            return medicalRecords.get(0);
        }
        return null;
    }

    public void setMedicalRechargeRecord(MedicalRechargeRecord medicalRechargeRecord, Integer id) {
        medicalRechargeRecord.setMedicalRecordId(id);
        medicalRechargeRecord.setRechargeDate(new Date());
        medicalRechargeRecord.setRecorderId(SecurityUtil.getCurrentUserId());
        medicalRechargeRecord.setRecorderName(SecurityUtil.getCurrentUsername());
        medicalRechargeRecord.setSerialNumber(commonService.getSequence("SEQ_CHARGE_SERIAL_NUMBER"));
    }

    //根据收费单号查找患者信息
    public BusinessMessageBuilder<JSONObject> findSerialNumber(String serialNumber) {
        BusinessMessageBuilder<JSONObject> businessMessage = new BusinessMessageBuilder<>();
        businessMessage.success(false);
        businessMessage.msg("查询失败");
        try {
            List<MedicalTollRecordDTO> chargeHistory = medicalTollRecordMapper.findChargeHistory(null, null, null, serialNumber);
            if (chargeHistory != null && chargeHistory.size() != 0) {
                MedicalTollRecordDTO medicalTollRecordDTO = chargeHistory.get(0);
                List<MedicalTreatmentTollRecordDTO> medicalTreatmentTollRecordDTOS = medicalTollRecordDTO.getMedicalTreatmentTollRecordDTOS();
                for (int i = 0; i < medicalTreatmentTollRecordDTOS.size(); i++) {
                    MedicalTreatmentTollRecordDTO e = medicalTreatmentTollRecordDTOS.get(i);
                    if (e.getMedicalTreatmentRecordDetail().getTreatmentState() == 2) {
                        businessMessage.msg("治疗项已完成");
                        throw new Exception();
                    }
                    if (e.getMedicalTreatmentRecordDetail().getTollState() == 2) {
                        businessMessage.msg("已退费");
                        throw new Exception();
                    }
                }
                System.out.println("Dongsh:TEST---" + medicalTollRecordDTO);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("medicalRecord", medicalTollRecordDTO.getMedicalRecord());
                jsonObject.put("amount", medicalTollRecordDTO.getAmount());
                jsonObject.put("paymentBy", medicalTollRecordDTO.getPaymentBy());
                jsonObject.put("cardId", medicalTollRecordDTO.getCardId());
                jsonObject.put("list", medicalTollRecordDTO.getMedicalTreatmentTollRecordDTOS());
                businessMessage.data(jsonObject);
                businessMessage.success(true);
                businessMessage.msg("成功");
            } else {
                businessMessage.msg("收费单号不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessMessage;
    }

    @Transactional//保存退费单，更新关系表以及明细表
    public BusinessMessageBuilder<JSONObject> SaveMedicalRefundRecord(MedicalRefundRecord medicalRefundRecord) {
        BusinessMessageBuilder<JSONObject> builder = new BusinessMessageBuilder<>();
        builder.success(false);
        try {
            List<MedicalTollRecordDTO> chargeHistory = medicalTollRecordMapper.findChargeHistory(null, null, null, medicalRefundRecord.getSerialNumber());
            if (chargeHistory != null && chargeHistory.size() != 0) {
                MedicalTollRecordDTO medicalTollRecordDTO = chargeHistory.get(0);
                medicalRefundRecord.setAmount(medicalTollRecordDTO.getAmount());
                medicalRefundRecord.setAccount(medicalTollRecordDTO.getCardId());
                medicalRefundRecord.setRefundDate(new Date());
                medicalRefundRecord.setRecorderDate(new Date());
                medicalRefundRecord.setTollRecordId(medicalTollRecordDTO.getId());
                medicalRefundRecord.setSerialNumber(commonService.getSequence("SEQ_REFUND_SERIAL_NUMBER"));//插入退费单号
                medicalRefundRecordMapper.insert(medicalRefundRecord);//插入退费表


                //根据收费表ID查询关系表
                Example example = new Example(MedicalTreatmentTollRecord.class);
                example.createCriteria().andEqualTo("tollRecordId", medicalTollRecordDTO.getId());
                List<MedicalTreatmentTollRecord> medicalTreatmentTollRecords = medicalTreatmentTollRecordMapper.selectByExample(example);


                if (medicalTreatmentTollRecords != null && medicalTreatmentTollRecords.size() != 0) {
                    medicalTreatmentTollRecords.forEach(e -> {
                        MedicalTreatmentRecordDetail medicalTreatmentRecordDetail = medicalTreatmentRecordDetailMapper.selectByPrimaryKey(e.getTreatmentRecordDetailId());
                        medicalTreatmentRecordDetail.setTollState(2);
                        medicalTreatmentRecordDetailMapper.updateByPrimaryKey(medicalTreatmentRecordDetail);//更新明细表

                        e.setTollRecordId(medicalRefundRecord.getId());
                        e.setItemFlay(1);
                        Example example1 = new Example(MedicalTreatmentTollRecord.class);
                        example1.createCriteria().andEqualTo("treatmentRecordDetailId", e.getTreatmentRecordDetailId());
                        medicalTreatmentTollRecordMapper.updateByExample(e, example1);//跟新关系表
                    });
                }

                builder.msg("成功");
                builder.success(true);
            } else {
                builder.msg("收费单号不存在，请重试！");
            }
        } catch (Exception e) {
            log.error("错误" + e.getMessage());
            builder.msg("系统异常，请重试");
            throw new ServiceException("系统异常，请重试");
        }
        return builder;
    }

    public BusinessMessageBuilder editMedicalRefundRecord(Integer medicalRecordId, String ids, String reason) {
        BusinessMessageBuilder builder = new BusinessMessageBuilder();
        //获取诊疗明细，包含收费单信息
        String[] idList = ids.split(",");
        if (idList == null || idList.length <= 0) {
            builder.msg("请选择需退款项");
            return builder;
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < idList.length; i++) {
            list.add(Integer.parseInt(idList[i]));
        }
        List<MedicalTreatmentRecordDetailDTO> treatmentRecordDetailDTOList = medicalTreatmentRecordDetailMapper.listRecordDetailByMedicalRecordIdAndInIds(medicalRecordId, list);
        if (treatmentRecordDetailDTOList == null || treatmentRecordDetailDTOList.size() <= 0) {
            builder.msg("未查询到选中诊疗明细信息");
            return builder;
        }
        Integer userId = SecurityUtil.getCurrentUserId();
        String userName = SecurityUtil.getCurrentUsername();
        List<MedicalTreatmentRecordDetail> treatmentRecordDetailList = new ArrayList<>();
        List<MedicalTreatmentTollRecord> treatmentTollRecordList = new ArrayList<>();
        BigDecimal sum = new BigDecimal("0.00");
        for (MedicalTreatmentRecordDetailDTO detailDTO : treatmentRecordDetailDTOList) {
            if (detailDTO.getTollState() != 1) {
                builder.msg("治疗类型为“" + detailDTO.getCategory() + "”的“" + detailDTO.getName() + "”收费状态处于" + (detailDTO.getTollState() == 0 ? "待收费" : "取消状态，不可退费"));
                return builder;
            }
            if (detailDTO.getTreatmentState() > 1) {
                builder.msg("治疗类型为“" + detailDTO.getCategory() + "”的“" + detailDTO.getName() + "”治疗状态处于" + (detailDTO.getTreatmentState() == 2 ? "完成" : "取消状态，不可退费"));
                return builder;
            }
            sum = sum.add(detailDTO.getTotalPrice().setScale(2, BigDecimal.ROUND_DOWN));

            //生成诊疗明细实体，用于更新诊疗明细收费状态是做为条件、值
            MedicalTreatmentRecordDetail treatmentRecordDetail = new MedicalTreatmentRecordDetail();
            treatmentRecordDetail.setTollState(2);
            treatmentRecordDetail.setId(detailDTO.getId());
            treatmentRecordDetailList.add(treatmentRecordDetail);

            //生成退费关系表
            MedicalTreatmentTollRecord treatmentTollRecord = new MedicalTreatmentTollRecord();
            treatmentTollRecord.setTreatmentRecordDetailId(detailDTO.getId());
            treatmentTollRecord.setItemFlay(1);
            treatmentTollRecordList.add(treatmentTollRecord);
        }

        //保存退费单实体
        MedicalRefundRecord medicalRefundRecord = new MedicalRefundRecord();
        medicalRefundRecord.setAmount(sum);//退费金额
        medicalRefundRecord.setReason(reason);//退费说明
        medicalRefundRecord.setRefundDate(new Date());
        medicalRefundRecord.setRecorderDate(new Date());
        medicalRefundRecord.setRecorderId(userId);
        medicalRefundRecord.setRecorderName(userName);
        medicalRefundRecord.setSerialNumber(commonService.getSequence("SEQ_REFUND_SERIAL_NUMBER"));//插入退费单号
        medicalRefundRecordMapper.insert(medicalRefundRecord);

        //将退费单id赋至关系表中
        for (int i = 0; i < treatmentTollRecordList.size(); i++) {
            treatmentTollRecordList.get(i).setTollRecordId(medicalRefundRecord.getId());
        }
        //添加退费单
        //  medicalRefundRecordMapper.insertRefundRecordBatch(refundRecordList);
        for(MedicalTreatmentRecordDetailDTO detailDTO:treatmentRecordDetailDTOList){
            MedicalTollRecord medicalTollRecord = detailDTO.getMedicalTollRecord();//项目充值单
            for(MedicalRefundRecord medicalRefundRecord:refundRecordList){
                if(medicalTollRecord.getId().equals(medicalRefundRecord.getTollRecordId())){
                    break;
                }
            }
        }
        //保存明细与退费关系表
        medicalTreatmentTollRecordMapper.insertTreatmentTollRecordBatch(treatmentTollRecordList);
        //修改明细表收费状态
        //medicalTreatmentRecordDetailMapper.updateTollStateBatch(treatmentRecordDetailList);//配置不支持批量更新

        for (MedicalTreatmentRecordDetail treatmentRecordDetail : treatmentRecordDetailList) {
            MedicalTreatmentRecordDetail medicalTreatmentRecordDetail = medicalTreatmentRecordDetailMapper.selectByPrimaryKey(treatmentRecordDetail.getId());
            medicalTreatmentRecordDetail.setTollState(2);
            medicalTreatmentRecordDetailMapper.updateByPrimaryKey(medicalTreatmentRecordDetail);//更新明细表
        }

        builder.success(true);
        return builder;
    }

    *//**
     * @param value
     * @param date1
     * @param date2
     * @param page
     * @param size
     * @return
     *//*
    public BusinessMessageBuilder<PageInfo<MedicalRefundRecordDTO>> listRefundRecord(String value, String date1, String date2, Integer page, Integer size) {
        BusinessMessageBuilder<PageInfo<MedicalRefundRecordDTO>> builder = new BusinessMessageBuilder<>();
        try {
            if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)) {
                date1 = DateUtil.DateToString(DateUtil.getDayStartTime(new Date()), DateStyle.YYYY_MM_DD_HH_MM_SS);
                date2 = DateUtil.DateToString(DateUtil.getDayEndTime(new Date()), DateStyle.YYYY_MM_DD_HH_MM_SS);
            } else {
                date1 += " 00:00:00";
                date2 += " 23:59:59";
            }
            if (StringUtils.isEmpty(value)) {
                value = null;
            } else {
                value = "%" + value + "%";
            }
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
            List<MedicalRefundRecordDTO> list = medicalRefundRecordMapper.listRefundRecord(value, date1, date2);
            PageInfo<MedicalRefundRecordDTO> pageInfo = new PageInfo<>(list);
            builder.data(pageInfo);
            builder.msg("Success");
            builder.success(true);
        } catch (Exception e) {
            builder.success(false);
            e.printStackTrace();
        }
        return builder;
    }

    *//**
     * 根据退款单id查询诊疗明细
     *
     * @param refundRecordId
     * @return
     *//*
    public BusinessMessageBuilder<List<MedicalTreatmentRecordDetailDTO>> listRecordDetailRefundByRefundReturnId(Integer refundRecordId) {
        BusinessMessageBuilder<List<MedicalTreatmentRecordDetailDTO>> builder = new BusinessMessageBuilder<>();
        try {
            MedicalRefundRecordDTO medicalRefundRecordDTO = new MedicalRefundRecordDTO();
            List<MedicalTreatmentRecordDetailDTO> list = medicalTreatmentRecordDetailMapper.listRecordDetailRefundByRefundReturnId(refundRecordId);
            builder.data(list);
            builder.success(true);
        } catch (Exception e) {
            builder.success(false);
            e.printStackTrace();
        }
        return builder;
    }

    *//**
     * @param value          姓名/病历号/手机号
     * @param tollState      收费状态(0-待收费/1-已收费/2-取消)
     * @param treatmentState 治疗状态(0-待治疗 1-治疗中 2-完成 3-取消）
     * @param page
     * @param size
     * @return
     *//*
    public BusinessMessageBuilder<PageInfo<TakeDruks>> findTakeDruk(String value, Integer tollState, Integer treatmentState, Integer page, Integer size) {
        BusinessMessageBuilder<PageInfo<TakeDruks>> builder = new BusinessMessageBuilder<>();
        try {
            if (StringUtils.isEmpty(value)) {
                value = null;
            } else {
                value = "%" + value + "%";
            }
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
            List<TakeDruks> list = medicalTreatmentRecordMapper.findTakeDruk(value, tollState, treatmentState, null, null);
            PageInfo<TakeDruks> pageInfo = new PageInfo<>(list);
            builder.data(pageInfo);
            builder.msg("Success");
            builder.success(true);
        } catch (Exception e) {
            builder.success(false);
            e.printStackTrace();
        }
        return builder;
    }

    //
    public BusinessMessageBuilder<PageInfo<TakeDruks>> findTakeDrukHistory(String value, String date1, String date2, Integer page, Integer size) {
        BusinessMessageBuilder<PageInfo<TakeDruks>> builder = new BusinessMessageBuilder<>();
        try {
            if (StringUtils.isEmpty(value)) {
                value = null;
            } else {
                value = "%" + value + "%";
            }
            if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)) {
                date1 = null;
                date2 = null;
                if (StringUtils.isEmpty(value)) {
                    builder.success(false);
                    return builder;
                }
            } else {
                date1 += " 00:00:00";
                date2 += " 23:59:59";
            }
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
            List<TakeDruks> list = medicalTreatmentRecordMapper.findTakeDruk(value, 1, 2, date1, date2);
            PageInfo<TakeDruks> pageInfo = new PageInfo<>(list);
            builder.data(pageInfo);
            builder.msg("Success");
            builder.success(true);
        } catch (Exception e) {
            builder.success(false);
            e.printStackTrace();
        }
        return builder;
    }

    *//**
     * 通过ID进行取药操作
     *
     * @param checkboxvalue 治疗明细ID号
     * @return
     *//*
    @Transactional
    public BusinessMessage<JSONObject> putDruk(Integer[] checkboxvalue) {
        BusinessMessageBuilder<JSONObject> builder = new BusinessMessageBuilder<>();
        builder.success(false);
        builder.msg("系统异常,请重试");
        try {
            List<Integer> list = Arrays.asList(checkboxvalue);

            Example example = new Example(MedicalTreatmentRecordDetail.class);
            List<Integer> list1 = new ArrayList<>();
            list1.add(2);
            list1.add(3);
            example.createCriteria().andIn("id", list).andEqualTo("tollState", 1).andNotIn("treatmentState", list1);
            List<MedicalTreatmentRecordDetail> medicalTreatmentRecordDetails = medicalTreatmentRecordDetailMapper.selectByExample(example);
            medicalTreatmentRecordDetailMapper.updateDrukOnDetail(list, DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
            int Amout = 0;
            ManageUser manageUser = manageUserMapper.selectByPrimaryKey(SecurityUtil.getCurrentUserId());
            MedicalDrugsRecords medicalDrugsRecords = new MedicalDrugsRecords();
            medicalDrugsRecords.setSerialNumber(commonService.getSequence("SEQ_DRUGS_SERIAL_NUMBER"));
            medicalDrugsRecords.setInOut(1);
            medicalDrugsRecords.setRecordDate(new Date());
            medicalDrugsRecords.setSpecies(medicalTreatmentRecordDetails.size());
            medicalDrugsRecords.setAmount(0);
            medicalDrugsRecords.setUseOn(manageUser.getDepartmentId());
            medicalDrugsRecords.setResponsibleId(SecurityUtil.getCurrentUserId());//责任人ID
            medicalDrugsRecords.setLibrarianId(SecurityUtil.getCurrentUserId());//库管员ID
            medicalDrugsRecords.setCreator(SecurityUtil.getCurrentUserId());
            medicalDrugsRecords.setCreateDate(new Date());
            medicalDrugsRecordsMapper.insert(medicalDrugsRecords);
            System.out.println("Start for to 0");
            for (int i = 0; i < medicalTreatmentRecordDetails.size(); i++) {
                MedicalTreatmentRecordDetail e = medicalTreatmentRecordDetails.get(i);
                Example example1 = new Example(MedicalDrugs.class);
                example1.createCriteria().andEqualTo("code", e.getCode());
                List<MedicalDrugs> medicalDrugs = medicalDrugsMapper.selectByExample(example1);
                if (!utils.ListIsNotNull(medicalDrugs)) {
                    builder.msg("药品不存在");
                    throw new Exception();
                }
                MedicalDrugs medicalDrugs1 = medicalDrugs.get(0);

                if (medicalDrugs1.getAmount() < e.getAmount()) {
                    builder.msg("数量不足");
                    throw new Exception();
                }
                Amout += e.getAmount();
                medicalDrugs1.setAmount(medicalDrugs1.getAmount() - e.getAmount());
                medicalDrugsMapper.updateByPrimaryKey(medicalDrugs1);

                System.out.println("Start for to 1");
                MedicalDrugsRecordsDetail medicalDrugsRecordsDetail = new MedicalDrugsRecordsDetail();
                medicalDrugsRecordsDetail.setDrugsRecordsId(medicalDrugsRecords.getId());
                medicalDrugsRecordsDetail.setMedicalDrugsId(medicalDrugs1.getId());
                medicalDrugsRecordsDetail.setAmount(e.getAmount());
                System.out.println("TEST:" + medicalDrugsRecordsDetail);
                medicalDrugsRecordsDetailMapper.insert(medicalDrugsRecordsDetail);
                System.out.println("Start for to 2");
            }
            System.out.println("Start for to 3");
            medicalDrugsRecords.setAmount(Amout);

            medicalDrugsRecordsMapper.updateByPrimaryKey(medicalDrugsRecords);
            builder.success(true);
            builder.msg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException("错误");
        }
        return builder.build();
    }

    //退卡查询
    public BusinessMessageBuilder<JSONObject> findRemovecard(String value, String date1, String date2, Integer page, Integer size) {
        BusinessMessageBuilder<JSONObject> builder = new BusinessMessageBuilder<>();
        builder.success(false);
        try {
            if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)) {
                date1 = null;
                date2 = null;
            } else {
                date1 += " 00:00:00";
                date2 += " 23:59:59";
            }
            if (StringUtils.isEmpty(value)) {
                value = null;
            }
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
            List<HashMap> list = medicalRemovecardMapper.selectConntion(value, date1, date2);
            PageInfo<HashMap> pageInfo = new PageInfo<>(list);
            JSONObject json = JSON.parseObject(JSON.toJSONString(pageInfo));
            JSONArray jsonArray = new JSONArray();
            list.forEach(e -> {
                JSONObject item = new JSONObject();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                item.put("time", dateFormat.format(e.get("removecard_time")));
                item.put("info", e);
                jsonArray.add(item);
            });
            json.put("list", jsonArray);
            builder.data(json);
            builder.success(true);
        } catch (Exception e) {
            builder.success(false);
            e.printStackTrace();
        }
        return builder;
    }

    public BusinessMessage<JSONObject> saveTF(Integer id) {
        BusinessMessageBuilder<JSONObject> builder = new BusinessMessageBuilder<>();
        builder.msg("系统异常，请重试");
        builder.success(false);
        MedicalTreatmentRecordDetail medicalTreatmentRecordDetail = medicalTreatmentRecordDetailMapper.selectByPrimaryKey(id);
        if (medicalTreatmentRecordDetail.getTollState() == 1) {
            medicalTreatmentRecordDetail.setTreatmentState(2);
            medicalTreatmentRecordDetailMapper.updateByPrimaryKey(medicalTreatmentRecordDetail);
            builder.success(true);
        } else {
            builder.msg("项目未收费,请缴费后重试");
        }
        return builder.build();
    }

    *//**
     * pos刷卡信息保存
     *
     * @param medicalPos
     * @return
     *//*
    public BusinessMessageBuilder updateMedicalPos(MedicalPos medicalPos) {
        BusinessMessageBuilder builder = new BusinessMessageBuilder();
        if (medicalPos.getBusiType() == 0 && (medicalPos.getBusiId() == null || medicalPos.getBusiId() <= 0)) {
            builder.msg("参数异常，请刷新后重试！");
            return builder;
        }
        if (StringUtils.isEmpty(medicalPos.getT1AppType())) {
            builder.msg("请选择应用类型");
            return builder;
        }
        if (StringUtils.isEmpty(medicalPos.getT1Trans())) {
            builder.msg("请选择交易类型");
            return builder;
        }
        if (medicalPos.getT1Amount() == null) {
            builder.msg("请填写交易金额");
            return builder;
        }
        if (StringUtils.isEmpty(medicalPos.getT1Lrc())) {
            builder.msg("交易校验值不存在");
            return builder;
        }
        Integer userId = SecurityUtil.getCurrentUserId();
        Date currDate = new Date();
        medicalPos.setCreateDate(currDate);
        medicalPos.setCreator(userId);
        medicalPos.setEditDate(currDate);
        medicalPos.setEditor(userId);
        medicalPosMapper.insert(medicalPos);
        builder.success(true);
        builder.data(medicalPos.getId());
        return builder;
    }

    *//**
     * 获取诊疗详细的收费或退费信息
     *
     * @param action     toll收费、refund退费
     * @param cardNumber 卡号
     * @return
     *//*
    public BusinessMessageBuilder<Map> listRecordDetailByCardNumber(String action, String cardNumber) {
        BusinessMessageBuilder<Map> builder = new BusinessMessageBuilder<>();
        Map map = new HashMap<>();
        Example example = new Example(MedicalRecord.class);
        example.createCriteria().andEqualTo("cardNumber", cardNumber);
        List<MedicalRecord> medicalRecordList = medicalRecordMapper.selectByExample(example);
        if (medicalRecordList == null || medicalRecordList.size() <= 0) {
            builder.msg("未查询到与卡号匹配患者");
            return builder;
        }
        MedicalRecord medicalRecord = medicalRecordList.get(0);
        List<MedicalTreatmentRecordDetailDTO> list = medicalTreatmentRecordDetailMapper.listRecordDetailByCardNumber(action, cardNumber);
        map.put("medicalRecord", medicalRecord);
        map.put("treatmentRecordDetailList", list);
        builder.data(map);
        builder.success(true);
        return builder;
    }
}
*/