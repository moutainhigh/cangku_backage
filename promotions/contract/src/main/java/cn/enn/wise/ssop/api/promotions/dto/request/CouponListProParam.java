package cn.enn.wise.ssop.api.promotions.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("优惠券分页参数")
public class CouponListProParam extends QueryParam {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

    @ApiModelProperty("是否产品专用  1 是 2 否")
    private Byte goodsSpecial;

    @ApiModelProperty("是否可以转赠 1 是 2 否")
    private Byte issend;

    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte state;

    @ApiModelProperty("有效期-开始(实现负责，暂不支持)")
    private Timestamp validityStart;

    @ApiModelProperty("有效期-结束(实现负责，暂不支持)")
    private Timestamp validityEnd;

}
