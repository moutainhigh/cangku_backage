package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.OrderRefundExtend;
import cn.enn.wise.platform.mall.bean.bo.RefundApply;
import cn.enn.wise.platform.mall.bean.param.RefundApplyDetailedParam;
import cn.enn.wise.platform.mall.bean.param.RefundReqParam;
import cn.enn.wise.platform.mall.bean.vo.RefundOrderPc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/31 13:59
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Mapper
public interface RefundApplyMapper extends BaseMapper<RefundApply> {

    long addOrderRefundExtend(@Param("refundApplyDetailedParamList") List<RefundApplyDetailedParam> refundApplyDetailedParamList);

    List<OrderRefundExtend> findOrderRefundExtend(@Param("refundApplyIdList") List<Integer> refundApplyIdList);

    long updateOrderTicketRefundSts(@Param("id") Integer id);

    List<RefundOrderPc> findRefundListPc(@Param("refundReqParam") RefundReqParam refundReqParam);

    List<OrderRefundExtend> findOrderRefundByRefundNum(@Param("refundNum") String refundNum);

    long batchRefund(@Param("id") String[] id);

    List<OrderRefundExtend> findOrderRefundExtends(@Param("id") Integer id);

    long updateOrderTicketById(@Param("id") Integer id);

    long updateBoatOrderSts(@Param("ticketIds") String ticketIds,@Param("status") Integer status,@Param("refundPrice") String refundPrice,@Param("fee") String fee);

}
