package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.api.promotions.dto.response.ActivityRuleDetailsDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ActivityTypeDTO;
import cn.enn.wise.ssop.service.promotions.model.ActivityGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 安辉
 * 活动商品Mapper信息
 */
@Mapper
@Repository
public interface ActivityGoodsMapper extends BaseMapper<ActivityGoods> {

    List<ActivityTypeDTO> getActivityTypeListByGoods(@Param("goods") List<Long> goods);

    List<ActivityRuleDetailsDTO> getActivityRuleDetailsByGoods(@Param("id") Long id);

}
