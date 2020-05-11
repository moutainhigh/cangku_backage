package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.GoodsProjectPeriod;
import cn.enn.wise.platform.mall.bean.bo.OperationPeriodBo;
import cn.enn.wise.platform.mall.bean.vo.MinPriceVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author caiyt
 * @since 2019-05-24
 */
public interface GoodsProjectPeriodMapper extends BaseMapper<GoodsProjectPeriod> {

    List<OperationPeriodBo> selectPeriodListByDate(@Param("date") String date,@Param("placeId") Long placeId);

    List<GoodsProjectPeriod> selectPeriodList(@Param("projectId") Long projectId);

    List<GoodsProjectPeriod> selectPeriodByProjectId(@Param("projectId") Long projectId);

    Map<String, Object> selectMinPriceByProjectId(@Param("projectId") Long projectId);

    /**
     * 查询最低价格集合
     * @param projectId
     * @return
     */
    List<MinPriceVo> selectMinPriceByProjectIdList(List<Long> projectId);

    /**
     * 获取项目当前可预约时间
     * @param projectId 项目Id
     * @param operationDate 运营日期
     * @param ctime 当前时间 例如 15:10
     * @return 当前最早可预约的时间
     */
    Map<String ,String> getProjectAppointment(@Param("projectId") Long projectId,
                                              @Param("operationDate") String operationDate,
                                              @Param("ctime") String ctime);
}
