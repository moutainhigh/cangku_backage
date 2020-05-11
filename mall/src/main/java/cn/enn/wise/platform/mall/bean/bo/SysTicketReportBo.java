package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
@ApiModel("订单信息")
@TableName("sys_ticket_report")
public class SysTicketReportBo {

    @ApiModelProperty(value = "业务主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "日期")
    private Date reportDate;

    @ApiModelProperty(value = "当天买票数量（当天入园）")
    private Integer sellOutCount;

    @ApiModelProperty(value = "已经入园人数")
    private Integer checkInCount;

    @ApiModelProperty(value = "扫码次数")
    private Integer scansCount;

    @ApiModelProperty(value = "扫码转化率")
    private Double scansPercent;

}
