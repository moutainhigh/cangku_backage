package cn.enn.wise.platform.mall.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaUniformMessage;
import cn.enn.wise.platform.mall.bean.bo.CurrentWeather;
import cn.enn.wise.platform.mall.bean.bo.HourWeather;
import cn.enn.wise.platform.mall.bean.bo.Order;
import cn.enn.wise.platform.mall.bean.bo.ProdComm;
import cn.enn.wise.platform.mall.bean.param.ProdCommParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.config.WxMaConfiguration;
import cn.enn.wise.platform.mall.service.OrderService;
import cn.enn.wise.platform.mall.service.ProdCommService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.OpenIdAuthRequired;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/29 14:00
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:评论Api
 ******************************************/
@RestController
@RequestMapping("/prodComm")
@Api(value = "评论管理Controller", tags = {"评论管理Controller"})
@Slf4j
public class ProdCommController extends BaseController {



    @Autowired
    private OrderService orderService;

    @Autowired
    private ProdCommService prodCommService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @GetMapping(value = "/label")
    @ApiOperation(value = "评论标签",notes = "评论标签")
    public ResponseEntity queryProdCommLabel(){

        return new ResponseEntity(getLabelList());
    }

    private static List<ProdCommLabel> getLabelList(){
        List<ProdCommLabel> prodCommLabelList =new ArrayList<>();
        prodCommLabelList.add(new ProdCommLabel(1L,"体验很棒"));
        prodCommLabelList.add(new ProdCommLabel(2L,"安全系数高"));
        prodCommLabelList.add(new ProdCommLabel(3L,"景色很好"));
        return prodCommLabelList;
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "添加评论",notes = "添加评论")
    @OpenIdAuthRequired
    public ResponseEntity saveProdComm(@Value("#{request.getAttribute('currentUser')}") User user, @RequestBody ProdCommParam prodCommParam){
        ResponseEntity<Long> resultVo = new ResponseEntity<>();
        if (null == user){
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }

        if (Strings.isEmpty(prodCommParam.getOrderId())){
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("订单号为空");
            return resultVo;
        }

        List<Order> orderList = orderService.findOrderInfo(prodCommParam.getOrderId());

        if (CollectionUtils.isEmpty(orderList)){
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("暂无该订单,请核实");
            return resultVo;
        }

        //todo 1.评价状态待定 2.成功后修改评价状态
        if (orderList.get(0).getCommSts().equals(2)){
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("只能评价一次");
            return resultVo;
        }
        prodCommParam.setUserId(String.valueOf(user.getId()));
        long result = prodCommService.saveProdComm(prodCommParam);
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                :GeneConstant.INT_0);
        if (result == 0){
            resultVo.setMessage("服务器出错");
        }
        return resultVo;

    }


    @PostMapping("/update")
    @ApiOperation(value = "评论审核", notes = "评论审核")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "status", value = "是否显示，1:为显示，2:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是2,，否则1", required = true)})
    public ResponseEntity updateProdCommSts(String orderCode,Integer status) throws Exception{
        ResponseEntity<Long> resultVo = new ResponseEntity<>();
        if (Strings.isEmpty(orderCode)){
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("缺少订单号");
            return resultVo;
        }
        long result = prodCommService.updateProdCommStatus(orderCode,status);
        resultVo.setResult(result > 0 ? GeneConstant.INT_1
                :GeneConstant.INT_0);
        if (result == 0){
            resultVo.setMessage("服务器出错");
        }
        return resultVo;
    }

    @GetMapping(value = "/detail")
    @OpenIdAuthRequired
    @ApiOperation(value = "评价详情",notes = "评价详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true)})
    public ResponseEntity findProdCommDetail(@Value("#{request.getAttribute('currentUser')}") User user,String orderCode){
        ResponseEntity<ProdComm> resultVo = new ResponseEntity<>();
        if (null == user){
            resultVo.setResult(GeneConstant.INT_0);
            resultVo.setMessage("token已过期");
            return resultVo;
        }
        ProdComm prodComm = prodCommService.findProdCommDetail(orderCode);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(prodComm);
        return resultVo;
    }


    @PostMapping("/all/list")
    @ApiOperation(value = "评价列表PC",notes = "评价列表PC")
    public ResponseEntity<PageInfo<ProdCommVo>> findCommentList(@RequestBody ProdCommParam prodCommParam){
        ResponseEntity resultVo = new ResponseEntity();
        PageInfo<ProdCommVo> pageInfo = prodCommService.findCommentList(prodCommParam);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(pageInfo);
        return resultVo;
    }

    @GetMapping(value = "/weather/home")
    @ApiOperation(value = "首页天气接口",notes = "首页天气接口")
    public ResponseEntity getWeatherByCityCode(String cityCode){
        ResponseEntity resultVo = new ResponseEntity<>();
        if (Strings.isEmpty(cityCode)){
            resultVo.setResult(GeneConstant.INT_1);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }
        List<HourWeather> hourWeather = prodCommService.selectWeatherList(cityCode);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(hourWeather);
        return resultVo;
    }


    @GetMapping(value = "/weather")
    @ApiOperation(value = "天气接口",notes = "天气接口")
    public ResponseEntity getWeatherByCityCodes(String cityCode){
        ResponseEntity resultVo = new ResponseEntity<>();
        if (Strings.isEmpty(cityCode)){
            resultVo.setResult(GeneConstant.INT_1);
            resultVo.setMessage("缺少参数");
            return resultVo;
        }
        CurrentWeather currentWeather = prodCommService.findWeatherList(cityCode);
        resultVo.setResult(GeneConstant.INT_1);
        resultVo.setMessage(GeneConstant.SUCCESS);
        resultVo.setValue(currentWeather);
        return resultVo;
    }




    public void sendTemplateMsg(String openId,String formId,String amount,String itemName,String page) {
        WxMaService server = WxMaConfiguration.getMaService("wx76453a21da3fe5b3");
        log.info("小程序跳转页面++++++++++++++++++++++:"+page);
        try {
             server.getMsgService().sendUniformMsg(WxMaUniformMessage.builder()
                    .templateId("Gw5rzZrO5-jjjUq1JSsxWW1ndtkRxdIr9QZC0Xs1R5M")
                    .formId(formId)
                    .data(Lists.newArrayList(
                            new WxMaTemplateData("keyword1", amount, "#173177"),
                            new WxMaTemplateData("keyword2",itemName,"#173177"),
                            new WxMaTemplateData("keyword3","请您与项目开始前15分钟到达指定地点开启欢乐之旅","#173177"),
                            new WxMaTemplateData("keyword4","请您到达指定地点后向工作人员出示订单二维码","173177")))
                    .toUser(openId)
                    .page(page)
                    .build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取评论列表
     * @param projectId 项目列表
     * @param pageNum 页码
     * @param pageSize 每页数据大小
     * @return 评价列表
     */
    @GetMapping("/list")
    public ResponseEntity getCommentList(Long projectId,Integer pageNum,Integer pageSize){
        String redisCommonKey = "common_list:";
        String qParam = redisCommonKey+projectId+pageNum+pageSize ;

        String redisData = redisTemplate.opsForValue().get(qParam);
        if(StringUtils.isNotEmpty(redisData)){
            Map<String,Object> map = JSONObject.parseObject(redisData, Map.class);
            return new ResponseEntity(map);
        }else {
            Map<String,Object> map = prodCommService.getCommentList(projectId,pageNum,pageSize);
            redisTemplate.opsForValue().set(qParam,JSONObject.toJSONString(map),4, TimeUnit.HOURS);

            return new ResponseEntity(map);

        }



    }




}
