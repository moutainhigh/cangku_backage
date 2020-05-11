package cn.enn.wise.ssop.api.promotions.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author jiabaiye
 */

@Data
@ApiModel("返回团购订单主实体类")
public class GroupOrderDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("外键,指向活动id")
    private Long groupPromotionId;

    @ApiModelProperty("拼团编码")
    private String groupOrderCode;

    @ApiModelProperty("code")
    private String code;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty("活动人数，成团人数")
    private Integer groupSize;

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品编号")
    private String goodsNum;

    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("有效时间")
    private String validHours;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("拼团状态")
    private String status;

    @ApiModelProperty("参团人数")
    private Integer userCount;

    @ApiModelProperty("是否自动成团")
    private Byte isAutoFinish;

    @ApiModelProperty("拼团有效时间")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp availableTime;

    @ApiModelProperty("团长用户id")
    private Long userId;

    @ApiModelProperty("已经参与拼团的人数")
    private Long saleNum;

    @ApiModelProperty("已经参与拼团的人数")
    private Long projectId;

    @ApiModelProperty("团长微信头像")
    private String wxImg;

    @ApiModelProperty("团长微信名称")
    private String wxName;

    @ApiModelProperty("拼团Item信息")
    private List<GroupOrderItemDTO> itemList;
}
