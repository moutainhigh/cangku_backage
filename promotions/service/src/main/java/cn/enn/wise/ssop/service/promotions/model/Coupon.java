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
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@Table(name = "coupon")
public class Coupon extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "code",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "券id-根据规则生成")
    @ApiModelProperty("券id-根据规则生成")
    private String code;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "名称")
    @ApiModelProperty("名称")
    private String name;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR, length = 1000,comment = "备注")
    @ApiModelProperty("备注")
    private String remark;

    @Column(name = "state",type = MySqlTypeConstant.TINYINT,length = 1, defaultValue = "1", comment = "状态 1 有效 2 无效")
    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte state;

    @Column(name = "coupon_type",type = MySqlTypeConstant.TINYINT, length = 1,comment = "优惠券类型 1-体验券 2-满减券 3-代金券")
    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

}

