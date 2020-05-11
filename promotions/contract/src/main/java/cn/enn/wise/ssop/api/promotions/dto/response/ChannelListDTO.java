package cn.enn.wise.ssop.api.promotions.dto.response;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "渠道信息")
@Data
public class ChannelListDTO {

    @ApiModelProperty("id, 不传为新增数据")
    private Long id;

    @ApiModelProperty("营销渠道标识id")
    private String code;

    @ApiModelProperty("营销渠道名")
    private String channelName;

    @ApiModelProperty(value = "景区Id")
    private Long scenicId;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty("状态 1 启用 2 关闭")
    private Byte state;

//    @ApiModelProperty("状态 1 启用 2 关闭")
//    private String stateLabel;

    @ApiModelProperty("渠道类型 1 直营 2 分销")
    private Byte channelType;

//    @ApiModelProperty("渠道类型 1 直营 2 分销")
//    private String channelTypeLabel;

//    @ApiModelProperty(value = "营业执照正面")
//    private String businessResource;

    @ApiModelProperty("指定销售产品 1 不限 2 指定")
    private Byte saleGoodsType;


}