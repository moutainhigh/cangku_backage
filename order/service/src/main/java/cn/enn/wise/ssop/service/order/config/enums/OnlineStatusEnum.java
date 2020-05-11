package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class OnlineStatusEnum {


    @AllArgsConstructor
    @Getter
    @BusinessEnum
    public enum GoodsOnlineStatusEnum {
        ONLINE(ConstantValue.GOODS_ONLINE_STATUS, (byte) 1, "上架"),
        OFFLINE(ConstantValue.GOODS_ONLINE_STATUS, (byte) 2, "下架"),
        UNKONWN(ConstantValue.GOODS_ONLINE_STATUS, (byte) 3, "未知");

        private String type;

        private byte value;

        private String name;

    }

    @AllArgsConstructor
    @Getter
    @BusinessEnum
    public enum UserOnlineStatusEnum {
        ONLINE(ConstantValue.USER_ONLINE_STATUS,(byte)1,"在线"),
        OFFLINE(ConstantValue.USER_ONLINE_STATUS,(byte)2,"离线"),
        UNKONWN(ConstantValue.USER_ONLINE_STATUS,(byte)3,"未知");

        private String type;

        private byte value;

        private String name;
    }

}
