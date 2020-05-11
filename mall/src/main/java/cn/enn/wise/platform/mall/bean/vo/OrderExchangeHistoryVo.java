package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/1/13 19:30
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class OrderExchangeHistoryVo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("百邦达票号")
    private String ticketSerialBbd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty("二维码")
    private String qrCode;

    @ApiModelProperty("票主键")
    private Long ticketId;

    @ApiModelProperty(value = "次数")
    private String numbers;

}
