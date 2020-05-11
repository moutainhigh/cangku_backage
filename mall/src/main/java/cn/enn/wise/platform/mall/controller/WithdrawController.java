package cn.enn.wise.platform.mall.controller;


import cn.enn.wise.platform.mall.bean.bo.Page;
import cn.enn.wise.platform.mall.bean.bo.WithdrawRecord;
import cn.enn.wise.platform.mall.bean.param.WithdrawApplyParam;
import cn.enn.wise.platform.mall.bean.param.WithdrawQueryParam;
import cn.enn.wise.platform.mall.bean.vo.WithdrawWillingVO;
import cn.enn.wise.platform.mall.config.LocalCache;
import cn.enn.wise.platform.mall.service.ShipTicketService;
import cn.enn.wise.platform.mall.service.WithdrawLimitService;
import cn.enn.wise.platform.mall.service.WithdrawService;
import cn.enn.wise.platform.mall.service.WithdrawalService;
import cn.enn.wise.platform.mall.util.MessageSender;
import cn.enn.wise.platform.mall.util.RandomStr;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提现及相关
 *
 * @author gaoguanglin
 * @since version.wzd0911
 */
@Api(value = "提现相关",tags = {"提现相关"})
@RequestMapping("/withdraw")
@RestController
@Slf4j
public class WithdrawController {



    @Autowired
    private WithdrawService service;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private WithdrawLimitService limitService;

    @Autowired
    private WithdrawalService withdrawalService;


