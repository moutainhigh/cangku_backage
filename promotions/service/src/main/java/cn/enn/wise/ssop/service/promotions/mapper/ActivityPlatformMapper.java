package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.ActivityPlatform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author 安辉
 * 投放平台Mapper
 */
@Mapper
@Repository
public interface ActivityPlatformMapper extends BaseMapper<ActivityPlatform> {

}
