package cn.enn.wise.ssop.api.cms.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("评论接口请求参数")
public class ProductCommentParams{

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "评价，0-5分")
    private Integer score;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "* 评价(1.很差 2.一般 3.满意 4.非常满意 5.无可挑剔)")
    private Integer evaluate;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "评价标签")
    private String prodCommLabel;


    @ApiModelProperty(value = "订单客人")
    private String name;

    @ApiModelProperty(value = "商品Id")
    private Integer prodId;

    @ApiModelProperty(value = "页数")
    private Integer pageNum;

    @ApiModelProperty(value = "条数")
    private Integer pageSize;




}