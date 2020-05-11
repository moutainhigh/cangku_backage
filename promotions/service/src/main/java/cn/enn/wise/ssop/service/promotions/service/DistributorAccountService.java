package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.authority.dto.request.DistributionUserSaveParam;
import cn.enn.wise.ssop.api.authority.dto.response.DistributionUserDTO;
import cn.enn.wise.ssop.api.authority.facade.SystemUserFacade;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorAccountListParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorAccountParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorAccountDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorAccountListDTO;
import cn.enn.wise.ssop.service.promotions.consts.DistributorEnum;
import cn.enn.wise.ssop.service.promotions.mapper.ChannelMapper;
import cn.enn.wise.ssop.service.promotions.mapper.DistributorAccountMapper;
import cn.enn.wise.ssop.service.promotions.mapper.DistributorBaseMapper;
import cn.enn.wise.ssop.service.promotions.model.Channel;
import cn.enn.wise.ssop.service.promotions.model.DistributorAccount;
import cn.enn.wise.ssop.service.promotions.model.DistributorBase;
import cn.enn.wise.ssop.service.promotions.util.GeneConstant;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
 * 分销商账号信息
 */
@Service("distributorAccountService")
public class DistributorAccountService extends ServiceImpl<DistributorAccountMapper, DistributorAccount> {


    @Autowired
    DistributorAccountMapper distributorAccountMapper;

    @Autowired
    DistributorBaseService distributorBaseService;


    @Autowired
    DistributorBaseMapper distributorBaseMapper;


    @Autowired
    ChannelMapper channelMapper;


    @Autowired
    SystemUserFacade systemUserFacade;


    /**
     * 根据分销商id获取账号信息
     *
     * @param
     * @return
     */
    public List<DistributorAccountListDTO> getDistributionAccountByDistribuBaseId(DistributorAccountListParam distributorAccountListParam) {
        Long distribuBaseId = distributorAccountListParam.getDistributorBaseId();
        //查询分销商信息
        DistributorBase distributorBase = distributorBaseMapper.selectById(distribuBaseId);
        //查询渠道信息
        Channel channel = channelMapper.selectById(distributorBase.getChannelId());
        //根据分销商id查询分销商账号id
        QueryWrapper  queryWrapper=new QueryWrapper<DistributorAccount>();
        queryWrapper.eq("distributor_base_id", distribuBaseId);
        if(StringUtils.isNotBlank(distributorAccountListParam.getCreatorName())){
            queryWrapper.like("creator_name", distributorAccountListParam.getCreatorName());
        }
        List<DistributorAccount> distributorAccounts = distributorAccountMapper.selectList(queryWrapper);
        Long[] distributorAccountIds = new Long[distributorAccounts.size()];
        for (int i = 0; i < distributorAccounts.size(); i++) {
            distributorAccountIds[i] = distributorAccounts.get(i).getDistributorAccountId();
        }
        //通过分销商账号id查询账号列表
        R<List<DistributionUserDTO>> DistributorAccountsR = systemUserFacade.info(distributorAccountIds);
        System.out.println("==================掉账号服务返回参数"+DistributorAccountsR+"=====================");
        List<DistributionUserDTO> DistributorAccounts = DistributorAccountsR.getData();
        //组装数据返回
        List<DistributorAccountListDTO> distributorAccountListDTOs = new ArrayList<>();
        if (DistributorAccounts!=null) {
            for (int i = 0; i < DistributorAccounts.size(); i++) {
                //条件筛选
                if(StringUtils.isNotBlank(distributorAccountListParam.getAccountNumber())){
                  if(!DistributorAccounts.get(i).getUsername().equals(distributorAccountListParam.getAccountNumber())){
                   continue;
                  }
                }
                if(ObjectUtils.allNotNull(distributorAccountListParam.getState())){
                    System.out.println("前段传入账号状态值："+distributorAccountListParam.getState());
                  if(!DistributorAccounts.get(i).getStatus().equals(Integer.valueOf(distributorAccountListParam.getState()))){
                      System.out.println("前段传入账号状态值："+distributorAccountListParam.getState()+"=========列表返回的状态值："+DistributorAccounts.get(i).getStatus());
                      continue;
                  }
                }
                DistributorAccountListDTO distributorAccountListDTO = new DistributorAccountListDTO();
                for (int n = 0; n < distributorAccounts.size(); n++) {
                    if (distributorAccounts.get(n).getDistributorAccountId().equals(DistributorAccounts.get(i).getUserId())) {
                        distributorAccountListDTO.setId(distributorAccounts.get(n).getId());
                        distributorAccountListDTO.setRemark(distributorAccounts.get(n).getRemark());
                        distributorAccountListDTO.setCreatorName(distributorBase.getCreatorName());
                        distributorAccountListDTO.setCreateTime(distributorBase.getCreateTime());
                        distributorAccountListDTO.setChannelType(channel.getChannelType());
                        distributorAccountListDTO.setSendMessage(distributorAccounts.get(n).getSendMessage());
                        distributorAccountListDTO.setIsdefaultPassword(distributorAccounts.get(n).getIsdefaultPassword());
                        distributorAccountListDTO.setDistributorAccountId(distributorAccounts.get(n).getDistributorAccountId());
                    }
                }
                distributorAccountListDTO.setDistributorBaseId(distribuBaseId);
                distributorAccountListDTO.setDistributorName(distributorBase.getDistributorName());
                distributorAccountListDTO.setAccountNumber(DistributorAccounts.get(i).getUsername());
                distributorAccountListDTO.setState(new Byte(DistributorAccounts.get(i).getStatus().toString()));
                distributorAccountListDTO.setPhone(DistributorAccounts.get(i).getMobile());
                distributorAccountListDTOs.add(distributorAccountListDTO);
            }
        }
        return distributorAccountListDTOs;
    }

