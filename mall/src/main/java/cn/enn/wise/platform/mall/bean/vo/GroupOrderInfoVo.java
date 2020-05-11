package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 拼团详情Vo
 *
 * @author baijie
 * @date 2019-09-16
 */
@Data
@ApiModel("拼团详情Vo")
public class GroupOrderInfoVo {

    @ApiModelProperty("团单ID")
    private Long id;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目Id")
    private Long projectId;

    @ApiModelProperty("项目简介")
    private String projectPresent;

    @ApiModelProperty("商品单价")
    private BigDecimal goodPrice;

    @ApiModelProperty("拼团价")
    private BigDecimal groupPrice;

    @ApiModelProperty("项目地点")
    private String servicePlaceName;

    @ApiModelProperty("景点名称")
    private String scenicSpots;

    @ApiModelProperty("运营时间")
    private String operationTime;

    @ApiModelProperty("剩余拼团人数")
    private Integer remainingNumber;

    @ApiModelProperty("拼团剩余秒数")
    private Integer remainingSeconds;

    @ApiModelProperty("拼团人员头像,姓名信息")
    private List<Map<String,Object>> headUrls;

    @ApiModelProperty("拼团订单状态")
    private Byte status;

    @ApiModelProperty("参团userId集合")
    private String userIds;

    @ApiModelProperty("团长id")
    private String userId;

    @ApiModelProperty("成团人数")
    private Integer groupSize;

    @ApiModelProperty("wxopenId")
    private String openId;

    @ApiModelProperty("头图")
    private String headImg;

    @ApiModelProperty("活动Id")
    private Long groupPromotionId;

    @ApiModelProperty("商品Id")
    private Long goodsId;
}
