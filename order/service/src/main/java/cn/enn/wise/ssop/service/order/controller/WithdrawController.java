package cn.enn.wise.ssop.service.order.controller;


import cn.enn.wise.ssop.api.order.dto.request.WithdrawApplyParam;
import cn.enn.wise.ssop.api.order.dto.request.WithdrawQueryParam;
import cn.enn.wise.ssop.api.order.dto.response.PageWrapperDTO;
import cn.enn.wise.ssop.api.order.dto.response.WithdrawRecordDTO;
import cn.enn.wise.ssop.api.order.dto.response.WithdrawWillingDTO;
import cn.enn.wise.ssop.service.order.service.WithdrawMessageService;
import cn.enn.wise.ssop.service.order.service.WithdrawRecordService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/withdraw")
@Api(value = "提现", tags = {"提现"})
@Slf4j
public class WithdrawController {


    @Autowired
    private WithdrawRecordService recordService;
    @Autowired
    private WithdrawMessageService messageService;

    @GetMapping("/info/distribute")
    @ApiOperation(value = "查询指定时间段的分销信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distributorId", value = "分销商ID",required = true,paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "起始日期（含），格式：2016-01-01",required = true,paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "终止日期（含），格式：2016-01-01",required = true,paramType = "query")
    })
    public R<WithdrawWillingDTO> getWithdrawInfo(Long distributorId, String startDate, String endDate){
        log.info("====> DistributorID :{}, StartDate: {}, EndDate:{}",distributorId,startDate,endDate);
        return R.success(recordService.getDistributeInfo(distributorId, startDate, endDate));
    }


    @GetMapping("/verifyCode")
    @ApiOperation(value = "发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cellphone", value = "手机号",required = true,paramType = "query")
    })
    public R<String> sendVerifyCode(String cellphone){
        messageService.sendAuthCode(cellphone);
        return R.success("短信发送成功");
    }




    @PostMapping("/checkVerifyCode")
    @ApiOperation(value = "资料补充检验短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号",required = true,paramType = "query"),
            @ApiImplicitParam(name = "code", value = "手机验证码",required = true,paramType = "query")
    })
    public R<String> checkVerifyCode(String phone,String code){
        if(phone==null){
            return R.error(2,"手机号不能为空");
        }else if(code==null){
            return R.error(2,"验证码不能为空");
        }else if(!messageService.checkAuthCode(phone, code)){
            return R.error(2,"验证码不对");
        }
        return R.success("短信发送成功");
    }




    @PostMapping("/submitApply")
    @ApiOperation(value = "提交提现申请",notes = "提交提现申请单，1:成功；-2：资料不全（未配置微信或银行卡）；-3：今日提现已超过系统上限；3：更新分销单发生错误")
    public R<String> submitApply(@RequestBody WithdrawApplyParam param){

        // 验证提现资质
        if(!recordService.certifyWithdraw(param.getDistributorId())){
            return R.error(-2,"很抱歉～请前往个人中心补充提现资料后再进行提现！");
        }

        // 验证验证码是否正确
        if(!messageService.checkAuthCode(param.getCellphone(),param.getVerifyCode())){
            return R.error(-1,"验证码无效或已过期");
        }
        // 保存生成提现数据
        String withdrawOrderSerial = recordService.saveRecord(param);
        if(withdrawOrderSerial.equals("-1")){
            return R.error(-1,"很抱歉～您的分销状态异常，无法完成提现，请联系景区运营管理人员处理。");
        }
        if(withdrawOrderSerial.equals("-2")){
            return R.error(-2,"查询分销数据出错了～");
        }
        if(withdrawOrderSerial.equals("-3")){
            return R.error(-3,"很抱歉～已超出当日提现次数，请明日再来～");
        }
        if(withdrawOrderSerial.equals("-4")){
            return R.error(-4,"很抱歉～已超出提现金额上限，请明日再来～");
        }

        // 更新分销订单状态
        WithdrawRecordDTO record = recordService.getWithdrawInfoBySerial(withdrawOrderSerial);
        if(record==null){
            return R.error(-1,"提现单已经保存，查询提现记录发生错误");
        }
        // 更新分销单状态
        if(recordService.updateDistributeStatus(record.getOrdersId(),9)){
            // 更新本地状态
            recordService.modifyWithdrawDistributeStatus(withdrawOrderSerial);
        }else{
            return R.error(-1,"更新分销单状态发生错误");
        }
        // 发送短信给审批人
        recordService.sendComplementMessage(withdrawOrderSerial);
        return R.success("数据提交成功");

    }


    @GetMapping("/listByDate")
    @ApiOperation(value = "按照日期查询提现单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distributorId", value = "分销商ID",required = true,paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "起始日期（含），格式：2016-01-01",required = true,paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "终止日期（含），格式：2016-01-01",required = true,paramType = "query")
    })
    public R<List<WithdrawRecordDTO>> listInDate(Long distributorId, String startDate, String endDate){
        List<WithdrawRecordDTO> rst = recordService.listRecordsInDate(distributorId,startDate,endDate);
        return R.success(rst);
    }


    @GetMapping("/detail")
    @ApiOperation(value = "根据序列号查询提现单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "withdrawSerial", value = "分销单序列号",required = true,paramType = "query"),
    })
    public R<WithdrawRecordDTO> detail(String withdrawSerial){
        return R.success(recordService.getWithdrawInfoBySerial(withdrawSerial));
    }


    @GetMapping("/sendComplement")
    @ApiOperation(value = "补发短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "withdrawSerials", value = "补发短信,使用半角逗号分隔",required = true,paramType = "query"),
    })
    public R<String> sendComplement(String withdrawSerials){
        recordService.sendComplementMessage(withdrawSerials);
        recordService.clearWithdrawPermitStatus(withdrawSerials);
        return R.success("短信补发成功");
    }

    @GetMapping("/putOut")
    @ApiOperation(value = "批量设置状态为发放")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "withdrawSerials", value = "分销单序列号,使用半角逗号分隔",required = true,paramType = "query"),
    })
    public R<String> putOut(String withdrawSerials){
        recordService.putOut(withdrawSerials);
        return R.success("状态更新成功");
    }


    @PostMapping("/page/list")
    @ApiOperation(value = "后台数据列表：根据参数查询")
    public R<PageWrapperDTO<WithdrawRecordDTO>> listByParam(@RequestBody WithdrawQueryParam param){
        handleQueryParam(param);
        PageWrapperDTO<WithdrawRecordDTO> page = new PageWrapperDTO();
        page.setData(recordService.listRecordsByPage(param));
        page.setPageNum(param.getPageNum());
        page.setPageSize(param.getPageSize());
        String total = recordService.listRecordsByPageCount(param).toString();
        page.setTotal(Integer.parseInt(total));
        return R.success(page);
    }


    private void handleQueryParam(WithdrawQueryParam param){
        if(!StringUtils.isEmpty(param.getApplyDate())){
            String applyDate = param.getApplyDate();
            String[] arrApplyDate = applyDate.split("/");
            param.setApplyDateStart(arrApplyDate[0]);
            param.setApplyDateEnd(arrApplyDate[1]);
        }
        if(!StringUtils.isEmpty(param.getAuditDate())){
            String auditDate = param.getAuditDate();
            String[] arrAuditDate = auditDate.split("/");
            param.setAuditDateStart(arrAuditDate[0]);
            param.setAuditDateEnd(arrAuditDate[1]);
        }
    }



}
