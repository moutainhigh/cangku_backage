package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/9 13:49
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class UpdateApprovalsParam {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "不通过原因")
    private String noPassReason;

    @ApiModelProperty(value = "审批状态: 1:待审批 2.审批通过 3.审批不通过")
    private Integer approvalsSts;

    @ApiModelProperty(value = "短信验证码")
    private String code;

}
