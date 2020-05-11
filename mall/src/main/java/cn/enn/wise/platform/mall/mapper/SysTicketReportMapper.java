package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.SysTicketReportBo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * SysTicketReport Mapper 接口
 * </p>
 *
 * @author anhui
 * @since 2019-05-22
 */
public interface SysTicketReportMapper extends BaseMapper<SysTicketReportBo> {
    List<Map<String,Object>> listTicketReport(@Param("startDate") String startDate,@Param("endDate") String endDate);
    List<Map<String,Object>> listTicketReportTotal(@Param("startDate") String startDate,@Param("endDate") String endDate);
}
