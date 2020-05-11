package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 船舱数据库表信息
 * @author jiaby
 */
@Data
@Table(name = "cabin")
public class Cabin extends TableBase{

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "船舱名称")
    @ApiModelProperty("船舱名称")
    private String name;

    @Column(name = "cabin_id",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "船舱第三方id")
    @ApiModelProperty("船舱第三方id")
    private String cabinId;

    @Column(name = "ship_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "船舶主键id")
    @ApiModelProperty("船舶主键id")
    private Long shipId;

    @Column(name = "img_url",type = MySqlTypeConstant.VARCHAR,length = 100,comment = "缩略图路径")
    @ApiModelProperty("缩略图路径")
    private String imgUrl;

    @Column(name = "status",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "状态 1 运营中 2 停运")
    @ApiModelProperty("状态 1 启用中 2 未启用")
    private Byte status;

    @Column(name = "passenger_size",type = MySqlTypeConstant.INT, length = 10,comment = "载客量")
    @ApiModelProperty("载客量")
    private Integer passengerSize;
}
