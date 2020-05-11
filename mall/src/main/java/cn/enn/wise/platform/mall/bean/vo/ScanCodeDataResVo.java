package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 扫码数据
 *
 * @author baijie
 * @date 2019-07-18
 */
@Data
@ApiModel("扫码数据统计响应Vo")
public class ScanCodeDataResVo {


  @ApiModelProperty(value = "动态表头")
  private List<TableHeadVo> tableHead;

  @ApiModelProperty(value = "表数据")
  private List<Map<String,Object>> tableData;


}
