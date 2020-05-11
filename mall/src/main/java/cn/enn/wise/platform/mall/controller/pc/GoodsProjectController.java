package cn.enn.wise.platform.mall.controller.pc;

import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.mapper.GoodsProjectMapper;
import cn.enn.wise.platform.mall.service.GoodsProjectService;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 项目管理APi
 *
 * @author baijie
 * @date 2019-07-25
 */
@RestController
@RequestMapping("/goodsproject")
@Api("项目管理Api接口")
public class GoodsProjectController extends BaseController {


    @Autowired
    private GoodsProjectService goodsProjectService;

    @Autowired
    private GoodsProjectMapper goodsProjectMapper;

    @Autowired
    private MessageSender messageSender;

    @PostMapping("/save")
    @ApiOperation("新增项目接口")
    private ResponseEntity saveGoodsProject(@RequestBody GoodsProject goodsProject,
                                            @RequestHeader("token") String token) {

        SystemStaffVo userByToken = this.getUserByToken(token);

        GoodsProjectParamValidateUtil.checkSaveGoodsProject(goodsProject);
        goodsProject.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        goodsProject.setCreateUserId(userByToken.getId());
        goodsProject.setCreateUserName(userByToken.getName());

        GoodsProject goodsProjectByProjectCode = goodsProjectMapper.selectOne(new QueryWrapper<GoodsProject>().eq("project_code", goodsProject.getProjectCode()));
        if(goodsProjectByProjectCode != null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"项目编号不能重复");
        }

        //添加项目
        boolean save = goodsProjectService.save(goodsProject);
        if (!save) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "添加失败！");
        }

        List<GoodsProject> list = goodsProjectService.list();
        messageSender.sendGoodsProjectUpdateQueue(list);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE, GeneConstant.SUCCESS_CHINESE);
    }


    @PostMapping("/update")
    @ApiOperation("修改项目接口")
    public ResponseEntity updateGoodsProjectById(@RequestBody GoodsProject goodsProject,
                                                 @RequestHeader("token") String token) {

        SystemStaffVo userByToken = this.getUserByToken(token);
        GoodsProjectParamValidateUtil.checkSaveGoodsProject(goodsProject);
        if (goodsProject.getId() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "id不能为空");
        }
        goodsProject.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        goodsProject.setUpdateUserId(userByToken.getId());
        goodsProject.setUpdateUserName(userByToken.getName());
        GoodsProject byId = goodsProjectService.getById(goodsProject.getId());
        if (byId == null) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "该项目不存在");
        }
        GoodsProject goodsProjectByProjectCode = goodsProjectMapper.selectOne(new QueryWrapper<GoodsProject>().eq("project_code", goodsProject.getProjectCode()));
        if(goodsProjectByProjectCode != null){

            if(goodsProject.getId().longValue() != goodsProjectByProjectCode.getId().longValue()){

                throw new BusinessException(GeneConstant.BUSINESS_ERROR,"项目编号不能重复");
            }
        }


        boolean result = goodsProjectService.updateById(goodsProject);
        if (!result) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "更新失败,请检查是否有内容更新");
        }

        List<GoodsProject> list = goodsProjectService.list();
        messageSender.sendGoodsProjectUpdateQueue(list);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE, GeneConstant.SUCCESS_CHINESE);
    }

    @GetMapping("/get/{id}")
    @ApiOperation("获取项目详情")
    public ResponseEntity<GoodsProject> getGoodsProjectById(@PathVariable("id") Long id) {

        GoodsProject byId = goodsProjectService.getById(id);
        if (byId == null) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "该项目不存在");
        }

        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE, GeneConstant.SUCCESS_CHINESE, byId);
    }

    @PostMapping("/list")
    @ApiOperation("获取项目列表")
    public ResponseEntity<ResPageInfoVO<List<GoodsProject>>> getGoodsProjectList(
            @RequestBody ReqPageInfoQry<GoodsProject> projectPageQry,
            @RequestHeader("token") String token) {

        this.getUserByToken(token);
        return goodsProjectService.listGoodsProject(projectPageQry);
    }

    @GetMapping("/operation")
    @StaffAuthRequired
    public ResponseEntity getOperationProject(@Value("#{request.getAttribute('currentUser')}") User user,@RequestHeader("companyId")Long companyId){
//
        if(user == null || user.getId() == null){
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT,"token过期");
        }

        //根据userId查询distributorId,
        //region 注释
        /* 临时注释
        Map<String, String> urlconfig = SystemAdapter.URL_MAP.get(companyId);
        if(urlconfig != null){

                String url = urlconfig.get(AppConstants.getDistributeByUserId);
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("userId",String.valueOf(user.getId()));
                String post = HttpClientUtil.post(url, paramMap);
                if(StringUtils.isNotEmpty(post)){
                    JSONObject resultJson = JSONObject.parseObject(post);
                    Integer result = resultJson.getInteger("result");
                    if(result == 1){
                        JSONObject value = resultJson.getJSONObject("value");
                        Long id = value.getLong("id");

                        List<GoodsProject> operationProject = goodsProjectService.getOperationProject(id, companyId);

                        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,operationProject);

                    }else {
                        throw new BusinessException(GeneConstant.BUSINESS_ERROR,"接口返回失败!");
                    }


                }else {
                    throw new BusinessException(GeneConstant.BUSINESS_ERROR,"接口调用异常");
            }

        }else {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"接口调用路径配置错误");
        }

        */
        //endregion


        List<Map<String,Object>> projectMap = goodsProjectService.getProjectListByStaffId(user.getId(),user.getSysRoles());
        return new ResponseEntity(projectMap);
    }

    @GetMapping("/period")
    public ResponseEntity getProjectByPeriod(){

        List<GoodsProject> projectByPeriod = goodsProjectService.getProjectByPeriod();

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,projectByPeriod);
    }
}
