package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.GoodsProjectOperationBo;
import cn.enn.wise.platform.mall.bean.bo.SysTicketReportBo;
import cn.enn.wise.platform.mall.bean.bo.WeatherSun;
import cn.enn.wise.platform.mall.bean.param.UpdateGoodsOperationParam;
import cn.enn.wise.platform.mall.bean.vo.RealTimeOperationListVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 17:10
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Service
public interface MallAdminService {

    /**
     * 运营时段
     *
     * @param date
     * @param placeId
     * @return
     */
    ResponseEntity listOperationByDate(String date, Long placeId, String type);

    ResponseEntity listOperationByDateV2(String date, Long placeId, String type, Long projectId);

    /**
     * 添加运营状态
     *
     * @param operationBo
     * @return
     */
    ResponseEntity addGoodsProjectOperation(GoodsProjectOperationBo operationBo);

    /**
     * 更新运营状态
     *
     * @param id
     * @param remark
     * @param status
     * @param probability
     * @return
     */
    ResponseEntity updateGoodsProjectOperation(Long id, String remark, Byte status, Integer probability, Long staffId, String staffName, Long placeId);
    /**
     * 更新运营状态
     *
     * @param id
     * @param remark
     * @param status
     * @param probability
     * @return
     */
    ResponseEntity updateGoodsProjectOperationV2(Long id, String remark, Byte status, Integer probability, Long staffId, String staffName, Long placeId);

    /**
     * 实时获取运营状态
     *
     * @return
     */
    ResponseEntity listOperationRealTime();


    /***
     * 实时获取运营状态V2
     * @return
     */
    ResponseEntity<RealTimeOperationListVo> listOperationRealTimeV2(Long projectId);


    /***
     * 实时获取运营状态V2_2
     * @return
     */
    ResponseEntity<RealTimeOperationListVo> listOperationRealTimeV2_2(Long projectId,String date);

    /**
     * 添加天气预报
     *
     * @param weatherSun
     * @return
     */
    ResponseEntity addWeather(WeatherSun weatherSun);

    /**
     * 更新入园
     *
     * @param ticketReportBo
     * @return
     */
    ResponseEntity addEnterPark(SysTicketReportBo ticketReportBo);


    /***
     * 获取项目最低价格
     * @param projectId
     * @return
     */
    Map<String, Object> getMinPriceByProjectId(Long projectId);

    /**
     * 获取转化率统计
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> listTicketReport(String startDate, String endDate);

    /**
     * 获取地点列表根据项目id
     *
     * @param projectId
     * @return
     */
    List<Map<String, Object>> selectPlaceListByProject(long projectId);

    /**
     * 更新服务地点
     *
     * @param id
     * @param projectId
     * @param placeId
     * @return
     */
    Integer updateProjectPlace(Long id, Long projectId, Long placeId);


    /**
     * 批量更新项目状态
     *
     * @param updateGoodsOperationParam
     * @param user
     * @return
     */
    int updateProjectStatusBatch(UpdateGoodsOperationParam updateGoodsOperationParam, User user);

    /**
     * 更新项目
     * @param id
     * @param projectId
     * @return
     */
    Integer updateProject(Long id, Long projectId);

    /***
     * 更新运营状态
     * @param id
     * @param remark
     * @param status
     * @param degreeOfInfluence
     * @param userId
     * @param nickName
     * @param placeId
     * @return
     */
    ResponseEntity updateGoodsProjectOperationV3(Long id, String remark, Byte status, String degreeOfInfluence, Long userId, String nickName, Long placeId);

    /**
     *
     * @param wind
     * @return
     */
    ResponseEntity updateWind(Integer wind);
}
