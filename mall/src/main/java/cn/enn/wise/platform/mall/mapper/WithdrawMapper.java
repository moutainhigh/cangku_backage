package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.WithdrawRecord;
import cn.enn.wise.platform.mall.bean.param.WithdrawQueryParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WithdrawMapper extends BaseMapper<WithdrawRecord> {


    List<WithdrawRecord> selectByParam(@Param("param")WithdrawQueryParam param);


    Long selectByParamCount(@Param("param")WithdrawQueryParam param);




}
