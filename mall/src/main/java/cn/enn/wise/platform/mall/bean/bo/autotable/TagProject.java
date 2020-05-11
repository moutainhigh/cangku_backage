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
@Table(name = "tag_project")
public class TagProject {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Column(name = "project_id",type = MySqlTypeConstant.BIGINT, length = 20, comment = "项目id")
    private Long projectId;

    @Column(name = "tag_id",type = MySqlTypeConstant.BIGINT, length = 20, comment = "标签id")
    private Long tagId;

    @Column(name = "create_by",type = MySqlTypeConstant.BIGINT,length = 20,comment = "管理员id")
    private Long createBy;

    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_by",type = MySqlTypeConstant.BIGINT,length = 20,comment = "管理员id")
    private Long updateBy;

    @Column(name = "update_time",type = MySqlTypeConstant.TIMESTAMP,comment = "修改时间")
    private Timestamp updateTime;

}
