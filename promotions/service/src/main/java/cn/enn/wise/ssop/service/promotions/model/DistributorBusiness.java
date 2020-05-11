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
 * 分销商业务信息表
 * @author jiaby
 */
@Data
@Table(name = "distributor_business")
public class DistributorBusiness extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    @Column(name = "distributor_base_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分销商Id")
    private Long distributorBaseId;

    @Column(name = "level",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "综合等级  1 初级 2 中级 3 高级")
    @ApiModelProperty("综合等级  1 初级 2 中级 3 高级")
    private Byte level;

    @Column(name = "channel_type",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "渠道类型  1 直营 2 分销")
    @ApiModelProperty("渠道类型  1 直营 2 分销")
    private Byte channelType;

    @Column(name = "resource_type",type = MySqlTypeConstant.VARCHAR, length = 50, comment = "资源类型  1 酒店 2 导游 3 租车")
    @ApiModelProperty("资源类型  1 酒店 2 导游 3 租车")
    private String resourceType;

    @ApiModelProperty(value = "积分")
    @Column(name = "grade",type = MySqlTypeConstant.BIGINT,length = 20,comment = "积分")
    private Long grade;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "备注")
    @ApiModelProperty("备注")
    private String remark;

    @Column(name = "cooperation_method",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "合作方式  1 线上运营 2 线下运营")
    @ApiModelProperty("合作方式  1 线上运营 2 线下运营")
    private Byte cooperationMethod;

    @Column(name = "settlement_method",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "结算方式  1 底价结算 2 返利结算")
    @ApiModelProperty("结算方式  1 底价结算 2 返利结算")
    private Byte settlementMethod;

    @Column(name = "settlement_type",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "结算周期（类型）  1 月结 2 日结")
    @ApiModelProperty("结算周期（类型）  1 月结 2 日结")
    private Byte settlementType;

//    @Column(name = "settlement_time",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "结算时间")
//    @ApiModelProperty("结算时间")
//    private String settlementTime;

    @Column(name = "enjoy_service",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "享受服务")
    @ApiModelProperty("享受服务")
    private String enjoyService;

    @Column(name = "business_scope",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "业务范围 1 酒店 2 特产")
    @ApiModelProperty("业务范围 1 酒店 2 特产")
    private String businessScope;

    @Column(name = "business_counterpart",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "业务对接人")
    @ApiModelProperty("业务对接人")
    private String businessCounterpart;

    @Column(name = "preferential_strength",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "优惠力度")
    @ApiModelProperty("优惠力度")
    private String preferentialStrength;


}
