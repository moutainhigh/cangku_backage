package cn.enn.wise.ssop.service.order.utils;


import cn.enn.wise.ssop.service.order.config.status.Status;

import java.util.HashMap;
import java.util.Map;


public class StatusUtils {


    public static final String PC_STATUS = "PC_STATUS";

    public static final String APP_STATUS = "APP_STATUS";

    public static final String WECHAT_STATUS = "WECHAT_STATUS";

    public static final String PC_STATUS_VALUE = "PC_STATUS_VALUE";

    public static final String APP_STATUS_VALUE = "APP_STATUS_VALUE";

    public static final String WECHAT_STATUS_VALUE = "WECHAT_STATUS_VALUE";

    public static Map<String,String> getTicketShowStatus(Integer orderStatus, Integer payStatus, Integer transactionStatus, Integer systemStatus, Integer refundStatus){
        String status = buildString(orderStatus, payStatus, transactionStatus, systemStatus, refundStatus);
        Map<String,String> map = new HashMap<>();
        if(Status.ticketMap.get(status)!=null){
            Status.PCTicketCategoryStatus pcAppStatus = Status.ticketMap.get(status).getPcAppStatus();
            map.put(PC_STATUS,pcAppStatus.getName());

            map.put(APP_STATUS,pcAppStatus.getName());

            map.put(PC_STATUS_VALUE,String.valueOf(pcAppStatus.getValue()));
            map.put(APP_STATUS_VALUE,String.valueOf(pcAppStatus.getValue()));
            Status.WechatTicketCategoryStatus wechatStatus = Status.ticketMap.get(status).getXiaochengxuStatus();
            map.put(WECHAT_STATUS,wechatStatus.getName());
            map.put(WECHAT_STATUS_VALUE,String.valueOf(wechatStatus.getValue()));
        }else{
            map.put(PC_STATUS,Status.PCTicketCategoryStatus.CATEGORY_STATUS_1.getName());
            map.put(APP_STATUS,Status.PCTicketCategoryStatus.CATEGORY_STATUS_1.getName());
            map.put(WECHAT_STATUS,Status.WechatTicketCategoryStatus.CATEGORY_STATUS_1.getName());

            map.put(PC_STATUS_VALUE,String.valueOf(Status.PCTicketCategoryStatus.CATEGORY_STATUS_1.getValue()));
            map.put(APP_STATUS_VALUE,String.valueOf(Status.PCTicketCategoryStatus.CATEGORY_STATUS_1.getValue()));
            map.put(WECHAT_STATUS_VALUE,String.valueOf(Status.WechatTicketCategoryStatus.CATEGORY_STATUS_1.getValue()));
        }
        return map;
    }

    public static Map<String,String> getHotelShowStatus(Integer orderStatus, Integer payStatus, Integer transactionStatus, Integer systemStatus, Integer refundStatus){
        String status = buildString(orderStatus, payStatus, transactionStatus, systemStatus, refundStatus);
        Map<String,String> map = new HashMap<>();
        if(Status.hotelMap.get(status)!=null){
            Status.HotelCategoryStatus hotelCategoryStatus = Status.hotelMap.get(status);
            map.put(PC_STATUS,hotelCategoryStatus.getName());
            map.put(APP_STATUS,hotelCategoryStatus.getName());
            map.put(WECHAT_STATUS,hotelCategoryStatus.getName());
        }else{
            map.put(PC_STATUS,Status.HotelCategoryStatus.HTTELCATEGORY_STATUS_1.getName());
            map.put(APP_STATUS,Status.HotelCategoryStatus.HTTELCATEGORY_STATUS_1.getName());
            map.put(WECHAT_STATUS,Status.HotelCategoryStatus.HTTELCATEGORY_STATUS_1.getName());
        }
        return map;
    }

    private static String buildString(Integer orderStatus, Integer payStatus, Integer transactionStatus, Integer systemStatus, Integer refundStatus){
        StringBuilder ss = new StringBuilder();
        if(orderStatus==null){
            ss.append(0);
        }else{
            ss.append(orderStatus);
        }
        if(payStatus==null){
            ss.append(0);
        }else{
            ss.append(payStatus);
        }
        if(transactionStatus==null){
            ss.append(0);
        }else{
            ss.append(transactionStatus);
        }
        if(systemStatus==null){
            ss.append(0);
        }else{
            ss.append(systemStatus);
        }
        if(refundStatus==null){
            ss.append(0);
        }else{
            ss.append(refundStatus);
        }
        return ss.toString();
    }

}
