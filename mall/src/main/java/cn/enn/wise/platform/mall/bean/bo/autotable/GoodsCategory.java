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
@Table(name = "goods_category")
public class GoodsCategory extends TableBase{

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键id")
    private Long id;

    @Column(name = "parent_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "父分类id")
    @ApiModelProperty("父分类id")
    private Long parentId;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "名称")
    @ApiModelProperty("名称")
    private String name;

    @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "创建人名称")
    @ApiModelProperty("创建人名称")
    private String create_user_name;

    @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "修改人名称")
    @ApiModelProperty("修改人名称")
    private String update_user_name;

}
