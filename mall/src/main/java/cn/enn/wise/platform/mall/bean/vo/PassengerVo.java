package cn.enn.wise.platform.mall.bean.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/26 09:58
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
@Builder
public class PassengerVo {

    @ApiModelProperty(value = "乘客姓名")
    private String passengerName;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "101:成人 203:儿童 308:小童票")
    private String ticketType;

    @ApiModelProperty(value = "座位号")
    private String seatNumber;

    @ApiModelProperty(value = "船票状态 1 未使用 2已使用 3 已退款")
    private Integer shipTicketStatus;

    @ApiModelProperty(value = "携童信息")
    private String babyInfo;

    @ApiModelProperty(value = "船票Id")
    private Integer ticketId;

    @ApiModelProperty(value = "原价")
    private String price;

    @ApiModelProperty(value = "扣除手续费")
    private String fee;

    @ApiModelProperty(value = "可退金额")
    private String refundPrice;

}
