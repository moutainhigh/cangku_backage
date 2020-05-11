package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.service.GoodsExtendService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.thirdparty.LalyoubaShipHttpApiUtil;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.ShiftData;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.ShipBaseVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/23
 */
@RestController
@RequestMapping("/boat")
@Api(value = "客轮售票", tags = {"客轮售票"})
public class TicketController {

    @Autowired
    GoodsExtendService goodsExtendService;



    @Value("${spring.profiles.active}")
    String active;




    /**
     * 获取航线
     * @param from
     * @param to
     * @return
     */
    @RequestMapping(value = "/line", method = RequestMethod.GET)
    @ApiOperation(value = "航线列表", notes = "航线列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期 2011-11-11", paramType = "query"),
            @ApiImplicitParam(name = "from", value = "起点 例如:北海", paramType = "query"),
            @ApiImplicitParam(name = "to", value = "终点 例如:海口", paramType = "query")
    })
    private ResponseEntity<LineVo> line(String date,String from, String to) {
        if(!StringUtils.isNotEmpty(from) || !StringUtils.isNotEmpty(to)){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"参数错误");
        }
        return goodsExtendService.getLine(date,from,to);
    }

    /**
     * 获取航线的票
     * @param date
     * @return
     */
    @RequestMapping(value = "/line/ticket", method = RequestMethod.GET)
    @ApiOperation(value = "票务列表", notes = "票务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期 2011-11-11", paramType = "query"),
            @ApiImplicitParam(name = "from", value = "起点 例如:海口", paramType = "query"),
            @ApiImplicitParam(name = "to", value = "终点 例如:海口", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "排序 1 时间正序,2时间倒序", paramType = "query")
    })
    private ResponseEntity<LineTicketVo> getTicketByDate(String date,String from,String to,Integer order) {
        if(!StringUtils.isNotEmpty(date)){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"参数错误");
        }
        return goodsExtendService.getTicketByDate(date,from,to,order);
    }

    /**
     * 获取航线的票
     * @param date
     * @return
     */
    @RequestMapping(value = "/line/ticket/detail", method = RequestMethod.GET)
    @ApiOperation(value = "票务列表", notes = "票务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "日期 2011-11-11", paramType = "query"),
            @ApiImplicitParam(name = "from", value = "起点", paramType = "query"),
            @ApiImplicitParam(name = "to", value = "终点", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开船时间", paramType = "query"),
            @ApiImplicitParam(name = "shipName", value = "船名称", paramType = "query")
    })
    private ResponseEntity<LineDetailVo> getCabinByTicket(String date, String from, String to,String startTime,String shipName) {
        if(!StringUtils.isNotEmpty(date)){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"参数错误");
        }
        return goodsExtendService.getTicketDetail(date,from,to,startTime,shipName);
    }

    /**
     * 获取票型
     * @return
     */
    @RequestMapping(value = "/line/ticket/type", method = RequestMethod.GET)
    @ApiOperation(value = "票务列表", notes = "票务列表")
    private ResponseEntity<List<TicketTypeVo>> getTicketByDate() {
        List<TicketTypeVo> list = new ArrayList<>();

        TicketTypeVo ticketTypeVo = new TicketTypeVo();
        ticketTypeVo.setCode("101");
        ticketTypeVo.setName("成人票");
        ticketTypeVo.setDescription("普通全票 \n");
        list.add(ticketTypeVo);

        ticketTypeVo = new TicketTypeVo();
        ticketTypeVo.setCode("203");
        ticketTypeVo.setName("儿童票");
        ticketTypeVo.setDescription("身高1.2-1.5米 \n年龄小于14岁");
        list.add(ticketTypeVo);

        ticketTypeVo = new TicketTypeVo();
        ticketTypeVo.setCode("404");
        ticketTypeVo.setName("携童票");
        ticketTypeVo.setDescription("身高1.2米以下 \n年龄小于6岁");
        list.add(ticketTypeVo);

        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE,"票型",list);
    }


    /**
     * 查询港口信息
     * @param portName
     * @return
     */
    @RequestMapping(value = "/port/info", method = RequestMethod.GET)
    @ApiOperation(value = "查询港口信息", notes = "查询港口信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "portName", value = "港口名称(包含 涠洲，北海，海口)", paramType = "query"),
    })
    private ResponseEntity<PortInfo> portInfo(String portName) {
        if(!StringUtils.isNotEmpty(portName)){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"参数错误");
        }
        if(portName.contains("涠洲")){
            return ResponseEntity.ok(new PortInfo("涠洲岛西角码头","北海市海城区涠洲岛西角码头","0779-3071866","21.054970,109.092360"));
        }else if(portName.contains("北海")){
            return ResponseEntity.ok(new PortInfo("北海国际客运港","广西壮族自治区北海市银海区银滩旅游区18号(近三号路)","0779-3880711","21.419960,109.130760"));
        }else if(portName.contains("海口")){
            return ResponseEntity.ok(new PortInfo("海口港客运站","海南省海口市秀英区滨海大道102号","0898-66803800","21.054970,109.092360"));
        }else{
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"参数错误");
        }
    }


    /**
     * 查询港口信息
     * @return
     */
    @RequestMapping(value = "/lines", method = RequestMethod.GET)
    @ApiOperation(value = "查询航班", notes = "查询航班")
    private String portInfo(String iStart,String iEnd,String yyyyMMdd) {
        ShipBaseVo<ShiftData> shift = LalyoubaShipHttpApiUtil.getShift(active, iStart, iEnd, yyyyMMdd);
        String pretty = JSON.toJSONString(shift, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        return pretty;
    }


    /**
     * 查询退票规则
     * @return
     */
    @RequestMapping(value = "/refundRules", method = RequestMethod.GET)
    @ApiOperation(value = "查询退票规则", notes = "查询退票规则")
    private ResponseEntity refundRules() {
        String rules = "退票须知\n" +
                "\n" +
                "一、网上购票退款方式\n" +
                "\n" +
                "网上预订成功未取票的旅客，可致电话 0779-3071866或自行登录售票官网进行退票，我司将在相关工作日内把票款直接退回订票账户，不做任何现金退款。已取登船单未检票的旅客，可在网上办理退票手续；已取登船单且已检票的旅客，请到窗口办理退检后再返回购买处办理退票手续，退款按支付渠道原路返回。\n" +
                "\n" +
                "使用支付宝、微信等第三方支付平台支付票款的将在3-5个工作日内退还至第三方支付平台,第三方支付平台进行退款处理给银行（具体以银行处理时间和批次为准）。\n" +
                "\n" +
                "二、北海至涠洲岛船票退票规定\n" +
                "\n" +
                "1、在航班规定开航时间２小时以前，旅客可退票，但应支付退票费，退票费按照船票票面金额10%收取；在客船规定开航时间２小时以内的，不能退票。\n" +
                "\n" +
                "2、下列原因造成的退票，不收取退票费：\n" +
                "\n" +
                "（1）不可抗力；\n" +
                "\n" +
                "（2）承运人的责任。\n" +
                "\n" +
                "3、网上预订成功未取票的旅客，自行登录售票官网进行退票，我司将把票款直接退回订票账户，不做任何现金退款；\n" +
                "\n" +
                "4、已取登船单未检票的旅客，可在网上办理退票手续。\n" +
                "\n" +
                "5、全线停航,系统自动全额退票。\n" +
                "\n" +
                "三、北海至海口船票退票规定\n" +
                "\n" +
                "（一）退票手续\n" +
                "\n" +
                "所有销售渠道的退票手续费一致：\n" +
                "\n" +
                "旅客在规定的时限内退票，并支付退票手续费。超过退票时限的不允许退票。退票时限及手续费规定如下：\n" +
                "\n" +
                "在开航2小时之前退票收取船票10%手续费，2小时以内收取船票50%手续费，30分钟以内不予退票。\n" +
                "\n" +
                "（二）船票升舱的相关规定\n" +
                "\n" +
                "旅客在船上要求升舱时，须补交等级的差额款。\n" +
                "\n" +
                "四、海口至北海船票退票规定\n" +
                "\n" +
                "（一）开航前2天退票不收退票费；\n" +
                "\n" +
                "（二）开航前2天至开航前办理退票收取10%退票费；\n" +
                "\n" +
                "（三）开航后24小时之内办理退票的收取票价20%的退票费，超过24小时的不办理退票；\n" +
                "\n" +
                "（四）因天气变化、船舶故障、停航等特殊原因造成退票的可以免费办理；\n" +
                "\n" +
                "（五）退票费的计算按四舍五入到元。\n" +
                "\n" +
                "五、其他事项\n" +
                "\n" +
                "（一）由于不可抗力（如西南季风、台风、大风、大浪、雾、航道堵塞等）或船舶的原因造成停航的，只退还船票票款，安排旅客改乘造成的票差，多退少不补；已取船票的旅客请持身份证原件及船票到售票处窗口办理补退差价或退票手续；网络预订成功未取船票的旅客，请致电话0779-3071866办理取消订单退款业务，如自行在网上操作退票所产生的手续费由个人承担。\n" +
                "\n" +
                "（二）网上购票支持旅客在电脑客户端自行退票操作。如无法在电脑客户端完成退票，旅客可拨打客服电话0779-3071866/3069988进行退票。\n" +
                "\n" +
                "以上退票政策从2019年10月1日开始执行，本政策最终解释权归北海新绎游船有限公司";
        return ResponseEntity.ok(rules);
    }
}
