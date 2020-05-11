package cn.enn.wise.platform.mall.controller.export;


import cn.enn.wise.platform.mall.bean.bo.RefundApply;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.mapper.RefundApplyMapper;
import cn.enn.wise.platform.mall.service.BBDTicketService;
import cn.enn.wise.platform.mall.service.OrderHistoryService;
import cn.enn.wise.platform.mall.service.OrderService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.SignRequired;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 外部数据调用接口，供百邦达票务对接使用
 *
 * @author gaoguanglin
 * @date 2020-01-07
 * @since JDK1.8
 */
@RestController
@Slf4j
@RequestMapping("/bbd")
public class BBDControllerExport extends BaseController {

    @Autowired
    private BBDTicketService bbdTicketService;
    @Autowired
    private OrderHistoryService orderHistoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RefundApplyMapper refundApplyMapper;

    /**
     * 更新票的状态
     *
     * @return
     */
    @PostMapping("/ticket-status")
    @SignRequired
    public ResponseEntity<String> ticketStatus(BBDTicketStateParam param ){
        log.info("===> 更新船票的状态");
        try{
            QueryWrapper<RefundApply> refundApplyQueryWrapper = new QueryWrapper<>();
            refundApplyQueryWrapper.lambda().eq(RefundApply::getApprovalsSts, 2);
            List<RefundApply> refundApplyList = refundApplyMapper.selectList(refundApplyQueryWrapper);

            List<String> itemIdList = new ArrayList<>();
            refundApplyList.stream().forEach(refundApply -> {
                if (Strings.isNotEmpty(refundApply.getOrderItemId())){
                    itemIdList.addAll(Arrays.asList(refundApply.getOrderItemId().split(",")));
                }
            });
            log.info("退款审核通过的订单"+itemIdList);
            if (itemIdList.contains(String.valueOf(param.getTicketId()))){
                return ResponseEntity.success();
            }
            bbdTicketService.updateBBDTicketState(param.getTicketId(),param.getStatus());
            log.info("===> 更新票的状态已经处理完成");
            return ResponseEntity.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.error("数据更新失败");
        }

    }


    /**
     * 换票
     * @param param
     * @return
     */
    @PostMapping("/ticket-change")
    @SignRequired
    public ResponseEntity<String> ticketChange(BBDTicketStateParam param){
        log.info("===> 换票");
        if(param == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"请输入参数!");
        }
        if(StringUtils.isBlank(param.getTicketNum()) || param.getTicketId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"请输入有效信息!");
        }

        return orderHistoryService.ticketChange(param);
    }

    /**
     * 记录票号
     * @return
     */
    @PostMapping("/ticket-save")
    @SignRequired
    public ResponseEntity<String> ticketSave(Long ticketId, String ticketNum, String qrCode){
        log.info("===>记录票号");
        if(StringUtils.isBlank(ticketId.toString()) || StringUtils.isBlank(ticketNum)) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "请输入参数!");
        }

        return orderHistoryService.ticketSave(ticketId, ticketNum, qrCode);
    }

    @PostMapping("/test")
    @Scheduled(cron = "0 */1 *  * * * ")
    public void updateBaiBangDaOrderStatus(){

        orderService.updateBaBangDaOrderStatus();
    }

}
