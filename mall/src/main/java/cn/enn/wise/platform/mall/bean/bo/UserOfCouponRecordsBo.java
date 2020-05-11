package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author anhui
 */
@Data
@TableName("coupon_records")
public class UserOfCouponRecordsBo {


    @ApiModelProperty("领取id")
    private Long userOfCouponId;


    @ApiModelProperty("折扣金额")
    private BigDecimal couponPrice;

    @ApiModelProperty("产品单价")
    private BigDecimal singlePrice;


    @ApiModelProperty("游客id")
    private Long userId;

    @ApiModelProperty("订单时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @ApiModelProperty("订单价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty("消费产品")
    private String goodsName;


    @ApiModelProperty("完成时间")
    private String times;


    @ApiModelProperty("面值-折扣")
    private String price;


    @ApiModelProperty("券号")
    private String code;


    @ApiModelProperty("券id")
    private Long couponId;
}
