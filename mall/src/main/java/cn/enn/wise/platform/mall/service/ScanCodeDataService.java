package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.ScanCodeDataCol;
import cn.enn.wise.platform.mall.bean.vo.ScanCodeDataReqVo;
import cn.enn.wise.platform.mall.bean.vo.ScanCodeDataResVo;

import java.util.List;

/**
 * @author baijie
 * @date 2019-07-18
 */
public interface ScanCodeDataService {

    void insertScanCodeData(ScanCodeDataReqVo scanCodeDataReqVo) throws Exception;


    /**
     * 查询扫码数据
     * @return
     */
    ScanCodeDataResVo selectScanCodeDataList(ScanCodeDataReqVo scanCodeDataReqVo);


    List<ScanCodeDataCol> selectScanCodeDataListBySort(ScanCodeDataReqVo scanCodeDataReqVo);


}
