package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.DistributorAddParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorAddUpdateAppParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorAddDTO;
import cn.enn.wise.ssop.service.promotions.consts.ChannelEnum;
import cn.enn.wise.ssop.service.promotions.consts.DistributorEnum;
import cn.enn.wise.ssop.service.promotions.mapper.*;
import cn.enn.wise.ssop.service.promotions.model.*;
import cn.enn.wise.ssop.service.promotions.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.response.R;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.ObjectUtils;
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
 * 分销商补充信息
 */

@Service("distributorAddService")
public class DistributorAddService extends ServiceImpl<DistributorAddMapper, DistributorAdd> {


    @Autowired
    DistributorAddMapper distributorAddMapper;

    @Autowired
    ChannelMapper channelMapper;

    @Autowired
    DistributorBankMapper distributorBankMapper;

    @Autowired
    DistributorBaseMapper distributorBaseMapper;

    @Autowired
    DistributorBussinessMapper distributorBussinessMapper;

    @Autowired
    DistributorAccountMapper  distributorAccountMapper;

    @Autowired
    RedisUtil redisUtil;


    /**
     * 添加分销商补充信息
     *
     * @param param
     * @return
     */
    public Boolean saveDistributionAdd(DistributorAddParam param) {
        DistributorAdd distributorAdd = distributorAddMapper.selectOne(new QueryWrapper<DistributorAdd>().eq("distributor_base_id", param.getDistributorBaseId()));
        if (distributorAdd != null) {
            return false;
        }
        DistributorBase distributorBase = distributorBaseMapper.selectById(param.getDistributorBaseId());
        Channel channel = channelMapper.selectById(distributorBase.getChannelId());
        distributorAdd = new DistributorAdd();
        BeanUtils.copyProperties(param, distributorAdd,getNullPropertyNames(param));
        if (StringUtils.isNotBlank(channel.getRegisterResource())) {
            if (channel.getRegisterResource().contains(",") && channel.getRegisterResource().contains(ChannelEnum.REGISTER2.getValue().toString()) ||channel.getRegisterResource().contains(ChannelEnum.REGISTER3.getValue().toString())) {
                distributorAdd.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
            }else if(!channel.getRegisterResource().equals(ChannelEnum.REGISTER1.getValue().toString())){
                distributorAdd.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
            }
        }

        DistributorBase distributorBaseParam = new DistributorBase();
        distributorBaseParam.setId(param.getDistributorBaseId());
        distributorBaseParam.setEditStep(DistributorEnum.EDITSTEP5.getValue());
        distributorBaseMapper.updateById(distributorBaseParam);
        return this.save(distributorAdd);
    }


    /**
     * 修改分销商补充信息
     *
     * @param param
     * @return
     */
    public Boolean updateDistributionAdd(DistributorAddParam param) {
        DistributorAdd distributorAdd = new DistributorAdd();
        //判断渠道面审批类型不是App注册的直接审核通过
        DistributorBusiness distributorBusiness = distributorBussinessMapper.selectOne(new QueryWrapper<DistributorBusiness>().eq("distributor_base_id", param.getId()));
        BeanUtils.copyProperties(param, distributorAdd,getNullPropertyNames(param));
        if (distributorBusiness!=null) {
            DistributorBase distributorBase = distributorBaseMapper.selectById(param.getDistributorBaseId());
            Channel channel = channelMapper.selectById(distributorBase.getChannelId());
            if (StringUtils.isNotBlank(channel.getRegisterResource())) {
                if (channel.getRegisterResource().contains(",") && channel.getRegisterResource().contains(ChannelEnum.REGISTER2.getValue().toString()) ||channel.getRegisterResource().contains(ChannelEnum.REGISTER3.getValue().toString())) {
                    distributorAdd.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
                }else if(!channel.getRegisterResource().equals(ChannelEnum.REGISTER1.getValue().toString())){
                    distributorAdd.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
                }
            }
        }

        DistributorBase distributorBaseParam = new DistributorBase();
        distributorBaseParam.setId(param.getDistributorBaseId());
        distributorBaseParam.setEditStep(DistributorEnum.EDITSTEP5.getValue());
        distributorBaseMapper.updateById(distributorBaseParam);
        return this.saveOrUpdate(distributorAdd);
    }

