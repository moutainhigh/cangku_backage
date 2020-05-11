package cn.enn.wise.ssop.service.cms.service;


import cn.enn.wise.ssop.api.cms.dto.response.LocationDTO;
import cn.enn.wise.ssop.api.cms.dto.response.POIListDTO;
import cn.enn.wise.ssop.service.cms.mapper.ScenicsInfoMapper;
import cn.enn.wise.ssop.service.cms.mapper.TenantScenicAreaMapper;
import cn.enn.wise.ssop.service.cms.model.TenantScenicArea;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import cn.enn.wise.uncs.common.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



/**
 * @author shiz
 * 内容管理
 */
@Service("scenicAreaService")
@Slf4j
public class ScenicAreaService {


    @Autowired
    ScenicsInfoMapper scenicsInfoMapper;
    @Autowired
    TenantScenicAreaMapper tenantScenicAreaMapper;

    public List<SelectData> getScenicAreaSelectList(String companyId) {
        List<SelectData> list = scenicsInfoMapper.selectScenicAreaSelectList(companyId);
        return list;
    }

    public List<POIListDTO> getPOISelectList(String likePOIName,Integer POIType) {
        String companyId = HttpContextUtils.GetHttpHeader("company_id");
        //获取所属景区ids
        List<Object> scenicObjIds = tenantScenicAreaMapper.selectObjs(new LambdaQueryWrapper<TenantScenicArea>()
                .select(TenantScenicArea::getScenicAreaId)
                .eq(TenantScenicArea::getCompanyId, companyId));

        List<Long> scenicIds = scenicObjIds.stream().map(o -> (Long)o).collect(Collectors.toList());
        if(scenicIds.size()==0){
            scenicIds.add(-1L);
        }

        if(likePOIName!=null&&likePOIName.trim().isEmpty()) likePOIName=null;

        List<POIListDTO> list = scenicsInfoMapper.selectAllScenicAreaPOIList(scenicIds,likePOIName,POIType);
        return list;
    }

    public List<LocationDTO> getPOILocationBatch(Long[] poiIdList, String lonLat) {
        List<LocationDTO> locations = tenantScenicAreaMapper.selectPOILocationBatch(poiIdList);
        if(lonLat!=null&&!lonLat.isEmpty()) {
            String[] location = lonLat.split(",");
            Double lon = Double.valueOf(location[0]);
            Double lat = Double.valueOf(location[1]);
            for (LocationDTO locationDTO : locations) {
                double distance = MapUtil.distance(lon, lat, Double.valueOf(locationDTO.getLongitude()), Double.valueOf(locationDTO.getLatitude()));
                locationDTO.setDistance(distance);
            }
        }
        return locations;
    }


    public List<SelectData> getAllScenicAreaList() {
        List<SelectData> list = tenantScenicAreaMapper.selectAllScenicAreaList();
        return list;
    }
}
