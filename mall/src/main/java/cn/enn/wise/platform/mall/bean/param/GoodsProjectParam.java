package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 首页获取列表参数
 *
 * @author baijie
 * @date 2019-10-30
 */
@Data
@ApiModel("首页参数集合")
public class GoodsProjectParam {

    @ApiModelProperty("标签Id 多个使用逗号分隔")
    private String tagId;

    @ApiModelProperty("手机号，判断入口")
    private String phone;

    @ApiModelProperty("companyId 此参数从header中传输即可")
    private Long companyId;

    @ApiModelProperty(value = "小程序入口 ",required = false)
    private Boolean isDistribute = false;

    @ApiModelProperty(value = "分销商可分销的商品id集合 ",required = false)
    private List<Long> list;

    @ApiModelProperty("项目Id")
    private Long projectId;

    @ApiModelProperty("商品Id")
    private Long goodsId;

    @ApiModelProperty("活动类型")
    private Integer activityType;

    @ApiModelProperty("分类Id 多个使用逗号分割")
    private String categoryId;

    @ApiModelProperty("拼团活动Id")
    private Long promotionId;
}
