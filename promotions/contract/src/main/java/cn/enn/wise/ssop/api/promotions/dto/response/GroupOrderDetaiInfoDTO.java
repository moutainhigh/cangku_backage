package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 参团信息展示返回的参数
 *
 * @author yangshuaiquan
 */
@Data
@ApiModel(description = "参团信息展示返回的参数")
public class GroupOrderDetaiInfoDTO {

    @ApiModelProperty(value = "团长名称")
     private String headerName;

    @ApiModelProperty(value = "团长头像")
    private String headerImg;

    @ApiModelProperty(value = "还差几人拼团成功")
    private Integer groupSizeDiff;

    @ApiModelProperty(value = "拼团Id")
    private Long groupOrderId;

    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    @ApiModelProperty(value = "有效时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp availableTime;

    @ApiModelProperty(value = "参团的其他用户的头像")
     private List<String> userImgs;
}
