package cn.enn.wise.platform.mall.bean.bo.autotable;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/10
 */
@Data
public class TableBase {

    @Column(name = "create_user_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "创建人id")
    @ApiModelProperty("创建人id")
    private Long createUserId;

    @Column(name = "update_user_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "更改人id")
    @ApiModelProperty("更改人id")
    private Long updateUserId;

    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @Column(name = "update_time",type = MySqlTypeConstant.TIMESTAMP,comment = "修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Timestamp updateTime;


}
