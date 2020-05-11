package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 *商品售卖单位信息表
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "goods_sales_unit")
public class GoodsSalesUnit extends TableBase{


    @Column(name = "name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "计价单位")
    @ApiModelProperty(value = "计价单位")
    private String name;

    /**
     * 创建人名称
     */
    @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "创建人名称")
    @ApiModelProperty("创建人名称")
    private String createUserName;


    /**
     * 修改人名称
     */
    @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "修改人名称")
    @ApiModelProperty("修改人名称")
    private String updateUserName;
}
