package cn.enn.wise.ssop.service.promotions.model;


import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分销商财务账号信息表
 * @author jiaby
 */
@Data
@Table(name = "distributor_bank")
public class DistributorBank extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    @Column(name = "distributor_base_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分销商Id")
    private Long distributorBaseId;

    @Column(name = "bank_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "银行名称")
    @ApiModelProperty("银行名称")
    private String bankName;

    @Column(name = "bank_code",type = MySqlTypeConstant.VARCHAR, length = 30,comment = "银行代码")
    @ApiModelProperty("银行代码")
    private String bankCode;

    @Column(name = "user_name",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "账户名")
    @ApiModelProperty("账户名")
    private String userName;

    @Column(name = "card_number",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "银行卡号")
    @ApiModelProperty("银行卡号")
    private String cardNumber;

    @Column(name = "state",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "状态 1 正常 2 停用")
    @ApiModelProperty("状态 1 正常 2 停用")
    private Byte state;

    @Column(name = "bank_address",type = MySqlTypeConstant.VARCHAR,length = 200,comment = "详细地址")
    @ApiModelProperty("详细地址")
    private String bankAddress;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "备注(账户用途)")
    @ApiModelProperty("备注（账户用途）")
    private String remark;
}
