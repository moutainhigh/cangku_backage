package cn.enn.wise.platform.mall.bean.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/7
 */
@Data
public class KnowledgeVo {

    /**
     * id
     */
    @ApiModelProperty("主键id")
    private Long id;
    /**
     * 业务类别：1 订单问题 2 退款问题 3 安全指引 4景区实况
     */
    @ApiModelProperty( "业务类别：1 订单问题 2 退款问题 3 安全指引 4景区实况")
    private Integer businessCategory;

    /**
     * 编号
     */
    @ApiModelProperty( "编号")
    private String code;

    /**
     * 1、文章 2 问答 3 案例
     */
    @ApiModelProperty("1、文章 2 问答 3 案例")
    private Integer knowledgeType;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 知识内容
     */
    @ApiModelProperty("答案")
    private String answer;


    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty("创建人id")
    private Long createBy;

    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    private Timestamp updateTime;

    @ApiModelProperty("更新人id")
    private Long updateBy;

    /**
     * 附件
     */
    @ApiModelProperty("附件")
    private String attachment;

}
