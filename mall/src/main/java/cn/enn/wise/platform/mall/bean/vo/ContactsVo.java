package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zsj
 * @date 2019/12/24  15:37
 */
@Data
@ApiModel("联系人返回参数")
public class ContactsVo {

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