    /**
     * 添加或修改分销商账号信息
     *
     * @param distributorAccountParam
     * @return
     */
    @Transactional
    public Boolean saveOrDistributionAccount(DistributorAccountParam distributorAccountParam) {

        if (distributorAccountParam.getIsdefaultPassword().equals(DistributorEnum.IS_DEFAULT_PASSWORD.getValue())) {
            distributorAccountParam.setAccountPassword(GeneConstant.DEFAULT_PASSWORD_DISTRIBUTOR_ACCOUNT);
        }

        //请求接口添加用户信息
        DistributionUserSaveParam distributionUserSaveParam = new DistributionUserSaveParam();
        if (distributorAccountParam.getDistributorAccountId() != null) {
            distributionUserSaveParam.setUserId(distributorAccountParam.getDistributorAccountId());
        }
        distributionUserSaveParam.setUsername(distributorAccountParam.getAccountNumber());
        distributionUserSaveParam.setStatus(Integer.valueOf(distributorAccountParam.getState()));
        distributionUserSaveParam.setPassword(distributorAccountParam.getAccountPassword());
        distributionUserSaveParam.setMobile(distributorAccountParam.getPhone());

        R<Long> distributorAccountR = systemUserFacade.saveDistributionUser(distributionUserSaveParam);
        System.out.println("===================掉账号服务返回信息"+distributorAccountR+"=====================");
        DistributorAccount distributorAccount = null;
        boolean flag = false;
        if (distributorAccountR.getCode() == 0) {
            //如果返回成功 添加本地记录
            distributorAccount = new DistributorAccount();
            distributorAccount.setDistributorBaseId(distributorAccountParam.getDistributorBaseId());
            distributorAccount.setDistributorAccountId(distributorAccountR.getData());
            distributorAccount.setRemark(distributorAccountParam.getRemark());
            distributorAccount.setSendMessage(distributorAccountParam.getSendMessage());
            distributorAccount.setIsdefaultPassword(distributorAccountParam.getIsdefaultPassword());
            distributorAccount.setPhone(distributorAccountParam.getPhone());
            flag = this.saveOrUpdate(distributorAccount);
        }
        return flag;
    }

    /**
     * 修改分销商账号状态
     *
     * @param DistributorAccountParam
     * @return
     */
    @Transactional
    public Boolean updateDistributionAccountState(DistributorAccountParam DistributorAccountParam) {
        DistributorAccount distributorAccount = distributorAccountMapper.selectById(DistributorAccountParam.getId());
        //请求接口修改用户状态接口
        R<Boolean> booleanR = systemUserFacade.saveStatus(distributorAccount.getDistributorAccountId(), Integer.valueOf(DistributorAccountParam.getState()));
        if (booleanR.getCode() == 0) {
            return true;
        }
        return false;
    }


    /**
     * 根据id获取分销商账号详细信息
     *
     * @param DistributionAccountId
     * @return
     */
    public DistributorAccountDTO getDistributionAccountById(Long DistributionAccountId) {

        if (null == DistributionAccountId) {
            PARAM_ERROR.assertFail();
        }
        DistributorAccountDTO distributorAccountDTO = new DistributorAccountDTO();
        DistributorAccount distributorAccount = distributorAccountMapper.selectById(DistributionAccountId);
        DistributorBase distributorBase = distributorBaseMapper.selectById(distributorAccount.getDistributorBaseId());
        Channel channel = channelMapper.selectById(distributorBase.getChannelId());
        //请求接口返回账号详细信息
        Long[] distributorAccountIds = new Long[1];
        distributorAccountIds[0] = distributorAccount.getDistributorAccountId();
        R<List<DistributionUserDTO>> distributorAccountR = systemUserFacade.info(distributorAccountIds);
        List<DistributionUserDTO> distributionUserDTOs = distributorAccountR.getData();
        if (distributionUserDTOs.size() > 0) {
            DistributionUserDTO distributionUserDTO = distributionUserDTOs.get(0);
            distributorAccountDTO.setAccountNumber(distributionUserDTO.getUsername());
            distributorAccountDTO.setPhone("15110016666");
            distributorAccountDTO.setState(new Byte(distributionUserDTO.getStatus().toString()));
        }
        BeanUtils.copyProperties(distributorBase, distributorAccountDTO,getNullPropertyNames(distributorBase));
        distributorAccountDTO.setChannelType(channel.getChannelType());
        distributorAccountDTO.setDistributorBaseId(distributorBase.getId());
        distributorAccountDTO.setRemark(distributorAccount.getRemark());
        distributorAccountDTO.setSendMessage(distributorAccount.getSendMessage());
        distributorAccountDTO.setIsdefaultPassword(distributorAccount.getIsdefaultPassword());
        distributorAccountDTO.setDistributorAccountId(distributorAccount.getDistributorAccountId());
        return distributorAccountDTO;
    }


    /**
     * 通过用户id（即分销商账号id）判断用户是否是分销商账号
     *
     * @param userId
     * @return
     */
    public R<Boolean> getUserIsDistributorByUserId(Long userId) {
        if (null == userId) {
            PARAM_ERROR.assertFail();
        }

        DistributorAccount distributorAccount = distributorAccountMapper.selectOne(new QueryWrapper<DistributorAccount>().eq("distributor_account_id", userId));
        if (distributorAccount != null) {
            return new R<>(true);
        }
        return new R<>(false);
    }


}
