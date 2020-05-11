package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;


/**
 *项目时段关联信息表
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "goods_project_operation")
public class GoodsProjectOperation extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "period_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "运营时段")
    @ApiModelProperty(value = "运营时段")
    private Long periodId;

    @Column(name = "service_place_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "运营地点Id")
    @ApiModelProperty(value = "运营地点Id")
    private Long servicePlaceId;

    @Column(name = "operation_date",type = MySqlTypeConstant.DATE, comment = "运营日期")
    @ApiModelProperty(value = "运营日期")
    private Date operationDate;

    @Column(name = "period_title",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "时段标题")
    @ApiModelProperty(value = "时段标题")
    private String periodTitle;

    @Column(name = "probability",type = MySqlTypeConstant.INT, length = 11,comment = "可飞行概率1-100 表示 1%-100%")
    @ApiModelProperty(value = "可飞行概率1-100 表示 1%-100%")
    private Integer probability;

    @Column(name = "degree_of_influence",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "影响等级")
    @ApiModelProperty(value = "影响等级")
    private String degreeOfInfluence;

    @Column(name = "status",type = MySqlTypeConstant.INT, length = 4,comment = "状态：1 正常 2 停飞 3 临时征用")
    @ApiModelProperty(value = "状态：1 正常 2 停飞 3 临时征用")
    private Byte status;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "创建人")
    @ApiModelProperty(value = "创建人")
    private String createUserName;

    @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "更新人")
    @ApiModelProperty(value = "更新人")
    private String updateUserName;


}
