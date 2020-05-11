package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.DistributorAccount;
import cn.enn.wise.ssop.service.promotions.model.DistributorAdd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 耿小洋
 * 分销商账号信息Mapper
 */
@Mapper
@Repository
public interface DistributorAccountMapper extends BaseMapper<DistributorAccount> {

}
