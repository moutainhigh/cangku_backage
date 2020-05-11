package cn.enn.wise.platform.mall.controller.data;

import cn.enn.wise.platform.mall.bean.bo.ScanCodeDataCol;
import cn.enn.wise.platform.mall.bean.vo.ScanCodeDataReqVo;
import cn.enn.wise.platform.mall.bean.vo.ScanCodeDataResVo;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.ScanCodeDataService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ParamValidateUtil;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author baijie
 * @date 2019-07-18
 */
@RestController
@RequestMapping("/data")
@Api("扫码数据统计结果相关接口")
public class ScanCodeDataController extends BaseController {

    @Autowired
    private ScanCodeDataService scanCodeDataService;

    @PostMapping("/batch/insert")
    @ApiOperation("批量插入扫码数据统计结果")
    public ResponseEntity batchInsertScanCodeData(@RequestBody ScanCodeDataReqVo scanCodeDataReqVo) throws Exception {

        if(scanCodeDataReqVo == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"参数不能为空");
        }

        if(scanCodeDataReqVo.getContactPointName() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"接触点参数不能为空");
        }
        if(scanCodeDataReqVo.getCompanyId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"companyId不能为空");
        }

        if(scanCodeDataReqVo.getSacnDataList() == null || scanCodeDataReqVo.getSacnDataList().size() == 0){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"参数不能为空");
        }

       scanCodeDataService.insertScanCodeData(scanCodeDataReqVo);

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE);
    }

    @PostMapping("/scancode/list")
    @ApiOperation("查询扫码数据统计结果")
    public ResponseEntity getScanCodeDataList(@RequestBody ScanCodeDataReqVo scanCodeDataReqVo){

        ParamValidateUtil.validateScanCodeData(scanCodeDataReqVo);

        ScanCodeDataResVo scanCodeDataPageInfo = scanCodeDataService.selectScanCodeDataList(scanCodeDataReqVo);

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,scanCodeDataPageInfo);
    }




    @PostMapping("/scancode/sort")
    @ApiOperation("查询扫码数据统计结果")
    public ResponseEntity getScanDataCodeList(@RequestBody ScanCodeDataReqVo scanCodeDataReqVo){


        ParamValidateUtil.validateScanCodeData(scanCodeDataReqVo);

        List<ScanCodeDataCol> scanCodeData = scanCodeDataService.selectScanCodeDataListBySort(scanCodeDataReqVo);

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,scanCodeData);
    }
}
