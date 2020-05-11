package cn.enn.wise.platform.mall.bean.bo.autotable;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("withdraw_serial")
@Table(name = "withdraw_serial")
public class WithdrawSerial {

    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键")
    private Long id;

    @Column(name = "c_date",type = MySqlTypeConstant.DATE,comment = "日期")
    private LocalDate cDate;

    @Column(name = "serial",type = MySqlTypeConstant.INT,length = 10,comment = "今日已达流水号")
    private Integer serial;

}
