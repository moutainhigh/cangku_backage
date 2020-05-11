package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/9
 */
@Data
@Table(name = "goods_coupon_promotion")
public class GoodsCouponPromotion extends  TableBase{
    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(name = "code",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "优惠券活动编号")
    @ApiModelProperty("优惠券活动编号")
    private String code;


    @Column(name = "company_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "景区Id")
    @ApiModelProperty("景区Id")
    private Long companyId;

    @Column(name = "company_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "景区名称")
    @ApiModelProperty("景区名称")
    private String companyName;

    @Column(name = "scenic_spots",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "活动景点")
    @ApiModelProperty("活动景点")
    private String scenicSpots;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "活动名称")
    @ApiModelProperty("活动名称")
    private String name;

    @Column(name = "status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "活动状态( 1未开始 2活动中 3已结束 4 已失效)")
    @ApiModelProperty("活动状态( 1未开始 2活动中 3已结束 4 已失效)")
    private Byte status;

    @Column(name = "cost",type = MySqlTypeConstant.DECIMAL,length = 12,decimalLength = 2,comment = "预计成本")
    @ApiModelProperty("预计成本")
    private BigDecimal cost;

    @Column(name = "promotion_type",type = MySqlTypeConstant.TINYINT,length =4 ,comment = "活动类型 1 拼团 2 营销")
    @ApiModelProperty("活动类型 1 拼团 2 营销")
    private String promotionType;

    @Column(name = "org_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "成本部门")
    @ApiModelProperty("成本部门")
    private String orgName;

    /**
     * 开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_time",type = MySqlTypeConstant.TIMESTAMP,comment = "开始时间")
    @ApiModelProperty("开始时间")
    private Timestamp startTime;

    /**
     * 结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time",type = MySqlTypeConstant.TIMESTAMP,comment = "结束时间")
    @ApiModelProperty("结束时间")
    private Timestamp endTime;

    @Column(name = "manager",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "负责人")
    @ApiModelProperty("负责人")
    private String manager;

    @Column(name = "promotion_crowd_type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "活动对象类型( 1 通用 2 制定人群)")
    @ApiModelProperty("活动对象类型( 1 通用 2 制定人群)")
    private Byte promotionCrowdType;

    @Column(name = "promotion_crowd",type = MySqlTypeConstant.VARCHAR,length =2000 ,comment = "指定人群 1 新人 2 分销商")
    @ApiModelProperty("指定人群 1 新人 2 分销商")
    private String promotionCrowd;

    @Column(name = "promotion_reject_type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "活动互斥类型( 1 共享 2 互斥)")
    @ApiModelProperty("活动互斥类型( 1 共享 2 互斥)")
    private Byte promotionRejectType;

    @Column(name = "reject_promotion",type = MySqlTypeConstant.VARCHAR,length =2000 ,comment = "互斥活动")
    @ApiModelProperty("互斥活动")
    private String rejectPromotion;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR,length =2000 ,comment = "备注：活动规则")
    @ApiModelProperty("备注：活动规则")
    private String remark;


    @Column(name = "reason",type = MySqlTypeConstant.VARCHAR,length =2000 ,comment = "取消原因")
    @ApiModelProperty("取消原因")
    private String reason;


}
