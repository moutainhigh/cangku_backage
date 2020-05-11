package cn.enn.wise.ssop.service.cms.mapper;

import cn.enn.wise.ssop.api.cms.dto.request.oldcms.PlayMedia;
import cn.enn.wise.ssop.api.cms.dto.request.oldcms.ScenicInfo;
import cn.enn.wise.ssop.api.cms.dto.request.oldcms.ScenicSpotVo;
import cn.enn.wise.ssop.api.cms.dto.response.*;
import cn.enn.wise.ssop.service.cms.entity.oldcms.ScenicAreaEntity;
import cn.enn.wise.uncs.base.pojo.response.SelectData;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@DS("encdata")
@Repository
public interface ScenicsInfoMapper {
    int updateScenics(ScenicInfo scenicInfo);

    int updateState(ScenicInfo scenicInfo);

    void updateScenicsInfo(ScenicInfo scenicInfo);

    void insertScenicsInfo(ScenicInfo scenicInfo);

    ScenicInfo getScenicsInfoById(@Param("id") String id);

    void insertToMedia(List<PlayMedia> list);

    List<PlayMedia> getPlayMediaByScenicId(@Param("scenicId") String scenicId );

    void  deletePlayMediaByScenicId(@Param("scenicId") String scenicId );

    List<ScenicInfo> getScenicInfoList(@Param("scenicInfo") ScenicInfo scenicInfo, @Param("companyId") String companyId);

    List<ScenicSpotVo> getScenicSpotVo(@Param("companyId") String companyId);

    ScenicInfo getScenicInfo(@Param("id") String id);

    int deleteScenicInfoById(@Param("id") Integer id);

    int deletePlayMediaById(@Param("id") Integer id);

    /**
     * 根据景点id 获取景点信息
     * @param id
     * @return
     */
    ScenicInfoDTO getScenicsInfoAppletById(@Param("id") Integer id);

    /**
     * 根据一组景点id 获取景点信息
     * @param scenicIds
     * @return
     */
    List<ScenicFineDTO> getAppletFineScenicsList(@Param("scenicIds") List<Long> scenicIds);

    /**
     * 根据类型，获取资源点信息列表
     * @param type
     * @return
     */
    List<NearbyRestsAppDTO> getNearbyRestsList(@Param("type") byte type);

    /**
     * 根据租户id 获取 景区id,name 列表
     * @param companyId
     * @return
     */
    @DS("master")
    List<SelectData> selectScenicAreaSelectList(@Param("companyId") String companyId);

    /**
     * 根据资源id,名称，类型获取 资源列表
     * @param scenicIds
     * @param likePOIName
     * @param POIType
     * @return
     */
    List<POIListDTO> selectAllScenicAreaPOIList(@Param("scenicIds") List<Long> scenicIds, @Param("likePOIName") String likePOIName,@Param("POIType")Integer POIType);

    WeatherDTO getWeatherById(@Param("cityCode")String cityCode);

    List<ScenicAreaEntity> getScenicAreaSideByScneicAreaIds(@Param("scneicAreaIds")List<Long> scneicAreaIds);

    ScenicAreaEntity selectScenicAaeaById(@Param("id") Integer id);
}
