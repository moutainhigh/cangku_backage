package cn.enn.wise.ssop.api.order.dto.response.scan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;


@ApiModel("扫描结果")
@Data
public class ScanResultDTO {

    @ApiModelProperty("订单id")
    private Long orderId;//订单id

    @ApiModelProperty("票code 如果核销时用就加")
    private String ticketCode;//票code 如果核销时用就加

    @ApiModelProperty("订单状态描述")
    private String orderStateStr;//订单状态描述

    @ApiModelProperty("单票的话 票号：00000  整批分批订单的话 订单号：000000")
    private String ticketOrderCode;//单票的话 票号：00000  整批分批订单的话 订单号：000000

    @ApiModelProperty("产品名称")
    private String productName;//产品名称

    @ApiModelProperty("规格值名称—— 1级规格值及细化规格值")
    private String specificationValue;//规格值名称—— 1级规格值及细化规格值

    @ApiModelProperty("预订日期")
    private String bookingDate;//预订日期

    @ApiModelProperty("游客姓名")
    private String touristName;//游客姓名

    @ApiModelProperty("身份证号")
    private String touristIdCardNumber;//身份证号

    @ApiModelProperty("游客数")
    private int touristCount;//游客数

    @ApiModelProperty("联系电话")
    private String touristPhone;//联系电话

    @ApiModelProperty("子票 如果是非套餐核销 这个list是没有数据的")
    private List<OrderChildDTO> orderChildList;//子票 如果是非套餐核销 这个list是没有数据的

    @ApiModelProperty("非套餐整批 分批会有")
    private ContactInfoDTO contactInfo;//非套餐整批 分批会有

    @ApiModelProperty("附加信息描述（app中接送类型这几个字）")
    private String extraInfoDes;//附加信息描述（app中接送类型这几个字）

    @ApiModelProperty("附加信息")
    private List<ExtraInfoDTO> extraInfoList;//附加信息

    @ApiModelProperty("流转记录")
    private UseRecordInfoDTO useRecordInfo;

    @ApiModelProperty("核销方式：0整批 1分批")
    private int isCheckAll;//0整批 1分批


}
