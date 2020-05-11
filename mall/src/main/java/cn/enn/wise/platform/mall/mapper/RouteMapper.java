package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.Route;
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
public interface RouteMapper extends BaseMapper<Route> {

    List<Route> selectRouteByIds(@Param("ids")String ids);
}
