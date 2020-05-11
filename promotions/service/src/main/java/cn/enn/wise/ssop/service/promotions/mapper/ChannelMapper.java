
package cn.enn.wise.ssop.service.promotions.mapper;


import cn.enn.wise.ssop.service.promotions.model.Channel;
import cn.enn.wise.ssop.service.promotions.model.Partner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * @author jiabaiye
 * 渠道db操作
 */
@Mapper
@Repository
public interface ChannelMapper extends BaseMapper<Channel> {

}

