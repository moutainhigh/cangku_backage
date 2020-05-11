package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.RefundApply;
import cn.enn.wise.platform.mall.bean.param.BoatRefundApplyParam;
import cn.enn.wise.platform.mall.bean.param.RefundApplyParam;
import cn.enn.wise.platform.mall.bean.param.RefundReqParam;
import cn.enn.wise.platform.mall.bean.param.UpdateApprovalsParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/31 13:58
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
public interface RefundApplyService {

    long refundApply(RefundApplyParam refundApplyParam,String openId);

    long unifyRefundApply(RefundApplyParam refundApplyParam);

    long updateRefundSts(String refundNum);

    RefundApply findRefundDetail(String orderCode);

    ComposeRefundOrderVo appRefundDetail(String orderCode);

    ComposeRefundOrderVo appRefundDetailV2(String orderCode);

    ComposeRefundOrderVo laiU8RefundDetail(String orderCode);

    List<OrderRefundVo> refundOrderDetail(String orderCode);

    PageInfo<RefundOrderPc> findRefundListPc(RefundReqParam refundReqParam);

    RefundDetailPcVo findRefundDetailPc(String refundNum);

    long batchRefund(String[] id);

    long updateApprovalsSts(UpdateApprovalsParam updateApprovalsParam);

    long unifyUpdateApprovalsSts(UpdateApprovalsParam updateApprovalsParam);

    long sendRefund(String refundNum);

    RefundBoatOrderVo refundBoatOrderDetail(String orderCode,Long userId);

    long refundApplyBoat(BoatRefundApplyParam boatRefundApplyParam,Long userId);
}
