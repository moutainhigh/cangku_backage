package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/3/31 16:55
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
@Table(name = "award_rule")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AwardRule {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Column(name = "award_code",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "编号")
    @ApiModelProperty(value = "编号")
    private String awardCode;

    @Column(name = "coupon_id",type = MySqlTypeConstant.INT,length = 5,comment = "优惠券Id")
    private Integer couponId;

    @Column(name = "award_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "券名称")
    @ApiModelProperty(value = "券名称")
    private String awardName;

    @Column(name = "probability",type = MySqlTypeConstant.FLOAT,length = 50,comment = "概率（0.1代表10%，最多3位小数，即千分之一级）")
    @ApiModelProperty(value = "概率（0.1代表10%，最多3位小数，即千分之一级）")
    private float probability;

    @Column(name = "amount",type = MySqlTypeConstant.INT,length = 50,comment = "数量（该类奖品剩余数量）")
    @ApiModelProperty(value = "数量（该类奖品剩余数量）")
    private Integer amount;

    @Column(name = "validity_time",type = MySqlTypeConstant.TIMESTAMP,comment = "有效期")
    @ApiModelProperty("有效期")
    private Timestamp validityTime;

    @ApiModelProperty(value = "优惠券种类 1.门票券 2.民宿券 3.餐饮券")
    @Column(name = "kind",type = MySqlTypeConstant.INT,length = 2,comment = "优惠券种类 1.门票券 2.民宿券 3.餐饮券")
    private Integer kind;

}
