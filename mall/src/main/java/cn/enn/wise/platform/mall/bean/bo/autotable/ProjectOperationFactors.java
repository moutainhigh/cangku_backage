package cn.enn.wise.platform.mall.bean.bo.autotable;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table(name = "project_operation_factors")
public class ProjectOperationFactors {


  @Column(name = "id",type = MySqlTypeConstant.BIGINT,length =20,isKey = true,isAutoIncrement = true,comment = "主键")
  @ApiModelProperty(value = "主键")
  private Long id;

  @Column(name = "project_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "项目ID")
  @ApiModelProperty(value = "项目ID")
  private Long projectId;

  @Column(name = "factor_type",type = MySqlTypeConstant.INT,length =11,comment = "因素类型 1 运营时段因素 2 非时段因素")
  @ApiModelProperty(value = "因素类型 1 运营时段因素 2 非时段因素")
  private Integer factorType;

  @Column(name = "operation_status",type = MySqlTypeConstant.INT,length =11,comment = "运营状态1 正常 2 停止")
  @ApiModelProperty(value = "运营状态1 正常 2 停止")
  private Integer operationStatus;

  @Column(name = "operation_status_type",type = MySqlTypeConstant.INT,length =11,comment = "1 数值 2 文字标签")
  @ApiModelProperty(value = "1 数值 2 文字标签")
  private Integer operationStatusType;

  @Column(name = "status",type = MySqlTypeConstant.INT,length =11,comment = "状态 1 有效 2无效")
  @ApiModelProperty(value = "状态 1 有效 2无效")
  private Integer status;

  @Column(name = "desc_value",type = MySqlTypeConstant.VARCHAR,length =11,comment = "文字描述")
  @ApiModelProperty(value = "文字描述")
  private String descValue;

  @Column(name = "reason",type = MySqlTypeConstant.VARCHAR,length =50,comment = "原因,多个原因用逗号分割")
  @ApiModelProperty(value = "原因,多个原因用逗号分割")
  private String reason;

  @Column(name = "degree_of_influence",type = MySqlTypeConstant.VARCHAR,length =50,comment = "影响范围程度")
  @ApiModelProperty(value = "影响范围程度")
  private String degreeOfInfluence;

  @Column(name = "label",type = MySqlTypeConstant.VARCHAR,length =50,comment = "文字描述标签")
  @ApiModelProperty(value = "文字描述标签")
  private String label;

  @Column(name = "is_show_applet",type = MySqlTypeConstant.INT,length =11,comment = "是否在小程序端展示  1是 2 否")
  @ApiModelProperty(value = "是否在小程序端展示  1是 2 否")
  private Integer isShowApplet;

}
