package cn.enn.wise.ssop.api.cms.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class KnowledgeScenicDTO implements Serializable {


    /**
     * 知识ID
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 封面图片
     */
    @ApiModelProperty("封面图片")
    private String coverUrl;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 浏览量
     */
    @ApiModelProperty("浏览量")
    private Integer viewNumber;

}
