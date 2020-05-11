package cn.enn.wise.ssop.service.promotions.consts;

import cn.enn.wise.uncs.base.constant.BusinessEnum;
import lombok.Getter;

/**
 * @author 安辉
 * 活动类型枚举
 */
@BusinessEnum
@Getter
public enum ActivityBenefitTypeEnum {


    NOT_ROI("activityBenefitType", new Byte("1"),"非基线"),
    ROI("activityBenefitType", new Byte("2"),"基线");

    private String name;
    private Byte value;
    private String type;

    /**
     *
     * @param type
     * @param value
     * @param name
     */
    ActivityBenefitTypeEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type=type;
    }
}
