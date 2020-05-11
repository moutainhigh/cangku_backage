package cn.enn.wise.ssop.api.order.dto.response.scan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel("联系人信息列表")
@Data
public class ContactInfoDTO {

    @ApiModelProperty("联系人总数")
    private int totalCount;//联系人总数

    @ApiModelProperty("待核验数量")
    private int toBeUsedCount;//待核验数量

    @ApiModelProperty("已核验数量")
    private int usedCount;//已核验数量

    @ApiModelProperty("已退款数量")
    private int refundCount;//已退款数量

    @ApiModelProperty("联系人列表")
    private List<ContactDTO> contactList;//联系人列表

    @ApiModelProperty("是否实名制，0否")
    private int actualName;//是否实名制，0否
}
