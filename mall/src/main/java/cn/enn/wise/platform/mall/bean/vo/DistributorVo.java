package cn.enn.wise.platform.mall.bean.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * <p>
 * 分销用户表
 * </p>
 *
 * @author caiyt
 * @since 2019-05-29
 */
@Data
public class DistributorVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 运营单位
     */
    private Long companyId;

    /**
     * 运营单位名称
     */
    private String companyName;

    /**
     * 微信昵称
     */
    private String wechatNickname;

    /**
     * 会员id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 身份证号
     */
    private String idCardNumber;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 状态 1 有效 2 无效
     */
    private Byte status;

    /**
     * 申请日期
     */
    private Date applyDate;

    /**
     * 审核状态：0-待审核 1-审核通过 2-审核未通过
     */
    private Byte verifyStatus;

    /**
     * 级别
     */
    private Byte level;

    /**
     * 身份身份角色
     */
    private String userRole;

    /**
     * 备注
     */
    private String remark;

    /**
     * 注册来源：1 大峡谷运营App
     */
    private Byte source;

    /**
     * 附件地址
     */
    private String attachmentUrl;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 创建人id
     */
    private Long createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 更新人id
     */
    private Long updateUserId;

    /**
     * 修改人名称
     */
    private String updateUserName;

    /**
     * 过期日期
     */
    private Timestamp expiredTime;

    /**
     * 已提交申请次数
     */
    private Integer applyTimes;




}