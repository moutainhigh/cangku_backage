package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.ActivityBase;
import cn.enn.wise.ssop.service.promotions.model.Channel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author 安辉
 * 活动基础信息Mapper
 */
@Mapper
@Repository
public interface ActivityBaseMapper extends BaseMapper<ActivityBase> {

}
