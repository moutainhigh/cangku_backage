package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/******************************************
 * @author: haoguodong
 * @createDate: 2020/4/20 16:22
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class UserDrawInfoVo {

    @ApiModelProperty(value = "领取记录Id")
    private Long userOfCouponId;

    @ApiModelProperty(value = "优惠券种类 1.门票券 2.民宿券 3.餐饮券")
    private Integer kind;

    @ApiModelProperty(value = "优惠券面值")
    private Integer couponPrice;

    @ApiModelProperty(value = "使用条件")
    private String remark;

    @ApiModelProperty(value = "优惠券名称")
    private String awardName;

    @ApiModelProperty(value = "优惠券Id")
    private Long goodsCouponId;

}
