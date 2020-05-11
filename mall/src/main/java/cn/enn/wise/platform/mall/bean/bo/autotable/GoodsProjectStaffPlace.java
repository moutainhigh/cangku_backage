package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 *项目员工地点信息表
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "goods_project_staff_place")
public class GoodsProjectStaffPlace {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;


    @Column(name = "staff_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "员工Id")
    @ApiModelProperty(value = "员工Id")
    private Long staffId;

    @Column(name = "project_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "项目id")
    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @Column(name = "place_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "地点Id")
    @ApiModelProperty(value = "地点Id")
    private Long placeId;
}
