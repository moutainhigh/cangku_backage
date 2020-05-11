package cn.enn.wise.platform.mall.job;

import cn.enn.wise.platform.mall.service.OrderAppletsService;
import cn.enn.wise.platform.mall.service.OrderService;
import cn.enn.wise.platform.mall.util.thirdparty.BaiBangDaHttpApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/27 17:19
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:核销订单40分钟后改为已完成
 ******************************************/
@Slf4j
@RestController
@RequestMapping("/bill")
public class CancelOrderJob {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CancelOrderJob.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderAppletsService orderAppletsService;


    private static final Integer COMPANY_XZ = 5;

    private static final Integer COMPANY_WZD = 11;

    private static final Integer COMPANY_BSC = 10;

    private static final Integer COMPANY_NXJ = 13;


    @Value("${companyId}")
    private String companyId;


    @PostMapping(value = "/xzCheck")
    public void cancelOrder() {
        if (String.valueOf(COMPANY_XZ).equals(companyId)) {
            log.info("大峡谷每分钟生成账单 start");
            orderService.cancelOrder();
        }
    }


    @PostMapping(value = "/check")
    public void cancelWZDOrder() {
        if (String.valueOf(COMPANY_WZD).equals(companyId) || String.valueOf(COMPANY_BSC).equals(companyId) || String.valueOf(COMPANY_NXJ).equals(companyId)) {
            log.info("当天检票晚上9点生成账单 start");
            orderService.cancelWZDOrder();
        }
    }

    @PostMapping(value = "/noCheck")
    public void handleCheckOrder() {
        log.info("未检票的3天后生成账单 start");
        orderService.handleCheckOrder();
    }

    /**
     * 取消超过20分钟未支付订单
     */
//    @Scheduled(cron = "0 0/2 * * * ? ")
    public void cancelAppletsOrder() {
        LOGGER.info("===查询未在规定时间内支付的订单===");
        orderAppletsService.cancelExpireOrder();
        LOGGER.info("===取消支付订单完毕===");
    }


    //@Scheduled(cron = "0 0 0/23 * * ? ")
    public void bbdLogin(){
        LOGGER.info("百邦达登录start:");
        BaiBangDaHttpApiUtil.login();
        LOGGER.info("百邦达登录end:");
    }
}
