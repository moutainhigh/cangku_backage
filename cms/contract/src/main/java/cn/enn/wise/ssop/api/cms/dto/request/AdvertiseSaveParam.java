package cn.enn.wise.ssop.api.cms.dto.request;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AdvertiseSaveParam {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键",example = "null")
    private Long id;


    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 副标题
     */
    @ApiModelProperty("副标题")
    private String subtitle;

    /**
     * 封面图片/视频url
     */
    @ApiModelProperty("封面图片/视频url")
    private String coverUrl;

    /**
     * 广告路径
     */
    @ApiModelProperty("广告路径")
    private String address;

    /**
     * 投放位置 1 banner 2 首页线路
     */
    @ApiModelProperty(value = "投放位置 1 banner 2 首页线路 [advertiseLocation]",example = "1")
    private Byte location;

    /**
     * 播放方式 1 从左到右 2 从右到左   3淡入淡出
     */
    @ApiModelProperty(value = "播放方式 1 从左到右 2 从右到左 3淡入淡出 [advertisePlayMode]",example = "1")
    private Byte playMode;

    /**
     * 1启用  2禁用
     */
    @ApiModelProperty(value = "1启用  2禁用",example = "1")
    private Byte state;

    /**
     * 景区ID
     */
    @ApiModelProperty("景区ID")
    private Long scenicAreaId;

    /**
     * 景区名称
     */
    @ApiModelProperty("景区名称")
    private String scenicAreaName;

    /**
     * 链接类型 2攻略   3知识   4商品  5活动   6景点
     */
    @ApiModelProperty("链接类型  2攻略   3知识   4商品  5活动   6景点")
    private Byte linkType;

    /**
     * 链接内容ID
     */
    @ApiModelProperty("链接内容")
    private String linkId;

    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    private Timestamp publishTime;

    /**
     * 下架时间
     */
    @ApiModelProperty("下架时间")
    private Timestamp outTime;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 类别id
     */
    @ApiModelProperty("类别id")
    private Long categoryId;

    /**
     * 多个标签名称，逗号隔开
     */
    @ApiModelProperty("多个标签名称，逗号隔开")
    private String tags;


}
