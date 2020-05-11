package cn.enn.wise.platform.mall.bean.bo.autotable;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table(name = "schedule_jobs")
public class ScheduleJobs {


  @Column(name = "id",type = MySqlTypeConstant.BIGINT,length =20,isKey = true,isAutoIncrement = true,comment = "定时任务主键")
  @ApiModelProperty(value = "定时任务主键")
  private Long id;

  @Column(name = "group_name",type = MySqlTypeConstant.VARCHAR,length =50,comment = "任务分组（可按业务分）")
  @ApiModelProperty(value = "任务分组（可按业务分）")
  private String groupName;

  @Column(name = "job_name",type = MySqlTypeConstant.VARCHAR,length =50,comment = "任务名称（英文）")
  @ApiModelProperty(value = "任务名称（英文）")
  private String jobName;

  @Column(name = "description",type = MySqlTypeConstant.VARCHAR,length =50,comment = "定时任务描述")
  @ApiModelProperty(value = "定时任务描述")
  private String description;

  @Column(name = "cron",type = MySqlTypeConstant.VARCHAR,length =50,comment = "定时任务执行频率（cron表达式）")
  @ApiModelProperty(value = "定时任务执行频率（cron表达式）")
  private String cron;

  @Column(name = "url",type = MySqlTypeConstant.VARCHAR,length =50,comment = "访问路径")
  @ApiModelProperty(value = "访问路径")
  private String url;

  @Column(name = "status",type = MySqlTypeConstant.INT,length =11,comment = "状态：0-暂停执行 1-执行")
  @ApiModelProperty(value = "状态：0-暂停执行 1-执行")
  private Integer status;

}
