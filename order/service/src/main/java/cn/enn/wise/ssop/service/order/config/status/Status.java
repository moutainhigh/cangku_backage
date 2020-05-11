package cn.enn.wise.ssop.service.order.config.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Status {

    public static final Map<String, TicketCategoryStatus> ticketMap = new HashMap();
    public static final Map<String, HotelCategoryStatus> hotelMap = new HashMap();
    static{
        ticketMap.put("11000", TicketCategoryStatus.CATEGORY_STATUS_1);
        ticketMap.put("21000", TicketCategoryStatus.CATEGORY_STATUS_2);
        ticketMap.put("31000", TicketCategoryStatus.CATEGORY_STATUS_3);
        ticketMap.put("42700", TicketCategoryStatus.CATEGORY_STATUS_4);
        ticketMap.put("32505", TicketCategoryStatus.CATEGORY_STATUS_3);
        ticketMap.put("52700", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("52200", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("529100", TicketCategoryStatus.CATEGORY_STATUS_10);
        ticketMap.put("626122", TicketCategoryStatus.CATEGORY_STATUS_6);
        ticketMap.put("522110", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("527110", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("626125", TicketCategoryStatus.CATEGORY_STATUS_6);
        ticketMap.put("626124", TicketCategoryStatus.CATEGORY_STATUS_6);
        ticketMap.put("522100", TicketCategoryStatus.CATEGORY_STATUS_7);
        ticketMap.put("527100", TicketCategoryStatus.CATEGORY_STATUS_7);
        ticketMap.put("529100", TicketCategoryStatus.CATEGORY_STATUS_7);
        ticketMap.put("522122", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("526122", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("527122", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("522110", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("527110", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("522125", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("526125", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("527125", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("522124", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("526124", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("527124", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("62800", TicketCategoryStatus.CATEGORY_STATUS_6);
        ticketMap.put("52800", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("52200", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("52600", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("52700", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("52500", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("72200", TicketCategoryStatus.CATEGORY_STATUS_8);
        ticketMap.put("72600", TicketCategoryStatus.CATEGORY_STATUS_8);
        ticketMap.put("52200", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("52700", TicketCategoryStatus.CATEGORY_STATUS_5);
        ticketMap.put("52900", TicketCategoryStatus.CATEGORY_STATUS_5);


        hotelMap.put("61000",HotelCategoryStatus.HTTELCATEGORY_STATUS_1);
        hotelMap.put("31000",HotelCategoryStatus.HTTELCATEGORY_STATUS_2);
        hotelMap.put("12100",HotelCategoryStatus.HTTELCATEGORY_STATUS_3);
        hotelMap.put("12200",HotelCategoryStatus.HTTELCATEGORY_STATUS_4);
        hotelMap.put("12311",HotelCategoryStatus.HTTELCATEGORY_STATUS_5);
        hotelMap.put("32412",HotelCategoryStatus.HTTELCATEGORY_STATUS_6);
        hotelMap.put("12220",HotelCategoryStatus.HTTELCATEGORY_STATUS_7);
        hotelMap.put("12211",HotelCategoryStatus.HTTELCATEGORY_STATUS_8);
        hotelMap.put("32412",HotelCategoryStatus.HTTELCATEGORY_STATUS_6);
        hotelMap.put("12230",HotelCategoryStatus.HTTELCATEGORY_STATUS_9);
        hotelMap.put("22600",HotelCategoryStatus.HTTELCATEGORY_STATUS_10);
        hotelMap.put("22620",HotelCategoryStatus.HTTELCATEGORY_STATUS_11);
        hotelMap.put("22611",HotelCategoryStatus.HTTELCATEGORY_STATUS_11);
        hotelMap.put("32712",HotelCategoryStatus.HTTELCATEGORY_STATUS_12);
        hotelMap.put("22630",HotelCategoryStatus.HTTELCATEGORY_STATUS_10);
        hotelMap.put("42700",HotelCategoryStatus.HTTELCATEGORY_STATUS_13);
        hotelMap.put("22800",HotelCategoryStatus.HTTELCATEGORY_STATUS_14);
        hotelMap.put("42700",HotelCategoryStatus.HTTELCATEGORY_STATUS_13);


    }

    @AllArgsConstructor
    @Getter
    public enum TicketCategoryStatus{
        CATEGORY_STATUS_1(WechatTicketCategoryStatus.CATEGORY_STATUS_1,PCTicketCategoryStatus.CATEGORY_STATUS_1,"待支付，待支付"),
        CATEGORY_STATUS_2(WechatTicketCategoryStatus.CATEGORY_STATUS_2,PCTicketCategoryStatus.CATEGORY_STATUS_2,"超时取消，超时取消"),
        CATEGORY_STATUS_3(WechatTicketCategoryStatus.CATEGORY_STATUS_3,PCTicketCategoryStatus.CATEGORY_STATUS_3,"已取消，已取消"),
        CATEGORY_STATUS_4(WechatTicketCategoryStatus.CATEGORY_STATUS_4,PCTicketCategoryStatus.CATEGORY_STATUS_4,"待使用，待使用"),
        CATEGORY_STATUS_5(WechatTicketCategoryStatus.CATEGORY_STATUS_5,PCTicketCategoryStatus.CATEGORY_STATUS_5,"已使用，已使用"),
        CATEGORY_STATUS_6(WechatTicketCategoryStatus.CATEGORY_STATUS_6,PCTicketCategoryStatus.CATEGORY_STATUS_6,"已关闭，已关闭"),
        CATEGORY_STATUS_7(WechatTicketCategoryStatus.CATEGORY_STATUS_5,PCTicketCategoryStatus.CATEGORY_STATUS_10,"已使用，退票审核中"),
        CATEGORY_STATUS_8(WechatTicketCategoryStatus.CATEGORY_STATUS_7,PCTicketCategoryStatus.CATEGORY_STATUS_7,"已完成，已完成"),
        CATEGORY_STATUS_9(WechatTicketCategoryStatus.CATEGORY_STATUS_5,PCTicketCategoryStatus.CATEGORY_STATUS_7,"已使用，已完成"),
        CATEGORY_STATUS_10(WechatTicketCategoryStatus.CATEGORY_STATUS_10,PCTicketCategoryStatus.CATEGORY_STATUS_10,"退票审核中，退票审核中");
        private WechatTicketCategoryStatus xiaochengxuStatus;//小程序状态
        private PCTicketCategoryStatus pcAppStatus;//PC/APP的状态
        private String name;
    }
    @AllArgsConstructor
    @Getter
    public enum PCTicketCategoryStatus{
        CATEGORY_STATUS_1(1,"待支付，待支付"),
        CATEGORY_STATUS_2(2,"超时取消，超时取消"),
        CATEGORY_STATUS_3(3,"已取消，已取消"),
        CATEGORY_STATUS_4(4,"待使用，待使用"),
        CATEGORY_STATUS_5(5,"已使用，已使用"),
        CATEGORY_STATUS_6(6,"已关闭，已关闭"),
        CATEGORY_STATUS_7(10,"已使用，退票审核中"),
        CATEGORY_STATUS_8(7,"已完成，已完成"),
        CATEGORY_STATUS_9(7,"已使用，已完成"),
        CATEGORY_STATUS_10(10,"退票审核中，退票审核中");
        private int value;//PC/APP的状态
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public enum WechatTicketCategoryStatus{
        CATEGORY_STATUS_1(1,"待支付，待支付"),
        CATEGORY_STATUS_2(2,"超时取消，超时取消"),
        CATEGORY_STATUS_3(3,"已取消，已取消"),
        CATEGORY_STATUS_4(4,"待使用，待使用"),
        CATEGORY_STATUS_5(5,"已使用，已使用"),
        CATEGORY_STATUS_6(6,"已关闭，已关闭"),
        CATEGORY_STATUS_7(5,"已使用，退票审核中"),
        CATEGORY_STATUS_8(7,"已完成，已完成"),
        CATEGORY_STATUS_9(5,"已使用，已完成"),
        CATEGORY_STATUS_10(10,"退票审核中，退票审核中");
        private int value;//小程序状态
        private String name;
    }

    @AllArgsConstructor
    @Getter
    public enum HotelCategoryStatus{
        HTTELCATEGORY_STATUS_1(1,1,1,"待支付，待支付，待支付"),
        HTTELCATEGORY_STATUS_2(2,2,2,"已取消/超时取消，已取消/超时取消，已取消/超时取消"),
        HTTELCATEGORY_STATUS_3(3,4,4,"订单确认中，待确认，待确认"),
        HTTELCATEGORY_STATUS_4(5,5,5,"待使用，待使用，待使用"),
        HTTELCATEGORY_STATUS_5(6,6,6,"订单确认失败，退款中，已确认，已确认(运营) / 待确认(财务)"),
        HTTELCATEGORY_STATUS_6(7,7,7,"已退款，已退款，已退款"),
        HTTELCATEGORY_STATUS_7(8,8,8,"审核中，等待退订审核，等待退订审核"),
        HTTELCATEGORY_STATUS_8(8,9,9,"审核中，等待退款审核，等待退款审核"),
        HTTELCATEGORY_STATUS_9(5,10,10,"待使用，等待入住，等待入住"),
        HTTELCATEGORY_STATUS_10(9,11,0,"已使用，已入住，null"),
        HTTELCATEGORY_STATUS_11(8,0,0,"审核中，null，null"),
        HTTELCATEGORY_STATUS_12(7,7,0,"已退款，已退款，null"),
        HTTELCATEGORY_STATUS_13(10,12,0,"已完成，已完成，null"),
        HTTELCATEGORY_STATUS_14(13,13,13,"订单超时，订单超时，订单超时"),
        HTTELCATEGORY_STATUS_15(11,12,12,"已失效，已完成，已完成");
        private int xiaochengxuStatus;
        private int pcAppStatus;//供应方运营人员状态
        private int pcStatus;//景区财务&生态业务管理人员状态
        private String name;
    }
    
    
}
