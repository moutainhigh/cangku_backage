package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.DistributorBankParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorBankUpdateParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorContactUpdateParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorBankDTO;
import cn.enn.wise.ssop.service.promotions.consts.DistributorEnum;
import cn.enn.wise.ssop.service.promotions.mapper.DistributorBankMapper;
import cn.enn.wise.ssop.service.promotions.mapper.DistributorBaseMapper;
import cn.enn.wise.ssop.service.promotions.model.DistributorBank;
import cn.enn.wise.ssop.service.promotions.model.DistributorBase;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.hutool.core.util.ObjectUtil;
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

/*
 * @author 耿小洋
 * 分销商财务信息
 */

@Service("distributorBankService")
public class DistributorBankService extends ServiceImpl<DistributorBankMapper, DistributorBank> {


    @Autowired
    DistributorBankMapper distributorBankMapper;


    @Autowired
    DistributorBaseMapper distributorBaseMapper;

    /**
     * 添加分销商财务信息
     * @param param
     * @return
     */
    @Transactional
    public Boolean saveDistributorBank(List<DistributorBankParam> param) {
        ArrayList<DistributorBank> DistributorbankList = new ArrayList<>();
        for (DistributorBankParam distributorBankParam : param) {
            DistributorBank distributorBank = new DistributorBank();
            BeanUtils.copyProperties(distributorBankParam, distributorBank,getNullPropertyNames(distributorBankParam));
            DistributorbankList.add(distributorBank);
        }

        DistributorBase distributorBase = new DistributorBase();
        distributorBase.setId(param.get(0).getDistributorBaseId());
        distributorBase.setEditStep(DistributorEnum.EDITSTEP3.getValue());
        distributorBaseMapper.updateById(distributorBase);
        return this.saveBatch(DistributorbankList);

    }

    /**
     * 修改分销商财务信息
     * @param
     * @return
     */
    @Transactional
    public Boolean updateDistributionBank(DistributorBankUpdateParam param) {

        Boolean flag = false;
        //遍历前段传来的list 如果id不为空修改 为空添加
        List<DistributorBankParam> distributorBankParams = param.getDistributorBankParams();
        for (int i = 0; i <distributorBankParams.size() ; i++) {
            DistributorBankParam distributorContactParam = distributorBankParams.get(i);
            DistributorBank distributorBank = new DistributorBank();
            BeanUtils.copyProperties(distributorContactParam, distributorBank,getNullPropertyNames(distributorContactParam));
            if(null==distributorContactParam.getId()){
                flag =this.save(distributorBank);
            }else{
                flag =this.updateById(distributorBank);
            }
        }
        this.removeByIds(param.getDeleteBankIds());

        DistributorBase distributorBase = new DistributorBase();
        distributorBase.setId(distributorBankParams.get(0).getDistributorBaseId());
        distributorBase.setEditStep(DistributorEnum.EDITSTEP3.getValue());
        distributorBaseMapper.updateById(distributorBase);
        return flag;
    }


    /**
     * 根据分销商id获取财务信息
     *
     * @param
     * @return
     */
    public R<List<DistributorBankDTO>> getDistributionBankByDistribuBaseId(Long distribuBaseId,Integer state) {
        if(null==distribuBaseId){
            PARAM_ERROR.assertFail();
        }
        QueryWrapper<DistributorBank> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("distributor_base_id", distribuBaseId);

        if (ObjectUtil.isAllNotEmpty(state)) {
            objectQueryWrapper.eq("state", state);
        }
        List<DistributorBank> distributorBanks = distributorBankMapper.selectList(objectQueryWrapper);
        ArrayList<DistributorBankDTO> distributorBankDTOs = new ArrayList<>();
        for (DistributorBank distributorBank:distributorBanks) {
            DistributorBankDTO distributorBaseDTO= new DistributorBankDTO();
            BeanUtils.copyProperties(distributorBank,distributorBaseDTO,getNullPropertyNames(distributorBank));
            distributorBankDTOs.add(distributorBaseDTO);
        }
        return new R<>(distributorBankDTOs);
    }


}
