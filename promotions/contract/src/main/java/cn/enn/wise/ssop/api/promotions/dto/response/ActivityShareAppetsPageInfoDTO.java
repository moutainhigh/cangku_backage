package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 促销活动分享页面相关参数
 * @author xiaoyang
 */
@Data
@ApiModel("促销活动分享页面相关参数")
public class ActivityShareAppetsPageInfoDTO{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动基础信息Id")
    private Long activityBaseId;

    @ApiModelProperty("图片分享")
    private String imgShare;

    @ApiModelProperty("活动二维码")
    private String qrCode;

    @ApiModelProperty("规则描述")
    private String description;
}
