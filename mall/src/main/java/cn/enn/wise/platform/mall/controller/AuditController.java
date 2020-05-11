package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.bo.WithdrawRecord;
import cn.enn.wise.platform.mall.bean.vo.AuditPassInfo;
import cn.enn.wise.platform.mall.bean.vo.WithdrawInfoVo;
import cn.enn.wise.platform.mall.service.WithdrawService;
import cn.enn.wise.platform.mall.service.WithdrawalService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Api(value = "提现审核H5", tags = { "提现审核H5" })
@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    WithdrawalService withdrawalService;
    @Value("${audit.phone}")
    String auditerPhone;
    @Autowired
    WithdrawService withdrawService;
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    HttpServletResponse httpServletResponse;

    @ApiOperation("发送审核验证码(发给财务管理人手机号)")
    @PostMapping("sendAuditPhoneAuthCode")
    public ResponseEntity<Boolean> sendAuditPhoneAuthCode(){
        boolean isOK = withdrawalService.sendAuditPhoneAuthCode(auditerPhone);
        return ResponseEntity.ok(isOK);
    }


    @ApiOperation("审核详情")
    @GetMapping("/detail/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "审批id",required = true,paramType = "path")
    })
    public ResponseEntity<WithdrawInfoVo> getAuditDetail(@PathVariable String id){
        WithdrawInfoVo vo = new WithdrawInfoVo();

        WithdrawRecord withdrawRecord = withdrawService.getWithdrawInfoBySerial(id);
        if(withdrawRecord==null) return ResponseEntity.error();
        String withdraw = withdrawRecord.getWithdraw();
        Date applyDateTime = withdrawRecord.getApplyDateTime();

        String distributeDateBetween = withdrawRecord.getDistributeDateBetween();
        distributeDateBetween = distributeDateBetween.replaceAll("-",".").replaceAll("/","-");

        Byte permit = withdrawRecord.getPermit();
        String ordersId = withdrawRecord.getOrdersId();
        ordersId = ordersId.substring(ordersId.length()).equals(",") ? ordersId.substring(0, ordersId.length()-1) : ordersId;
        String[] orderIds = ordersId.split(",");

        vo.setApplyAmount(withdraw);
        vo.setApplyDate(applyDateTime);
        vo.setDateRange(distributeDateBetween);
        vo.setAuditResult(permit);
        vo.setOrderCount(orderIds.length);

        vo.setDistributorName(withdrawRecord.getDistributorName());
        vo.setDistributorType(withdrawRecord.getDistributorAccountType()==0?"微信":"银行卡");
        vo.setRequiredInfo(withdrawRecord.getDistributorMessage());
        vo.setDistributorPhone(withdrawRecord.getDistributorCellphone());
        return ResponseEntity.ok(vo);
    }


    @ApiOperation("提交审核结果")
    @PostMapping("/submitAudit")
    public ResponseEntity<Boolean> submitAudit(@RequestBody @Validated AuditPassInfo auditPassInfo){
        boolean isAuthed = withdrawalService.checkPhoneAuthCode(auditerPhone,auditPassInfo.getPhoneAuthCode());
        if(!isAuthed) return new ResponseEntity<>(GeneConstant.PHONE_NO_PADD);
        boolean isOk = withdrawalService.updateAuditResult(auditPassInfo);
        return ResponseEntity.ok(isOk);
    }



    @ApiOperation("审核页面跳转到真实链接")
    @GetMapping("/c/{shortLineCode}")
    public ResponseEntity rediractRealLink(@PathVariable String shortLineCode) throws IOException {
        String requestURI = httpServletRequest.getHeader("X-Request_UrL");
        String realLink = withdrawalService.getRealLink(requestURI);
        if(Strings.isEmpty(realLink)) return ResponseEntity.error("页面找不到");
        httpServletResponse.sendRedirect(realLink);
        return null;
    }


    @PostConstruct
    public void initShortLineToRedis(){
        withdrawalService.initShortLineToRedis();
    }









}