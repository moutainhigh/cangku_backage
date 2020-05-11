package cn.enn.wise.ssop.service.cms.service;

import cn.enn.wise.ssop.api.cms.dto.request.AdvertiseSaveParam;
import cn.enn.wise.ssop.api.cms.dto.response.AdvertiseAppDTO;
import cn.enn.wise.ssop.api.cms.dto.response.SimpleAdvertiseDTO;
import cn.enn.wise.ssop.service.cms.consts.CmsEnum;
import cn.enn.wise.ssop.service.cms.consts.RedisKey;
import cn.enn.wise.ssop.service.cms.mapper.AdvertiseMapper;
import cn.enn.wise.ssop.service.cms.mapper.CategoryMapper;
import cn.enn.wise.ssop.service.cms.model.Advertise;
import cn.enn.wise.ssop.service.cms.model.Category;
import cn.enn.wise.ssop.service.cms.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.param.QueryPage;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.common.http.GeneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("advertiseService")
public class AdvertiseService extends ServiceImpl<AdvertiseMapper, Advertise> {
    @Autowired
    AdvertiseMapper advertiseMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    RedisUtil redisUtil;

    public QueryData<SimpleAdvertiseDTO> getAdvertiseList(QueryParam queryParam) {

        IPage<Advertise> page = this.page(new QueryPage<>(queryParam),
        new LambdaQueryWrapper<Advertise>().orderByDesc(Advertise::getSort));

        return new QueryData<>(page, advertise -> {
            SimpleAdvertiseDTO dto = new SimpleAdvertiseDTO();
            BeanUtils.copyProperties(advertise, dto);
            return dto;
        });
    }

    @Cacheable(value="CMS", key="T(String).format(T(cn.enn.wise.ssop.service.cms.consts.RedisKey).ADVERTISE_DETAIL,#id)")
    public SimpleAdvertiseDTO getAdvertiserDetail(Long id) {
        return getAdvertiserDetailById(id);
    }


    public SimpleAdvertiseDTO getAdvertiserDetailById(Long id) {
        Advertise advertise = this.getById(id);

        SimpleAdvertiseDTO advertiseDTO = new SimpleAdvertiseDTO();
        if(advertise==null) return null;
        BeanUtils.copyProperties(advertise,advertiseDTO,GeneUtil.getNullPropertyNames(advertise));

        //分类名称
        Category category = categoryMapper.selectById(advertiseDTO.getCategoryId());
        if(category!=null){
            String categoryName = category.getCategoryName();
            advertiseDTO.setCategoryName(categoryName);
        }
        return advertiseDTO;
    }

    public Long saveAdvertise(AdvertiseSaveParam advertiseAddParam) {

        Advertise advertise = new Advertise();
        BeanUtils.copyProperties(advertiseAddParam,advertise, GeneUtil.getNullPropertyNames(advertiseAddParam));

        boolean saveOrUpdate = this.saveOrUpdate(advertise);
        if (saveOrUpdate) {
            SimpleAdvertiseDTO advertiseDTO = new SimpleAdvertiseDTO();
            BeanUtils.copyProperties(advertise, advertiseDTO, GeneUtil.getNullPropertyNames(advertiseDTO));
            String voteHashKey = RedisKey.ServerName+String.format(RedisKey.ADVERTISE_DETAIL, advertise.getId());
            redisUtil.set(voteHashKey, advertiseDTO);
        }
        return advertise.getId();
    }

    public Boolean delAdvertise(Long id) {
        boolean remove = this.removeById(id);
        if (remove) {
            String voteHashKey = RedisKey.ServerName+String.format(RedisKey.ADVERTISE_DETAIL, id);
            redisUtil.del(voteHashKey);
        }
        return remove;
    }

    public Boolean editState(Long id, Byte state) {
        Advertise advertise = new Advertise();
        advertise.setId(id);
        advertise.setState(state);
        return this.updateById(advertise);
    }

    @Transactional
    public Boolean doSort(Long id, Integer sort) {
        //查询当前排序位是否 存在
        int count = this.count(new LambdaQueryWrapper<Advertise>().eq(Advertise::getSort, sort));
        if(count>0){
            //修改大于等于sort的  sort+1
            this.update(new LambdaUpdateWrapper<Advertise>()
                    .set(Advertise::getSort,sort+1)
                    .ge(Advertise::getSort,sort));
        }
        Advertise advertise = new Advertise();
        advertise.setId(id);
        advertise.setSort(sort);
        boolean isOk = this.updateById(advertise);
        return isOk;
    }


    /**
     * 查询广告列表
     * @return
     */
    @Cacheable(value="CMS", key="T(cn.enn.wise.ssop.service.cms.consts.RedisKey).ADVERTISE_LIST")
    public List<AdvertiseAppDTO> getadvertiseList(){
        LambdaQueryWrapper<Advertise> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Advertise::getId,Advertise::getCoverUrl,Advertise::getAddress)
                .eq(Advertise::getState,"1")
                .orderByDesc(Advertise::getSort);

        List<Advertise> advertiseList = advertiseMapper.selectList(wrapper);

        if(CollectionUtils.isNotEmpty(advertiseList)){
            List<AdvertiseAppDTO> advertiseAppDTOList = advertiseList.stream().map(r -> {
                AdvertiseAppDTO advertiseAppDTO = new AdvertiseAppDTO();
                advertiseAppDTO.setId(r.getId());
                advertiseAppDTO.setCoverUrl(r.getCoverUrl());
                advertiseAppDTO.setAddress(r.getAddress());
                return advertiseAppDTO;
            }).collect(Collectors.toList());

            return advertiseAppDTOList;
        }
        return new ArrayList<>();

    }

    public void addLoadNumber(List<AdvertiseAppDTO> list) {
        for (AdvertiseAppDTO time : list) {
            String load = RedisKey.ServerName+String.format(RedisKey.ADVERTISE_CLICK_LOAD, CmsEnum.ADVERTISE_LOAD.getValue());
            //加载量加一
            redisUtil.zZsetByKeyValueAddScore(load, time.getId());
        }
    }

}
