package cn.enn.wise.platform.mall.util;

import cn.enn.wise.platform.mall.bean.param.GoodsReqParam;
import cn.enn.wise.platform.mall.constants.AppConstants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 17:12
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
public class MallUtil {
    /**
     * 计算两个时间字符串相差的分钟数
     *
     * @param start 开始时间 如 08:00
     * @param end   结束时间 如 08:30
     * @return 30
     */
    public static int minutesDiff2TimeStr(String start, String end) {
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        String[] startArr = start.split(":");
        calStart.set(Calendar.HOUR, Integer.parseInt(startArr[0]));
        calStart.set(Calendar.MINUTE, Integer.parseInt(startArr[1]));
        String[] endArr = end.split(":");
        calEnd.set(Calendar.HOUR, Integer.parseInt(endArr[0]));
        calEnd.set(Calendar.MINUTE, Integer.parseInt(endArr[1]));
        Long diff = (calEnd.getTimeInMillis() - calStart.getTimeInMillis())/1000/60;
        return diff.intValue();
    }

    /**
     * 设置公共参数
     * @param goodsReqQry
     * @return
     */
    public static GoodsReqParam setCommonParam(GoodsReqParam goodsReqQry){
        goodsReqQry.setCategoryId(AppConstants.RQQ_CATEGORY_ID);
        goodsReqQry.setResourceId(AppConstants.RQQ_RESOURCE_ID);
        goodsReqQry.setProjectId(AppConstants.RQQ_PROJECT_ID);
        return goodsReqQry;
    }

    /**
     * 根据类型获取时间节点
     * @param timeFrame
     * @return
     */
    public static String getDateByType(Integer timeFrame) {
        String operationTime = null;
        boolean flag = true;
        if(AppConstants.TODAY.equals(timeFrame)){
            //今天
            operationTime = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getDayBegin());
            flag = false;
        }
        if(AppConstants.TOMORROW.equals(timeFrame)){
            //明天
            operationTime = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getBeginDayOfTomorrow());
            flag = false;
        }
        if(AppConstants.ACQUIRED.equals(timeFrame)){
            //后天
            operationTime = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getBeginDayOfAcquired());
            flag = false;
        }
        if(flag){
            operationTime = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getDayBegin());
        }
        return operationTime;
    }

    /**
     * 根据类型获取时间
     * @param timeFrame
     * @return
     */
    public static Date getDateByTimeFrame(Integer timeFrame) {
        Date date =new Date(DateUtil.getDayBegin().getTime());
        boolean flag = true;
        if(AppConstants.TODAY.equals(timeFrame)){
            //今天
           date = new Date(DateUtil.getDayBegin().getTime());
            flag = false;
        }
        if(AppConstants.TOMORROW.equals(timeFrame)){
            //明天
            date = new Date(DateUtil.getBeginDayOfTomorrow().getTime());
            flag = false;
        }
        if(AppConstants.ACQUIRED.equals(timeFrame)){
            //后天
            date = new Date(DateUtil.getBeginDayOfAcquired().getTime());
            flag = false;
        }
        return date;
    }

    public static Integer getTimeFrameByDate(Date date){
        Integer timeFrame = 1;

       try {

           //今天的日期字符串
           String dayBegin = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getDayBegin());
           //明天的日期字符串
           String tomorrow = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getBeginDayOfTomorrow());
           //后天的日期字符串
           String acquired = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getBeginDayOfAcquired());

           //入园订单日期
           String enterTime = new SimpleDateFormat("yyyy-MM-dd").format(date);

           if(dayBegin.equals(enterTime)){
             timeFrame = 1;
           }else if(tomorrow.equals(enterTime)){
               timeFrame =2;
           }else if(acquired.equals(enterTime)){
               timeFrame = 3;
           }


       }catch (Exception e){
           e.printStackTrace();
       }


        return timeFrame;
    }



    public static Map<String, Object> toMap(String jsonStr){
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject json = JSONObject.parseObject(jsonStr);
        for(Object k : json.keySet()){
            Object v = json.get(k);
            if(v instanceof JSONArray){
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                Iterator<Object> it = ((JSONArray)v).iterator();
                while(it.hasNext()){
                    JSONObject json2 = (JSONObject)it.next();
                    list.add(toMap(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }





}
