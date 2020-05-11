package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.api.order.dto.request.PcOrderGoodsParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderGoodsListDTO;
import cn.enn.wise.ssop.api.order.dto.response.OrderListDTO;
import cn.enn.wise.ssop.service.order.config.status.OrderStatusEnum;
import cn.enn.wise.ssop.service.order.mapper.OrderGoodsMapper;
import cn.enn.wise.ssop.service.order.mapper.PcComboMapper;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import cn.enn.wise.ssop.service.order.service.PcComboService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PcComboServiceImpl extends ServiceImpl<OrderGoodsMapper, OrderGoods> implements PcComboService {


    @Autowired
    PcComboMapper pcComboMapper;


    @Override
    public OrderListDTO getOrderGoodsList(PcOrderGoodsParam pcOrderGoodsParam) {

        if(pcOrderGoodsParam.getTimeCriteria()!=null&&pcOrderGoodsParam.getTimeCriteria()==1){
            pcOrderGoodsParam.setCheckStartTime(pcOrderGoodsParam.getStartTime());
            pcOrderGoodsParam.setCheckEndTime(pcOrderGoodsParam.getEndTime());
            pcOrderGoodsParam.setStartTime("");
            pcOrderGoodsParam.setEndTime("");
        }
        if(pcOrderGoodsParam.getSearchCriteria()!=null&&pcOrderGoodsParam.getSearchCriteria().equals(1)){
            pcOrderGoodsParam.setOrderNo(pcOrderGoodsParam.getSearch());
            pcOrderGoodsParam.setSearch("");
        }
        if(pcOrderGoodsParam.getSearchCriteria()!=null&&pcOrderGoodsParam.getSearchCriteria().equals(2)){
            pcOrderGoodsParam.setCustomerName(pcOrderGoodsParam.getSearch());
            pcOrderGoodsParam.setSearch("");
        }
        if(pcOrderGoodsParam.getSearchCriteria()!=null&&pcOrderGoodsParam.getSearchCriteria().equals(3)){
            pcOrderGoodsParam.setCertificateNo(pcOrderGoodsParam.getSearch());
            pcOrderGoodsParam.setSearch("");
        }
        if(pcOrderGoodsParam.getSearchCriteria()!=null&&pcOrderGoodsParam.getSearchCriteria().equals(4)){
            pcOrderGoodsParam.setPhone(pcOrderGoodsParam.getSearch());
            pcOrderGoodsParam.setSearch("");
        }
        List<OrderGoodsListDTO> orderGoodsListDTOs = pcComboMapper.searchPcComboList(pcOrderGoodsParam);
        Integer orderCount = pcComboMapper.searchOrderCount(pcOrderGoodsParam);
        pcOrderGoodsParam.setOrderStatus((byte) OrderStatusEnum.TICKET_WAIT_USE.getValue());
        Integer waitOrderCount = pcComboMapper.searchOrderCount(pcOrderGoodsParam);
        orderGoodsListDTOs.forEach(c->{
            if(c.getExtraInformation()!=null&&c.getExtraInformation()!="") {
                String extraInformation = c.getExtraInformation();
                Map<String, Object> map = (Map<String, Object>) JSONObject.parse(extraInformation);
                c.setExtraInformations(map);
            }
        });
        OrderListDTO orderListDTO = new OrderListDTO();
        orderListDTO.setPeopleNumBer(orderCount);
        orderListDTO.setWaitCount(waitOrderCount);
        orderListDTO.setOrderGoodsListDTOS(orderGoodsListDTOs);
        return orderListDTO;
    }

    @Override
    public List<OrderGoods> getPcOrderGoodsDetails(Long orderId) {
        return pcComboMapper.getPcComboListByOrderId(orderId);
    }

}
