package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table(name = "route")
public class Route extends TableBase{


  @Column(name = "id",type = MySqlTypeConstant.BIGINT,length =50,isAutoIncrement = true,isKey = true,comment = "主键")
  @ApiModelProperty(value = "主键")
  private Long id;

  @Column(name = "code",type = MySqlTypeConstant.VARCHAR,length =50,comment = "线路编号")
  @ApiModelProperty(value = "线路编号")
  private String code;

  @Column(name = "name",type = MySqlTypeConstant.VARCHAR,length =50,comment = "线路名称")
  @ApiModelProperty(value = "线路名称")
  private String name;

  @Column(name = "scenic",type = MySqlTypeConstant.BIGINT,length =20,comment = "景区Id")
  @ApiModelProperty(value = "景区Id")
  private Long scenic;

  @Column(name = "state",type = MySqlTypeConstant.INT,length =11,comment = "线路状态：1代表启用，2代表停用。默认1")
  @ApiModelProperty(value = "线路状态：1代表启用，2代表停用。默认1")
  private Integer state;

  @Column(name = "type",type = MySqlTypeConstant.INT,length =11,comment = "景区内外1、内；2、外")
  @ApiModelProperty(value = "景区内外1、内；2、外")
  private Integer type;

  @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR,length =50,comment = "创建人名称")
  @ApiModelProperty(value = "创建人名称")
  private String createUserName;

  @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR,length =50,comment = "修改人名称")
  @ApiModelProperty(value = "修改人名称")
  private String updateUserName;

}
