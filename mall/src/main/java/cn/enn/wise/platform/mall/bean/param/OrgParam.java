package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/11
 */
@Data
public class OrgParam {

    @ApiModelProperty("费用承担方")
    private String orgName;


    @ApiModelProperty("费用承担方")
    private Integer orgScale;

}
