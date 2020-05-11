
package cn.enn.wise.ssop.service.promotions.mapper;


import cn.enn.wise.ssop.service.promotions.model.GroupOrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @author jiabaiye
 * 拼团订单操作
 */
@Mapper
@Repository
public interface GroupOrderItemMapper extends BaseMapper<GroupOrderItem> {

    Long getCountInsertOrderByGoodsAndUserId(@Param("groupActivityId")Long groupActivityId,@Param("userId")Long userId,@Param("goodId")Long goodId);

    void selectOne(Long groupOrderId);
}

