package cn.enn.wise.ssop.service.order.mapper;

import cn.enn.wise.ssop.api.order.dto.request.WithdrawQueryParam;
import cn.enn.wise.ssop.service.order.model.WithdrawRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Mapper
@Repository
public interface WithdrawRecordMapper extends BaseMapper<WithdrawRecord> {


    List<WithdrawRecord> selectByParam(@Param("param") WithdrawQueryParam param);

    Long selectByParamCount(@Param("param")WithdrawQueryParam param);


}
