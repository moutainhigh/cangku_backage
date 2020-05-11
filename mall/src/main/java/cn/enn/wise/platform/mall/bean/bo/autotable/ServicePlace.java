package cn.enn.wise.platform.mall.bean.bo.autotable;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table(name = "service_place")
public class ServicePlace {


  @Column(name = "id",type = MySqlTypeConstant.BIGINT,length =20,isKey = true,isAutoIncrement = true,comment = "主键")
  @ApiModelProperty(value = "主键")
  private Long id;

  @Column(name = "service_place_name",type = MySqlTypeConstant.VARCHAR,length =50,comment = "服务地点名称")
  @ApiModelProperty(value = "服务地点名称")
  private String servicePlaceName;

  @Column(name = "status",type = MySqlTypeConstant.INT,length =11,comment = "1 启用 2 禁用")
  @ApiModelProperty(value = "1 启用 2 禁用")
  private Integer status;

  @Column(name = "company_id",type = MySqlTypeConstant.INT,length =11,comment = "所属景区的id")
  @ApiModelProperty(value = "所属景区的id")
  private Integer companyId;

}
