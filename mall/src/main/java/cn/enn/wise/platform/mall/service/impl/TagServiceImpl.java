package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.TagBo;
import cn.enn.wise.platform.mall.bean.param.TagParam;
import cn.enn.wise.platform.mall.mapper.TagMapper;
import cn.enn.wise.platform.mall.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/16
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, TagBo> implements TagService {



    @Override
    public TagBo addTag(TagParam param) {

        if(param.getId()!=null && param.getId()!=0 ){
            TagBo bo = this.baseMapper.selectById(param.getId());
            bo.setName(param.getName());
            bo.setCategoryId(param.getCategoryId());
            bo.setType(param.getType());
            bo.setPid(param.getPid());
            this.baseMapper.updateById(bo);
            return bo;
        }else{
            TagBo tagBo  =new TagBo();
            tagBo.setType(param.getType());
            tagBo.setName(param.getName());
            tagBo.setCategoryId(param.getCategoryId());
            tagBo.setCreateBy(-1L);
            tagBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            tagBo.setUpdateBy(-1L);
            tagBo.setPid(param.getPid());
            tagBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            this.baseMapper.insert(tagBo);

            return tagBo;
        }
    }

    @Override
    public Integer deleteTag(List<Long> ids) {
        return this.baseMapper.deleteBatchIds(ids);
    }
}
