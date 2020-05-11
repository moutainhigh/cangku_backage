package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.api.promotions.dto.request.DistributorBaseListParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorBaseDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorListDTO;
import cn.enn.wise.ssop.service.promotions.model.DistributorBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 耿小洋
 * 分销商基础信息Mapper
 */
@Mapper
@Repository
public interface DistributorBaseMapper extends BaseMapper<DistributorBase> {


    List<DistributorListDTO> getDistributorPageInfo(DistributorBaseListParam param);

   int getDistributorCount(DistributorBaseListParam param);

    DistributorBaseDTO getDistributorBaseBy(Long id);

}
