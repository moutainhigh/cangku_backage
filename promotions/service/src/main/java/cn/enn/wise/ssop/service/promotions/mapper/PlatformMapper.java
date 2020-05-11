package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.Platform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author 安辉
 * 投放平台信息Mapper
 */
@Mapper
@Repository
public interface PlatformMapper extends BaseMapper<Platform> {

}
