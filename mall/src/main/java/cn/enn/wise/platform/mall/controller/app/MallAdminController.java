package cn.enn.wise.platform.mall.controller.app;

import cn.enn.wise.platform.mall.bean.param.ReportParam;
import cn.enn.wise.platform.mall.bean.param.UpdateGoodsOperationParam;
import cn.enn.wise.platform.mall.bean.vo.OperationResultVo;
import cn.enn.wise.platform.mall.bean.vo.RealTimeOperationListVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.service.MallAdminService;
import cn.enn.wise.platform.mall.service.OrderService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.StaffAuthRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

/**
 * MallAdmin API
 *
 * @author anhui
 * @since 2019-05-22
 */
@RestController
@RequestMapping("/mall/admin/")
@Api(value = "运营管理Controller", tags = {"运营管理Controller"})
public class MallAdminController {

    @Autowired
    MallAdminService mallAdminService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "热气球运营管理", notes = "热气球运营管理")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "date", value = "日期 例如 2018-08-08", paramType = "query"),
            @ApiImplicitParam(required = true, name = "placeId", value = "地点id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "type", value = "上下午：0 全天 1 上午 2 下午", paramType = "query")
    })
    public ResponseEntity<OperationResultVo> getManageList(String date, Long placeId, String type) {

        return mallAdminService.listOperationByDate(date, placeId, type);
    }

    @RequestMapping(value = "/listV2", method = RequestMethod.POST)
    @ApiOperation(value = "热气球运营管理V2", notes = "热气球运营管理V2")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "date", value = "日期 例如 2018-08-08", paramType = "query"),
            @ApiImplicitParam(required = true, name = "placeId", value = "地点id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "type", value = "上下午：0 全天 1 上午 2 下午", paramType = "query")
    })
    public ResponseEntity<OperationResultVo> getManageListV2(String date, Long placeId, String type, Long projectId) {
        return mallAdminService.listOperationByDateV2(date, placeId, type, projectId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑运营时段可飞概率", notes = "编辑运营时段可飞概率")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "id", value = "id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "status", value = "可飞状态：1可飞，2不可飞", paramType = "query"),
            @ApiImplicitParam(required = true, name = "remark", value = "原因", paramType = "query"),
            @ApiImplicitParam(required = true, name = "probability", value = "飞行概率", paramType = "query"),
            @ApiImplicitParam(required = true, name = "placeId", value = "地点id", paramType = "query")
    })
    @StaffAuthRequired
    public ResponseEntity update(Long id, Byte status, String remark, Integer probability, Long placeId,
                                 @Value("#{request.getAttribute('currentUser')}") User user) {
        return mallAdminService.updateGoodsProjectOperation(id, remark, status, probability, user.getId(), user.getNickName(), placeId);
    }

    @RequestMapping(value = "/updateV2", method = RequestMethod.POST)
    @ApiOperation(value = "编辑运营时段可飞概率V2", notes = "编辑运营时段可飞概率V2")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "id", value = "id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "status", value = "可飞状态：1可飞，2不可飞", paramType = "query"),
            @ApiImplicitParam(required = true, name = "remark", value = "原因", paramType = "query"),
            @ApiImplicitParam(required = true, name = "probability", value = "飞行概率", paramType = "query"),
            @ApiImplicitParam(required = true, name = "placeId", value = "地点id", paramType = "query")
    })
    @StaffAuthRequired
    public ResponseEntity updateV2(Long id, Byte status, String remark, Integer probability, Long placeId,
                                   @Value("#{request.getAttribute('currentUser')}") User user) {
        return mallAdminService.updateGoodsProjectOperationV2(id, remark, status, probability, user.getId(), user.getNickName(), placeId);
    }

    @RequestMapping(value = "/updateV3", method = RequestMethod.POST)
    @ApiOperation(value = "编辑运营时段排队V3", notes = "编辑排队")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "id", value = "id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "status", value = "可飞状态：1可飞，2不可飞", paramType = "query"),
            @ApiImplicitParam(required = true, name = "remark", value = "原因", paramType = "query"),
            @ApiImplicitParam(required = true, name = "degreeOfInfluence", value = "排队等级", paramType = "query"),
            @ApiImplicitParam(required = true, name = "placeId", value = "地点id", paramType = "query")
    })
    @StaffAuthRequired
    public ResponseEntity updateV3(Long id, Byte status, String remark, String degreeOfInfluence, Long placeId,
                                   @Value("#{request.getAttribute('currentUser')}") User user) {
        return mallAdminService.updateGoodsProjectOperationV3(id, remark, status, degreeOfInfluence, user.getId(), user.getNickName(), placeId);
    }

    /**
     * 司机端首页
     *
     * @return
     */
    @RequestMapping(value = "/real/time/list", method = RequestMethod.POST)
    @ApiOperation(value = "分销商运营概况", notes = "分销商运营概况")
    @StaffAuthRequired
    public ResponseEntity<RealTimeOperationListVo> realTimeList(@Value("#{request.getAttribute('currentUser')}") User user) {
        return mallAdminService.listOperationRealTime();
    }

    /**
     * 司机端首页
     *
     * @return
     */
    @RequestMapping(value = "/real/time/list/v2", method = RequestMethod.POST)
    @ApiOperation(value = "分销商运营概况", notes = "分销商运营概况")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "projectId", value = "项目Id", paramType = "query")
    })
    //@StaffAuthRequired
    public ResponseEntity<RealTimeOperationListVo> realTimeListV2(Long projectId, @Value("#{request.getAttribute('currentUser')}") User user) {
        return mallAdminService.listOperationRealTimeV2(projectId);
    }

    /**
     * 司机端首页
     *
     * @return
     */
    @RequestMapping(value = "/real/time/list/v2_2", method = RequestMethod.POST)
    @ApiOperation(value = "分销商运营概况", notes = "分销商运营概况")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "projectId", value = "项目Id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "paramDate", value = "日期 例如 2018-08-08", paramType = "query")
    })
    //@StaffAuthRequired
    public ResponseEntity<RealTimeOperationListVo> realTimeListV2_2(Long projectId,String paramDate, @Value("#{request.getAttribute('currentUser')}") User user) {

        return mallAdminService.listOperationRealTimeV2_2(projectId,paramDate);
    }
    /**
     * 地点列表
     *
     * @return
     */
    @RequestMapping(value = "/place/list", method = RequestMethod.POST)
    @ApiOperation(value = "热气球运营地点", notes = "热气球运营地点")
    public ResponseEntity realPlaceList() {
        List<Map<String, Object>> placeList = new ArrayList<>();
        Map<String, Object> placeMap = new HashMap<>();
        placeMap.put("id", 1);
        placeMap.put("name", "吞白");
        placeList.add(placeMap);

        placeMap = new HashMap<>();
        placeMap.put("id", 2);
        placeMap.put("name", "酒店");
        placeList.add(placeMap);

        return new ResponseEntity(placeList);
    }

    /**
     * 公司项目下的地点列表
     *
     * @return
     */
    @RequestMapping(value = "/project/place/list", method = RequestMethod.POST)
    @ApiOperation(value = "项目运营地点", notes = "项目运营地点")
    public ResponseEntity realPlaceListByCompanyId(Long projectId) {
        List<Map<String, Object>> placeList = new ArrayList<>();
        if (projectId < 1) {
            return new ResponseEntity(placeList);
        }
        placeList = mallAdminService.selectPlaceListByProject(projectId);
        return new ResponseEntity(placeList);
    }

    /**
     * 地点运营时段
     *
     * @param placeId
     * @return
     */
    @RequestMapping(value = "/place/time/list", method = RequestMethod.POST)
    @ApiOperation(value = "项目运营时段", notes = "项目运营时段")
    public ResponseEntity getTimeListByPlaceId(@PathVariable("placeId") long placeId) {
        List<Map<String, Object>> placeList = new ArrayList<>();
        Map<String, Object> placeMap = new HashMap<>();
        placeMap.put("id", 1);
        placeMap.put("name", "吞白");
        placeList.add(placeMap);

        placeMap = new HashMap<>();
        placeMap.put("id", 2);
        placeMap.put("name", "酒店");
        placeList.add(placeMap);

        return new ResponseEntity(placeList);
    }

    /**
     * 分销报表
     */
    @RequestMapping(value = "/report/distribute/list", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "startDate", value = "2019-07-09", paramType = "query"),
            @ApiImplicitParam(required = true, name = "endDate", value = "2019-07-10", paramType = "query")
    })
    public ResponseEntity listDistribute(@RequestBody ReportParam param) {
        if (param.getStartDate() == null) {
            param.setStartDate("2010-01-01");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(param.getEndDate())) {
            param.setEndDate(df.format(new Date())+" 23:59:59");
        }else {
            param.setEndDate(param.getEndDate()+" 23:59:59");
        }

        return new ResponseEntity(1, "成功", orderService.listReport(param.getStartDate(),param.getEndDate()));
    }


    /**
     * 转化率报表
     */
    @RequestMapping(value = "/report/transaction/list", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "startDate", value = "2019-07-09", paramType = "query"),
            @ApiImplicitParam(required = true, name = "endDate", value = "2019-07-10", paramType = "query"),

    })
    public ResponseEntity transactionReportList(@RequestBody ReportParam param) {
        if (param.getStartDate() == null) {
            param.setStartDate("2010-01-01");
        }
        if (param.getEndDate() == null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            param.setEndDate(df.format(new Date())+" 23:59:59");
        }
        return new ResponseEntity(1, "成功", mallAdminService.listTicketReport(param.getStartDate(), param.getEndDate()));
    }

    /**
     * 更新服务地点
     */
    @RequestMapping(value = "/place/update", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "projectId", value = "项目id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "placeId", value = "服务地点id", paramType = "query"),
    })
    @StaffAuthRequired
    public ResponseEntity updateProjectPlace(Long projectId, Long placeId, @Value("#{request.getAttribute('currentUser')}") User user) {
        Integer result = mallAdminService.updateProjectPlace(user.getId(), projectId, placeId);
        if (result > 0) {
            return new ResponseEntity(1, "更新成功", null);
        }
        return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "更新失败", null);
    }

    /**
     * 批量更新状态
     */
    @RequestMapping(value = "/update/batch", method = RequestMethod.POST)
    @ApiOperation(value = " 批量更新运营状态", notes = " 批量更新运营状态")
    @StaffAuthRequired
    public ResponseEntity updateProjectStatus(@RequestBody UpdateGoodsOperationParam updateGoodsOperationParam, @Value("#{request.getAttribute('currentUser')}") User user) {

        int code = GeneConstant.SUCCESS_CODE;
        String msg = "执行成功";

        if (CollectionUtils.isNotEmpty(updateGoodsOperationParam.getDateList()) && user != null) {
            int count = mallAdminService.updateProjectStatusBatch(updateGoodsOperationParam, user);
            if (count == -1) {
                code = GeneConstant.PARAM_INVALIDATE;
                msg = "当前项目权限有变更,请前去核实服务信息。";
            }
            return new ResponseEntity(code, msg, count);
        }
        return new ResponseEntity(GeneConstant.PARAM_INVALIDATE, "更新失败，缺失参数!");
    }

    /**
     * 更新服务项目
     */
    @RequestMapping(value = "/project/update", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "projectId", value = "项目id", paramType = "query")
    })
    @StaffAuthRequired
    public ResponseEntity updateProject(Long projectId,@Value("#{request.getAttribute('currentUser')}") User user) {

        Integer result =mallAdminService.updateProject(user.getId(),projectId);
        if(result>0){
            return new ResponseEntity(1,"更新成功",null);
        }if(result==-1) {
            return new ResponseEntity(2, "您不可以管理此项目", null);
        }
        return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"更新失败",null);
    }





}
