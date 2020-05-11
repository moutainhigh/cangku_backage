package cn.enn.wise.ssop.service.cms.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @date:2020/4/2
 * @author:sz
 * 广告
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "advertise")
public class Advertise extends TableBase {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;


    /**
     * 标题
     */
    @Column(name = "title",type = MySqlTypeConstant.VARCHAR,length = 30,defaultValue = "",comment = "标题")
    private String title;

    /**
     * 副标题
     */
    @Column(name = "subtitle",type = MySqlTypeConstant.VARCHAR,length = 30,defaultValue = "",comment = "副标题")
    private String subtitle;

    /**
     * 封面图片/视频url
     */
    @Column(name = "cover_url",type = MySqlTypeConstant.VARCHAR,length = 300,defaultValue = "",comment = "封面图片/视频url")
    private String coverUrl;

    /**
     * 广告路径
     */
    @Column(name = "address",type = MySqlTypeConstant.VARCHAR,length = 300,defaultValue = "",comment = "广告路径")
    private String address;

    /**
     * 广告目标 1 活动推广 2 下载引流 3会员拉新 4其他
     */
    @Column(name = "target",type = MySqlTypeConstant.TINYINT,defaultValue = "1",comment = "广告目标 1 活动推广 2 下载引流 3会员拉新 4其他")
    private Byte target;

    /**
     * 投放位置 1 banner 2 首页线路
     */
    @Column(name = "location",type = MySqlTypeConstant.TINYINT,defaultValue = "1",comment = "投放位置 1 banner 2 首页线路")
    private Byte location;

    /**
     * 播放方式 1 从左到右 2 从右到左   3淡入淡出
     */
    @Column(name = "play_mode",type = MySqlTypeConstant.TINYINT,defaultValue = "1",comment = "播放方式 1 从左到右 2 从右到左 3淡入淡出")
    private Byte playMode;

    /**
     * 排序
     */
    @Column(name = "sort",type = MySqlTypeConstant.INT,defaultValue = "1",comment = "排序")
    private Integer sort;

    /**
     * 1启用  2禁用
     */
    @Column(name = "state",type = MySqlTypeConstant.TINYINT,defaultValue = "0",comment = "1启用  2禁用")
    private Byte state;

    /**
     * 景区ID
     */
    @Column(name = "scenic_area_id",type = MySqlTypeConstant.BIGINT,length = 20,defaultValue = "0",comment = "景区ID")
    private Long scenicAreaId;

    /**
     * 点赞量
     */
    @Column(name = "click_number",type = MySqlTypeConstant.BIGINT,length = 20,defaultValue = "0",comment = "点赞量")
    private Long clickNumber;

    /**
     * 加载量
     */
    @Column(name = "load_number",type = MySqlTypeConstant.BIGINT,length = 20,defaultValue = "0",comment = "加载量")
    private Long loadNumber;

    /**
     * 景区名称
     */
    @Column(name = "scenic_area_name",type = MySqlTypeConstant.VARCHAR,length = 30,defaultValue = "",comment = "景区名称")
    private String scenicAreaName;

    /**
     * 链接类型 2攻略   3知识   4商品  5活动   6景点
     */
    @Column(name = "link_type",type = MySqlTypeConstant.TINYINT,defaultValue = "1",comment = "链接类型  2攻略   3知识   4商品  5活动   6景点")
    private Byte linkType;

    /**
     * 链接内容ID
     */
    @Column(name = "link_id",type = MySqlTypeConstant.VARCHAR,length = 30,defaultValue = "",comment = "链接内容ID")
    private Long linkId;

    /**
     * 发布时间
     */
    @Column(name = "publish_time",type = MySqlTypeConstant.TIMESTAMP,comment = "发布时间")
    private Timestamp publishTime;

    /**
     * 下架时间
     */
    @Column(name = "out_time",type = MySqlTypeConstant.TIMESTAMP,comment = "下架时间")
    private Timestamp outTime;

    /**
     * 类别id
     */
    @Column(name = "category_id",type = MySqlTypeConstant.BIGINT,defaultValue = "0",comment = "类别id")
    private Long categoryId;

    /**
     * 多个标签名称，逗号隔开
     */
    @Column(name = "tags",type = MySqlTypeConstant.VARCHAR,length = 100,defaultValue = "",comment = "多个标签名称，逗号隔开")
    private String tags;


}
