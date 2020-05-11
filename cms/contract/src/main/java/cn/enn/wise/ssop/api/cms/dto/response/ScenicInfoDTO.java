package cn.enn.wise.ssop.api.cms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


@Data
public class ScenicInfoDTO implements Serializable {

    @ApiModelProperty(value = "景点id")
    private Integer id;

    @ApiModelProperty(value = "景点名称")
    private String name;

    @ApiModelProperty(value = "景区编码")
    private String code;

    @ApiModelProperty(value = "类别")
    private Integer type;

    @ApiModelProperty(value = "中心维度")
    private Double lat;

    @ApiModelProperty(value = "中心经度")
    private Double lon;

    @ApiModelProperty(value = "0未使用，1可使用")
    private Integer isUsed;

    @ApiModelProperty(value = "营业开始时间")
    private String startTime;

    @ApiModelProperty(value = "营业结束时间")
    private String endTime;

    @ApiModelProperty(value = "最佳游玩时间")
    private String optimumTime;

    @ApiModelProperty(value = "景点半径")
    private Integer radius;

    @ApiModelProperty(value = "图片路径")
    private String picUrl;

    @ApiModelProperty(value = "音频路径")
    private String redLangue;

    @ApiModelProperty(value = "视频路径")
    private String video;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "数据更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp updateTime;

    @ApiModelProperty(value = "地址信息")
    private String address;

    @ApiModelProperty(value = "是否收藏")
    private Boolean enshrine;

    @ApiModelProperty(value = "知识ids")
    private String knowledgesIds;

    @ApiModelProperty(value = "关联全部知识内容")
    private List<KnowledgeScenicDTO> knowledgeDTOS;



}