    /**
     * app段修改分销商补充信息和财务信息
     *
     * @param param
     * @return
     */
    @Transactional
    public R<Boolean> appUpdateDistributionAdd(DistributorAddUpdateAppParam param) {
        //校验验证码
        Object redisCodeObject = redisUtil.get(param.getPhone());
        System.out.println("redis中通过手机号:"+param.getPhone()+"获取到的验证码:"+redisCodeObject);
        if(redisCodeObject==null){
            return R.error("请先获取验证码");
        }else if(!param.getMessageCode().equals(redisCodeObject.toString())){
            return R.error("验证码错误");
        }

        DistributorAdd distributorAdd = new DistributorAdd();
        BeanUtils.copyProperties(param, distributorAdd,getNullPropertyNames(param));
        distributorAdd.setWechatHead(param.getHeadImg());
        distributorAdd.setBankCardImg(param.getBankCardPage());
        DistributorBank distributorBank = new DistributorBank();
        BeanUtils.copyProperties(param, distributorBank,getNullPropertyNames(param));
        distributorBank.setUserName(param.getBankAccountName());
        distributorBank.setCardNumber(param.getBankCardNumber());
        DistributorAccount distributorAccount = distributorAccountMapper.selectOne(new QueryWrapper<DistributorAccount>().eq("distributor_account_id", param.getUserId()));
        if(distributorAccount==null){
            return R.error("此分销商账户不存在");
        }
        DistributorAdd distributorAddParam = distributorAddMapper.selectOne(new QueryWrapper<DistributorAdd>().eq("distributor_base_id",distributorAccount.getDistributorBaseId()));
        //如果DistributorBankId为空 添加分销商财务信息 否则修改分销商财务信息
        if (ObjectUtil.isAllNotEmpty(distributorAddParam.getDistributorBankId())) {
            distributorBankMapper.updateById(distributorBank);
        } else {
            distributorBankMapper.insert(distributorBank);
            distributorAdd.setDistributorBankId(distributorBank.getId());
        }
        //判断渠道面审批类型不是App注册的直接审核通过
        DistributorBusiness distributorBusiness = distributorBussinessMapper.selectOne(new QueryWrapper<DistributorBusiness>().eq("distributor_base_id", distributorAccount.getDistributorBaseId()));
        if (distributorBusiness!=null) {
            DistributorBase distributorBase = distributorBaseMapper.selectById(distributorAccount.getDistributorBaseId());
            Channel channel = channelMapper.selectById(distributorBase.getChannelId());
            BeanUtils.copyProperties(param, distributorAdd,getNullPropertyNames(param));
            if (StringUtils.isNotBlank(channel.getRegisterResource())) {
                if (channel.getRegisterResource().contains(",") && channel.getRegisterResource().contains(ChannelEnum.REGISTER2.getValue().toString()) ||channel.getRegisterResource().contains(ChannelEnum.REGISTER3.getValue().toString())) {
                    distributorAdd.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
                }else if(!channel.getRegisterResource().equals(ChannelEnum.REGISTER1.getValue().toString())){
                    distributorAdd.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
                }
            }
        }

        boolean flag = this.saveOrUpdate(distributorAdd);
        if (flag) {
            return R.success(null);
        }
        return R.error("修改分销商补充信息失败");
    }


    /**
     * 根据分销商id获取补充信息
     *
     * @param
     * @return
     */
    public R getDistributionAddByDistribuBaseId(Long distribuBaseId) {
        if (null == distribuBaseId) {
            PARAM_ERROR.assertFail();
        }
        QueryWrapper<DistributorAdd> objectQueryWrapper = new QueryWrapper<>();
        DistributorAdd distributorAdd = distributorAddMapper.selectOne(objectQueryWrapper.eq("distributor_base_id", distribuBaseId));
        DistributorAddDTO distributorAddDTO = null;
        if (distributorAdd != null) {
            DistributorBank distributorBank = distributorBankMapper.selectById(distributorAdd.getDistributorBankId());
            distributorAddDTO = new DistributorAddDTO();
            if (null != distributorBank) {
                BeanUtils.copyProperties(distributorBank, distributorAddDTO,getNullPropertyNames(distributorBank));
            }
            BeanUtils.copyProperties(distributorAdd, distributorAddDTO,getNullPropertyNames(distributorAdd));
        }
        return new R<>(distributorAddDTO);
    }


}
