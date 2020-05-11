package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 联系人请求参数
 * @author zsj
 * @date 2019/12/24  11:08
 */
@Data
@ApiModel("联系人请求参数")
public class ContactsParam {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("会员id")
    private Long memberId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("身份证号码")
    private String idCard;

    @ApiModelProperty("手机号码")
    private String phoneNum;

    @ApiModelProperty("联系人类型  101-成人  203-儿童  404-携童")
    private Integer ticketType;
}
