package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.param.UpdateGoodsOperationParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.MallAdminService;
import cn.enn.wise.platform.mall.service.ProjectOperationStatusService;
import cn.enn.wise.platform.mall.util.AuthHandlerInterceptor;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.MessageSender;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/******************************************
 * @author: anhui
 * @createDate: 2019/5/22 17:11
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Service
public class MallAdminServiceImpl implements MallAdminService {


    private static final Logger logger = LoggerFactory.getLogger(AuthHandlerInterceptor.class);

    @Autowired
    GoodsProjectMapper goodsProjectMapper;

    @Autowired
    GoodsProjectOperationMapper goodsProjectOperationMapper;

    @Autowired
    PlaceMapper placeMapper;

    @Autowired
    GoodsProjectPeriodMapper goodsProjectPeriodMapper;

    @Autowired
    GoodsProjectOperationActionMapper goodsProjectOperationActionMapper;

    @Autowired
    SysTicketReportMapper sysTicketReportMapper;

    @Autowired
    MessageSender messageSender;

    @Autowired
    WeatherSunMapper weatherSunMapper;

    private String OPERATION_MASSAGE = "%s时段的热气球飞行状态有变化，%s已%s";

    private String OPERATION_MASSAGE_PROJECT = "%s时段的%s状态有变化，%s已%s";

    @Value("${companyId}")
    private String companyId;

    @Value("${queueconfig.prefix}")
    public String prefix;

    @Autowired
    private ServicePlaceMapper servicePlaceMapper;

    @Autowired
    private GoodsProjectStaffPlaceMapper goodsProjectStaffPlaceMapper;

    @Autowired
    private GoodsProjectStaffMapper goodsProjectStaffMapper;

    @Autowired
    private ProjectOperationStatusService projectOperationStatusService;

    @Autowired
    private ProjectOperationStatusMapper projectOperationStatusMapper;

    @Override
    public ResponseEntity listOperationByDate(String date, Long placeId, String type) {
        OperationResultVo resultVo = new OperationResultVo();
        //运营时段
        List<OperationBo> operationBoList = goodsProjectOperationMapper.selectOperationListByDate(date, placeId);

        //#region 天气预报时段,7点开始,21点结束
        List<OperationPeriodBo> operationPeriodBoList = getPeriodBos(operationBoList);
        //获取可维护的时间段
        List<OperationPeriodBo> targetPeriodBoList = getOperationPeriodBos(operationPeriodBoList);
        resultVo.setOperationPeriodBoList(targetPeriodBoList);
        //#endregion


        //#region 入园人数
        Map<String, Object> params = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date reportDate = null;
        try {
            reportDate = new java.sql.Date(sdf.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        params.put("report_date", reportDate);
        List<SysTicketReportBo> reportBoList = sysTicketReportMapper.selectByMap(params);
        resultVo.setEnterCount(0);
        resultVo.setPredictionCount(0);
        if (reportBoList != null && reportBoList.size() > 0) {
            resultVo.setEnterCount(reportBoList.get(0).getCheckInCount());
            resultVo.setPredictionCount(reportBoList.get(0).getSellOutCount());
        }
        //#endregion

        //#region 筛选上下午 type: 1 上午 2 下午 0 全天
        List<OperationBo> targetList = new ArrayList<>();

        filterByType(type, operationBoList, targetList);
        resultVo.setOperationBoList(targetList);
        // #endregion

        return new ResponseEntity(1, "娱乐项目运营管理", resultVo);
    }

    /**
     * 根据上下午 过滤数据  type: 1 上午 2 下午 0 全天
     *
     * @param type
     * @param operationBoList
     * @param targetList
     */
    private void filterByType(String type, List<OperationBo> operationBoList, List<OperationBo> targetList) {
        for (OperationBo item : operationBoList) {
            String startTime = item.getTitle().split("-")[0];
            SimpleDateFormat f = new SimpleDateFormat("HH:mm");
            // 开始时间
            Long sTime = 0L;
            try {
                sTime = f.parse(startTime).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // 中午时间
            Long sCompareTime = 0L;
            try {
                sCompareTime = f.parse("12:00").getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if ("0".equals(type)) {
                targetList.add(item);
            } else if ("1".equals(type)) {
                if (sTime < sCompareTime) {
                    targetList.add(item);
                }
            } else if ("2".equals(type)) {
                if (sTime >= sCompareTime) {
                    targetList.add(item);
                }
            }
        }
    }

    /**
     * 转化成时段信息
     *
     * @param operationBoList
     * @return
     */
    private List<OperationPeriodBo> getPeriodBos(List<OperationBo> operationBoList) {
        List<OperationPeriodBo> operationPeriodBoList = new ArrayList<>();
        for (OperationBo item : operationBoList) {
            OperationPeriodBo operationPeriodBo = new OperationPeriodBo();
            operationPeriodBo.setProbability(item.getProbability());
            operationPeriodBo.setStatus(item.getStatus().toString());
            operationPeriodBo.setTitle(item.getTitle());
            operationPeriodBo.setId(item.getPeriodId());
            operationPeriodBo.setDegreeOfInfluence(item.getDegreeOfInfluence());
            operationPeriodBoList.add(operationPeriodBo);
        }
        return operationPeriodBoList;
    }


    /**
     * 获取运营时段列表
     *
     * @param operationPeriodBoList
     * @return
     */
    private List<OperationPeriodBo> getOperationPeriodBos(List<OperationPeriodBo> operationPeriodBoList) {
        List<OperationPeriodBo> targetPeriodBoList = new ArrayList<>();
        Integer i = 1;
        Long hour = new Date().getTime();
        for (OperationPeriodBo operationPeriodBo : operationPeriodBoList) {
            Long currentHour = getCurrentHour(operationPeriodBo);
            if (currentHour > hour && i <= 3) {
                targetPeriodBoList.add(operationPeriodBo);
                i = i + 1;
            }
        }
        return targetPeriodBoList;
    }

    /***
     * 获取项目时段结束时间
     * @param operationPeriodBo
     * @return
     */
    private Long getCurrentHour(OperationPeriodBo operationPeriodBo) {
        // egg: 08:00-09:00
        String title = operationPeriodBo.getTitle();
        // 结束时间
        Long currentHour = 0L;
        try {

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String endTime = df.format(new Date()) + " " + title.split("-")[1];
            logger.info("endTime:" + endTime);
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            currentHour = df1.parse(endTime).getTime();

        } catch (Exception ex) {
            logger.info("时间类型错误！请价差时段配置：HH:mm-HH:mm");
        }
        return currentHour;
    }

    @Override
    public ResponseEntity listOperationByDateV2(String date, Long placeId, String type, Long projectId) {
        OperationResultVo resultVo = new OperationResultVo();
        //运营时段
        List<OperationBo> operationBoList = goodsProjectOperationMapper.selectOperationListByDateV2(date, placeId, projectId);

        //#region 天气预报时段,7点开始,21点结束
        List<OperationPeriodBo> operationPeriodBoList = getPeriodBos(operationBoList);

        // 当前时间
        List<OperationPeriodBo> targetPeriodBoList = getOperationPeriodBos(operationPeriodBoList);

        resultVo.setOperationPeriodBoList(targetPeriodBoList);
        //#endregion

        //#region 入园人数
        Map<String, Object> params = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date reportDate = null;
        try {
            reportDate = new java.sql.Date(sdf.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        params.put("report_date", reportDate);
        List<SysTicketReportBo> reportBoList = sysTicketReportMapper.selectByMap(params);
        resultVo.setEnterCount(0);
        resultVo.setPredictionCount(0);
        if (reportBoList != null && reportBoList.size() > 0) {
            resultVo.setEnterCount(reportBoList.get(0).getCheckInCount());
            resultVo.setPredictionCount(reportBoList.get(0).getSellOutCount());
        }
        //#endregion

        //#region 筛选上下午 type: 1 上午 2 下午 0 全天
        List<OperationBo> targetList = new ArrayList<>();
        filterByType(type, operationBoList, targetList);
        if(targetList==null||targetList.size()<=0){
            List<GoodsProjectPeriod>  goodsProjectPeriodList = goodsProjectPeriodMapper.selectPeriodByProjectId(projectId);
            if(goodsProjectPeriodList!=null&&goodsProjectPeriodList.size()>0){
                resultVo.setIsPeriod(1);
            }else{
                resultVo.setIsPeriod(2);
            }
        }else{
            resultVo.setIsPeriod(1);
        }
        resultVo.setOperationBoList(targetList);
        // #endregion

        //组装项目运营因素
        ProjectOperationStatusResponseAppVO voByProjectId = projectOperationStatusService.getProjectOperationStatusResponseAppVOByProjectId(projectId, 1);

        resultVo.setOperationStatus(voByProjectId);

        return new ResponseEntity(1, "娱乐项目运营管理", resultVo);
    }


    @Override
    public ResponseEntity addGoodsProjectOperation(GoodsProjectOperationBo operationBo) {
        Integer result = goodsProjectOperationMapper.insert(operationBo);
        if (result > 0) {
            return new ResponseEntity(1, "添加成功", result);
        } else {
            return new ResponseEntity(2, "添加失败", result);
        }
    }

    @Override
    public ResponseEntity updateGoodsProjectOperation(Long id, String remark, Byte status, Integer probability, Long staffId, String staffName, Long placeId) {

        //根据时段id设置可飞
        List<GoodsProjectOperationBo> operationBoList = goodsProjectOperationMapper.selectByPeriod(id, placeId);


        Boolean sendFail = false;
        for (GoodsProjectOperationBo operationBo : operationBoList) {
            operationBo.setRemark(remark);
            Byte fromStatus = new Byte(operationBo.getStatus());
            Short fromProbability = operationBo.getProbability().shortValue();
            operationBo.setStatus(status);
            operationBo.setProbability(probability);
            Integer result = goodsProjectOperationMapper.updateById(operationBo);
            if (result > 0) {
                Long userId = staffId;
                addAction(remark, probability, staffName, operationBo, fromStatus, fromProbability, userId,"");

                String placeName = "吞白";
                if (placeId == 2L) {
                    placeName = "酒店";
                }
                String statusTxt = "可飞";
                if (operationBo.getStatus() == 2) {
                    statusTxt = "停飞";
                }
                String message = String.format(OPERATION_MASSAGE, operationBo.getPeriodTitle(), placeName, statusTxt);

                logger.info("发送消息：" + message);
                messageSender.send(message);

            } else {
                sendFail = true;
            }
        }
        if (sendFail)
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "更新失败", null);
        return new ResponseEntity(GeneConstant.INT_1, "更新成功", null);
    }

    @Override
    public ResponseEntity updateGoodsProjectOperationV2(Long id, String remark, Byte status, Integer probability, Long staffId, String staffName, Long placeId) {

        //根据时段id设置可飞
        List<GoodsProjectOperationProjectBo> operationBoList = goodsProjectOperationMapper.selectProjectByPeriod(id, placeId);
        if(operationBoList.size()==0){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "更新失败", null);
        }

        // 是否有管理权限
        Map<String, Object> params = new HashMap<>();
        params.put("staff_id", staffId);
        params.put("project_id",operationBoList.get(0).getProjectId());
        List<GoodsProjectStaffPlace> placeList = goodsProjectStaffPlaceMapper.selectByMap(params);
        if(placeList.size()==0){
            return new ResponseEntity(2, "您不可以管理此项目", null);
        }

        String placeName = goodsProjectOperationMapper.selectPlaceNameById(placeId);

        Boolean sendFail = false;
        for (GoodsProjectOperationProjectBo operationBo : operationBoList) {
            operationBo.setRemark(remark);
            Byte fromStatus = new Byte(operationBo.getStatus());
            Short fromProbability = operationBo.getProbability().shortValue();
            operationBo.setStatus(status);
            operationBo.setProbability(probability);
            Integer result = goodsProjectOperationMapper.updateById(operationBo);
            if (result > 0) {
                Long userId = staffId;
                addAction(remark, probability, staffName, operationBo, fromStatus, fromProbability, userId,"");
                String statusTxt = "正常";
                if (operationBo.getStatus() == 2) {
                    statusTxt = "停运";
                }
                String message = String.format(OPERATION_MASSAGE_PROJECT, operationBo.getPeriodTitle(), operationBo.getProjectName(), placeName, statusTxt);
                logger.info("发送消息：" + message);
                messageSender.send(message, operationBo.getProjectId());
            } else {
                sendFail = true;
            }
        }
        if (sendFail)
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "更新失败", null);
        return new ResponseEntity(GeneConstant.INT_1, "更新成功", null);
    }

    @Override
    public ResponseEntity updateGoodsProjectOperationV3(Long id, String remark, Byte status, String degreeOfInfluence, Long staffId, String staffName, Long placeId) {

        //根据时段id设置可飞
        List<GoodsProjectOperationProjectBo> operationBoList = goodsProjectOperationMapper.selectProjectByPeriod(id, placeId);

        String placeName = goodsProjectOperationMapper.selectPlaceNameById(placeId);

        Boolean sendFail = false;
        for (GoodsProjectOperationProjectBo operationBo : operationBoList) {
            operationBo.setRemark(remark);
            Byte fromStatus = new Byte(operationBo.getStatus());
            Short fromProbability = operationBo.getProbability().shortValue();
            operationBo.setStatus(status);
            operationBo.setDegreeOfInfluence(degreeOfInfluence);
            Integer result = goodsProjectOperationMapper.updateById(operationBo);
            if (result > 0) {
                Long userId = staffId;
                addAction(remark, fromProbability.intValue(), staffName, operationBo, fromStatus, fromProbability, userId,degreeOfInfluence);

                Map<String,Object> params = new HashMap<>();
                params.put("project_id",operationBo.getProjectId());
                params.put("operation_status",status);
                List<ProjectOperationStatus> projectOperationStatusList =projectOperationStatusMapper.selectByMap(params);

                if(projectOperationStatusList.size()==0){
                    continue;
                }
                String statusTxt =projectOperationStatusList.get(0).getDescValue();
                String message = String.format(OPERATION_MASSAGE_PROJECT, operationBo.getPeriodTitle(), operationBo.getProjectName(), placeName, statusTxt);
                logger.info("发送消息：" + message);
                messageSender.send(message, operationBo.getProjectId());
            } else {
                sendFail = true;
            }
        }
        if (sendFail)
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "更新失败", null);
        return new ResponseEntity(GeneConstant.INT_1, "更新成功", null);
    }

    @Override
    public ResponseEntity updateWind(Integer wind) {

        QueryWrapper<WeatherSun> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("id");
        wrapper.last("limit 1");

        WeatherSun weatherSun = weatherSunMapper.selectOne(wrapper);
        weatherSun.setWindp(wind);
        int result = weatherSunMapper.updateById(weatherSun);
        if(result==0){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"失败");
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功");
    }

    /**
     * 添加运营操作记录：根据天气预报记录飞行概率
     *
     * @param remark
     * @param probability
     * @param staffName
     * @param operationBo
     * @param fromStatus
     * @param fromProbability
     * @param userId
     */
    private void addAction(String remark, Integer probability, String staffName, GoodsProjectOperationBo operationBo, Byte fromStatus, Short fromProbability, Long userId,String degree) {
        GoodsProjectOperationActionBo actionBo = new GoodsProjectOperationActionBo();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        actionBo.setCreateTime(timestamp);
        actionBo.setCreateUserId(userId);
        actionBo.setCreateUserName(staffName);
        actionBo.setFromProbability(fromProbability);
        actionBo.setFromStatus(fromStatus);
        actionBo.setOperationBy(userId);
        actionBo.setOperationTime(timestamp);
        actionBo.setOperationId(operationBo.getId());
        actionBo.setRemark(remark);
        actionBo.setToProbability(probability.shortValue());
        actionBo.setToStatus(operationBo.getStatus());
        actionBo.setCreateUserName(staffName);
        actionBo.setCreateTime(timestamp);
        actionBo.setCreateUserId(userId);
        actionBo.setUpdateTime(timestamp);
        actionBo.setUpdateUserId(userId);
        actionBo.setUpdateUserName(staffName);
        actionBo.setDegreeOfInfluence(degree);

        goodsProjectOperationActionMapper.insert(actionBo);
    }

    @Override
    public ResponseEntity<RealTimeOperationListVo> listOperationRealTime() {

        java.sql.Date paramDate = new java.sql.Date(new Date().getTime());

        List<GoodsOperationPeriodVo> list = goodsProjectOperationMapper.selectRealTimeOperationList(paramDate);
        List<GoodsOperationPeriodVo> tunBaiList = new ArrayList<>();
        List<GoodsOperationPeriodVo> suoSongList = new ArrayList<>();

        //当天没有数据 用明天的数据
        if (list == null || list.size() == 0) {
            paramDate = getDate();
            list = goodsProjectOperationMapper.selectRealTimeOperationList(paramDate);
        }

        for (GoodsOperationPeriodVo item : list) {
            if (item.getServicePlaceId().equals(1L)) {
                tunBaiList.add(item);
            } else if (item.getServicePlaceId().equals(2L)) {
                suoSongList.add(item);
            }
        }
        RealTimeOperationListVo realTimeOperationListVo = new RealTimeOperationListVo();
        realTimeOperationListVo.setSuoSong(suoSongList);
        realTimeOperationListVo.setTunBai(tunBaiList);

        List<RealTimeOperationListItem> itemList = new ArrayList<>();
        RealTimeOperationListItem item = new RealTimeOperationListItem();
        item.setList(tunBaiList);
        item.setName("吞白");
        item.setPlaceId(1L);
        item.setOperationDate(paramDate);
        itemList.add(item);
        item = new RealTimeOperationListItem();
        item.setList(suoSongList);
        item.setName("酒店");
        item.setPlaceId(2L);
        item.setOperationDate(paramDate);
        itemList.add(item);

        realTimeOperationListVo.setDataList(itemList);
        return new ResponseEntity(realTimeOperationListVo);
    }

    @Override
    public ResponseEntity<RealTimeOperationListVo> listOperationRealTimeV2(Long projectId) {

        java.sql.Date paramDate = new java.sql.Date(new Date().getTime());

        List<GoodsOperationPeriodVo> list = goodsProjectOperationMapper.selectRealTimeOperationListV2(paramDate, projectId);
        //当天没有数据 用明天的数据
        if (list == null || list.size() == 0) {
            paramDate = getDate();
            list = goodsProjectOperationMapper.selectRealTimeOperationListV2(paramDate, projectId);
        }

        if (list == null || list.size() == 0) {


            List<Map<String,Object>> placeList=goodsProjectMapper.getProjectPlaceListByProjectId(projectId);
            List<RealTimeOperationListItem> itemList = new ArrayList<>();

            for(Map<String,Object> place:placeList){
                String[] placeIds =place.get("placeId").toString().split(",");
                String[] placeNames=place.get("placeName").toString().split(",");
                for(Integer i=0;i<placeIds.length;i++){
                    RealTimeOperationListItem item = new RealTimeOperationListItem();
                    item.setList(new ArrayList<>());
                    item.setName(placeNames[i]);
                    item.setPlaceId(Long.valueOf(placeIds[i]));
                    item.setOperationDate(paramDate);
                    itemList.add(item);
                }
            }

            RealTimeOperationListVo realTimeOperationListVo = new RealTimeOperationListVo();
            realTimeOperationListVo.setDataList(itemList);

           return new ResponseEntity(1, "没有查询到数据", realTimeOperationListVo);
        }

        //匹配项目运营因素
        ProjectOperationStatusResponseAppVO operationStatusResponseAppVO = projectOperationStatusService.getProjectOperationStatusResponseAppVOByProjectId(projectId, 1);

        List<GoodsOperationPeriodVo> operationPeriodVoList = new ArrayList<>();
        List<RealTimeOperationListItem> itemList = new ArrayList<>();
        Long placeId = list.get(0).getPlaceId();
        String placeName = list.get(0).getPlaceName();
        for (GoodsOperationPeriodVo goodsOperationPeriodVo : list) {
            if (placeId != goodsOperationPeriodVo.getPlaceId()) {
                RealTimeOperationListItem item = new RealTimeOperationListItem();
                item.setList(operationPeriodVoList);
                item.setName(placeName);
                item.setPlaceId(placeId);
                item.setOperationDate(paramDate);
                itemList.add(item);

                operationPeriodVoList = new ArrayList<>();
                placeId = goodsOperationPeriodVo.getPlaceId();
                placeName = goodsOperationPeriodVo.getPlaceName();

            }
            //组装原因数据
            List<ProjectOperationStatusDescVo> descVos = operationStatusResponseAppVO.getDescVos();

            if(CollectionUtils.isNotEmpty(descVos)){
                for (ProjectOperationStatusDescVo descVo : descVos) {

                    Integer status = goodsOperationPeriodVo.getStatus().intValue();
                    Integer operationStatus = descVo.getOperationStatus();
                    if(status.equals(operationStatus)){
                        goodsOperationPeriodVo.setStatusValue(descVo.getDescValue());
                    }
                }
            }

            operationPeriodVoList.add(goodsOperationPeriodVo);
        }
        RealTimeOperationListItem item = new RealTimeOperationListItem();
        item.setList(operationPeriodVoList);
        item.setName(placeName);
        item.setPlaceId(placeId);
        item.setOperationDate(paramDate);
        itemList.add(item);

        RealTimeOperationListVo realTimeOperationListVo = new RealTimeOperationListVo();
        realTimeOperationListVo.setDataList(itemList);
        //设置运营因素模板参数
        realTimeOperationListVo.setOperationStatusType(operationStatusResponseAppVO.getOperationStatusType());
        realTimeOperationListVo.setLabel(operationStatusResponseAppVO.getLabel());

        return new ResponseEntity(realTimeOperationListVo);
    }



    @Override
    public ResponseEntity<RealTimeOperationListVo> listOperationRealTimeV2_2(Long projectId,String date) {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        try {
            date1 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        java.sql.Date paramDate = new java.sql.Date(date1.getTime());

        List<GoodsOperationPeriodVo> list = goodsProjectOperationMapper.selectRealTimeOperationListV2(paramDate, projectId);
        //当天没有数据 用明天的数据
        if (list == null || list.size() == 0) {
            paramDate = getDate();
            list = goodsProjectOperationMapper.selectRealTimeOperationListV2(paramDate, projectId);
        }

        if (list == null || list.size() == 0) {


            List<Map<String,Object>> placeList=goodsProjectMapper.getProjectPlaceListByProjectId(projectId);
            List<RealTimeOperationListItem> itemList = new ArrayList<>();

            for(Map<String,Object> place:placeList){
                String[] placeIds =place.get("placeId").toString().split(",");
                String[] placeNames=place.get("placeName").toString().split(",");
                for(Integer i=0;i<placeIds.length;i++){
                    RealTimeOperationListItem item = new RealTimeOperationListItem();
                    item.setList(new ArrayList<>());
                    item.setName(placeNames[i]);
                    item.setPlaceId(Long.valueOf(placeIds[i]));
                    item.setOperationDate(paramDate);
                    itemList.add(item);
                }
            }

            RealTimeOperationListVo realTimeOperationListVo = new RealTimeOperationListVo();
            realTimeOperationListVo.setDataList(itemList);

            return new ResponseEntity(1, "没有查询到数据", realTimeOperationListVo);
        }

        //匹配项目运营因素
        ProjectOperationStatusResponseAppVO operationStatusResponseAppVO = projectOperationStatusService.getProjectOperationStatusResponseAppVOByProjectId(projectId, 1);

        List<GoodsOperationPeriodVo> operationPeriodVoList = new ArrayList<>();
        List<RealTimeOperationListItem> itemList = new ArrayList<>();
        Long placeId = list.get(0).getPlaceId();
        String placeName = list.get(0).getPlaceName();
        for (GoodsOperationPeriodVo goodsOperationPeriodVo : list) {
            if (placeId != goodsOperationPeriodVo.getPlaceId()) {
                RealTimeOperationListItem item = new RealTimeOperationListItem();
                item.setList(operationPeriodVoList);
                item.setName(placeName);
                item.setPlaceId(placeId);
                item.setOperationDate(paramDate);
                itemList.add(item);

                operationPeriodVoList = new ArrayList<>();
                placeId = goodsOperationPeriodVo.getPlaceId();
                placeName = goodsOperationPeriodVo.getPlaceName();

            }
            //组装原因数据
            List<ProjectOperationStatusDescVo> descVos = operationStatusResponseAppVO.getDescVos();

            if(CollectionUtils.isNotEmpty(descVos)){
                for (ProjectOperationStatusDescVo descVo : descVos) {

                    Integer status = goodsOperationPeriodVo.getStatus().intValue();
                    Integer operationStatus = descVo.getOperationStatus();
                    if(status.equals(operationStatus)){
                        goodsOperationPeriodVo.setStatusValue(descVo.getDescValue());
                    }
                }
            }

            operationPeriodVoList.add(goodsOperationPeriodVo);
        }
        RealTimeOperationListItem item = new RealTimeOperationListItem();
        item.setList(operationPeriodVoList);
        item.setName(placeName);
        item.setPlaceId(placeId);
        item.setOperationDate(paramDate);
        itemList.add(item);

        RealTimeOperationListVo realTimeOperationListVo = new RealTimeOperationListVo();
        realTimeOperationListVo.setDataList(itemList);
        //设置运营因素模板参数
        realTimeOperationListVo.setOperationStatusType(operationStatusResponseAppVO.getOperationStatusType());
        realTimeOperationListVo.setLabel(operationStatusResponseAppVO.getLabel());

        return new ResponseEntity(realTimeOperationListVo);
    }
    private java.sql.Date getDate() {
        java.sql.Date paramDate;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        paramDate = new java.sql.Date(calendar.getTime().getTime());
        return paramDate;
    }

    @Override
    public ResponseEntity addWeather(WeatherSun weatherSun) {
        return new ResponseEntity(weatherSunMapper.insert(weatherSun));
    }

    @Override
    public ResponseEntity addEnterPark(SysTicketReportBo ticketReportBo) {
        Map<String, Object> params = new HashMap<>();
        params.put("report_date", ticketReportBo.getReportDate());
        List<SysTicketReportBo> targetList = sysTicketReportMapper.selectByMap(params);
        Integer result = 0;
        if (targetList.size() > 0) {
            targetList.get(0).setCheckInCount(ticketReportBo.getCheckInCount());
            targetList.get(0).setReportDate(ticketReportBo.getReportDate());
            targetList.get(0).setSellOutCount(ticketReportBo.getSellOutCount());

            if (ticketReportBo.getScansCount() > 0) {
                targetList.get(0).setScansCount(ticketReportBo.getScansCount());
            }
            if (ticketReportBo.getScansPercent() > 0) {
                targetList.get(0).setScansPercent(ticketReportBo.getScansPercent());
            }

            sysTicketReportMapper.updateById(targetList.get(0));
        } else {
            result = sysTicketReportMapper.insert(ticketReportBo);
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE, "添加成功", result);
    }

    @Override
    public Map<String, Object> getMinPriceByProjectId(Long projectId) {
        return goodsProjectPeriodMapper.selectMinPriceByProjectId(projectId);
    }

    @Override
    public List<Map<String, Object>> listTicketReport(String startDate, String endDate) {
        List<Map<String, Object>> mapList = sysTicketReportMapper.listTicketReport(startDate, endDate);
        List<Map<String, Object>> totalMapList = sysTicketReportMapper.listTicketReportTotal(startDate, endDate);

        mapList.add(totalMapList.get(0));


        return mapList;
    }


    /**
     * 初始化运营数据:预处理三天内的数据
     */
    //@Scheduled(cron = "0 */1 *  * * * ")
    public void generateProjectOperations() {
        // 地点信息 ，生成运营时段信息
        List<Long> placeList = new ArrayList<>();
        placeList.add(1L);
        placeList.add(2L);

        List<Date> dateList = new ArrayList<>();
        dateList.add(new Date());
        // 预置三天的运营数据
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        dateList.add(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        dateList.add(calendar.getTime());

        //时段信息
        List<GoodsProjectPeriod> periodList = goodsProjectPeriodMapper.selectPeriodList(1L);

        for (Long place : placeList) {
            for (GoodsProjectPeriod projectPeriod : periodList) {
                for (Date createTime : dateList) {

                    // 运营日期、时段、地点 构成一个运营单元
                    java.sql.Date operationDate = new java.sql.Date(createTime.getTime());
                    Map<String, Object> params = new HashMap<>();
                    params.put("operation_date", operationDate);
                    params.put("period_id", projectPeriod.getId());
                    params.put("service_place_id", place);
                    List<GoodsProjectOperationBo> resultOperationList = goodsProjectOperationMapper.selectByMap(params);

                    if (resultOperationList.size() > 0) {

                        for (GoodsProjectOperationBo item : resultOperationList) {
                            item.setPeriodTitle(projectPeriod.getTitle());
                            goodsProjectOperationMapper.updateById(item);
                        }
                        continue;
                    }


                    //生成分时段运营信息
                    GoodsProjectOperationBo operationBo = new GoodsProjectOperationBo();
                    Timestamp createTimestamp = new Timestamp(createTime.getTime());
                    operationBo.setCreateTime(createTimestamp);
                    operationBo.setCreateUserId(-1L);
                    operationBo.setCreateUserName("sys");
                    operationBo.setOperationDate(operationDate);
                    operationBo.setPeriodId(projectPeriod.getId());
                    operationBo.setPeriodTitle(projectPeriod.getTitle());
                    // todo 1、计算可飞概率
                    operationBo.setProbability(50);
                    operationBo.setRemark("初始化");
                    operationBo.setServicePlaceId(place);
                    Byte status = 0;
                    operationBo.setStatus(status);

                    addGoodsProjectOperation(operationBo);


                }
            }
        }
    }


    /**
     * 初始化运营数据:预处理三天内的数据
     */
    @Scheduled(cron = "0 */1 *  * * * ")
    public void generateProjectOperationsV1() {

        //根据companyId获取服务地点
        List<ServicePlace> servicePlaces = servicePlaceMapper.selectByMap(new HashMap<String, Object>() {{
            put("company_id", companyId);
            put("status", 1);
        }});

        List<Date> dateList = new ArrayList<>();

        dateList.add(new Date());
        // 预置三天的运营数据
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        dateList.add(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        dateList.add(calendar.getTime());

        List<GoodsProject> goodsProjects = goodsProjectMapper.listProjectByCompanyId(Long.valueOf(companyId), 1L);

        if (goodsProjects != null) {
            for (GoodsProject goodsProject : goodsProjects) {

                //时段信息
                List<GoodsProjectPeriod> periodList = goodsProjectPeriodMapper.selectPeriodList(goodsProject.getId());
                //查询所有的服务地点
                String servicePlaceId = goodsProject.getServicePlaceId();
                String[] split = servicePlaceId.split(",");

                for (String place : split) {

                    for (GoodsProjectPeriod projectPeriod : periodList) {
                        for (Date createTime : dateList) {

                            // 运营日期、时段、地点 构成一个运营单元
                            java.sql.Date operationDate = new java.sql.Date(createTime.getTime());
                            Map<String, Object> params = new HashMap<>();
                            params.put("operation_date", operationDate);
                            params.put("period_id", projectPeriod.getId());
                            params.put("service_place_id", place);
                            List<GoodsProjectOperationBo> resultOperationList = goodsProjectOperationMapper.selectByMap(params);

                            if (resultOperationList.size() > 0) {

                                for (GoodsProjectOperationBo item : resultOperationList) {
                                    item.setPeriodTitle(projectPeriod.getTitle());
                                    goodsProjectOperationMapper.updateById(item);
                                }
                                continue;
                            }


                            //生成分时段运营信息
                            GoodsProjectOperationBo operationBo = new GoodsProjectOperationBo();
                            Timestamp createTimestamp = new Timestamp(createTime.getTime());
                            operationBo.setCreateTime(createTimestamp);
                            operationBo.setCreateUserId(-1L);
                            operationBo.setCreateUserName("sys");
                            operationBo.setOperationDate(operationDate);
                            operationBo.setPeriodId(projectPeriod.getId());
                            operationBo.setPeriodTitle(projectPeriod.getTitle());
                            // todo 1、计算可飞概率
                            operationBo.setProbability(50);
                            operationBo.setRemark("初始化");
                            operationBo.setServicePlaceId(Long.valueOf(place));
                            Byte status = 0;
                            operationBo.setStatus(status);

                            addGoodsProjectOperation(operationBo);


                        }
                    }

                }

            }

        }

    }

    /**
     * 更新天气预报
     */
    @Scheduled(cron = "0 */20 *  * * * ")
    public void updateWeather() throws ParseException {


        String cityCody = "101140403005";
        if (prefix.toLowerCase().equals("wqz")) {
            cityCody = "101301303";
        }
        Map<String, Object> weatherMap = goodsProjectOperationMapper.selectWeatherList(cityCody);
        if (weatherMap == null || weatherMap.get("forecast") == null) {
            return;
        }

        java.sql.Date date = new java.sql.Date(new Date().getTime());
        List<GoodsProjectOperationExtendBo> operationBoList = goodsProjectOperationMapper.selectTheNextThreeDaysOperationProjectList(date);
        String weatherStr = weatherMap.get("forecast").toString();
        String[] weathers = weatherStr.split(";");

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 1 遍历运营时段
        for (GoodsProjectOperationExtendBo goodsProjectOperationBo : operationBoList) {
            // 人为设置过时段，忽略
            if (goodsProjectOperationBo.getStatus() != 0) {
                continue;
            }
            //时间格式 format :2019-06-27 14:00:00
            //时段格式 PeriodTitle: 08:00-09:00
            String operationTime = goodsProjectOperationBo.getOperationDate() + " " + goodsProjectOperationBo.getPeriodTitle().substring(0, 2) + ":00:00";
            String operationEndTime = goodsProjectOperationBo.getOperationDate() + " " + goodsProjectOperationBo.getPeriodTitle().split("-")[1].substring(0, 2) + ":00:00";
            Date startTime = f.parse(operationTime);
            Date endTime = f.parse(operationEndTime);
            // 查询时间跨度,以1小时为单位,天气预报1小时更新一次
            Integer hours = (int) ((endTime.getTime() - startTime.getTime()) / (1000 * 60 * 60));
            List<String> operationTimes = new ArrayList<>();
            for (int i = 0; i < hours; i++) {
                operationTimes.add(addDateMinut(operationTime, i));
            }

            // 2 编辑天气->找到当前时段天气,计算运营时段跨度内,概率的平均值
            List<Integer> probabilityList = new ArrayList<>();
            for (String weather : weathers) {
                String[] dimensions = weather.split(",");
                Long target_time = f.parse(dimensions[0]).getTime();
                String base = dimensions[1];
                Integer windLevel = Integer.parseInt(dimensions[dimensions.length - 1]);

                for (String item : operationTimes) {
                    if (dimensions[0].equals(item)) {
                        Integer probability = 0;
                        // TODO 以后需要根据
                        if (prefix.toLowerCase().equals("wqz")) {
                            // region projectId 1 帆船 2游艇
                            if (goodsProjectOperationBo.getProjectId() == 1L) {
                                probability = calcWzdSailboat(base, windLevel, target_time);
                            } else if (goodsProjectOperationBo.getProjectId() == 2L) {
                                probability = calcWzdYacht(base, windLevel, target_time);
                            }
                            //#endregion
                        } else {
                            probability = calc(base, windLevel, target_time);
                        }
                        probabilityList.add(probability);
                    }
                }
            }

            if (probabilityList.size() > 0) {
                Integer probability = 0;
                for (Integer pro : probabilityList) {
                    probability = probability + pro;
                }
                probability = probability / probabilityList.size();
                goodsProjectOperationBo.setProbability(probability);
                goodsProjectOperationMapper.updateById(goodsProjectOperationBo);
            }
        }
    }

    /**
     * 计算转化率
     */
    //TODO 上线后改成每晚执行
    //@Scheduled(cron = "0 */1 *  * * * ")
    @Scheduled(cron = "0 0 23 * * ? ")
    public void calculationReport() {
        goodsProjectOperationMapper.calculationReport();
    }


    /**
     * 增加小时
     *
     * @param day
     * @param hour
     * @return
     */
    private String addDateMinut(String day, int hour) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        System.out.println("front:" + format.format(date)); //显示输入的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        date = cal.getTime();
        System.out.println("after:" + format.format(date));  //显示更新后的日期
        cal = null;
        return format.format(date);

    }

    /**
     * 计算热气球的运行概率的
     *
     * @param {*} weather
     */
    public Integer calc(String base, Integer wind_level, Long target_time) throws ParseException {
        Double probability = 100.0;
        //阴天、雨天、雪天、大雾天等不具备
        if (!base.equals("睛天") || !base.equals("多云") || !base.equals("阴")) {
            probability = 0.30 * probability;
            //晚间不飞；
        } else if (target_time < getTime(target_time, "07:30") || target_time > getTime(target_time, "20:30")) {
            probability = 0.30 * probability;
            //晴|多云、白天、
        } else if (wind_level <= 1 & (target_time < getTime(target_time, "11:00") || target_time > getTime(target_time, "16:30"))) {
            probability = 0.95 * probability;
        } else if (wind_level <= 1 & (target_time > getTime(target_time, "11:00") && target_time < getTime(target_time, "16:30"))) {
            probability = 0.80 * probability;
        } else if (wind_level <= 2 & (target_time < getTime(target_time, "11:00") || target_time > getTime(target_time, "16:30"))) {
            probability = 0.85 * probability;
        } else if (wind_level <= 2 & (target_time > getTime(target_time, "11:00") && target_time < getTime(target_time, "16:30"))) {
            probability = 0.55 * probability;

        } else if (wind_level <= 3 & (target_time < getTime(target_time, "11:00") || target_time > getTime(target_time, "16:30"))) {
            probability = 0.60 * probability;
        } else if (wind_level <= 3 & (target_time > getTime(target_time, "11:00") || target_time < getTime(target_time, "16:30"))) {
            probability = 0.50 * probability;
        } else if (wind_level > 3 & (target_time < getTime(target_time, "11:00") || target_time > getTime(target_time, "16:30"))) {
            probability = 0.40 * probability;
        } else {
            probability = 0.30 * probability;
        }
        return probability.intValue();
    }

    /**
     * 计算帆船的运行概率的
     *
     * @param {*} weather
     */
    public Integer calcWzdSailboat(String base, Integer wind_level, Long target_time) throws ParseException {
        Double probability = 100.0;
        //阴天、雨天、雪天、大雾天等不具备
        if (base.equals("大雨") || base.equals("暴雨") || base.equals("大暴雨")) {
            probability = 0.30 * probability;
            //晚间不飞；
        } else if (wind_level <= 5) {
            probability = 0.90 * probability;
            //晴|多云、白天、
        } else if (wind_level > 5 && wind_level <= 6) {
            probability = 0.40 * probability;
        } else if (wind_level > 6) {
            probability = 0.30 * probability;
        }
        return probability.intValue();
    }

    /**
     * 计算游艇的运行概率的
     *
     * @param {*} weather
     */
    public Integer calcWzdYacht(String base, Integer wind_level, Long target_time) throws ParseException {
        Double probability = 100.0;
        //阴天、雨天、雪天、大雾天等不具备
        if (base.equals("暴雨") || base.equals("大暴雨") || base.equals("大雨")) {
            probability = 0.30 * probability;
            //晚间不飞；
        } else if (wind_level <= 5) {
            probability = 0.90 * probability;
            //晴|多云、白天、
        } else if (wind_level > 5 && wind_level <= 6) {
            probability = 0.40 * probability;
        } else if (wind_level > 6) {
            probability = 0.30 * probability;
        }
        return probability.intValue();
    }


    private Long getTime(Long target_time, String params) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(target_time);
        String dateTime = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " + params + ":00";
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.parse(dateTime).getTime();
    }

    @Override
    public List<Map<String, Object>> selectPlaceListByProject(long projectId) {
        GoodsProject goodsProject = goodsProjectMapper.getProjectById(projectId, 1L);
        List<Map<String, Object>> placeList = placeMapper.selectPlaceListByProject(goodsProject.getServicePlaceId());
        return placeList;
    }

    @Override
    public Integer updateProjectPlace(Long id, Long projectId, Long placeId) {

        Map<String, Object> params = new HashMap<>();
        params.put("staff_id", id);
        params.put("project_id", projectId);
        List<GoodsProjectStaffPlace> placeList = goodsProjectStaffPlaceMapper.selectByMap(params);
        if (placeList.size() == 0) {
            GoodsProjectStaffPlace place = new GoodsProjectStaffPlace();
            place.setStaffId(id);
            place.setProjectId(projectId);
            place.setPlaceId(placeId);
            return goodsProjectStaffPlaceMapper.insert(place);
        }
        GoodsProjectStaffPlace place = placeList.get(0);
        place.setPlaceId(placeId);
        return goodsProjectStaffPlaceMapper.updateById(place);
    }

    @Override
    public int updateProjectStatusBatch(UpdateGoodsOperationParam updateGoodsOperationParam, User user) {

        List<Map<String, Object>> projectList = goodsProjectMapper.getProjectListByStaffId(user.getId());
        if (CollectionUtils.isEmpty(projectList)) {
            return -1;
        }
        boolean flag = projectList.stream().map(map -> Integer.parseInt(map.get("projectId").toString())).collect(Collectors.toList()).contains(updateGoodsOperationParam.getProjectId());
        if (!flag) {
            return -1;
        }
        int status = updateGoodsOperationParam.getStatus();
        int projectId = updateGoodsOperationParam.getProjectId();
        String remark = updateGoodsOperationParam.getRemark();
        int servicePlaceId = updateGoodsOperationParam.getServicePlaceId();
        int probability = updateGoodsOperationParam.getProbability();
        AtomicInteger count = new AtomicInteger();
        logger.info("项目运营状态 开始更新...");
        updateGoodsOperationParam.getDateList().forEach(date -> {
            logger.info("更新参数 date {},status {},projectId {},remark {},servicePlaceId {},user {}", date, status, projectId, remark, servicePlaceId, user.getId());
            count.addAndGet(goodsProjectOperationMapper.updateStatusBatch(date, status, projectId, remark, probability, servicePlaceId, user,updateGoodsOperationParam.getDegreeOfInfluence()));
        });
        logger.info("项目运营状态 更新结束...");
        return count.get();
    }

    @Override
    public Integer updateProject(Long id, Long projectId) {
        GoodsProject goodsProject = goodsProjectMapper.selectByStaffIdAndProjectId(id,projectId);
        if(goodsProject==null){
            return -1;
        }
        else{
            Integer result=0;
            Map<String,Object> params = new HashMap<>();
            params.put("staff_id",id);
            List<GoodsProjectStaff> projectStaff = goodsProjectStaffMapper.selectByMap(params);
            for(GoodsProjectStaff staff:projectStaff){
                staff.setProjectId(projectId);
                result=goodsProjectStaffMapper.updateById(staff);
            }
            return result;
        }
    }


}
