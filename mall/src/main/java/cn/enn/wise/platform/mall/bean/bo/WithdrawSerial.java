package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("withdraw_serial")
public class WithdrawSerial {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 当前日期
     */
    private LocalDate cDate;

    /**
     * 今日已达流水号
     */
    private Integer serial;

}
