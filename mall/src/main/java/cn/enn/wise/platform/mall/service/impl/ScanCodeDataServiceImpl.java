package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.ScanCodeDataCol;
import cn.enn.wise.platform.mall.bean.bo.ScanCodeDataValue;
import cn.enn.wise.platform.mall.bean.vo.ScanCodeDataReqVo;
import cn.enn.wise.platform.mall.bean.vo.ScanCodeDataResVo;
import cn.enn.wise.platform.mall.bean.vo.TableHeadVo;
import cn.enn.wise.platform.mall.mapper.ScanCodeDataMapper;
import cn.enn.wise.platform.mall.service.ScanCodeDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

/**
 * @author baijie
 * @date 2019-07-18
 */
@Service
@Slf4j
public class ScanCodeDataServiceImpl implements ScanCodeDataService {

    @Autowired
    private ScanCodeDataMapper scanCodeDataMapper;


    /**
     * 批量添加扫码统计结果数据
     * @param scanCodeDataReqVo
     * @throws Exception
     */
    @Override
    public void insertScanCodeData(ScanCodeDataReqVo scanCodeDataReqVo) throws Exception {

        //查询扫码点是否存在
        Integer companyId = scanCodeDataReqVo.getCompanyId();
        String contactPointName = scanCodeDataReqVo.getContactPointName();
        ScanCodeDataCol scanCodeDataColByColumnName = scanCodeDataMapper.getScanCodeDataColByColumnName(contactPointName, companyId);

        ScanCodeDataCol scanCodeDataCol  = new ScanCodeDataCol();

        if(scanCodeDataColByColumnName == null){

           scanCodeDataCol.setCompanyId(companyId);
            scanCodeDataCol.setColumnName(contactPointName);
            //添加扫码点
            scanCodeDataMapper.addScanCodeDataColumn(scanCodeDataCol);

        }else {
            scanCodeDataCol.setId(scanCodeDataColByColumnName.getId());
        }

        //添加扫码数据
        List<ScanCodeDataValue> sacnDataList = scanCodeDataReqVo.getSacnDataList();
        for (ScanCodeDataValue scanCodeDataValue : sacnDataList) {
            scanCodeDataValue.setColId(scanCodeDataCol.getId());
        }
        scanCodeDataMapper.addScanCodeDataValue(sacnDataList);

    }

    @Override
    public ScanCodeDataResVo selectScanCodeDataList(ScanCodeDataReqVo scanCodeDataReqVo) {
        ScanCodeDataResVo scanCodeDataResVo = new ScanCodeDataResVo();
        Integer appId = scanCodeDataReqVo.getCompanyId();

        //查询数据结果
        List<ScanCodeDataCol> allColumn = scanCodeDataMapper.getAllColumn(appId,scanCodeDataReqVo.getContactPointName());
        //动态的表头部分
        LinkedList<TableHeadVo> headVoList = new LinkedList<>();

        //所有的表头Id
        List<Long> allColumnIds = new ArrayList<>();

        for (ScanCodeDataCol scanCodeDataCol : allColumn) {

            TableHeadVo tableHeadVo = new TableHeadVo();
            tableHeadVo.setLabel(scanCodeDataCol.getColumnName());
            tableHeadVo.setProp("column"+scanCodeDataCol.getId());
            headVoList.add(tableHeadVo);

            allColumnIds.add(scanCodeDataCol.getId());
        }
        headVoList.addFirst(new TableHeadVo(){{
            setLabel("日期");
            setProp("scanDate");
        }});
        scanCodeDataResVo.setTableHead(headVoList);

        //  分页查询所有数据
        List<ScanCodeDataValue> scanCodeDataValues = scanCodeDataMapper.selectScanCodeDataList(scanCodeDataReqVo);
        //按照日期分组
        Map<Date, List<ScanCodeDataValue>> groupingByDate = scanCodeDataValues.stream().collect(Collectors.groupingBy(x -> x.getScanDate()));
        Set<Map.Entry<Date, List<ScanCodeDataValue>>> entries = groupingByDate.entrySet();

        List<Map<String,Object>> resultValueList = new ArrayList<>();
        entries.forEach((x) ->{

            Map<String,Object> map = new TreeMap<>();
            Date scanDate = x.getKey();
            map.put("scanDate",new SimpleDateFormat("yyyy-MM-dd").format(scanDate));
            List<ScanCodeDataValue> value = x.getValue();
            System.out.println(value);

           //匹配表头数据
            for (ScanCodeDataValue sv : value){

                for (TableHeadVo tableHead : headVoList) {

                    String prop = tableHead.getProp();
                    if(prop.equals("column"+sv.getId())){
                        map.put(prop,sv.getScanAmount());
                    }
                }
            }
            resultValueList.add(map);

        });

        scanCodeDataResVo.setTableData(resultValueList);
        return scanCodeDataResVo;
    }

    @Override
    public List<ScanCodeDataCol> selectScanCodeDataListBySort(ScanCodeDataReqVo scanCodeDataReqVo) {

        return scanCodeDataMapper.selectScanCodeDataListBySort(scanCodeDataReqVo);
    }


}
