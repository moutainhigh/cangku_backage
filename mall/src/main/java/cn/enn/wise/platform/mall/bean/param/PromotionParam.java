package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/5
 */
@ApiModel("活动筛选")
@Data
public class PromotionParam {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty("活动类型 1 拼团 2 营销")
    private List<Integer> promotionType;

    @ApiModelProperty("活动状态")
    private List<Byte> status;
}
