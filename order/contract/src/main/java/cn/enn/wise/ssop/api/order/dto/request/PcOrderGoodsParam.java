package cn.enn.wise.ssop.api.order.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("pc用户传入参数")
public class PcOrderGoodsParam extends QueryParam {


    @ApiModelProperty(value = "订单来源 1小程序 2 APP 3 第三方订单 4  H5")
    private Byte orderSource;

    @ApiModelProperty(value = "渠道id")
    private Integer channelId;

    @ApiModelProperty(value = "渠道名称")
    private String channelName;

    @ApiModelProperty(value = "景点名")
    private String scenicName;

    @ApiModelProperty(value = "订单状态")
    private Byte orderStatus;

    @ApiModelProperty(value = "订单联系人证件号")
    private String certificateNo;

    @ApiModelProperty(value = "支付状态")
    private Byte payStatus;

    @ApiModelProperty(value = "交易状态")
    private Byte transactionStatus;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "电话/订单编号/姓名,搜索条件")
    private String search;

    @ApiModelProperty(value = "游客姓名")
    private String customerName;

    @ApiModelProperty(value = "订单类型 1 门票 2 体验项目 3 住宿 4 套餐 5 导游 6 热气球 7 缆车 8 租车 9 餐饮 10酒店")
    private Byte orderCategory;


    private Integer isMaster;

    @ApiModelProperty(value = "套餐使用时间")
    private String checkStartTime;

    @ApiModelProperty(value = "搜索的类型 1 订单编号 2 游客名称 3游客身份证号 4 联系人手机号")
    private Byte searchCriteria;

    @ApiModelProperty(value = "结束时间")
    private String checkEndTime;

    @ApiModelProperty(value = "1全部时间 2今日 3本周 4本月 5自定义时间 ")
    private Byte timeCriteria;


}
