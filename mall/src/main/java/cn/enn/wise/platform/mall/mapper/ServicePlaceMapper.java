package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.ServicePlace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baijie
 * @since 2019-07-25
 */
public interface ServicePlaceMapper extends BaseMapper<ServicePlace> {

    List<ServicePlace> selectServicePlaceByIds(@Param("ids")String ids);

    /**
     * 根据projectId获取服务地点
     * @param projectId
     * @return
     */
    List<ServicePlace> getServicePlaceByProjectId(@Param("id")Long projectId);
}
