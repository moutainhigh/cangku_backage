package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.DistributorBank;
import cn.enn.wise.ssop.service.promotions.model.DistributorBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 耿小洋
 * 分销商财务信息Mapper
 */
@Mapper
@Repository
public interface DistributorBankMapper extends BaseMapper<DistributorBank> {

}
