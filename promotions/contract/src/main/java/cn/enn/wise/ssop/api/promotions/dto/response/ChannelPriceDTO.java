package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@ApiModel(description = "渠道政策售价返利信息")
@Data
public class ChannelPriceDTO {

    @ApiModelProperty("售价")
    private String salePrice;

    @ApiModelProperty("结算价")
    private String settlementPrice;

    @ApiModelProperty("返利")
    private String rebate;

    @ApiModelProperty("渠道政策可用日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Timestamp ruleDay;

}