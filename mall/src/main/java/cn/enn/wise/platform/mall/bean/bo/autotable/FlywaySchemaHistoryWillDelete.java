package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "flyway_schema_history_will_delete")
public class FlywaySchemaHistoryWillDelete {

    @Column(name = "installed_rank",type = MySqlTypeConstant.INT, length = 11,isKey = true,isAutoIncrement = true,comment = "主键")
    @ApiModelProperty(value = "installed_rank")
    @TableId(value = "installed_rank", type = IdType.AUTO)
    private Integer installedRank;

    @Column(name = "version",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "版本")
    @ApiModelProperty("版本")
    private String version;

    @Column(name = "description",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "描述")
    @ApiModelProperty("描述")
    private String description;

    @Column(name = "type",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "类型")
    @ApiModelProperty("类型")
    private String type;

    @Column(name = "script",type = MySqlTypeConstant.VARCHAR, length = 1000,comment = "字母")
    @ApiModelProperty("字母")
    private String script;

    @Column(name = "checksum",type = MySqlTypeConstant.INT, length = 11,comment = "核对数量")
    @ApiModelProperty("核对数量")
    private Integer checksum;

    @Column(name = "installed_by",type = MySqlTypeConstant.VARCHAR, length = 100)
    @ApiModelProperty("installed_by")
    private String installedBy;

    @Column(name = "installed_on",type = MySqlTypeConstant.TIMESTAMP)
    @ApiModelProperty("installed_on")
    private String installedOn;

    @Column(name = "execution_time",type = MySqlTypeConstant.INT, length = 11,comment = "核对时间")
    @ApiModelProperty("核对时间")
    private Integer executionTime;

    @Column(name = "success",type = MySqlTypeConstant.INT, length = 2,comment = "成功")
    @ApiModelProperty("成功")
    private Integer success;

}
