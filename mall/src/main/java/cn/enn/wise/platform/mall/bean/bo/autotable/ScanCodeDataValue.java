package cn.enn.wise.platform.mall.bean.bo.autotable;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
@Table(name = "scan_code_data_value")
public class ScanCodeDataValue {


  @Column(name = "id",type = MySqlTypeConstant.BIGINT,length =20,isAutoIncrement = true,isKey = true,comment = "主键")
  @ApiModelProperty(value = "主键")
  private Integer id;

  @Column(name = "scan_date",type = MySqlTypeConstant.DATE,comment = "扫码日期 yyy-MM-dd")
  @ApiModelProperty(value = "扫码日期 yyy-MM-dd")
  private Date scanDate;

  @Column(name = "scan_amount",type = MySqlTypeConstant.BIGINT,length =20,comment = "扫码次数 默认为0")
  @ApiModelProperty(value = "扫码次数 默认为0")
  private Long scanAmount;

  @Column(name = "scan_type",type = MySqlTypeConstant.BIGINT,length =20,comment = "数据类型 1 扫码次数 2 扫码人次 3 扫码未进入小程序的人数")
  @ApiModelProperty(value = "数据类型 1 扫码次数 2 扫码人次 3 扫码未进入小程序的人数")
  private Long scanType;

  @Column(name = "col_id",type = MySqlTypeConstant.BIGINT,length =20,comment = "关联列")
  @ApiModelProperty(value = "关联列")
  private Long colId;

}
