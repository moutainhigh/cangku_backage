package cn.enn.wise.ssop.api.cms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@ApiModel(description = "内容列表")
@Data
public class ContentListDTO {

    
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("编号")
    private String code;

    
    @ApiModelProperty("标题")
    private String title;


    @ApiModelProperty("内容类型名称")
    private String typeName;


    @ApiModelProperty("标签名称，关键字")
    private String tags;


    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp updateTime;


}
