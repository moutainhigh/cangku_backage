package cn.enn.wise.platform.mall.job;


import cn.enn.wise.platform.mall.bean.bo.Order;
import cn.enn.wise.platform.mall.bean.bo.OrderTickets;
import cn.enn.wise.platform.mall.mapper.OrderDao;
import cn.enn.wise.platform.mall.service.BBDTicketService;
import cn.enn.wise.platform.mall.util.thirdparty.BaiBangDaHttpApiUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * 百邦达票状态实时更新
 */
@Component
@Slf4j
public class TicketStatusBBDJob {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BBDTicketService bbdTicketService;

    @Value("${spring.profiles.active}")
    private String profile;

    @Scheduled(cron = "0 */5 * * * ?")
    public void synchronizeTicketStatus(){
        List<Order> orders = orderDao.queryTicketUnusedBBDOrder();
        if(orders==null || orders.size()==0){
            return ;
        }
        String[] orderCodes = new String[orders.size()];
        int i=0;
        for(Order order : orders){
            String orderCode = order.getOrderCode();
            if(!StringUtils.isEmpty(orderCode)){
                orderCodes[i] = orderCode;
                i++;
            }
        }
        if(i==0){
            return;
        }
        List<OrderTickets> tickets = orderDao.findOrderTicket(orderCodes);
        try{
            for(OrderTickets orderTicket : tickets){

                long ticketId = orderTicket.getId().longValue();
                JSONObject jsonObject = BaiBangDaHttpApiUtil.getBbdTicketDetail(ticketId);
                Integer code = jsonObject.getInteger("code");
                if(code!=null && code.intValue()==1){
                    int status = jsonObject.getJSONObject("data").getInteger("status");
                    bbdTicketService.updateBBDTicketState(ticketId,status);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("==!! 获取百邦达订单详情错误:{}",e.getMessage());
        }

    }

}
