package cn.enn.wise.ssop.service.cms.mapper;

import cn.enn.wise.ssop.api.cms.dto.response.LocationDTO;
import cn.enn.wise.ssop.service.cms.model.TenantScenicArea;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@DS("master")
public interface TenantScenicAreaMapper extends BaseMapper<TenantScenicArea> {

    /**
     * 批量获取经纬度
     * @param poiIdList
     * @return
     */
    @DS("encdata")
    List<LocationDTO> selectPOILocationBatch(@Param("poiIdList") Long[] poiIdList);

    /**
     * 全部 景区列表
     * @return
     */
    @DS("encdata")
    List<SelectData> selectAllScenicAreaList();
}
