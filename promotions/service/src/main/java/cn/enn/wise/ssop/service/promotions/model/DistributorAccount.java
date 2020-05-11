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
 * 分销商账号信息表
 * @author jiaby
 */
@Data
@Table(name = "distributor_account")
public class DistributorAccount extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    @Column(name = "distributor_base_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分销商Id")
    private Long distributorBaseId;

    @ApiModelProperty(value = "账号Id")
    @Column(name = "distributor_account_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "账号Id")
    private Long distributorAccountId;

    @ApiModelProperty("电话")
    @Column(name = "phone",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "电话")
    private String phone;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR,length = 500,comment = "备注")
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否短信通知 1是 2 否")
    @Column(name = "send_message",type = MySqlTypeConstant.TINYINT,length = 2, defaultValue = "2",comment = "是否短信通知 1是 2 否")
    private Byte sendMessage;

    @ApiModelProperty("是否默认密码 1是 2否")
    @Column(name = "isdefault_password",type = MySqlTypeConstant.TINYINT,length = 2, defaultValue = "2",comment = "是否默认密码 1是 2否")
    private Byte isdefaultPassword;

}
