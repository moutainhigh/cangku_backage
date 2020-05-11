package cn.enn.wise.ssop.service.cms.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * 分类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "category")
public class Category extends TableBase {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;

    /**
     * 分类名称
     */
    @Column(name = "category_name",type = MySqlTypeConstant.VARCHAR,length = 10,defaultValue = "",comment = "分类名称")
    private String categoryName;

    /**
     * 副标题
     */
    @Column(name = "subtitle",type = MySqlTypeConstant.VARCHAR,length = 20,defaultValue = "",comment = "副标题")
    private String subtitle;

    /**
     * 本级排序
     */
    @Column(name = "sort",type = MySqlTypeConstant.VARCHAR,length = 300,defaultValue = "",comment = "本级排序")
    private Integer sort;

    /**
     * 1启用  2禁用
     */
    @Column(name = "state",type = MySqlTypeConstant.TINYINT,defaultValue = "0",comment = "1启用  2禁用")
    private Byte state;

    /**
     * 父id
     */
    @Column(name = "parent_id",type = MySqlTypeConstant.BIGINT,defaultValue = "-1",comment = "父id")
    private Long parentId;








}