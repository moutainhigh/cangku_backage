
package cn.enn.wise.ssop.service.promotions.mapper;


import cn.enn.wise.ssop.service.promotions.model.Partner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * @author shiz
 * 合作伙伴db操作
 */
@Mapper
@Repository
public interface PartnerMapper extends BaseMapper<Partner> {

}

