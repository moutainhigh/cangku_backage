package cn.enn.wise.platform.mall.bean.bo.autotable;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
@Table(name = "sys_ticket_report")
public class SysTicketReport {


  @Column(name = "id",type = MySqlTypeConstant.BIGINT,length =20,isAutoIncrement = true,isKey = true,comment = "主键")
  @ApiModelProperty(value = "主键")
  private Integer id;

  @Column(name = "report_date",type = MySqlTypeConstant.DATE,comment = "日期")
  @ApiModelProperty(value = "日期")
  private Date reportDate;

  @Column(name = "sell_out_count",type = MySqlTypeConstant.INT,length =11,comment = "卖出票数")
  @ApiModelProperty(value = "卖出票数")
  private Integer sellOutCount;

  @Column(name = "check_in_count",type = MySqlTypeConstant.INT,length =11,comment = "检票人数")
  @ApiModelProperty(value = "检票人数")
  private Integer checkInCount;

  @Column(name = "scans_count",type = MySqlTypeConstant.INT,length =11,comment = "扫码次数")
  @ApiModelProperty(value = "扫码次数")
  private Integer scansCount;

  @Column(name = "scans_percent",type = MySqlTypeConstant.DOUBLE,comment = "扫码转化率")
  @ApiModelProperty(value = "扫码转化率")
  private Double scansPercent;

  @Column(name = "order_count",type = MySqlTypeConstant.INT,length =11,comment = "预订人数")
  @ApiModelProperty(value = "预订人数")
  private Integer orderCount;

  @Column(name = "order_percent",type = MySqlTypeConstant.DOUBLE,comment = "预订转化率")
  @ApiModelProperty(value = "预订转化率")
  private Double orderPercent;

  @Column(name = "order_finish_count",type = MySqlTypeConstant.INT,length =11,comment = "体验成功人数")
  @ApiModelProperty(value = "体验成功人数")
  private Integer orderFinishCount;

  @Column(name = "order_finish_percent",type = MySqlTypeConstant.DOUBLE,comment = "体验成功率")
  @ApiModelProperty(value = "体验成功率")
  private Double orderFinishPercent;

}
