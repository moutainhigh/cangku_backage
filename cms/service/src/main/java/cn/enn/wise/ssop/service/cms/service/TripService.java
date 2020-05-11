package cn.enn.wise.ssop.service.cms.service;

import cn.enn.wise.ssop.api.cms.dto.request.CompanyRouteSaveParam;
import cn.enn.wise.ssop.api.cms.dto.response.ScenicSpotInfo;
import cn.enn.wise.ssop.api.cms.dto.response.TripListAppletDTO;
import cn.enn.wise.ssop.service.cms.entity.oldcms.LatAndLonEntity;
import cn.enn.wise.ssop.service.cms.entity.oldcms.ScenicAreaEntity;
import cn.enn.wise.ssop.service.cms.mapper.EnshrineMapper;
import cn.enn.wise.ssop.service.cms.mapper.TenantScenicAreaMapper;
import cn.enn.wise.ssop.service.cms.mapper.TripMapper;
import cn.enn.wise.ssop.service.cms.model.CompanyRoute;
import cn.enn.wise.ssop.service.cms.model.Enshrine;
import cn.enn.wise.ssop.service.cms.model.TenantScenicArea;
import cn.enn.wise.uncs.common.http.GeneUtil;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import cn.enn.wise.uncs.common.map.MapUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @date:2020/4/2
 * @author:sz
 */
@Service("tripService")
public class TripService{
    @Autowired
    TripMapper tripMapper;
    @Autowired
    ScenicsInfoService scenicsInfoService;
    @Autowired
    TenantScenicAreaMapper tenantScenicAreaMapper;
    @Autowired
    EnshrineMapper enshrineMapper;



    /**
     * 获取全部行程
     * @param lonLat
     * @return
     */
    @DS("encdata")
    public List<TripListAppletDTO> getAllTripList(String lonLat) {
        List<TripListAppletDTO> tripList = getTripList(lonLat);

        if(lonLat!=null&&!lonLat.isEmpty()){
            String[] location = lonLat.split(",");
            Double lon = Double.valueOf(location[0]);
            Double lat = Double.valueOf(location[1]);

            for (TripListAppletDTO tripListAppletDTO : tripList) {
                List<ScenicSpotInfo> scenicSpotInfoList = tripListAppletDTO.getScenicSpotInfoList();
                if(scenicSpotInfoList.size()>0){

                    ScenicSpotInfo scenicSpotInfo = scenicSpotInfoList.get(2);//取第一个景点坐标为 线路坐标
                    double distance = MapUtil.distance(lon, lat, scenicSpotInfo.getLongitude(), scenicSpotInfo.getLatitude());//计算当前距离
                    tripListAppletDTO.setDistance((int)distance);
                }else{
                    tripListAppletDTO.setDistance(0);
                }

            }

            //根据距离排序
            tripList.sort((o1, o2) -> o1.getDistance());
        }
        return tripList;
    }


    public List<TripListAppletDTO> getTripList(String lonLat) {
        String companyId = HttpContextUtils.GetHttpHeader("company_id");
        //获取所属景区ids
        List<Long> scenicObjIds = tenantScenicAreaMapper.selectObjs(new LambdaQueryWrapper<TenantScenicArea>()
                .select(TenantScenicArea::getScenicAreaId)
                .eq(TenantScenicArea::getCompanyId, companyId)).stream().map(o -> (Long)o).collect(Collectors.toList());

        if(!StringUtils.isEmpty(lonLat)){
            String[] location = lonLat.split(",");
            Double lon = Double.valueOf(location[0]);
            Double lat = Double.valueOf(location[1]);
            //获取是否在景区内
            ScenicAreaEntity scenicAreaEntity = scenicsInfoService.getScenicAreaIdByLocation(scenicObjIds, new LatAndLonEntity(lon, lat));
            if(scenicAreaEntity!=null&&scenicAreaEntity.getId()>0){
                //在某个景区
                scenicObjIds = Arrays.asList(scenicAreaEntity.getId());
            }
        }

        List<TripListAppletDTO> list = tripMapper.selectTripList(companyId,scenicObjIds);
        return list;
    }

    @Transactional
    @DS("encdata")
    public Boolean saveCompanyRoute(CompanyRouteSaveParam param) {
        String companyId = HttpContextUtils.GetHttpHeader("company_id");

        if(param.getCompnayRouteId()!=null){
            tripMapper.delete(new LambdaQueryWrapper<CompanyRoute>().eq(CompanyRoute::getCompanyRouteId,param.getCompnayRouteId()));
        }
        List<Object> companyRoutes = tripMapper.selectObjs(new QueryWrapper<CompanyRoute>()
                .select("max(company_route_id) company_route_id"));
        long newCompnayRouteId = (Long)companyRoutes.get(0) + 1;
        List<Long> routeIds = param.getRouteIds();
        for (Long routeId : routeIds) {
            tripMapper.insert(new CompanyRoute(Long.valueOf(companyId),newCompnayRouteId,routeId));
        }
        return true;
    }

    public List<String> getCompanyRouteInfo(Long compnayRouteId) {

        List<String> list = tripMapper.selectRouteCodeByCompanyRouteId(compnayRouteId);
        return list;
    }
}
