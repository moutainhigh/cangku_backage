package cn.enn.wise.ssop.service.cms.mapper;

import cn.enn.wise.ssop.service.cms.model.Vote;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

/**
 * @date:2020/4/2 17:04
 * @author:hsq
 */
@Repository
public interface VoteMapper extends BaseMapper<Vote> {

    @Delete("DELETE FROM vote WHERE id = #{id}")
    int deleteVoteById(Long id);
}
