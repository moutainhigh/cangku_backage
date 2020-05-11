package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.group.GroupRuleBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/12
 */
@Data
@ApiModel("活动列表实体类")
public class GroupPromotionDetailVo extends GroupPromotionVo {

    @ApiModelProperty("规则列表")
    private GroupRuleBo ruleInfo;

    @ApiModelProperty("商品列表")
    private List<GroupPromotionGoodsVo> goodsList;



}
