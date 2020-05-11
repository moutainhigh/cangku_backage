package cn.enn.wise.ssop.service.promotions.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 合作伙伴
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "partner")
public class Partner {

    //主键
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 11,isAutoIncrement =true,isKey = true)
    private Long id;

    //合作伙伴名称
    private String name;

    //地址
    private String address;

    //介绍
    private String descs;

    //开户账号
    private String bankAccount;

    //银行用户名
    private String bankName;

    //开户行
    private String bankAddress;

    //联系人名称
    private String contactName;

    //联系方式
    private String contactPhone;

    //联系邮箱地址
    private String contactEmail;

    //服务器白名单
    private String ipWhiteList;

    //服务器通知地址
    private String serverNoticeUrl;

    //appKey
    private String appkey;

    //appSecret
    private String appsecret;

    //创建时间
    private Date createTime;

    //修改时间
    private Date updateTime;

    //禁启用状态 1启用 2禁用
    private int state;

    //是否删除 1正常 2已删除
    private int isDelete;

}