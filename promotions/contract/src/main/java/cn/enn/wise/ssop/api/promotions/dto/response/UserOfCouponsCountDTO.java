package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("用户用券统计")
public class UserOfCouponsCountDTO {

    @ApiModelProperty("发放张数")
    private Integer sendNumber;

    @ApiModelProperty("发放金额")
    private Integer sendPrice;

    @ApiModelProperty("领取张数")
    private Integer receivedNumber;

    @ApiModelProperty("领取金额")
    private Integer receivedPrice;

    @ApiModelProperty("使用张数")
    private Integer useNumber;

    @ApiModelProperty("使用金额")
    private Integer usePrice;

    @ApiModelProperty("待使用张数")
    private Integer notUseNumber;

    @ApiModelProperty("待使用金额")
    private Integer notUsePrice;

    @ApiModelProperty("过期张数")
    private Integer overdueNumber;

    @ApiModelProperty("过期金额")
    private Integer overduePrice;
}
