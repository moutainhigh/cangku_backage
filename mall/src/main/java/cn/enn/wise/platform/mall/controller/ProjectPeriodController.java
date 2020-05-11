package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.param.ProjectPeriodReqParam;
import cn.enn.wise.platform.mall.bean.vo.ProjectPeriodResVo;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.service.GoodsProjectPeriodService;
import cn.enn.wise.platform.mall.service.GoodsProjectService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GoodsProjectPeriod API
 *
 * @author caiyt
 * @since 2019-05-28
 */
@RestController
@RequestMapping("/period")
@Api(value = "后台管理项目时段运营信息api")
public class ProjectPeriodController extends BaseController {
    @Autowired
    private GoodsProjectPeriodService goodsProjectPeriodService;

    @Autowired
    private GoodsProjectService goodsProjectService;


    /**
     * 根据项目ID查询该项目下的所有运营时段信息
     *
     * @param projectId 项目ID
     * @return
     */
    @RequestMapping(value = "/list/{projectId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据项目ID查询该项目下的所有运营时段信息")
    public ResponseEntity<List<ProjectPeriodResVo>> listByProjectId(@PathVariable("projectId") long projectId,
                                                                    @RequestHeader(value = "token") String token) {
        this.getUserByToken(token);
        return new ResponseEntity<>(goodsProjectPeriodService.listByProjectId(projectId));
    }

    /**
     * 保存项目时段运营信息
     *
     * @param projectPeriodReqParamList 项目时段运营信息
     * @return
     */
    @RequestMapping(value = "/update/{projectId}", method = RequestMethod.POST)
    @ApiOperation(value = "根据项目ID查询该项目下的所有运营时段信息")
    public ResponseEntity updateGoodsProjectPeriod(@PathVariable("projectId") long projectId,
                                                   @RequestBody List<ProjectPeriodReqParam> projectPeriodReqParamList,
                                                   @RequestHeader(value = "token") String token) {
        SystemStaffVo staffVo = this.getUserByToken(token);
        return goodsProjectPeriodService.updateGoodsProjectPeriod(projectId, projectPeriodReqParamList, staffVo);
    }

    @GetMapping(value = "/project/list")
    @ApiOperation(value = "获取所有项目列表")
    public ResponseEntity listProject(){
        QueryWrapper<GoodsProject> wrapper = new QueryWrapper<>();
        wrapper.eq("project_status",1);
        List<GoodsProject> list = goodsProjectService.list(wrapper);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS,list);
    }

    @GetMapping(value = "/company/projectList")
    @ApiOperation(value = "获取所有项目列表")
    public ResponseEntity listProjectByCompany(Long  companyId){

        List<GoodsProject> list = goodsProjectService.listProjectByCompanyId(companyId);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS,list);
    }

    @GetMapping(value = "/company/project")
    @ApiOperation(value = "获取所有项目列表")
    public ResponseEntity getProjectByCompanyId(long companyId){

        List<GoodsProject> list = goodsProjectService.getProjectByCompanyId(companyId);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS,list);
    }
}
