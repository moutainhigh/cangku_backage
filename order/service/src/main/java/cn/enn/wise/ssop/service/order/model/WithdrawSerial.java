package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDate;



@TableName("withdraw_serial")
@Table(name = "withdraw_serial")
@Data
@ApiModel("提现序列号")
public class WithdrawSerial extends TableBase {

    @Unique
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true,comment = "主键")
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 当前日期
     */
    @ApiModelProperty("当前日期")
    @Column(name = "c_date",type = MySqlTypeConstant.DATE,comment = "当前日期")
    private LocalDate cDate;

    /**
     * 今日已达流水号
     */
    @Column(name = "serial",type = MySqlTypeConstant.INT,comment = "今日已达流水号",length = 11)
    private Integer serial;

}
