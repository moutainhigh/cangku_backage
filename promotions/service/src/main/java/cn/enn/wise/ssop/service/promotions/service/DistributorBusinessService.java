package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.DistributorBusinessParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorBankDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorBusinessDTO;
import cn.enn.wise.ssop.service.promotions.consts.ChannelEnum;
import cn.enn.wise.ssop.service.promotions.consts.DistributorEnum;
import cn.enn.wise.ssop.service.promotions.mapper.ChannelMapper;
import cn.enn.wise.ssop.service.promotions.mapper.DistributorAddMapper;
import cn.enn.wise.ssop.service.promotions.mapper.DistributorBaseMapper;
import cn.enn.wise.ssop.service.promotions.mapper.DistributorBussinessMapper;
import cn.enn.wise.ssop.service.promotions.model.Channel;
import cn.enn.wise.ssop.service.promotions.model.DistributorAdd;
import cn.enn.wise.ssop.service.promotions.model.DistributorBase;
import cn.enn.wise.ssop.service.promotions.model.DistributorBusiness;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.PARAM_ERROR;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/*
 * @author 耿小洋
 * 分销商业务信息
 */

@Service("distributorBusinessService")
public class DistributorBusinessService extends ServiceImpl<DistributorBussinessMapper, DistributorBusiness> {


    @Autowired
    DistributorBussinessMapper distributorBussinessMapper;

    @Autowired
    DistributorAddMapper distributorAddMapper;

    @Autowired
    DistributorBaseMapper distributorBaseMapper;

    @Autowired
    ChannelMapper channelMapper;


    /**
     * 添加分销商业务信息
     *
     * @param param
     * @return
     */
    @Transactional
    public Boolean saveDistributionBussiness(DistributorBusinessParam param) {
        DistributorBusiness distributorBusiness = new DistributorBusiness();
        BeanUtils.copyProperties(param, distributorBusiness,getNullPropertyNames(param));
        Boolean flag = this.save(distributorBusiness);
        updateDistiorBaseVerifyTypeByDistribuBase(param.getDistributorBaseId());
        DistributorBase distributorBase = new DistributorBase();
        distributorBase.setId(param.getDistributorBaseId());
        distributorBase.setEditStep(DistributorEnum.EDITSTEP4.getValue());
        distributorBaseMapper.updateById(distributorBase);
        return flag;
    }


    /**
     * 修改分销商业务信息
     *
     * @param param
     * @return
     */
    @Transactional
    public Boolean updateDistributionBussiness(DistributorBusinessParam param) {
        DistributorBusiness distributorBusiness = new DistributorBusiness();
        BeanUtils.copyProperties(param, distributorBusiness,getNullPropertyNames(param));
        Boolean flag = this.saveOrUpdate(distributorBusiness);
        updateDistiorBaseVerifyTypeByDistribuBase(param.getDistributorBaseId());

        DistributorBase distributorBaseParam = new DistributorBase();
        distributorBaseParam.setId(param.getDistributorBaseId());
        distributorBaseParam.setEditStep(DistributorEnum.EDITSTEP4.getValue());
        distributorBaseMapper.updateById(distributorBaseParam);

        return flag;
    }


    /**
     * 根据分销商id获取业务信息
     *
     * @param
     * @return
     */
    public R getDistributionBussinessByDistribuBaseId(Long distribuBaseId) {
        if (null == distribuBaseId) {
            PARAM_ERROR.assertFail();
        }
        QueryWrapper<DistributorBusiness> objectQueryWrapper = new QueryWrapper<>();
        DistributorBusiness distributorBusiness = distributorBussinessMapper.selectOne(objectQueryWrapper.eq("distributor_base_id", distribuBaseId));
        DistributorBusinessDTO distributorBusinessDTO = new DistributorBusinessDTO();
        if (null != distributorBusiness) {
            BeanUtils.copyProperties(distributorBusiness, distributorBusinessDTO,getNullPropertyNames(distributorBusiness));
        }
        return new R<>(distributorBusinessDTO);
    }


    /**
     * 修改分销商审核状态
     *
     * @param
     * @return
     */
    public void updateDistiorBaseVerifyTypeByDistribuBase(Long distributorBaseId) {
        DistributorBase distribution = distributorBaseMapper.selectById(distributorBaseId);
        DistributorAdd distributorAdd = distributorAddMapper.selectOne(new QueryWrapper<DistributorAdd>().eq("distributor_base_id", distributorBaseId));
        Channel channel = channelMapper.selectById(distribution.getChannelId());
        if (StringUtils.isNotBlank(channel.getRegisterResource())) {
            if (channel.getRegisterResource().contains(",") && channel.getRegisterResource().contains(ChannelEnum.REGISTER2.getValue().toString()) || channel.getRegisterResource().contains(ChannelEnum.REGISTER3.getValue().toString())) {
                distribution.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
                distribution.setState(DistributorEnum.EFFECTIVE.getValue());
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                distribution.setIntoTime(Timestamp.valueOf(fmt.format(new Date())));
                if (distributorAdd != null) {
                    distributorAdd.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
                    distributorAddMapper.updateById(distributorAdd);
                }
            } else if (!channel.getRegisterResource().equals(ChannelEnum.REGISTER1.getValue().toString())) {
                distribution.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
                distribution.setState(DistributorEnum.EFFECTIVE.getValue());
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                distribution.setIntoTime(Timestamp.valueOf(fmt.format(new Date())));
                if (distributorAdd != null) {
                    distributorAdd.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
                    distributorAddMapper.updateById(distributorAdd);
                }
            }
        }
        //修改分销商基础信息
        distribution.setVerifyType(DistributorEnum.AVAILABLE_DISTRIBUTOR.getValue());
        distributorBaseMapper.updateById(distribution);
    }


}
