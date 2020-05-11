package cn.enn.wise.ssop.api.cms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Results;
import springfox.documentation.service.Tags;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 行程列表
 * @author shiz
 */
@Data
public class TripListAppletDTO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 行程名称
     */
    @ApiModelProperty(value = "行程名称")
    private String name;

    /**
     * 行程图片
     */
    @ApiModelProperty(value = "行程图片")
    private String picture;

    /**
     * 多个行程code
     */
    @ApiModelProperty(value = "多个行程code")
    private List<String> codes;

    /**
     * 景区名称
     */
    @ApiModelProperty(value = "景区名称")
    private String scenic;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String tags;

    /**
     * 景点个数
     */
    @ApiModelProperty(value = "景点个数")
    private Integer scenicSpotCount;

    /**
     * 天数
     */
    @ApiModelProperty(value = "天数")
    private Integer dayCount;

    /**
     * 距离 单位米
     */
    @ApiModelProperty(value = "距离 单位米")
    private Integer distance = 0;

    @ApiModelProperty(value = "景点列表")
    private List<ScenicSpotInfo> scenicSpotInfoList = new ArrayList<>();






}
