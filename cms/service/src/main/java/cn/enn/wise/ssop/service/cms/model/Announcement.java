package cn.enn.wise.ssop.service.cms.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * 公告
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "announcement")
public class Announcement extends TableBase {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;

    /**
     * 内容
     */
    @Column(name = "content",type = MySqlTypeConstant.VARCHAR,length = 500,defaultValue = "",comment = "内容")
    private String content;

    /**
     * 1启用  2禁用
     */
    @Column(name = "state",type = MySqlTypeConstant.TINYINT,defaultValue = "0",comment = "1启用  2禁用")
    private Byte state;

    /**
     * 类别id
     */
    @Column(name = "category_id",type = MySqlTypeConstant.BIGINT,defaultValue = "0",comment = "类别id")
    private Long categoryId;

    /**
     * 发布者
     */
    @Column(name = "publisher_name",type = MySqlTypeConstant.VARCHAR,length = 100,defaultValue = "",comment = "内容")
    private String publisherName;

    /**
     * 发布时间
     */
    @Column(name = "publish_time",type = MySqlTypeConstant.TIMESTAMP,comment = "发布时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp publishTime;

    /**
     * 浏览量
     */
    @Column(name = "view_number",type = MySqlTypeConstant.INT,defaultValue = "0",comment = "浏览量")
    private Integer viewNumber;

    /**
     * 景区ID
     */
    @Column(name = "scenic_area_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "景区ID")
    private Long scenicAreaId;

    /**
     * 景区名称
     */
    @Column(name = "scenic_area_name",type = MySqlTypeConstant.VARCHAR,length = 30,defaultValue = "",comment = "景区名称")
    private String scenicAreaName;

}
