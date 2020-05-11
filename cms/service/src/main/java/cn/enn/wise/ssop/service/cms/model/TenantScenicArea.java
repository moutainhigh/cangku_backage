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
 * @date:2020/4/10
 * @author:shiz
 * 租户和景区关系表 （多对多）
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "tenant_scenic_area")
public class TenantScenicArea {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;

    /**
     * 租户id
     */
    @Column(name = "company_id",type = MySqlTypeConstant.BIGINT,length = 20,defaultValue = "1",comment = "租户id")
    private Long companyId;

    /**
     * 景区id
     */
    @Column(name = "scenic_area_id",type = MySqlTypeConstant.BIGINT,length = 20,defaultValue = "0",comment = "景区id")
    private Long scenicAreaId;

    /**
     * 景区名称
     */
    @Column(name = "scenic_area_name",type = MySqlTypeConstant.VARCHAR,length = 20,defaultValue = "",comment = "景区名称")
    private String scenicAreaName;

    /**
     * 权限 1 查看 2 管理
     */
    @Column(name = "role",type = MySqlTypeConstant.TINYINT,defaultValue = "1",comment = "1 查看 2 管理")
    private Byte role;

}
