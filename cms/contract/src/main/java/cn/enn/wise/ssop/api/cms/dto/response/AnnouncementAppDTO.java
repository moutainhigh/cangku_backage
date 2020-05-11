package cn.enn.wise.ssop.api.cms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class AnnouncementAppDTO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容",required = true)
    private String content;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间",required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp publishTime;


}
