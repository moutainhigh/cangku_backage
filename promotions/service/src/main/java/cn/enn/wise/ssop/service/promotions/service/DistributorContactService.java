package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.DistributorContactParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorContactUpdateParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorContactDTO;
import cn.enn.wise.ssop.service.promotions.consts.DistributorEnum;
import cn.enn.wise.ssop.service.promotions.mapper.DistributorBaseMapper;
import cn.enn.wise.ssop.service.promotions.mapper.DistributorContactMapper;
import cn.enn.wise.ssop.service.promotions.model.DistributorBase;
import cn.enn.wise.ssop.service.promotions.model.DistributorContact;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.PARAM_ERROR;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * @author 耿小洋
 * 分销商联系人信息
 */
@Service("distributorContactService")
public class DistributorContactService extends ServiceImpl<DistributorContactMapper, DistributorContact> {



    @Autowired
    DistributorContactMapper distributorContactMapper;

    @Autowired
    DistributorBaseMapper distributorBaseMapper;



    /**
     * 添加分销商联系人信息
     * @param
     * @return
     */
    @Transactional
    public Boolean saveDistributionContact(List<DistributorContactParam> paramList) {
        ArrayList<DistributorContact> DistributorContactList = new ArrayList<>();
        for (DistributorContactParam DistributorContactParam:paramList) {
            DistributorContact distributorContact = new DistributorContact();
            BeanUtils.copyProperties(DistributorContactParam,distributorContact,getNullPropertyNames(distributorContact));
            DistributorContactList.add(distributorContact);
        }

        DistributorBase distributorBase = new DistributorBase();
        distributorBase.setId(paramList.get(0).getDistributorBaseId());
        distributorBase.setEditStep(DistributorEnum.EDITSTEP2.getValue());
        distributorBaseMapper.updateById(distributorBase);
        return this.saveBatch(DistributorContactList);
    }

    /**
     * 修改分销商联系人信息
     * @param
     * @return
     */
    @Transactional
    public Boolean updateDistributionContact(DistributorContactUpdateParam param) {

        Boolean flag = false;
        //遍历前段传来的list 如果id不为空修改 为空添加
        List<DistributorContactParam> paramList = param.getDistributorContactParam();
        for (int i = 0; i <paramList.size() ; i++) {
            DistributorContactParam distributorContactParam = paramList.get(i);
            DistributorContact distributorContact = new DistributorContact();
            BeanUtils.copyProperties(distributorContactParam, distributorContact,getNullPropertyNames(distributorContactParam));
            if(null==distributorContactParam.getId()){
                flag =this.save(distributorContact);
            }else{
                flag =this.updateById(distributorContact);
            }
        }
        this.removeByIds(param.getDeleteContactIds());
        DistributorBase distributorBase = new DistributorBase();
        distributorBase.setId(paramList.get(0).getDistributorBaseId());
        distributorBase.setEditStep(DistributorEnum.EDITSTEP2.getValue());
        distributorBaseMapper.updateById(distributorBase);
        return flag;
    }

    /**
     * 根据分销商id获取联系人信息
     * @param
     * @return
     */
    public R<List<DistributorContactDTO>> getDistributionContactByDistribuBaseId(Long distribuBaseId) {
        if(null==distribuBaseId){
            PARAM_ERROR.assertFail();
        }
        QueryWrapper<DistributorContact> objectQueryWrapper = new QueryWrapper<>();
        List<DistributorContact> distributorContacts = distributorContactMapper.selectList(objectQueryWrapper.eq("distributor_base_id", distribuBaseId));
        ArrayList<DistributorContactDTO> DistributorContactTDOs = new ArrayList<>();
        for (DistributorContact DistributorContact:distributorContacts) {
            DistributorContactDTO distributorBaseDTO= new DistributorContactDTO();
            BeanUtils.copyProperties(DistributorContact,distributorBaseDTO,getNullPropertyNames(DistributorContact));
            DistributorContactTDOs.add(distributorBaseDTO);
        }

        return new R<>(DistributorContactTDOs);
    }





}
