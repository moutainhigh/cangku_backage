package cn.enn.wise.ssop.api.cms.dto.request.oldcms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("添加标题")
public class AddKnowledgeParams {

    @ApiModelProperty("id")
    private Long id;


    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("答案")
    private String answer;

    @ApiModelProperty("附件")
    private String attachment;

    /**
     * 1、文章 2 问答 3 案例
     */
    @ApiModelProperty("1、文章 2 问答 3 案例")
    private Integer knowledgeType;

    /**
     * 业务类别：1 订单问题 2 退款问题 3 安全指引 4景区实况
     */
    @ApiModelProperty("业务类别：1 订单问题 2 退款问题 3 安全指引 4景区实况")
    private Integer businessCategory;

}