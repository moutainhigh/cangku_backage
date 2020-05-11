package cn.enn.wise.ssop.service.order.mapper;



import cn.enn.wise.ssop.api.order.dto.request.PcOrderGoodsParam;
import cn.enn.wise.ssop.api.order.dto.response.OrderGoodsListDTO;
import cn.enn.wise.ssop.service.order.model.OrderGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PcComboMapper extends BaseMapper<OrderGoods> {


    Integer searchOrderCount(PcOrderGoodsParam pcOrderGoodsParam); //查询条数

    List<OrderGoods> getPcComboListByOrderId(Long orderId);//详情

    List<OrderGoodsListDTO> searchPcComboList(PcOrderGoodsParam pcOrderGoodsParam);//列表

}
