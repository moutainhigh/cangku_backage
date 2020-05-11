package cn.enn.wise.ssop.api.cms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Data
public class ProductCommentDTO implements Serializable {


    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "评价内容")
    private String content;

    @ApiModelProperty(value = "评价时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recTime;

    @ApiModelProperty(value = "星级,0-5级")
    private Integer score;

    @ApiModelProperty(value = "是否显示，1:为显示，2:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是2,，否则1")
    private Integer status;

    @ApiModelProperty(value = "评价(1.很差 2.一般 3.满意 4.非常满意 5.无可挑剔)")
    private Integer evaluate;

    @ApiModelProperty(value = "标签")
    private String prodCommLabel;

    @ApiModelProperty(value = "标签内容")
    private String prodCommLabels;

    @ApiModelProperty(value = "订单客人")
    private String name;




}
