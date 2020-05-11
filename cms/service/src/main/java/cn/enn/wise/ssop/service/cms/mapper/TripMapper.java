package cn.enn.wise.ssop.service.cms.mapper;

import cn.enn.wise.ssop.api.cms.dto.response.TripListAppletDTO;
import cn.enn.wise.ssop.service.cms.model.CompanyRoute;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 行程
 * @date:2020/4/2 17:04
 * @author:hsq
 */
@Repository
@DS("encdata")
public interface TripMapper extends BaseMapper<CompanyRoute> {

    @DS("encdata")
    List<TripListAppletDTO> selectTripList(@Param("scenic") String scenic, @Param("scenicObjIds") List<Long> scenicObjIds);

    List<String> selectRouteCodeByCompanyRouteId(@Param("compnayRouteId") Long compnayRouteId);
}
