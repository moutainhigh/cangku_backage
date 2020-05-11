package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *ios app版本类
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "app_version")
public class AppVersionTable {

    @Column(name = "companyId",type = MySqlTypeConstant.INT, length = 11,comment = "景区id")
    @ApiModelProperty("景区id")
    private Integer companyId;

    @Column(name = "version",type = MySqlTypeConstant.VARCHAR,length = 20,comment = "版本")
    @ApiModelProperty("版本")
    private String version;
}
