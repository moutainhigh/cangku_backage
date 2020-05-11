package cn.enn.wise.platform.mall.controller.app;

import cn.enn.wise.platform.mall.service.MallAdminService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.MessageSender;
import cn.enn.wise.platform.mall.util.RabbitUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;


@RestController
@RequestMapping("/message")
public class MessageController {

    @Value("${queueconfig.prefix}")
    private String prefix;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private MallAdminService mallAdminService;

    /**
     * 消息推送测试接口
     */
    @GetMapping("/hello")
    public void hello() {

        messageSender.send("今日07:00-08:00时段的热气球飞行状态有变化，详细请登录App查看实时飞行概况。");
    }

    /**
     * 消息推送测试接口
     */
    @GetMapping("/hello1")
    public void hello1() {

        messageSender.sendOrderMessage("订单时间:12:00,有2位客人下单成功，时段07:30-09:30，详情请查看运营APP订单内容。",11L,1,1L,"15303786335");
    }


    @GetMapping("/refundmsg")
    public String sendRefundOrderMessage(String msg,Long projectId,Integer isDistributeOrder,Long distributeId,String phone){
        messageSender.sendOrderRefundMessage(msg,projectId,isDistributeOrder,distributeId,phone);
        return GeneConstant.SUCCESS;
    }
    @GetMapping("/closemsg")
    public String sendCloseMsg(String msg,Long projectId,Integer isDistributeOrder,Long distributeId,String phone){
        messageSender.sendWriteOffOrder(msg,projectId,isDistributeOrder,distributeId,phone);
        return GeneConstant.SUCCESS;
    }



    /**
     * 消息推送测试接口
     */
    @GetMapping("/order")
    public void distributeOrder() {

        messageSender.sendCreateDistributeOrder("");
    }

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitAdmin rabbitAdmin;

    /**
     * 交换机消息队列绑定
     */
    @RequestMapping("/bind")
    public void bind(String phone) {
        RabbitUtil rabbitUtil  = new RabbitUtil(rabbitAdmin,rabbitTemplate);
        TopicExchange exchange= (TopicExchange) ExchangeBuilder.topicExchange(prefix+"amq.fanout").build();
        String[] phones = phone.split(",");
        for(String strPhone:phones){
            //#region 上线之后改成30分钟过期
            Queue queue= QueueBuilder.durable(prefix+strPhone).build();
            rabbitUtil.addQueue(queue);
            rabbitUtil.addBinding(queue,exchange,prefix+"amp.fanout");
            //#endregion
        }
    }

    /**
     * 发送价格同步消息
     */
    @GetMapping("/update/price")
    public void update(Long projectId) {
        Map<String,Object> result  = mallAdminService.getMinPriceByProjectId(projectId);
        String minPrice = result.get("price").toString();
        String distributionPrice = result.get("distributionPrice").toString();
        //#region 发送消息
        messageSender.sendGoodsPrice(projectId,new BigDecimal(minPrice),new BigDecimal(distributionPrice));
        //#endregion
    }
}