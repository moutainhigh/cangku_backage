package cn.enn.wise.ssop.service.promotions.service;


import cn.enn.wise.ssop.api.promotions.dto.request.PartnerQueryParam;
import cn.enn.wise.ssop.api.promotions.dto.request.PartnerSaveParam;
import cn.enn.wise.ssop.api.promotions.dto.response.PartnerAddRespDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.PartnerDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.SimplePartnerDTO;
import cn.enn.wise.uncs.base.pojo.param.QueryPage;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.ssop.service.promotions.mapper.PartnerMapper;
import cn.enn.wise.ssop.service.promotions.model.Partner;
import cn.enn.wise.ssop.service.promotions.util.RedisUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static cn.enn.wise.uncs.base.exception.assertion.CustomExceptionAssert.PARTNER_NAME_ISEXIST;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;


/**
 * @author shiz
 * 合作伙伴管理
 */
@Service("partnerService")
public class PartnerService extends ServiceImpl<PartnerMapper,Partner> {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取合作伙伴列表
     * @param partnerParam 查询条件
     * @return
     */
    
    public QueryData<SimplePartnerDTO> getPartners(PartnerQueryParam partnerParam) {
        String name = partnerParam.getName();

        LambdaQueryWrapper<Partner> wrapper = new LambdaQueryWrapper<>();
        IPage<Partner> page = this.page(new QueryPage<>(partnerParam), wrapper);
        if(!StringUtils.isEmpty(name)){
            wrapper.eq(Partner::getName, name);
        }

        return new QueryData<>(page, this::toSimpleLicenceDTO);
    }

    /**
     * 保存合作伙伴
     * @param partnerSaveParam 合作伙伴参数
     * @return
     */
    
    public PartnerAddRespDTO addPartner(PartnerSaveParam partnerSaveParam) {
        boolean isAdd = partnerSaveParam.getId() == null;

        Partner partner = new Partner();

        BeanUtils.copyProperties(partnerSaveParam,partner,getNullPropertyNames(partnerSaveParam));


        //判断名称唯一
        String name = partnerSaveParam.getName();
        boolean isExist = checkNameExist(name);

        //断言 合作伙伴名称已经存在
        PARTNER_NAME_ISEXIST.assertIsFalse(isExist,name);

        //增加
        if(isAdd){
            //生成appkey,appsecret
            String appkey = IdUtil.createSnowflake(1, 1).nextId()+"";
            String appsecret = IdUtil.simpleUUID();
            partner.setAppkey(appkey);
            partner.setAppsecret(appsecret);
            partner.setCreateTime(new Date());
        }

        boolean isSaveOk = this.saveOrUpdate(partner);
        Long id = partner.getId();
        if(isSaveOk){
//            String appDetailKey = String.format(RedisKey.PARTNER_DETAIL, id);
//            redisUtil.set(appDetailKey,partner);
        }

        return new PartnerAddRespDTO();
    }

    /**
     * 删除合作伙伴
     * @param id 主键
     * @return
     */
    public boolean delPartner(Long id) {
        boolean isOK = this.removeById(id);
        return isOK;
    }

    /**
     * 获取合作伙伴详情
     * @param id 主键
     * @return
     */
    public PartnerDTO getPartnerDetail(Long id) {
        Partner partner = this.getById(id);
        if(partner==null) return null;
        PartnerDTO dto = new PartnerDTO();
        BeanUtils.copyProperties(partner,dto,getNullPropertyNames(partner));

        return dto;
    }

    public boolean editState(Long id, Integer state) {
        Partner partner = this.getById(id);
        partner.setState(state);
        return this.updateById(partner);
    }

    public int getPartnerState(Long id) {
        Partner partner = this.getById(id);
        return partner.getState();
    }

    public boolean sendStateChangeMessage(Long id, Integer state) {

        return false;
    }


    /**
     * 检查合作伙伴名称是否存在
     * @param name 新名称
     * @return
     */
    private boolean checkNameExist(String name) {
        int count = this.count(new LambdaQueryWrapper<Partner>().eq(Partner::getName, name));
        return count>0;
    }


    private SimplePartnerDTO toSimpleLicenceDTO(Partner partner) {
        SimplePartnerDTO dto = new SimplePartnerDTO();
        BeanUtils.copyProperties(partner,dto,getNullPropertyNames(partner));
        return dto;
    }


    public PartnerDTO getPartnerDetailByAppKey(String appKey) {
        Partner partner = this.getOne(new LambdaQueryWrapper<Partner>().eq(Partner::getAppkey,appKey));
        if(partner==null) return null;
        PartnerDTO dto = new PartnerDTO();
        BeanUtils.copyProperties(partner,dto,getNullPropertyNames(partner));

        return dto;
    }
}
