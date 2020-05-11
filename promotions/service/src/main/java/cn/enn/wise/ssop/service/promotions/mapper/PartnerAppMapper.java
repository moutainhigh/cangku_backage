
package cn.enn.wise.ssop.service.promotions.mapper;


import cn.enn.wise.ssop.service.promotions.model.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * @author shiz
 * 客户端db操作
 */
@Mapper
@Repository
public interface PartnerAppMapper extends BaseMapper<Application> {

}

