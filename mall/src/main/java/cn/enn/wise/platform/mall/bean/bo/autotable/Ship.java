package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 船舶数据库信息表
 * @author jiaby
 */
@Data
@Table(name = "ship")
public class Ship extends TableBase{

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "船舶名称")
    @ApiModelProperty("船舶名称")
    private String name;

    @Column(name = "code",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "编号")
    @ApiModelProperty("编号")
    private String code;

    @Column(name = "ship_type",type = MySqlTypeConstant.INT, length = 4,defaultValue = "1",comment = "类型 1-客运")
    @ApiModelProperty("类型 1-客运")
    private Integer shipType;

    @Column(name = "organization",type = MySqlTypeConstant.INT,length = 4,comment = "所属机构 1 华南新绎游船")
    @ApiModelProperty("所属机构 1 华南新绎游船")
    private Integer organization;

    @Column(name = "max_passenger",type = MySqlTypeConstant.INT, length = 10,comment = "最大载客量")
    @ApiModelProperty("最大载客量")
    private Integer maxPassenger;

    @Column(name = "third_id",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "第三方主键id")
    @ApiModelProperty("第三方主键id")
    private String thirdId;

    @Column(name = "status",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "状态 1 运营中 2 停运")
    @ApiModelProperty("状态 1 运营中 2 停运")
    private Byte status;

    @Column(name = "img_url",type = MySqlTypeConstant.VARCHAR,length = 100,comment = "缩略图路径")
    @ApiModelProperty("缩略图路径")
    private String imgUrl;

}
