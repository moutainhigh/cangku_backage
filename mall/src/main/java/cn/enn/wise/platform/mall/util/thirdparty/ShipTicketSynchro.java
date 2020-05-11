package cn.enn.wise.platform.mall.util.thirdparty;

import cn.enn.wise.platform.mall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 船票状态更新
 * @program: mall
 * @author: zsj
 * @create: 2020-01-13 15:06
 **/
@Component
public class ShipTicketSynchro {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShipTicketSynchro.class);

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 */5 * * * ?")
    public void shipTicketSync(){
        LOGGER.info("==========开始船票同步===========");
        orderService.shipTicketSync();
        LOGGER.info("==========船票同步完成===========");
    }
}
