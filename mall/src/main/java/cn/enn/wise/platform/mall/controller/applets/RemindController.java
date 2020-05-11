package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.bo.group.GroupPromotionGoodsBo;
import cn.enn.wise.platform.mall.bean.vo.RemindVo;
import cn.enn.wise.platform.mall.service.GroupPromotionService;
import cn.enn.wise.platform.mall.service.KnowledgeService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.HttpClientUtil;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/7
 */
@RestController
@RequestMapping("/remind")
@Api(value = "AI智能提醒", tags = {"AI智能提醒"})
public class RemindController {

    @Autowired
    KnowledgeService knowledgeService;

    @Autowired
    GroupPromotionService groupPromotionService;

    private static String URL_TYPE_1="https://travel.enn.cn/wzd-tourism-pandaro/info/flightList?type=1&date=";
    private static String URL_TYPE_2="https://travel.enn.cn/wzd-tourism-pandaro/info/flightList?type=2&date=";


    /**
     * 获取提醒信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list/test", method = RequestMethod.GET)
    @ApiOperation(value = "获取提醒信息", notes = "获取提醒信息")
    public ResponseEntity listRemindTest() throws ParseException {

        List<RemindVo> remindVoList = new ArrayList<>();
        RemindVo remindVo = new RemindVo();
        String[] weather = knowledgeService.getWeatherByDate(getTime(1));


        return new ResponseEntity(1,"",weather);
    }


    /**
     * 获取提醒信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取提醒信息", notes = "获取提醒信息")
    public ResponseEntity<List<RemindVo>> listRemind() throws ParseException {

        List<RemindVo> remindVoList = new ArrayList<>();
        RemindVo remindVo = new RemindVo();
        String[] weather = knowledgeService.getWeatherByDate(getTime(1));
        String targetWeather="雨";
        if(weather[1].contains(targetWeather)){
            remindVo.setType("1");
            remindVo.setTitle("天气预警");
            remindVo.setContent("1小时后可能会有"+weather[1]+"，前往景点游览时记得带好雨具哦~");
            remindVoList.add(remindVo);
        }

        // TODO 暂时认为14点温度最高
        weather = knowledgeService.getWeatherByDate(getTime(14));
        if( Integer.parseInt(weather[GeneConstant.INT_2])>28){
            remindVo = new RemindVo();
            remindVo.setType("1");
            remindVo.setTitle("天气预警");
            remindVo.setContent("明日涠洲岛最高温度"+weather[2]+"摄氏度，前往景点游览时记得备好防暑用品哦~");
            remindVoList.add(remindVo);
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date=df.format(new Date());

        String content="";
        String targetStatus ="延误";
        JSONObject jsonObject = JSON.parseObject(HttpClientUtil.get(URL_TYPE_1+date));
        JSONArray jsonObjectArray  = jsonObject.getJSONArray("value");
        for (int i =0;i<jsonObjectArray.size();i++){
            JSONObject object = jsonObjectArray.getJSONObject(i);
            String status = object.getString("flightStatus");
            if(status.equals(targetStatus)){
                content+=object.getString("shipName")+"("+object.getString("boatDate")+" "+object.getString("startTime")+" 涠洲-北海)";
            }
        }
        jsonObject = JSON.parseObject(HttpClientUtil.get(URL_TYPE_2+date));
        jsonObjectArray  = jsonObject.getJSONArray("value");
        for (int i =0;i<jsonObjectArray.size();i++){
            JSONObject object = jsonObjectArray.getJSONObject(i);
            String status = object.getString("flightStatus");
            if(status.equals(targetStatus)){
                content+=object.getString("shipName")+"("+object.getString("boatDate")+" "+object.getString("startTime")+" 北海-涠洲)";
            }
        }
        if(!"".equals(content)) {
            remindVo = new RemindVo();
            remindVo.setType("2");
            remindVo.setTitle("航班预警");
            remindVo.setContent("以下航班因故延迟：" + content);
            remindVoList.add(remindVo);
        }

        List<GroupPromotionGoodsBo> list = groupPromotionService.listActivePromotionGoodsList();

        if(list.size()>0) {
            remindVo = new RemindVo();
            remindVo.setType("3");
            remindVo.setTitle("消费引导");
            remindVo.setContent("\""+list.get(0).getGoodsName()+"\"正在火热拼团中，赶快去了解下吧~");
            remindVoList.add(remindVo);
        }
       return new ResponseEntity<>(remindVoList);
    }


    /**
     * 获取未来时间
     * @return
     * @throws ParseException
     */
    private String getTime(int i) throws ParseException {
        Date day=new Date();
        Calendar cal = Calendar.getInstance();
        int hour =  cal.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = df.format(day);
        Date date = df.parse(s);
        cal.setTime(date);

        cal.add(Calendar.HOUR, (i+hour));
        date = cal.getTime();
        String s1 = format.format(date);
        return s1;

    }
}
