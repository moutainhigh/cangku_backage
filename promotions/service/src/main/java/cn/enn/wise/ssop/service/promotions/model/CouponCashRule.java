package cn.enn.wise.ssop.service.promotions.model;


import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@Table(name = "coupon_cash_rule")
public class CouponCashRule extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "优惠券ID")
    @Column(name = "coupon_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "优惠券ID")
    private Long couponId;

    @Column(name = "rebate_method",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "优惠计算方式 1 金额  2 折扣/百分比")
    @ApiModelProperty("优惠计算方式 1 金额  2 折扣/百分比")
    private Byte rebateMethod;

    @Column(name = "rebate_price",type = MySqlTypeConstant.INT,length = 12,comment = "加价多少/折扣多少")
    @ApiModelProperty("加价多少/折扣多少")
    private Integer rebatePrice;

    @Column(name = "israndom",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "是否随机金额 1 是  2 不是")
    @ApiModelProperty("是否随机金额 1 是  2 不是")
    private Byte israndom;

    @Column(name = "min_price",type = MySqlTypeConstant.INT,length = 12,comment = "随机最小金额")
    @ApiModelProperty("随机最小金额")
    private Integer minPrice;

    @Column(name = "max_price",type = MySqlTypeConstant.INT,length = 12,comment = "随机最大金额")
    @ApiModelProperty("随机最大金额")
    private Integer maxPrice;

    @Column(name = "goods_special",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "是否产品专用  1 是 2 否")
    @ApiModelProperty("是否产品专用  1 是 2 否")
    private Byte goodsSpecial;

    @Column(name = "issend",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "是否可以转赠 1 是 2 否")
    @ApiModelProperty("是否可以转赠 1 是 2 否")
    private Byte issend;

    @Column(name = "start_time",type = MySqlTypeConstant.TIMESTAMP,comment = "开始时间")
    @ApiModelProperty("开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp startTime;

    @Column(name = "end_time",type = MySqlTypeConstant.TIMESTAMP,comment = "结束时间（有效时间）")
    @ApiModelProperty("结束时间（有效时间）")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp endTime;

    @Column(name = "validity_day",type = MySqlTypeConstant.INT, length = 10,defaultValue = "0",comment = "有效期类型 2领取后几日可用 有效天数")
    @ApiModelProperty("有效期类型为2领取后几日可用 有效天数")
    private Integer validityDay;

    @Column(name = "validity_type",type = MySqlTypeConstant.INT, defaultValue = "1",length = 10,comment = "有效期类型 1-固定期限 2领取后几日可用")
    @ApiModelProperty("有效期类型 1-固定期限 2领取后几日可用")
    private Integer validityType;

    @Column(name = "unuse_start_time",type = MySqlTypeConstant.TIMESTAMP,comment = "不可用开始时间")
    @ApiModelProperty("不可用开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp unuseStartTime;

    @Column(name = "unuse_end_time",type = MySqlTypeConstant.TIMESTAMP,comment = "不可用结束时间")
    @ApiModelProperty("不可用结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp unuseEndTime;



}