    @GetMapping("/info")
    @ApiOperation(value = "获取分销信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distributorId", value = "分销商ID",required = true,paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "起始日期（含），格式：2016-01-01",required = true,paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "终止日期（含），格式：2016-01-01",required = true,paramType = "query")
    })
    public ResponseEntity<WithdrawWillingVO> getWithdrawInfo(Long distributorId,String startDate,String endDate){
        log.info("====> DistributorID :{}, StartDate: {}, EndDate:{}",distributorId,startDate,endDate);
        return new ResponseEntity(service.getDistributeInfo(distributorId, startDate, endDate));
    }


    @GetMapping("/verify-code")
    @ApiOperation(value = "发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cellphone", value = "分销商手机号",required = true,paramType = "query")
    })
    public ResponseEntity sendVerifyCode(String cellphone){
        String code = RandomStr.digits(6);
        Map<String,String> map = new HashMap();
        map.put("code", code);
        map.put("companyId", String.valueOf(11));
        map.put("phone", cellphone);
        map.put("type", String.valueOf(1));
        messageSender.sendSmsV2(map);
        saveVerifyCode(cellphone,code);
        return ResponseEntity.ok();
    }

    @GetMapping("/phonecode")
    @ApiOperation(value = "资料补充发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号",required = true,paramType = "query")
    })
    public ResponseEntity sendCode(String phone){
        if(phone==null){
            throw  new BeanCreationException("手机号不能为空");
        }
        withdrawalService.sendAuditPhoneAuthCode(phone);
        return ResponseEntity.ok();
    }

    @PostMapping("/checkcode")
    @ApiOperation(value = "资料补充验证短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号",required = true,paramType = "query"),
            @ApiImplicitParam(name = "code", value = "手机验证码",required = true,paramType = "query")
    })
    public ResponseEntity sendVerifyCode(String phone,String code){
        if(phone==null){
            return new ResponseEntity(2,"手机号不能为空");
        }else if(code==null){
            return new ResponseEntity(2,"验证码不能为空");
        }else if(!withdrawalService.checkPhoneAuthCode(phone,code)){
            return new ResponseEntity(2,"验证码不对");
        }
        return ResponseEntity.ok();
    }

    @PostMapping("/submit-apply")
    @ApiOperation(value = "提交提现申请",notes = "提交提现申请单，1:成功；-2：资料不全（未配置微信或银行卡）；-3：今日提现已超过系统上限；3：更新分销单发生错误")
    public ResponseEntity<String> submitApply(@RequestBody  WithdrawApplyParam param){

        // 验证提现资质
        if(!service.certifyWithdraw(param.getDistributorId())){
            ResponseEntity entity = new ResponseEntity();
            entity.setResult(-2);
            entity.setMessage("资料不全（未配置微信或银行卡）");
            return entity;
        }

        if(!limitService.checkLimit(param.getDistributorId())){
            ResponseEntity entity = new ResponseEntity();
            entity.setResult(-3);
            entity.setMessage("今日提现已超过系统上限");
            return entity;
        }

        // 验证验证码是否正确
        String codes = getVerifyCode(param.getCellphone());
        if(param.getVerifyCode()==null || !param.getVerifyCode().equals(codes)){
            return ResponseEntity.error("验证码无效或已过期");
        }

        // 保存生成提现数据
        String withdrawOrderSerial = service.saveRecord(param);
        if(withdrawOrderSerial==null){
            return ResponseEntity.error("数据保存发生错误");
        }
        if(withdrawOrderSerial.equals("-1")){
            return ResponseEntity.error("分销商状态无效");
        }

        // 更新分销订单状态
        WithdrawRecord record = service.getWithdrawInfoBySerial(withdrawOrderSerial);
        if(record==null){
            return ResponseEntity.error("提现单已经保存，数据查询发生错误");
        }
        if(service.updateDistributeStatus(record.getOrdersId(),9)){
            // 更新本地状态
            service.modifyWithdrawDistributeStatus(withdrawOrderSerial);
        }else{
            return ResponseEntity.error("更新分销单状态发生错误");
        }
        service.sendComplementMessage(withdrawOrderSerial);
        return ResponseEntity.ok();

    }




    @GetMapping("/list-in-date")
    @ApiOperation(value = "按照日期查询提现单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distributorId", value = "分销商ID",required = true,paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "起始日期（含），格式：2016-01-01",required = true,paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "终止日期（含），格式：2016-01-01",required = true,paramType = "query")
    })
    public ResponseEntity<List<WithdrawRecord>> listInDate(Long distributorId,String startDate,String endDate){
        List<WithdrawRecord> rst = service.listRecordsInDate(distributorId,startDate,endDate);
        return ResponseEntity.ok(rst);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "根据序列号查询提现单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "withdrawSerial", value = "分销单序列号",required = true,paramType = "query"),
    })
    public ResponseEntity<WithdrawRecord> detail(String withdrawSerial){
        return ResponseEntity.ok(service.getWithdrawInfoBySerial(withdrawSerial));
    }



    @GetMapping("/send-complement")
    @ApiOperation(value = "补发短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "withdrawSerials", value = "补发短信,使用半角逗号分隔",required = true,paramType = "query"),
    })
    public ResponseEntity<String> sendComplement(String withdrawSerials){
        service.sendComplementMessage(withdrawSerials);
        service.clearWithdrawPermitStatus(withdrawSerials);
        return ResponseEntity.ok();
    }

    @GetMapping("/put-out")
    @ApiOperation(value = "批量发放")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "withdrawSerials", value = "分销单序列号,使用半角逗号分隔",required = true,paramType = "query"),
    })
    public ResponseEntity<String> putOut(String withdrawSerials){
        service.putOut(withdrawSerials);
        return ResponseEntity.ok();
    }



    @PostMapping("/list-page")
    @ApiOperation(value = "后台数据列表：根据参数查询")
    public ResponseEntity<Page<WithdrawRecord>> listByParam(@RequestBody WithdrawQueryParam param){
        handleQueryParam(param);
        Page<WithdrawRecord> page = new Page();
        page.setData(service.listRecordsByPage(param));
        page.setPageNum(param.getPageNum());
        page.setPageSize(param.getPageSize());
        String total = service.listRecordsByPageCount(param).toString();
        page.setTotal(Integer.parseInt(total));
        return ResponseEntity.ok(page);
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



    private void saveVerifyCode(String cellphone,String code){

        String key = "DISTRIBUTOR_WITHDRAW_"+cellphone;
        LocalCache.set(key,code);
    }

    public String getVerifyCode(String cellphone){
        String key = "DISTRIBUTOR_WITHDRAW_"+cellphone;
        Object o = LocalCache.get(key);
        if(o==null){
            return null;
        }
        return (String)o;
    }


    @Autowired
    ShipTicketService shipTicketService;

    @GetMapping("test")
    public void init(){
        shipTicketService.updateShipDataToGoods();
    }





}
