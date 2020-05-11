package cn.enn.wise.ssop.service.cms.mapper;

import cn.enn.wise.ssop.service.cms.model.Enshrine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @date:2020/4/9 17:04
 * @author:hsq
 */
@Repository
public interface EnshrineMapper extends BaseMapper<Enshrine> {

    int deleteEnshrine(@Param("memberId") String memberId, @Param("articleId") Long articleId, @Param("type") Byte type);
}
