package cn.enn.wise.ssop.api.promotions.dto.response;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 表名：member
 * 备注：会员基本信息
 *
 * @author jiabaiye
 * @date 2020-04-15
 * @since JDK1.8 normalize-1.0
 */
@Data
@ApiModel("返回会员用户信息")
public class UserDTO {



    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("性别，1男 2女")
    private Integer gender;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("头像")
    private String headImg;

    @ApiModelProperty("微信号")
    private String wechat;

//    @ApiModelProperty("生日，格式yyyy-MM-dd")
//    private String birthday;
//
//    @ApiModelProperty("城市")
//    private String city;
//
//    @ApiModelProperty("省份")
//    private String province;
//
//    @ApiModelProperty("国家")
//    private String country;
//
    @ApiModelProperty("身份证")
    private String idCard;
//
//    @ApiModelProperty("注册来源，例：雅鲁藏布大峡谷（小程序）")
//    private String comeFrom;



}
