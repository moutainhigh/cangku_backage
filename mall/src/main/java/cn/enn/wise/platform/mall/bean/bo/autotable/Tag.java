package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 拼团表
 *
 * @author baijie
 * @date 2019-09-11
 */
@Data
@Table(name = "tag")
public class Tag {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Column(name = "category_id",type = MySqlTypeConstant.BIGINT, length = 20, comment = "外键,分类id")
    private Long categoryId;

    @Column(name = "project_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "外键，指向项目id")
    private String projectId;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "标签名称")
    private String name;


    @Column(name = "type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "标签类型 1 项目 2 商品")
    private Byte type;


    @Column(name = "create_by",type = MySqlTypeConstant.BIGINT,length = 20,comment = "管理员id")
    private Long createBy;

    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_by",type = MySqlTypeConstant.BIGINT,length = 20,comment = "管理员id")
    private Long updateBy;

    @Column(name = "update_time",type = MySqlTypeConstant.TIMESTAMP,comment = "修改时间")
    private Timestamp updateTime;

    @Column(name = "pid",type = MySqlTypeConstant.BIGINT,length = 20,comment = "父级标签id")
    private Long  pid;

}
