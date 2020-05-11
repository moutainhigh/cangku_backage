package cn.enn.wise.platform.mall.bean.bo.autotable;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table(name = "scan_code_data_col")
public class ScanCodeDataCol {


  @Column(name = "id",type = MySqlTypeConstant.BIGINT,length =20,isKey = true,isAutoIncrement = true,comment = "主键")
  @ApiModelProperty(value = "主键")
  private Integer id;

  @Column(name = "column_name",type = MySqlTypeConstant.VARCHAR,comment = "接触点名称")
  @ApiModelProperty(value = "接触点名称")
  private String columnName;

  @Column(name = "company_id",type = MySqlTypeConstant.INT,length =11,comment = "景区Id 西藏小程序7 涠洲岛11 巴松措10")
  @ApiModelProperty(value = "景区Id 西藏小程序7 涠洲岛11 巴松措10")
  private Integer companyId;

}
