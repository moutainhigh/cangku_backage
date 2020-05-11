package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.DrawRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 安辉
 * 抽奖信息记录Mapper
 */
@Mapper
@Repository
public interface DrawRecordMapper extends BaseMapper<DrawRecord> {

}
