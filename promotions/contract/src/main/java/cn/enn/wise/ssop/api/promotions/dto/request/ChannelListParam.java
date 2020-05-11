package cn.enn.wise.ssop.api.promotions.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelListParam extends QueryParam {

    @ApiModelProperty("营销渠道名")
    private String channelName;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("状态 1 启用 2 关闭")
    @NotNull
    private Byte state;

    @ApiModelProperty("渠道类型 1 直营 2 分销")
    @NotNull
    private Byte channelType;

    @ApiModelProperty("是否有可用政策 1 是 2 否")
    @NotNull
    private Byte ishaveRule;

    @ApiModelProperty("操作人")
    private String updateUserName;

}