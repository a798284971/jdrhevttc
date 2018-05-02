/*package com.jdr.interview.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jdr.interview.bean.BusinessMessage;
import com.songpo.ceg.domain.*;
import com.songpo.ceg.entity.*;
import com.songpo.ceg.service.CashierService;
import com.songpo.ceg.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cashier")
@Slf4j
public class CashierController {
	
	private final Logger log = LoggerFactory.getLogger(CashierController.class);
    @Autowired
    private CashierService cashierService;

    @GetMapping("findUnpaid")//查询收费列表
    public BusinessMessage<PageInfo<MedicalTreatmentRecordRecordDTO>> findUnpaid(String value, String date1, String date2, Integer page, Integer size) {
        BusinessMessage<PageInfo<MedicalTreatmentRecordRecordDTO>> build = new BusinessMessage<PageInfo<MedicalTreatmentRecordRecordDTO>>();
        build.setSuccess(true);
        build.setMsg("操作成功！");
        try {
            build = cashierService.findUnpaid(value, 0, date1, date2, page, size).build();
        } catch (ServiceException e) {
            build.setMsg(e.getMessage());
            build.setSuccess(false);
        } catch (Exception e) {
            build.setSuccess(false);
            build.setMsg("系统错误");
        }
        return build;
    }

    //查找历史收费
    @GetMapping("findChargeHistory")
    public BusinessMessage<Info<MedicalTollRecordDTO>> findChargeHistory(String value, String date1, String date2, Integer page, Integer size) {
        return cashierService.findCharPagegeHistory(value, date1, date2, page, size).build();
    }

    @GetMapping("findUnpaidDetails")//根据就诊记录Id查询收费项目
    public BusinessMessage<JSONObject> findUnpaidDetails(Integer id, Integer flag) {
        BusinessMessage<JSONObject> build = new BusinessMessage<JSONObject>();
        build.setSuccess(true);
        build.setMsg("操作成功！");
        try {
            build = cashierService.findUnpaidDetails(id, flag).build();
        } catch (ServiceException e) {
            build.setMsg(e.getMessage());
            build.setSuccess(false);
        } catch (Exception e) {
            build.setSuccess(false);
            build.setMsg("系统错误");
        }
        return build;
    }

    @PostMapping("saveUnpaid")//新增收费单
    public BusinessMessage<Integer> saveUnpaid(Integer[] checkboxvalue, MedicalTollRecord medicalTollRecord) {
        BusinessMessage<Integer> build = new BusinessMessage<Integer>();
        build.setSuccess(true);
        build.setMsg("操作成功！");
        try {
            build = cashierService.saveUnpaid(checkboxvalue, medicalTollRecord);
        } catch (ServiceException e) {
            build.setMsg(e.getMessage());
            build.setSuccess(false);
        } catch (Exception e) {
            build.setSuccess(false);
            build.setMsg("系统错误");
        }
        return build;
    }

    @PostMapping("getAmount")//获取用户充值总金额（余额）
    public BusinessMessage<JSONObject> getAmount(MedicalRecord medicalRecord) {
        BusinessMessage<JSONObject>  build = new BusinessMessage<JSONObject>();
        build.setSuccess(true);
        build.setMsg("操作成功！");
        try {
            build = cashierService.getAmount(medicalRecord.getIdCard());
        } catch (ServiceException e) {
            build.setMsg(e.getMessage());
            build.setSuccess(false);
        } catch (Exception e) {
            build.setSuccess(false);
            build.setMsg("系统错误");
        }
        return build;
    }

    *//**
     * 新增充值单
     *
     * @param medicalRecord
     * @param medicalRechargeRecord
     * @return
     *//*
    @RequestMapping(value = "addRechargeList")
    @ResponseBody
    public BusinessMessage<JSONObject> addRechargeList(MedicalRecord medicalRecord, MedicalRechargeRecord medicalRechargeRecord) {
        BusinessMessageBuilder<JSONObject> builder = new BusinessMessageBuilder<JSONObject>();
        builder.success(true);
        builder.msg("操作成功！");
        try {
            if (medicalRecord != null && medicalRechargeRecord != null) {
                builder = cashierService.addRechargeList(medicalRecord, medicalRechargeRecord);
            } else {
                builder.msg("参数异常，请重试！");
                return builder.build();
            }
        } catch (Exception e) {
            log.error("新增充值单异常", e);
        }
        return builder.build();
    }


    @PostMapping("addRechargeRechargeList")//新增退卡单
    public BusinessMessage<JSONObject> addRechargeRechargeList(MedicalRecord medicalRecord, MedicalRechargeRecord medicalRechargeRecord, MedicalRemovecard medicalRemovecard) {
        BusinessMessageBuilder<JSONObject> builder = new BusinessMessageBuilder<JSONObject>();
        builder.success(true);
        builder.msg("操作成功！");
        try {
            if (medicalRecord != null && medicalRechargeRecord != null && medicalRemovecard != null) {
                builder = cashierService.addRechargeRechargeList(medicalRecord, medicalRechargeRecord, medicalRemovecard);
            } else {
                builder.msg("参数异常，请重试！");
                return builder.build();
            }
        } catch (Exception e) {
            log.error("新增退卡单异常", e);
        }
        return builder.build();
    }

    @GetMapping("findRechargeList")//查找充值单
    public BusinessMessage<PageInfo<MedicalRechargeRecordDTO>> findRechargeList(String value, String date1, String date2, Integer page, Integer size) {
        BusinessMessage<PageInfo<MedicalRechargeRecordDTO>> build = new BusinessMessage<PageInfo<MedicalRechargeRecordDTO>>();
        build.setSuccess(true);
        build.setMsg("操作成功！");
        try {
            build = cashierService.findRechargeList(value, date1, date2, page, size).build();
        } catch (ServiceException e) {
            build.setMsg(e.getMessage());
            build.setSuccess(false);
        } catch (Exception e) {
            build.setSuccess(false);
            build.setMsg("系统错误");
        }
        return build;
    }

    @PostMapping("findSerialNumber")//根据收费单号查询患者信息
    public BusinessMessage<JSONObject> findSerialNumber(String serialNumber) {
        return cashierService.findSerialNumber(serialNumber).build();
    }

    @PostMapping("findTreatments")//根据收费单号查询治疗项目
    public BusinessMessage<JSONObject> findTreatments(String serialNumber) {
        return cashierService.findSerialNumber(serialNumber).build();
    }

    @PostMapping("SaveMedicalRefundRecord")//新增退费单
    public BusinessMessage<JSONObject> SaveMedicalRefundRecord(MedicalRefundRecord medicalRefundRecord) {
        BusinessMessage<JSONObject> build = new BusinessMessage<JSONObject>();
        try {
            build = cashierService.SaveMedicalRefundRecord(medicalRefundRecord).build();
        } catch (ServiceException e) {
            build.setMsg(e.getMessage());
            build.setSuccess(false);
        } catch (Exception e) {
            build.setSuccess(false);
            build.setMsg("系统错误");
        }
        return build;
    }

    *//**
     * 诊疗项退费
     *
     * @param medicalRecordId
     * @param treatmentRecordDetailIds
     * @param reason
     * @return
     *//*
    @PostMapping("editMedicalRefundRecord")
    public BusinessMessage editMedicalRefundRecord(Integer medicalRecordId, String treatmentRecordDetailIds, String reason) {
        BusinessMessageBuilder builder = new BusinessMessageBuilder();
        builder.success(true);
        builder.msg("操作成功！");
        try {
            if (medicalRecordId != null && medicalRecordId > 0 && !StringUtils.isEmpty(treatmentRecordDetailIds)) {
                builder = cashierService.editMedicalRefundRecord(medicalRecordId, treatmentRecordDetailIds, reason);
            } else {
                builder.msg("参数异常，请刷新后重试！");
                return builder.build();
            }
        } catch (Exception e) {
            log.error("退费操作出现异常：", e);
        }
        return builder.build();
    }

    @GetMapping("listRefundRecord")//查找退费历史
    public BusinessMessage<PageInfo<MedicalRefundRecordDTO>> listRefundRecord(String value, String date1, String date2, Integer page, Integer size) {
        BusinessMessage<PageInfo<MedicalRefundRecordDTO>> builder = new BusinessMessage<>();
        try {
            builder = cashierService.listRefundRecord(value, date1, date2, page, size).build();
        } catch (Exception e) {
            log.error("查询退费记录出现异常：", e);
        }
        return builder;
    }

    @RequestMapping("listRecordDetailRefundByRefundReturnId")//查找退费历史
    public BusinessMessage<List<MedicalTreatmentRecordDetailDTO>> listRecordDetailRefundByRefundReturnId(Integer refundRecordId) {
        BusinessMessageBuilder<List<MedicalTreatmentRecordDetailDTO>> builder = new BusinessMessageBuilder<>();
        builder.success(true);
        builder.msg("操作成功！");
        try {
            if (refundRecordId != null && refundRecordId > 0) {
                builder = cashierService.listRecordDetailRefundByRefundReturnId(refundRecordId);
            } else {
                builder.msg("参数异常，请刷新后重试！");
                return builder.build();
            }
        } catch (Exception e) {
            log.error("根据给定退费单id查询退费记录出现异常：", e);
        }
        return builder.build();
    }//

    @GetMapping("findTakeDruk")//取药查询
    public BusinessMessage<PageInfo<TakeDruks>> findTakeDruk(String value, Integer tollState, Integer page, Integer size) {
        System.out.println("tollState" + tollState);
        return cashierService.findTakeDruk(value, tollState, 0, page, size).build();
    }

    @PostMapping("putDruk")//取药
    public BusinessMessage<JSONObject> putDruk(Integer[] checkboxvalue) {
        BusinessMessage<JSONObject> build = new BusinessMessage<JSONObject>();
        build.setSuccess(true);
        build.setMsg("操作成功！");
        try {
            build = cashierService.putDruk(checkboxvalue);
        } catch (ServiceException e) {
            build.setMsg(e.getMessage());
            build.setSuccess(false);
        } catch (Exception e) {
            build.setSuccess(false);
            build.setMsg("系统错误");
        }
        return build;
    }


    @GetMapping("findTakeDrukHistory")//取药查询历史
    public BusinessMessage<PageInfo<TakeDruks>> findTakeDrukHistory(String value, String date1, String date2, Integer page, Integer size) {
        return cashierService.findTakeDrukHistory(value, date1, date2, page, size).build();
    }

    @GetMapping("findRemovecard")//退卡
    public BusinessMessage<JSONObject> findRemovecard(String value, String date1, String date2, Integer page, Integer size) {
        return cashierService.findRemovecard(value, date1, date2, page, size).build();
    }

    @RequestMapping(value = "saveTF")
    @ResponseBody
    public BusinessMessage<JSONObject> saveTF(Integer id) {
        return cashierService.saveTF(id);
    }

    *//**
     * 新增pos刷卡信息
     *
     * @param medicalPos
     * @return
     *//*
    @RequestMapping(value = "updateMedicalPos")
    @ResponseBody
    public BusinessMessage<JSONObject> updateMedicalPos(MedicalPos medicalPos) {
        BusinessMessageBuilder builder = new BusinessMessageBuilder();
        builder.success(true);
        builder.msg("操作成功！");
        try {
            if (medicalPos != null && medicalPos.getBusiType() != null) {
                builder = cashierService.updateMedicalPos(medicalPos);
            } else {
                builder.msg("参数异常，请刷新后重试！");
                return builder.build();
            }
        } catch (Exception e) {
            log.error("新增pos刷卡信息出现异常：", e);
        }
        return builder.build();
    }

    *//**
     * 根据给定患者卡号获取诊疗详细的收费或退费信息
     *
     * @param action     toll收费、refund退费
     * @param cardNumber 卡号
     * @return
     *//*
    @RequestMapping("listRecordDetailByCardNumber")
    public BusinessMessage<Map> listRecordDetailByCardNumber(String action, String cardNumber) {
        BusinessMessageBuilder<Map> builder = new BusinessMessageBuilder<>();
        builder.success(true);
        builder.msg("操作成功！");
        try {
            if (StringUtils.isEmpty(cardNumber)) {
                builder.msg("请填写就诊卡号");
                return builder.build();
            }
            builder = cashierService.listRecordDetailByCardNumber(action, cardNumber);
        } catch (Exception e) {
            log.error("根据给定患者卡号获取诊疗详细的收费或退费信息出现异常：", e);
        }
        return builder.build();
    }
}
*/