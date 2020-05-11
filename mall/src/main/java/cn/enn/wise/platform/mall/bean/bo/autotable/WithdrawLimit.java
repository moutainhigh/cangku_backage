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
@TableName("withdraw_limit")
@Table(name = "withdraw_limit")
public class WithdrawLimit {


    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键")
    private Long id;

    @Column(name = "money",type = MySqlTypeConstant.DOUBLE,length = 11,decimalLength = 2,comment = "今日已提现订单金额")
    private Double money;

    @Column(name = "times",type = MySqlTypeConstant.INT,length = 10,comment = "今日已提现订单次数")
    private Integer times;

    @Column(name = "c_date",type = MySqlTypeConstant.DATE,comment = "日期")
    private LocalDate cDate;

    @Column(name = "distributor_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分销商ID")
    private Long distributorId;




}
