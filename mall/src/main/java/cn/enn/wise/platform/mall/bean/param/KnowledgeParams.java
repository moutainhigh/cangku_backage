package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/7
 */
@Data
@ApiModel("知识库参数")
public class KnowledgeParams {

    @ApiModelProperty("标题")
    private String title;

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

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endTime;



}
