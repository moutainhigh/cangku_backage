package cn.enn.wise.ssop.service.promotions.service;


import cn.enn.wise.ssop.service.promotions.mapper.PartnerAppMapper;
import cn.enn.wise.ssop.api.promotions.dto.request.AppSaveParam;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.uncs.base.pojo.param.QueryPage;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.ssop.service.promotions.model.Application;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;


/**
 * @author shiz
 * 合作伙伴管理
 */
@Service("partnerAppService")
public class PartnerAppService extends ServiceImpl<PartnerAppMapper, Application> {


    /**
     * 获取客户端列表
     *
     * @param id
     * @param queryParam 查询条件
     * @return
     */
    
    public QueryData<SimpleAppDTO> getApps(Long id, QueryParam queryParam) {

        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        IPage<Application> page = this.page(new QueryPage<>(queryParam), wrapper);
        wrapper.eq(Application::getPartnerId,id);

        return new QueryData<>(page, this::toSimpleDTO);
    }

    /**
     * 保存合作伙伴
     * @param appSaveParam 合作伙伴参数
     * @return
     */
    
    public Boolean saveApp(AppSaveParam appSaveParam) {
        boolean isAdd = appSaveParam.getId() == null;

        Application application = new Application();

        BeanUtils.copyProperties(appSaveParam,application,getNullPropertyNames(appSaveParam));

        if(isAdd){
            application.setCreateTime(new Date());
            application.setState(1);
            application.setDeleted(1);
        }
        boolean isOk = this.saveOrUpdate(application);

        return isOk;
    }

    /**
     * 删除合作伙伴
     * @param id 主键
     * @return
     */
    public boolean delApp(Long id) {
        boolean isOK = this.removeById(id);
        return isOK;
    }

    /**
     * 获取合作伙伴详情
     * @param id 主键
     * @return
     */
    public AppDTO getAppDetail(Long id) {
        Application application = this.getById(id);

        AppDTO dto = new AppDTO();
        BeanUtils.copyProperties(application,dto,getNullPropertyNames(application));

        return dto;
    }

    public boolean editState(Long id, Integer state) {
        Application application = this.getById(id);
        application.setState(state);
        return this.updateById(application);
    }


    private SimpleAppDTO toSimpleDTO(Application application) {
        SimpleAppDTO dto = new SimpleAppDTO();
        BeanUtils.copyProperties(application,dto,getNullPropertyNames(application));
        return dto;
    }
}
