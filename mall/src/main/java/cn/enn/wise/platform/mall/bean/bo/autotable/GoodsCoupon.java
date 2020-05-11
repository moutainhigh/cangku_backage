package cn.enn.wise.platform.mall.bean.bo.autotable;

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
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/9
 */
@Data
@Table(name = "goods_coupon")
public class GoodsCoupon extends  TableBase{

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

//    @Column(name = "goods_coupon_rule_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "规则id")
//    @ApiModelProperty("规则id")
//    private Long goodsCouponRuleId;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "名称")
    @ApiModelProperty("名称")
    private String name;

    @Column(name = "use_platform",type = MySqlTypeConstant.INT, length = 10,comment = "使用平台 1-小程序 2-微信 0-任何平台")
    @ApiModelProperty("使用平台 1-小程序 2-微信 0-任何平台")
    private Long usePlatform;

    @Column(name = "org_name",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "费用承担方")
    @ApiModelProperty("费用承担方")
    private String orgName;

    @Column(name = "coupon_type",type = MySqlTypeConstant.INT, length = 10,comment = "优惠券类型 1-抵用券 2-折扣券")
    @ApiModelProperty("优惠券类型 1-抵用券 2-折扣券")
    private Integer couponType;

    @Column(name = "price",type = MySqlTypeConstant.INT, length = 10,comment = "面值 抵用券的时候代表多少元，折扣券的时候是折扣力度（7折，输入70）")
    @ApiModelProperty("面值 抵用券的时候代表多少元，折扣券的时候是折扣力度（7折，输入70）")
    private Integer price;

    @Column(name = "get_limit",type = MySqlTypeConstant.INT, length = 10,defaultValue = "-1",comment = "领取限制数量 -1 不限制")
    @ApiModelProperty("领取限制数量")
    private Integer getLimit;

    @Column(name = "geted_size",type = MySqlTypeConstant.INT, length = 10,defaultValue = "0",comment = "已经领取数量")
    @ApiModelProperty("已经领取数量")
    private Integer getedSize;

    @Column(name = "init_size",type = MySqlTypeConstant.INT, length = 10,defaultValue = "-1",comment = "发放数量 -1 不限制")
    @ApiModelProperty("发放数量")
    private Integer initSize;

    @Column(name = "start_time",type = MySqlTypeConstant.TIMESTAMP,comment = "开始时间")
    @ApiModelProperty("开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startTime;

    @Column(name = "validity_time",type = MySqlTypeConstant.TIMESTAMP,comment = "结束时间（有效时间）")
    @ApiModelProperty("结束时间（有效时间）")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp validityTime;

    @Column(name = "validity_day",type = MySqlTypeConstant.INT, length = 10,defaultValue = "0",comment = "有效期类型 2领取后几日可用 有效天数")
    @ApiModelProperty("有效期类型为2领取后几日可用 有效天数")
    private Integer validityDay;

    @Column(name = "validity_type",type = MySqlTypeConstant.INT, defaultValue = "1",length = 10,comment = "有效期类型 1-固定期限 2领取后几日可用")
    @ApiModelProperty("有效期类型 1-固定期限 2领取后几日可用")
    private Integer validityType;

    @Column(name = "tag",type = MySqlTypeConstant.VARCHAR, length = 100,comment = "业务标签 1 常规 2 奖励")
    @ApiModelProperty("业务标签")
    private String tag;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR, length = 1000,comment = "备注")
    @ApiModelProperty("备注")
    private String remark;

    @Column(name = "status",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "状态 1 可用 2 不可用")
    @ApiModelProperty("状态 1 可用 2 不可用")
    private Byte status;

    @Column(name = "get_type",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "领取方式：1 不限量 2 限量")
    @ApiModelProperty("领取方式：1 不限量 2 限量")
    private Byte getType;

    @Column(name = "init_type",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "领取方式：1 不限量 2 限量")
    @ApiModelProperty("发放方式：1 不限量 2 限量")
    private Byte initType;

    @Column(name = "kind",type = MySqlTypeConstant.INT,length = 10,comment = "优惠券种类 1.门票券 2.民宿券 3.餐饮券")
    @ApiModelProperty("优惠券种类 1.门票券 2.民宿券 3.餐饮券")
    private Integer kind;

}

