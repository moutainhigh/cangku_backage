package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.bo.Orders;
import cn.enn.wise.platform.mall.mapper.WzdOrderAppletsMapper;
import cn.enn.wise.platform.mall.service.GoodsService;
import cn.enn.wise.platform.mall.service.impl.NxjThirdOrderServiceImpl;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping(value = "/task")
@RestController
@Slf4j
public class JobController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private WzdOrderAppletsMapper wzdOrderAppletsMapper;

    @PostMapping(value = "/syn/ticket")
    @ApiOperation(value = "定时同步票数据", notes = "定时同步票数据")
    public ResponseEntity<String> synTicket(){
        ResponseEntity<String> resultVo = new ResponseEntity<>();
        goodsService.autoPiaoFuTongGoods();
        return resultVo;
    }

    @PostMapping(value = "/order/faild")
    @ApiOperation(value = "处理支付完成未提交到第三方订单系统的订单数据",notes = "处理支付完成未提交到票付通的订单数据" )
    public void processFailedOrder(){
        processNxjFailedOrder();
    }

    private void processNxjFailedOrder() {
        log.info("处理楠溪江的失败订单==start");
        NxjThirdOrderServiceImpl bean = applicationContext.getBean(NxjThirdOrderServiceImpl.class);

        List<Orders> orders = wzdOrderAppletsMapper.selectFailedOrder();
        if(CollectionUtils.isNotEmpty(orders)){
            log.info("查询出失败的的订单条数=>{}",orders.size());
            for (Orders order : orders) {

                if(StringUtils.isEmpty(order.getBatCodeOther()) && StringUtils.isEmpty(order.getTicketOrderCode())){
                    log.info("当前处理订单号:=>{},id=>{}",order.getOrderCode(),order.getId());
                    bean.processPaySuccessOrder(order.getBatCode());
                }
            }
        }

        log.info("处理楠溪江的失败订单==end");


    }
}
