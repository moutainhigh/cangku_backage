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
 * 分销商联系人信息表
 * @author jiaby
 */
@Data
@Table(name = "distributor_contact")
public class DistributorContact extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    @Column(name = "distributor_base_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分销商Id")
        private Long distributorBaseId;

    @Column(name = "contact_name",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "姓名")
    @ApiModelProperty("姓名")
    private String contactName;

    @Column(name = "phone",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "电话")
    @ApiModelProperty("电话")
    private String phone;

    @Column(name = "qq",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "QQ")
    @ApiModelProperty("QQ")
    private String qq;

    @Column(name = "email",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "邮箱")
    @ApiModelProperty("邮箱")
    private String email;

    @Column(name = "wechat",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "微信")
    @ApiModelProperty("微信")
    private String wechat;

    @Column(name = "position",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "职务")
    @ApiModelProperty("职务")
    private String position;

    @ApiModelProperty(value = "省Id")
    @Column(name = "province_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "省Id")
    private Long provinceId;

    @Column(name = "province_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "省名称")
    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty(value = "城市Id")
    @Column(name = "city_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "城市Id")
    private Long cityId;

    @Column(name = "city_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "城市名称")
    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty(value = "区Id")
    @Column(name = "area_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "区Id")
        private Long areaId;

    @Column(name = "area_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "区名称")
    @ApiModelProperty("区名称")
    private String areaName;

    @Column(name = "contact_address",type = MySqlTypeConstant.VARCHAR,length = 200,comment = "详细地址")
    @ApiModelProperty("详细地址")
    private String contactAddress;

}
