package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.GroupPromotionBo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * GroupOrder Mapper 接口
 * </p>
 *
 * @author jiabaiye
 * @since 2019-09-12
 */
public interface GroupPromotionMapper extends BaseMapper<GroupPromotionBo> {




    /**
     * 根据id获取拼团活动信息
     * @param id
     * @return
     */
    GroupPromotionBo getGroupPromotionById(@Param("id") Long id);



}
