package cn.enn.wise.platform.mall.bean.param;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 团购请求参数
 *
 * @author anhui
 * @since 2019-09-12
 */
@Data
@ApiModel("团购订单请求参数")
public class GroupOrderParam {

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("联系人姓名")
    private String contacts;

    @ApiModelProperty("拼团状态 1待成团 2 拼团中 3拼团成功 4拼团失败")
    private List<Byte> status;

    @ApiModelProperty("活动名称")
    private String promotionName;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("是否自动成团 1 是 2 否")
    private String isAutoFinish;

    @ApiModelProperty("没页显示条数")
    private Long offset;

    @ApiModelProperty("当前页面")
    private Long limit;
}
