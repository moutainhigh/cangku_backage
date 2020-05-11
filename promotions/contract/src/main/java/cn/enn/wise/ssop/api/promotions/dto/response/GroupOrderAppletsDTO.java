package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 拼团详情页展示返回的参数
 *
 * @author yangshuaiquan
 */
@Data
@ApiModel(description = "拼团详情页展示返回的参数")
public class GroupOrderAppletsDTO {


    @ApiModelProperty(value = "拼团Id")
    private Long groupOrderId;

    @ApiModelProperty(value = "团长名称")
     private String headerName;

    @ApiModelProperty(value = "团长头像")
    private String headerImg;

    @ApiModelProperty(value = "还差几人拼团成功")
    private Integer groupSizeDiff;

    @ApiModelProperty(value = "拼团商品")
    private GroupOrderGoodsDTO groupOrderGoodsDTO;

    @ApiModelProperty(value = "有效时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp availableTime;

    @ApiModelProperty(value = "拼团状态 1待成团 2 拼团中 3拼团成功 4拼团失败 ")
    private Byte status;

    @ApiModelProperty(value = "参团的其他用户的信息")
    private List<UserDTO> userDTOS;
}
