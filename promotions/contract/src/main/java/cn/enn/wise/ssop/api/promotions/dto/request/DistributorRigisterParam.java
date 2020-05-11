package cn.enn.wise.ssop.api.promotions.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * 分销商注册相关参数
 * @author 耿小洋
 */
@Data
@ApiModel("分销商注册相关参数")
public class DistributorRigisterParam {

    @ApiModelProperty("注册状态：1-合作商家 2-运营管理人员")
    @NotNull
    private Byte registerType;

    @ApiModelProperty("分销商名称")
    @NotNull
    private String distributorName;

    @ApiModelProperty("电话")
    @NotNull
    private String phone;

    @ApiModelProperty("密码")
    @NotNull
    private String accountPassword;

    @ApiModelProperty(value = "区Id")
    private Long areaId;

    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty(value = "渠道Id")
    @NotNull
    private Long channelId;

    @ApiModelProperty("渠道名称")
    @NotNull
    private String channelName;

    @ApiModelProperty("分销商类型 1 集团企业 2 个人企业 3 个人")
    @NotNull
    private Byte distributorType;

  /*  @ApiModelProperty(value = "营业执照正面")
    private String businessLicense1;

    @ApiModelProperty(value = "营业执照反面")
    private String businessLicense2;

    @ApiModelProperty(value = "身份证正面")
    private String idCardPage1;

    @ApiModelProperty(value = "身份证背面")
    private String idCardPage2;

    @ApiModelProperty(value = "驾驶证正面")
    private String driverCardPage1;

    @ApiModelProperty(value = "驾驶证反面")
    private String driverCardPage2;

    @ApiModelProperty(value = "导游证正面")
    private String guideCardPage1;

    @ApiModelProperty(value = "导游证反面")
    private String guideCardPage2;*/


    @ApiModelProperty("业务范围")
    private String businessScope;

    @ApiModelProperty("业务对接人")
    @NotNull
    private String businessCounterpart;


    @ApiModelProperty("验证码")
    @NotNull
    private String verificationCode;

    @ApiModelProperty("补充信息文件集合")
    private List<DistribytorAddInfo> distribytorAddInfoList;

    public static class DistribytorAddInfo{

        @ApiModelProperty("类型 1营业执照 2身份证 3驾驶证 4导游证 5电子合同 6电子合同扫描件")
        @NotNull
        public byte type;
        @ApiModelProperty("正面")
        @NotNull
        public String page1;
        @ApiModelProperty("反面")
        public String page2;

    }

    public  DistribytorAddInfo getDistribytorAddInfo(){
        return  new DistribytorAddInfo();
    }



}
