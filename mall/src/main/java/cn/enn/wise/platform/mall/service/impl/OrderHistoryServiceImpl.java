package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.autotable.OrderHistory;
import cn.enn.wise.platform.mall.controller.export.BBDTicketStateParam;
import cn.enn.wise.platform.mall.mapper.OrderDao;
import cn.enn.wise.platform.mall.mapper.OrderHistoryMapper;
import cn.enn.wise.platform.mall.service.OrderHistoryService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * @program: mall
 * @author: zsj
 * @create: 2020-01-07 16:33
 **/
@Service
public class OrderHistoryServiceImpl extends ServiceImpl<OrderHistoryMapper, OrderHistory> implements OrderHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryServiceImpl.class);

    @Autowired
    private OrderHistoryMapper orderHistoryMapper;
    @Autowired
    private OrderDao orderDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity ticketChange(BBDTicketStateParam bBDTicketStateParam) {
        //先根据id查出记录
        //OrderTickets orderTickets = orderDao.findOrderTicketsById(bBDTicketStateParam.getTicketId());
        //if(orderTickets == null){
        //    throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"请输入有效信息!");
        //}
        //插入一条历史记录
        LOGGER.info("======更改票号=====");
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setTicketSerialBbd(bBDTicketStateParam.getTicketNum());
        orderHistory.setCreateTime(new Timestamp(System.currentTimeMillis()));
        orderHistory.setQrCode(bBDTicketStateParam.getQrCode());
        orderHistory.setTicketId(bBDTicketStateParam.getTicketId());
        orderHistoryMapper.insert(orderHistory);
        //修改为新的票号
        orderDao.updateOrderTicketSerialBBD(bBDTicketStateParam.getTicketId(), bBDTicketStateParam.getTicketNum(),bBDTicketStateParam.getQrCode());
        LOGGER.info("======更改票号成功=====");
        return new ResponseEntity();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity ticketSave(Long ticketId, String ticketNum, String qrCode) {
        //if(StringUtils.isBlank(ticketId.toString()) || StringUtils.isBlank(ticketNum)) {
        //    throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "请输入参数!");
        //}
        LOGGER.info("======保存历史记录====="+ticketId+"票号"+ticketNum+"二维码"+qrCode);
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setTicketId(ticketId);
        orderHistory.setTicketSerialBbd(ticketNum);
        orderHistory.setQrCode(qrCode);
        orderHistory.setCreateTime(new Timestamp(System.currentTimeMillis()));
        orderHistoryMapper.insert(orderHistory);
        LOGGER.info("=====保存数据成功=======");
        return new ResponseEntity();
    }
}
