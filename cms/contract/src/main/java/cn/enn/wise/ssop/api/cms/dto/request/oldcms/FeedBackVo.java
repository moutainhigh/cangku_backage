package cn.enn.wise.ssop.api.cms.dto.request.oldcms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@ApiModel("建议反馈")
@Data
public class FeedBackVo implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty("反馈内容")
    private String content;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("1.未回复2.已回复3.已忽略")
    private Integer status;
    @ApiModelProperty("1.团餐餐饮2.大峡⾕谷酒店3.景区观光车4.景区服务45.其他")
    private Integer businessType;
    @ApiModelProperty("短信")
    private String messages;
    @ApiModelProperty("反馈图片")
    private String picture;
    @ApiModelProperty("反馈时间")
    private String createTime;
    @ApiModelProperty("景区ID")
    private Integer scenicId;
    @ApiModelProperty("类型名称")
    private String businessName;
    @ApiModelProperty("标题")
    private String title;

}
