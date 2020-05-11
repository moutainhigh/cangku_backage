package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.param.BbdOrderChangeParam;
import cn.enn.wise.platform.mall.bean.param.BbdOrderParams;
import cn.enn.wise.platform.mall.bean.param.BbdOrderSaveParams;
import cn.enn.wise.platform.mall.bean.vo.OrderReqVo;
import cn.enn.wise.platform.mall.bean.vo.OrderResVo;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.constants.AuditConstants;
import cn.enn.wise.platform.mall.service.OfflineService;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 离线订单接口
 *
 * @author baijie
 * @date 2019-07-16
 */
@RestController
@RequestMapping("/offline")
@Api(value = "离线订单接口", tags = "离线订单接口")
@Slf4j
public class OfflineOrderController extends BaseController {

    @Autowired
    private OfflineService offlineService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @PostMapping("/order/save")
    @ApiOperation(value = "保存离线订单")
    public ResponseEntity saveOfflineOrder(@RequestBody OrderReqVo orderReqVo,

        @RequestHeader(value = "token") String token)throws Exception {
        SystemStaffVo userByToken = this.getUserByToken(token);
        orderReqVo.setOfflineUser(userByToken.getName());
        orderReqVo.setScenicId(userByToken.getCompanyId());
        ParamValidateUtil.validateSaveOfflineOrder(orderReqVo);

        ResponseEntity responseEntity = offlineService.saveOrUpdateOfflineOrder(orderReqVo);
        return responseEntity;
    }

    @PostMapping("/order/update")
    @ApiOperation(value = "更新离线订单")
    public ResponseEntity updateOfflineOrder(@RequestBody OrderReqVo orderReqVo    ,
                                             @RequestHeader(value = "token") String token)throws Exception {
        SystemStaffVo userByToken = this.getUserByToken(token);
        orderReqVo.setOfflineUser(userByToken.getName());
        orderReqVo.setScenicId(userByToken.getCompanyId());
        ParamValidateUtil.validateSaveOfflineOrder(orderReqVo);
        if(orderReqVo.getId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"id不能为空");
        }
        ParamValidateUtil.validateSaveOfflineOrder(orderReqVo);

        ResponseEntity responseEntity = offlineService.saveOrUpdateOfflineOrder(orderReqVo);
        return responseEntity;
    }

    @GetMapping("/order/detail")
    @ApiOperation("获取离线订单详情")
    public ResponseEntity getOfflineOrderInfo(Long id,@RequestHeader(value = "token") String token){
        this.getUserByToken(token);
        if(id == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,GeneConstant.ERROR);
        }
        OrderResVo offlineOrderInfo = offlineService.getOfflineOrderInfo(id);
        if(offlineOrderInfo == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"未查询到商品信息");
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,offlineOrderInfo);
    }

    @GetMapping("/order/delete")
    @ApiOperation("删除订单")
    public ResponseEntity deleteOfflineOrderById(Long id,@RequestHeader(value = "token") String token){
        this.getUserByToken(token);
        if(id == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,GeneConstant.ERROR);
        }
        Boolean result = offlineService.deleteOfflineOrderById(id);
        if(result){

            return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE);
        }else {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"删除失败!");
        }
    }

    @PostMapping("/save/bbd/order")
    @ApiOperation("百邦达离线下单")
    @SignRequired
    public ResponseEntity saveBbdOfflineOrder(@RequestBody BbdOrderParams bbdOrderParams) throws Exception {
        String message ="success";
        List<BbdOrderSaveParams> saveParams ;
        List<Map<String,Object>> responseMap = new ArrayList<>();
        try {
            ParamValidateUtil.checkBbdOfflineOrderParams(bbdOrderParams);

            saveParams = paramsTransfer(bbdOrderParams);

        }catch (Exception e){
            log.error("百邦达订单同步异常:参数错误=>",e);
            message = e.getMessage();
            List<BbdOrderParams.OrderItem> orderItems = bbdOrderParams.getOrderItems();
            if(CollectionUtils.isNotEmpty(orderItems)){
                for (BbdOrderParams.OrderItem orderItem : orderItems) {
                    responseMap.add(new HashMap<String, Object>(){{
                       put("ticketId",9999);
                       put("ticketIdBbd",orderItem.getThirdOrderNo());
                    }});
                }

            }
            return new ResponseEntity(1,message,responseMap);
        }



        for (BbdOrderSaveParams saveParam : saveParams) {

            try {
                Map<String, Object> objectMap = offlineService.saveBbdOfflineOrder(saveParam);

                responseMap.add(objectMap);

            }catch (Exception e){
                message = e.getMessage();
                //TODO update log to DB
                log.error("百邦达订单同步异常");
                responseMap.add(new HashMap<String, Object>(){{
                    put("ticketId",9999);
                    put("ticketIdBbd",saveParam.getThirdOrderNo());
                }});
            }
        }


        return new ResponseEntity(1,message,responseMap);
    }

    private List<BbdOrderSaveParams> paramsTransfer(BbdOrderParams bbdOrderParams) {

        List<BbdOrderSaveParams>  saveParams = new ArrayList<>();

        List<BbdOrderParams.OrderItem> orderItems = bbdOrderParams.getOrderItems();

        for (BbdOrderParams.OrderItem orderItem : orderItems) {
            BbdOrderSaveParams.BbdOrderSaveParamsBuilder builder = BbdOrderSaveParams
                    .builder();

                    builder
                            .productId(bbdOrderParams.getProductId())
                            .departureDate(bbdOrderParams.getDepartureDate())
                            .tourismName(bbdOrderParams.getTourismName())
                            .phone(bbdOrderParams.getPhone())
                            .orderPrice(orderItem.getOrderPrice())
                            .thirdOrderNo(orderItem.getThirdOrderNo())
                            .orderSerialBbd(orderItem.getOrderSerialBbd())
                            .ticketQrCodeBbd(orderItem.getTicketQrCodeBbd())
                            .ticketSerialBbd(orderItem.getTicketSerialBbd())
                            .amount(1L);
                    //成人票
                    if(orderItem.getTicketType() == 1){
                        builder
                                .businessNumber(bbdOrderParams.getBusinessNumber())
                                .isScenicService(bbdOrderParams.getIsScenicService());
                    }
                    saveParams.add(builder.build());
        }

        return saveParams;
    }

    /**
     * 线上线下订单绑定
     * @param phone
     * @return
     */
    @PostMapping("/bind")
    @OpenIdAuthRequired
    public ResponseEntity bindOfflineOrderToLine(String phone,String code,@Value("#{request.getAttribute('currentUser')}") User user) throws Exception {
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){

            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"手机号或验证码不能为空");
        }


        String redisKey = String.format(AuditConstants.AUDITSMS_CODE, phone);

        String messageCode = redisTemplate.opsForValue().get(redisKey);

        if(!code.equals(messageCode)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"短信验证码错误，请重新输入");
        }

        redisTemplate.delete(redisKey);

        offlineService.bindOfflineOrder(phone, user);

        return ResponseEntity.ok();
    }

    /**
     * 百邦达票改签
     * @return
     */
    @PostMapping("/bbd/change")
    public ResponseEntity changeOfflineOrder(@RequestBody BbdOrderChangeParam changeParam){

        ParamValidateUtil.checkBbdOrderChange(changeParam);

        List<Map<String, Object>> mapList = offlineService.changeBbdTicket(changeParam);


        return ResponseEntity.ok(mapList);
    }
}
