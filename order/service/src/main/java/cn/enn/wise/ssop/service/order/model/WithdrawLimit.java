package cn.enn.wise.ssop.service.order.model;


import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;


@TableName("withdraw_limit")
@Table(name = "withdraw_limit")
@Data
@ApiModel("提现操作上限")
public class WithdrawLimit extends TableBase {


    @Unique
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true,comment = "主键")
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 今日已提现金额（总和）
     */
    @Column(name = "money",type = MySqlTypeConstant.DECIMAL,decimalLength = 2,length = 19,comment = "今日已提现金额（总和）")
    @ApiModelProperty("今日已提现金额（总和）")
    private Double money;

    /**
     * 今日已提现次数
     */
    @Column(name = "times",type = MySqlTypeConstant.INT,length = 11,comment = "今日已提现次数")
    @ApiModelProperty("今日已提现次数")
    private Integer times;

    /**
     * 今日日期
     */
    @Column(name = "c_date",type = MySqlTypeConstant.DATE,comment = "今日日期")
    @ApiModelProperty("今日日期")
    private LocalDate cDate;

    /**
     * 分销商ID
     */
    @Column(name="distributor_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分销商ID")
    @ApiModelProperty("分销商ID")
    private Long distributorId;




}
