package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.DistributorContact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 耿小洋
 * 分销商联系人信息Mapper
 */
@Mapper
@Repository
public interface DistributorContactMapper extends BaseMapper<DistributorContact> {

}
