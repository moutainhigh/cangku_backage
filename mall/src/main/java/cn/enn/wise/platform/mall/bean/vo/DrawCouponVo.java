package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/4/2 10:38
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class DrawCouponVo {

    @ApiModelProperty(value = "优惠券券号")
    private Integer id;

    @ApiModelProperty(value = "过期时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value = "适用范围")
    private String useScope;

    @ApiModelProperty(value = "1领取未使用 2已使用 3已过期")
    private Integer couponSts;

    @ApiModelProperty(value = "使用条件")
    private String remark;

    @ApiModelProperty(value = "面值")
    private String couponPrice;

    @ApiModelProperty(value = "1.可用 2.不可用")
    private Integer useSts =1;

    @ApiModelProperty(value = "优惠券种类 1.门票券 2.民宿券 3.餐饮券")
    private Integer kind;

    @ApiModelProperty(value = "領取優惠券ID")
    private Integer infoId;

    @ApiModelProperty(value = "优惠券二维码")
    private String codeUrl ="http://travel.enn.cn/group1/M00/01/94/CiaAUl6U1oOAW-TcAAARxBPWM7Y072.png";

}
