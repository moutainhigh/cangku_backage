package cn.enn.wise.ssop.service.cms.service;

import cn.enn.wise.ssop.api.cms.dto.request.oldcms.FeedBackVo;
import cn.enn.wise.ssop.api.cms.dto.request.oldmall.FeedBackParam;
import cn.enn.wise.ssop.service.cms.mapper.FeedBackMapper;
import cn.enn.wise.ssop.service.cms.model.FeedBack;
import cn.enn.wise.ssop.service.cms.model.FeedBackExpand;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedBackService extends ServiceImpl<FeedBackMapper, FeedBack> {

    @Resource
    private FeedBackMapper feedBackMapper;

    public Integer addFeedBack(FeedBackVo feedBackVo) {
        String companyId = HttpContextUtils.GetHttpHeader("company_id");
        FeedBack feedBack = new FeedBack();
        BeanUtils.copyProperties(feedBackVo, feedBack);
        return feedBackMapper.insert(feedBack);
    }

    public PageInfo<FeedBackVo> findAllFeedBackList(FeedBackParam feedBackParam) {
        String companyId = HttpContextUtils.GetHttpHeader("company_id");
        feedBackParam.setScenicId(Integer.valueOf(companyId));
        List<FeedBackVo> list = feedBackMapper.findAllFeedBackList(feedBackParam);
        return new PageInfo<>(list);
    }

    public List<FeedBackExpand> findAllFeedBackType() {
        String companyId = HttpContextUtils.GetHttpHeader("company_id");
        return feedBackMapper.findAllFeedBackType(companyId);
    }

    public Boolean updateStatus(Long id, byte type) {
        UpdateWrapper<FeedBack> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", type);
        updateWrapper.eq("id",id);
        return this.update(updateWrapper);
    }

    public FeedBackVo findFeedBackDetails(Integer id) {
        String companyId = HttpContextUtils.GetHttpHeader("company_id");
        return feedBackMapper.findFeedBackDetails(id, companyId);
    }
}
