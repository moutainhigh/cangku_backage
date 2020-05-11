package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.GoodsProjectOperationBo;
import cn.enn.wise.platform.mall.bean.bo.GoodsProjectOperationExtendBo;
import cn.enn.wise.platform.mall.bean.bo.GoodsProjectOperationProjectBo;
import cn.enn.wise.platform.mall.bean.bo.OperationBo;
import cn.enn.wise.platform.mall.bean.vo.GoodsOperationPeriodVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 17:10
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Mapper
public interface GoodsProjectOperationMapper extends BaseMapper<GoodsProjectOperationBo> {

    /**
     * 客流监测
     *
     * @param date
     * @return
     */
    List<OperationBo> selectOperationListByDate(@Param("date") String date, @Param("placeId") Long placeId);

    List<OperationBo> selectOperationListByDateV2(@Param("date") String date, @Param("placeId") Long placeId, @Param("projectId") Long projectId);

    /**
     * 热气球实时运营状况
     *
     * @return
     */
    List<GoodsOperationPeriodVo> selectRealTimeOperationList(@Param("operationDate") Date operationDate);


    /**
     * 获取未来三天的运营概况
     *
     * @param date
     * @return
     */
    List<GoodsProjectOperationBo> selectTheNextThreeDaysOperationList(@Param("operationDate") Date date);

    /**
     * 根据时间段获取运营时段
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<GoodsProjectOperationBo> selectByTime(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("placeId") Long placeId);

    /**
     * 获取天气预报
     *
     * @return
     */
    Map<String, Object> selectWeatherList(@Param("cityCode") String cityCode);

    /**
     * 根据时段id段获取运营时段
     *
     * @param id
     * @param placeId
     * @return
     */
    List<GoodsProjectOperationBo> selectByPeriod(@Param("id") Long id, @Param("placeId") Long placeId);

    String selectPlaceNameById(@Param("placeId") Long placeId);


    void calculationReport();

    /***
     * 根据项目获取地点列表
     * @param paramDate
     * @param projectId
     * @return
     */
    List<GoodsOperationPeriodVo> selectRealTimeOperationListV2(@Param("operationDate") Date paramDate, @Param("projectId") Long projectId);

    /**
     * 根据时段id段获取运营时段
     *
     * @param id
     * @param placeId
     * @return
     */
    List<GoodsProjectOperationProjectBo> selectProjectByPeriod(@Param("id") Long id, @Param("placeId") Long placeId);

    List<GoodsProjectOperationExtendBo> selectTheNextThreeDaysOperationProjectList(@Param("operationDate") Date date);


    /**
     * 批量更新项目状态
     *
     * @param date
     * @param status
     * @param projectId
     * @param user
     */
    int updateStatusBatch(@Param("date") Date date, @Param("status") int status, @Param("projectId") int projectId,
                          @Param("remark") String remark,
                          @Param("probability") int probability,
                          @Param("servicePlaceId") int servicePlaceId,
                          @Param("user") User user,
                          @Param("degreeOfInfluence") String degreeOfInfluence);


}
