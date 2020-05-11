package cn.enn.wise.ssop.api.promotions.dto.request;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 渠道销售和结算价格计算所需参数
 * @author 耿小洋
 */
@Data
@ApiModel("渠道销售和结算价格计算所需参数")
public class ChannelsellPriceaAndSettlementPriceParam {

    @ApiModelProperty("商品id")
    @NotNull
    private Long goodsId;

    @ApiModelProperty("商品销售价格")
    @NotNull
    private Integer sellPrice;

    @ApiModelProperty("商品成本价格")
    @NotNull
    private Integer costPrice;

/*    @ApiModelProperty("分销商id")
    @NotNull
    private Long distributorId;*/

    @ApiModelProperty("计算渠道销售价格或者结算价格 1 销售价格 2 结算价格")
    @NotNull
    private Integer sellPriceOrSettlementPrice;

    @ApiModelProperty("渠道政策使用时间")
    @NotNull
    private Date ruleDay;

    @ApiModelProperty("分销商手机号")
    private String phone;


}
