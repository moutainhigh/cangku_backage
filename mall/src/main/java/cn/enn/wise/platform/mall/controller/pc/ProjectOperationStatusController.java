package cn.enn.wise.platform.mall.controller.pc;

import cn.enn.wise.platform.mall.bean.vo.ProjectOperationStatusResponseAppVO;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.ProjectOperationStatusService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ParamValidateUtil;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 项目运营因素APi接口
 *
 * @author baijie
 * @date 2019-08-26
 */
@RestController
@RequestMapping("/operation/status")
public class ProjectOperationStatusController extends BaseController {

    @Autowired
    private ProjectOperationStatusService projectOperationStatusService;


    @PostMapping("/edit")
    @ApiOperation("编辑项目运营因素")
    public ResponseEntity editProjectOperationStatus(@RequestBody ProjectOperationStatusResponseAppVO projectOperationStatusRequestVO) throws Exception {

        ParamValidateUtil.validateEditProjectOperationStatus(projectOperationStatusRequestVO);

        projectOperationStatusService.editOperationStatus(projectOperationStatusRequestVO);

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE);
    }

    @GetMapping("/list/{projectId}")
    @ApiOperation(("根据projectId获取项目运营状态" ))
    public ResponseEntity getProjectOperationStatusList(@PathVariable("projectId") Long projectId){

        if(projectId == null){

            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"项目不能为空");
        }

        ProjectOperationStatusResponseAppVO projectOperationStatusResponseAppVO =  projectOperationStatusService.getProjectOperationStatusList(projectId);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,projectOperationStatusResponseAppVO);
    }
}
