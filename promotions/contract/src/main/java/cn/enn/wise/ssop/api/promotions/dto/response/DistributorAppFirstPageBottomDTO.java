package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分销商App首页下半部分返回参数
 * @author xiaoyang
 */
@Data
@ApiModel("分销商App首页下半部分返回参数")
public class DistributorAppFirstPageBottomDTO {

    @ApiModelProperty("商品id")
    private int goodsId;
  
    @ApiModelProperty("商品名称")
    private String goodsName;
  
    @ApiModelProperty("景区名称")
    private String scenicName;
   
    @ApiModelProperty("商品图片")
    private String goodsImg;
  
    @ApiModelProperty("最大优惠价格")
    private double maxDiscountPrice;
   
    @ApiModelProperty("最低分销价格")
    private double minDistributePrice;
   
    @ApiModelProperty("原价")
    private double originalPrice;
}
