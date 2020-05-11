package cn.enn.wise.ssop.api.cms.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
public class KnowledgeSaveParam {

    @ApiModelProperty(value = "主键, 增加不需要传或传null",example = "null")
    private Long id;

    /**
     * 语音讲解
     */
    @ApiModelProperty(value = "语音讲解",required = true)
    @NotNull
    private String voiceUrl;


    /**
     * 标题
     */
    @ApiModelProperty(value = "标题",required = true)
    @NotNull
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容",required = true)
    @NotNull
    private List<RichText> contentList;

    /**
     * 商品ids，逗号隔开
     */
    @ApiModelProperty(value = "商品ids，逗号隔开")
    private String goodsIds;


    /**
     * 多个标签名称，逗号隔开
     */
    @ApiModelProperty(value = "多个标签名称，逗号隔开",required = true)
    @NotNull
    private String tags;


    /**
     * 1启用  2禁用
     */
    @ApiModelProperty(value = "1启用  2禁用",required = true)
    @NotNull
    private Byte state;


    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间",required = true)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    @NotNull
    private Timestamp publishTime;


    /**
     * 类别id
     */
    @ApiModelProperty(value = "类别id",required = true)
    @NotNull
    private Long categoryId;

    /**
     * 封面图片/视频url
     */
    @NotNull
    @ApiModelProperty(value = "封面图片/视频url",required = true)
    private String coverUrl;

    /**
     * 景区ID
     */
    @ApiModelProperty("景区ID")
    @NotNull
    private Long scenicAreaId;

    /**
     * 景区名称
     */
    @ApiModelProperty("景区名称")
    @NotNull
    private String scenicAreaName;
}
