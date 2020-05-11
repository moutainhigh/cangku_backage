package cn.enn.wise.ssop.service.cms.service;

import cn.enn.wise.ssop.api.cms.dto.request.AnnouncementSaveParam;
import cn.enn.wise.ssop.api.cms.dto.response.AnnouncementAppDTO;
import cn.enn.wise.ssop.api.cms.dto.response.AnnouncementDTO;
import cn.enn.wise.ssop.api.cms.dto.response.SimpleAnnouncementDTO;
import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.mapper.AnnouncementMapper;
import cn.enn.wise.ssop.service.cms.model.Announcement;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.enn.wise.ssop.api.cms.consts.CmsExceptionAssert.ANNOUNCEMENT_STATUS_ISENABLE;

/**
 * 公告管理
 */
@Service("announcementService")
public class AnnouncementService extends ServiceImpl<AnnouncementMapper, Announcement> {

    @Autowired
    AnnouncementMapper announcementMapper;
    @Autowired
    RedisUtil redisUtil;

    public Long saveAnnouncement(AnnouncementSaveParam announcementAddParam) {

        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(announcementAddParam,announcement);
        if(announcement.getState()==1){
            announcement.setPublishTime(Timestamp.valueOf(LocalDateTime.now()));
        }
        boolean saveOrUpdate = this.saveOrUpdate(announcement);
        if (saveOrUpdate) {
            String voteHashKey = RedisKey.ServerName+String.format(RedisKey.ANNOUNCEMENT_DETAIL, announcement.getId());
            AnnouncementDTO advertiseDTO = new AnnouncementDTO();
            BeanUtils.copyProperties(announcement, advertiseDTO);
            redisUtil.set(voteHashKey, advertiseDTO);
        }
        return announcement.getId();
    }

    public QueryData<SimpleAnnouncementDTO> getAnnouncementList(QueryParam queryParam) {

        //发布时间倒序查询
        IPage<SimpleAnnouncementDTO> page = announcementMapper.selectAnnouncement(new Page(queryParam.getPageNo(), queryParam.getPageSize()));

        return new QueryData(page);
    }

    public Boolean delAnnouncement(Long id) {
        Announcement announcement = this.getById(id);
        if(announcement==null) return false;
        ANNOUNCEMENT_STATUS_ISENABLE.assertIsTrue(announcement.getState()==2,id);
        boolean remove = this.removeById(id);
        if (remove) {
            String voteHashKey = RedisKey.ServerName+String.format(RedisKey.ANNOUNCEMENT_DETAIL, id);
            redisUtil.del(voteHashKey);
        }
        return remove;
    }

    @Cacheable(value="CMS", key="T(String).format(T(cn.enn.wise.ssop.service.cms.consts.RedisKey).ANNOUNCEMENT_DETAIL,#id)")
    public AnnouncementDTO getAnnouncementrDetail(Long id) {
        return getAnnouncementrDetailById(id);
    }

    public AnnouncementDTO getAnnouncementrDetailById(Long id) {
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        Announcement announcement = this.getById(id);
        BeanUtils.copyProperties(announcement,announcementDTO);
        return announcementDTO;
    }

    public Boolean editState(Long id, Byte state) {

        Announcement announcement = new Announcement();
        announcement.setId(id);
        announcement.setState(state);

        if(1==state){
            //启用 更改发布时间
            announcement.setPublishTime(Timestamp.valueOf(LocalDateTime.now()));
        }
        return this.updateById(announcement);
    }

    /**
     * 查询公告列表
     * @return
     */
    @Cacheable(value="CMS", key="T(cn.enn.wise.ssop.service.cms.consts.RedisKey).ANNOUNCEMENT_LIST")
    public List<AnnouncementAppDTO> getAnnouncementList(){
        LambdaQueryWrapper<Announcement> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Announcement::getId,Announcement::getContent,Announcement::getPublishTime).eq(Announcement::getState,"1").orderByDesc(Announcement::getPublishTime).last("limit 5");
        List<Announcement> announcementList = announcementMapper.selectList(wrapper);
        if(CollectionUtils.isNotEmpty(announcementList)){
            List<AnnouncementAppDTO> announcementAppDTOList = announcementList.stream().map(r -> {
                AnnouncementAppDTO announcementAppDTO = new AnnouncementAppDTO();
                announcementAppDTO.setId(r.getId());
                announcementAppDTO.setContent(r.getContent());
                announcementAppDTO.setPublishTime(r.getPublishTime());
                return announcementAppDTO;
            }).collect(Collectors.toList());

            return announcementAppDTOList;
        }
            return new ArrayList<>();
    }

}
