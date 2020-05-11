package cn.enn.wise.platform.mall.bean.param;

import lombok.Data;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/3/4 14:47
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class PFTOrderCallBack {

    private String VerifyCode;

    private String Order16U;

    private String ActionTime;

    private String OrderCall;

    private String Tnumber;

    private String OrderState;

    private String AllCheckNum;

    private String Action;

    private String Explain;

    private String Refundtype;

    private String Source;

    private String RefundAmount;

    private String RefundFee;

    private String RemoteSn;
}
