package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * <p>
 * 运营时段列表包装类
 * </p>
 *
 * @author anhui
 * @since 2019-05-29
 */
@Data
public class RealTimeOperationListItem {

    @ApiModelProperty(value = "列表")
    private List<GoodsOperationPeriodVo> list;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "地点id")
    private Long placeId;
    @ApiModelProperty(value = "运营时间")
    private Date operationDate;

}