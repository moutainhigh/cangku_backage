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
 * 合作伙伴客户端
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "application")
public class Application {

    //主键
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 11,isAutoIncrement =true,isKey = true)
    private Long id;

    //合作伙伴id
    private Long partnerId;

    //客户端类型 1 微信小程序
    private int clientType;

    //appId
    private String appId;

    //微信支付商户号id
    private String mchId;

    //微信支付商户公匙
    private String mchKey;

    //微信支付商户私匙
    private Byte[] mchPrivateKey;

    //创建时间
    private Date createTime;

    //修改时间
    private Date updateTime;

    //禁启用状态 1启用 2禁用
    private int state;

    //是否删除 1正常 2已删除
    private int deleted;



}