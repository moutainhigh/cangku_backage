package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 拼单活动
 *
 * @author baijie
 * @date 2019-09-11
 */
@Data
@Table(name = "group_promotion")
public class GroupPromotion {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Column(name = "code",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "编号")
    private String code;

    @Column(name = "rule_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "外键指向 group_rule 的主键")
    private Long ruleId;

    @Column(name = "company_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "景区Id")
    private Long companyId;

    @Column(name = "company_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "景区名称")
    private String companyName;

    @Column(name = "scenic_spots",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "景点")
    private String scenicSpots;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "名称")
    private String name;

    /**
     * 开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_time",type = MySqlTypeConstant.TIMESTAMP,comment = "开始时间")
    private Timestamp startTime;

    /**
     * 结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time",type = MySqlTypeConstant.TIMESTAMP,comment = "结束时间")
    private Timestamp endTime;

    @Column(name = "cost",type = MySqlTypeConstant.DECIMAL,length = 12,decimalLength = 2,comment = "预计成本")
    private BigDecimal cost;


    @Column(name = "org_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "成本部门")
    private String orgName;

    @Column(name = "status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "( 1未开始 2活动中 3已结束 4 已失效)")
    private Byte status;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    private Timestamp createTime;

    @Column(name = "create_by",type = MySqlTypeConstant.BIGINT,length = 20,comment = "创建人id")
    private Long createBy;

    @Column(name = "group_manager",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "负责人")
    private String groupManager;

    @Column(name = "group_type",type = MySqlTypeConstant.TINYINT,length =4 ,comment = "活动类型 1 拼团 2 营销")
    private String groupType;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR,length =2000 ,comment = "备注：活动规则")
    private String remark;

    @Column(name = "reason",type = MySqlTypeConstant.VARCHAR,length =2000 ,comment = "失效原因")
    private String reason;


    @Column(name = "promotion_crowd_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "( 1 通用 2 制定人群)")
    private Byte promotionCrowdStatus;

    @Column(name = "promotion_reject_status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "( 1 共享 2 互斥)")
    private Byte promotionRejectStatus;


    @Column(name = "reject_promotion",type = MySqlTypeConstant.VARCHAR,length =2000 ,comment = "互斥活动")
    private String rejectPromotion;

    @Column(name = "crowd_promotion",type = MySqlTypeConstant.VARCHAR,length =2000 ,comment = "互斥人群")
    private String crowdPromotion;


}
