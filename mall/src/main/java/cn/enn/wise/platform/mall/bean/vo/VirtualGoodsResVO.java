package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 响应api虚拟商品类
 *
 * @author lishuiquan
 * @since 2019-10-29
 */
@Data
@ApiModel(value = "商品类信息", description = "响应的商品数据封装类")
public class VirtualGoodsResVO implements Serializable {
     /**
      * 主键
      */
     @ApiModelProperty(value = "业务主键")
     private Long id;

     /**
      * 商品名称
      */
     @ApiModelProperty(value = "商品编号")
     private String goodsId;

     /**
      * 商品名称
      */
     @ApiModelProperty(value = "商品名称")
     private String goodsName;

     /**
      * 简介
      */
     @ApiModelProperty(value = "商品简介")
     private String introduction;

     /**
      * 分类
      */
     @ApiModelProperty(value = "商品分类")
     private String category;

     /**
      * 价格
      */
     @ApiModelProperty(value = "商品价格")
     private BigDecimal goodsPrice;

     /**
      * 供应商
      */
     @ApiModelProperty(value = "供应商")
     private String supplier;

     /**
      * 库存
      */
     @ApiModelProperty(value = "库存")
     private String inventory;

     /**
      * 缩略图
      */
     @ApiModelProperty(value = "缩略图")
     private String thumbnail;

     /**
      * 轮播图
      */
     @ApiModelProperty(value = "轮播图")
     private String banner;

     /**
      * 淘宝链接
      */
     @ApiModelProperty(value = "淘宝链接")
     private String taobaoLink;

      /**
       * 已卖数量
       */
      @ApiModelProperty(value = "已卖数量")
      private Integer sell;

     /**
      * 商品详情
      */
     @ApiModelProperty(value = "商品详情")
     private String goodsDetail;

     /**
      * 商品状态
      */
     @ApiModelProperty(value = "商品状态商品状态1:上架，2;下架，默认1")
     private Byte goodsStatus;

     /**
      * 是否删除
      */
     @ApiModelProperty(value = "是否删除1:正常，2:删除,默认1")
     private Byte isDelete;
}