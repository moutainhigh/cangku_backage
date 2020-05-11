package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.ScanCodeDataCol;
import cn.enn.wise.platform.mall.bean.bo.ScanCodeDataValue;
import cn.enn.wise.platform.mall.bean.vo.ScanCodeDataReqVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 扫码数据持久化层
 *
 * @author baijie
 * @date 2019-07-18
 */
@Mapper
public interface ScanCodeDataMapper {


    /**
     * 查询扫码点是否存在
     * @param columnName
     * @param companyId
     * @return
     */
  ScanCodeDataCol getScanCodeDataColByColumnName(@Param("columnName")String columnName,@Param("companyId")Integer companyId);

    /**
     * 添加一个扫码点
     * @param scanCodeDataCol
     */
  void addScanCodeDataColumn(ScanCodeDataCol scanCodeDataCol);

    /**
     * 查询各个扫码点数据
     */
   List<ScanCodeDataValue> selectScanCodeDataList(ScanCodeDataReqVo scanCodeDataReqVo);


    /**
     * 批量添加扫码数据值
     * @param scanCodeDataValues
     */
   void addScanCodeDataValue(List<ScanCodeDataValue> scanCodeDataValues);

    /**
     * 获取所有的列
     * @param companyId
     * @return
     */
   List<ScanCodeDataCol> getAllColumn(@Param("companyId") Integer companyId,@Param("contactPointName") String contactPointName);

    /**
     * 查看总体扫码数据
     * @param scanCodeDataReqVo
     * @return
     */
    List<ScanCodeDataCol>  selectScanCodeDataListBySort(ScanCodeDataReqVo scanCodeDataReqVo);

}
