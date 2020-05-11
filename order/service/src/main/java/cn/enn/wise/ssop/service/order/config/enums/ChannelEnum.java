package cn.enn.wise.ssop.service.order.config.enums;

import cn.enn.wise.ssop.service.order.config.constants.ConstantValue;
import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@BusinessEnum
public enum ChannelEnum {

    TICKET_ALL(ConstantValue.TICKET_CHANNEL,(byte)-1,"全部渠道"),
    TICKET_DIRECT(ConstantValue.TICKET_CHANNEL,(byte)2,"直营渠道"),
    TICKET_LOCAL(ConstantValue.TICKET_CHANNEL,(byte)3,"本地旅行社（分销渠道）"),
    TICKET_JIUDIAN(ConstantValue.TICKET_CHANNEL,(byte)4,"酒店代理（分销渠道）"),
    TICKET_SIJI(ConstantValue.TICKET_CHANNEL,(byte)5,"司机（分销渠道）"),

    ZHUSU_ALL("zhusuPayStatus",(byte)-1,"全部渠道"),
    ZHUSU_DIRECT("zhusuPayStatus",(byte)2,"直营渠道"),
    ZHUSU_LOCAL("zhusuPayStatus",(byte)3,"本地旅行社（分销渠道）"),
    ZHUSU_JIUDIAN("zhusuPayStatus",(byte)4,"酒店代理（分销渠道）"),
    ZHUSU_SIJI("zhusuPayStatus",(byte)5,"司机（分销渠道）");

    private String type;

    private byte value;

    private String name;
}
