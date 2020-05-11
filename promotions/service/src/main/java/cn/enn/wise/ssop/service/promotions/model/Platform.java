package cn.enn.wise.ssop.service.promotions.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动投放平台信息表（适用于所有活动）
 * @author jiaby
 */
@Data
@Table(name = "platform")
public class Platform extends TableBase {
    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "投放平台名称")
    @ApiModelProperty("投放平台名称")
    private String name;

    @Column(name = "state",type = MySqlTypeConstant.TINYINT, length = 1, comment = "状态 1 可用  2 不可用")
    @ApiModelProperty("状态 1 可用  2 不可用")
    private Byte state;
}
