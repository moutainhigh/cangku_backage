package cn.enn.wise.platform.mall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jiaby
 */

@Mapper
public interface PlaceMapper {
   // List<Order> findAllOrderList(@Param("orderBean") OrderBean orderBean);
    List<Map<String,Object>> selectPlaceListByProject(@Param("projectId") String projectId);
}
