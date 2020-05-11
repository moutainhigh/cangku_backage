package cn.enn.wise.platform.mall.bean.bo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("withdraw_limit")
public class WithdrawLimit {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 今日已提现金额（总和）
     */
    private Double money;

    /**
     * 今日已提现次数
     */
    private Integer times;

    /**
     * 今日日期
     */
    private LocalDate cDate;

    /**
     * 分销商ID
     */
    private Long distributorId;




}
