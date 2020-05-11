package cn.enn.wise.ssop.api.cms.dto.request.oldmall;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FeedBackParam {
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("1.未回复2.已回复3.已忽略")
    private Integer status;
    @ApiModelProperty("1.团餐餐饮2.大峡⾕谷酒店3.景区观光车4.景区服务45.其他")
    private Integer businessType;
    @ApiModelProperty("页码(默认1)")
    private int pageNum = 1;
    @ApiModelProperty("每页最大条目数(默认10)")
    private int pageSize = 10;
    @ApiModelProperty("景区ID")
    private Integer scenicId;
}
