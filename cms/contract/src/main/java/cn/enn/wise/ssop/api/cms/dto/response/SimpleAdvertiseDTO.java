package cn.enn.wise.ssop.api.cms.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SimpleAdvertiseDTO {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
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
     * 广告目标 1 活动推广 2 下载引流 3会员拉新 4其他
     */
    @ApiModelProperty("广告目标 1 活动推广 2 下载引流 3会员拉新 4其他 [advertiseTarget]")
    private Byte target;

    /**
     * 投放位置 1 banner 2 首页线路
     */
    @ApiModelProperty("投放位置 1 banner 2 首页线路 [advertiseLocation]")
    private Byte location;

    /**
     * 播放方式 1 从左到右 2 从右到左   3淡入淡出
     */
    @ApiModelProperty(value = "播放方式 1 从左到右 2 从右到左   3淡入淡出",example = "1")
    private Byte playMode;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 1启用  2禁用
     */
    @ApiModelProperty("1启用  2禁用")
    private Byte state;

    /**
     * 加载次数
     */
    @ApiModelProperty("加载次数")
    private Long loadNumber;

    /**
     * 点击次数
     */
    @ApiModelProperty("点击次数")
    private Long clickNumber;

    /**
     * 发布时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("发布时间")
    private Timestamp publishTime;

    /**
     * 下架时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("下架时间")
    private Timestamp outTime;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp updateTime;

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
     * 类别id
     */
    @ApiModelProperty("类别id")
    private Long categoryId;

    /**
     * 类别名称
     */
    @ApiModelProperty("类别名称")
    private String categoryName;

    /**
     * 多个标签名称，逗号隔开
     */
    @ApiModelProperty("多个标签名称，逗号隔开")
    private String tags;

    /**
     * 链接类型 2攻略   3知识   4商品  5活动   6景点
     */
    @ApiModelProperty("链接类型 2攻略   3知识   4商品  5活动   6景点")
    private Byte linkType;

    /**
     * 链接内容ID
     */
    @ApiModelProperty("链接内容ID")
    private Long linkId;



}
