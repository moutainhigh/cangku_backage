package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.authority.dto.request.DistributionUserSaveParam;
import cn.enn.wise.ssop.api.authority.facade.SystemUserFacade;
import cn.enn.wise.ssop.api.promotions.dto.request.ChannelRuleDetailParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorBaseListParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorBaseParam;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorRigisterParam;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.ssop.service.promotions.consts.ChannelEnum;
import cn.enn.wise.ssop.service.promotions.consts.DistributorEnum;
import cn.enn.wise.ssop.service.promotions.mapper.*;
import cn.enn.wise.ssop.service.promotions.model.*;
import cn.enn.wise.ssop.service.promotions.util.DateUtil;
import cn.enn.wise.ssop.service.promotions.util.GeneConstant;
import cn.enn.wise.ssop.service.promotions.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.enn.wise.ssop.api.promotions.consts.PromotionsExceptionAssert.*;
import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * @author 耿小洋
 * 分销商基础信息
 */
@Service("distributorService")
public class DistributorBaseService extends ServiceImpl<DistributorBaseMapper, DistributorBase> {


    @Autowired
    DistributorBaseMapper distributorBaseMapper;

    @Autowired
    DistributorAddMapper distributorAddMapper;

    @Autowired
    DistributorContactMapper distributorContactMapper;


    @Autowired
    DistributorBussinessMapper distributorBussinessMapper;

    @Autowired
    ChannelMapper channelMapper;


    @Autowired
    SystemUserFacade systemUserFacade;

    @Autowired
    ChannelRuleMapper channelRuleMapper;

    @Autowired
    DistributorBankMapper distributorBankMapper;

    @Autowired
    DistributorBusinessService distributorBusinessService;

    @Autowired
    DistributorAccountMapper distributorAccountMapper;

    @Autowired
    RedisUtil redisUtil;


    /**
     * 获取分销商id和名称列表
     *
     * @return
     */
    public List<DistributorBaseDTO> getDistributorIdAndNameList() {
        List<DistributorBase> distributorBases = this.list();
        ArrayList<DistributorBaseDTO> distributorBaseDTOs = new ArrayList<>();
        for (DistributorBase distributorBase : distributorBases) {
            DistributorBaseDTO distributorBaseDTO = new DistributorBaseDTO();
            BeanUtils.copyProperties(distributorBase, distributorBaseDTO, getNullPropertyNames(distributorBase));
            distributorBaseDTOs.add(distributorBaseDTO);
        }
        return distributorBaseDTOs;
    }

    /**
     * 分销商列表
     *
     * @param param
     * @return
     */
    public R<QueryData<List<DistributorListDTO>>> listByPage(DistributorBaseListParam param) {

        //分页查询分销商列表
        Page<DistributorListDTO> DistributorListDTOList = new Page<>(param.getPageNo(), param.getPageSize());
        DistributorListDTOList.setRecords(distributorBaseMapper.getDistributorPageInfo(param));
        QueryData<DistributorListDTO> result = new QueryData<>();
        result.setPageNo(param.getPageNo());
        result.setPageSize(param.getPageSize());
        //查询总数
        result.setTotalCount(distributorBaseMapper.getDistributorCount(param));
        List<DistributorListDTO> targetList = new ArrayList<>();
        DistributorListDTOList.getRecords().forEach(x -> {
            DistributorListDTO target = new DistributorListDTO();
            BeanUtils.copyProperties(x, target, getNullPropertyNames(x));
            targetList.add(target);
        });

        result.setRecords(targetList);

        return new R(result);
    }


    /**
     * 添加销商基础信息
     *
     * @param param
     * @return
     */
    @Transactional
    public R saveDistributionBase(DistributorBaseParam param) {
        //判断分销商名是否重复
        DistributorBase distribution = distributorBaseMapper.selectOne(new QueryWrapper<DistributorBase>().eq("distributor_name", param.getDistributorName()));
        if (distribution != null) {
            DISTRIBUTION_NAME_ISEXIST.assertFail(distribution.getDistributorName());
        }
        distribution = new DistributorBase();
        BeanUtils.copyProperties(param, distribution, getNullPropertyNames(param));

        //添加分销商
        distribution.setRegisterResource(ChannelEnum.REGISTER2.getValue().toString());
        this.save(distribution);

        distribution = distributorBaseMapper.selectOne(new QueryWrapper<DistributorBase>().eq("distributor_name", distribution.getDistributorName()));
        //修改分销商
        SimpleDateFormat fmt = new SimpleDateFormat("YYYY");
        String year = fmt.format(new Date());
        String serialNumber = String.format("%05d", distribution.getId());
        String code = "F" + year + serialNumber;
        distribution.setCode(code);
        this.updateById(distribution);

        DistributorBaseDTO distributorBaseDTO = new DistributorBaseDTO();
        BeanUtils.copyProperties(distribution, distributorBaseDTO, getNullPropertyNames(distribution));

        return new R<>(distributorBaseDTO);
    }

    /**
     * 修改分销商基础信息
     *
     * @param param
     * @return
     */
    public Boolean updateDistributionBase(DistributorBaseParam param) {
        //判断分销商名是否重复
        DistributorBase distribution = distributorBaseMapper.selectOne(new QueryWrapper<DistributorBase>().eq("distributor_name", param.getDistributorName()));
        if (distribution != null && !distribution.getId().equals(param.getId())) {
            DISTRIBUTION_NAME_ISEXIST.assertFail(distribution.getDistributorName());
        }

        distribution = new DistributorBase();
        BeanUtils.copyProperties(param, distribution, getNullPropertyNames(param));
        //如果是审核通过  添加入住入驻日期
        if (DistributorEnum.BASE_ADD_AUDIT_PASS.getValue().equals(param.getVerifyState())) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            distribution.setIntoTime(Timestamp.valueOf(fmt.format(new Date())));
        }
        DistributorBusiness distributorBusiness = distributorBussinessMapper.selectOne(new QueryWrapper<DistributorBusiness>().eq("distributor_base_id", param.getId()));
        if (distributorBusiness != null) {
            distributorBusinessService.updateDistiorBaseVerifyTypeByDistribuBase(param.getId());
        }
        //修改分销商
        return this.updateById(distribution);
    }

    /**
     * 根据分销商id获取基础信息
     *
     * @param
     * @return
     */
    public R<DistributorBaseDTO> getDistributionBaseByDistribuBaseId(Long distribuBaseId) {
        if (null == distribuBaseId) {
            PARAM_ERROR.assertFail();
        }
        DistributorBaseDTO distributorBaseDTO = distributorBaseMapper.getDistributorBaseBy(distribuBaseId);
        if (null == distributorBaseDTO) {
            DINFORMATION_NOT_EXIST.assertFail();
        }
        return new R<>(distributorBaseDTO);
    }


    /**
     * 根据分销商id验证该分销商是否可以提现（即入驻审批和补充审批都已通过和银行卡信息不能为空）
     *
     * @return
     */
    public DistributorIsCashWithdrawalDTO getDistrbutorIsCashWithdrawal(Long distribuBaseId) {
        DistributorIsCashWithdrawalDTO distributorIsCashWithdrawalDTO = new DistributorIsCashWithdrawalDTO();
        distributorIsCashWithdrawalDTO.setIsCashWithdrawal(1);
        if (null == distribuBaseId) {
            PARAM_ERROR.assertFail();
        }
        //查询分销商信息是否正常
        DistributorBase distributorBase = distributorBaseMapper.selectById(distribuBaseId);
        if (distributorBase == null || !DistributorEnum.BASE_ADD_AUDIT_PASS.getValue().equals(distributorBase.getVerifyState()) || DistributorEnum.INVALID.getValue().equals(distributorBase.getState())) {
            distributorIsCashWithdrawalDTO.setIsCashWithdrawal(2);
            return distributorIsCashWithdrawalDTO;
        }

        //查询分销商补充信息时候正常
        DistributorAdd distributorAdd = distributorAddMapper.selectOne(new QueryWrapper<DistributorAdd>().eq("distributor_base_id", distribuBaseId));
        if (distributorAdd == null || !DistributorEnum.BASE_ADD_AUDIT_PASS.getValue().equals(distributorAdd.getVerifyState())) {
            distributorIsCashWithdrawalDTO.setIsCashWithdrawal(2);
        }
        //查询分销商微信和银行卡信息时候正常
        DistributorBank distributorBank = distributorBankMapper.selectById(distributorAdd.getDistributorBankId());
        if (StringUtils.isBlank(distributorBank.getBankName()) || StringUtils.isBlank(distributorAdd.getBankCardImg()) || StringUtils.isBlank(distributorBank.getUserName()) || StringUtils.isBlank(distributorBank.getCardNumber()) || StringUtils.isBlank(distributorBank.getBankCode())) {
            if (StringUtils.isBlank(distributorAdd.getWechatNickname()) || StringUtils.isBlank(distributorAdd.getOpenId())) {
                distributorIsCashWithdrawalDTO.setIsCashWithdrawal(2);
            }
        }

        DistributorAddDTO distributorAddDTO = new DistributorAddDTO();
        BeanUtils.copyProperties(distributorAdd, distributorAddDTO, getNullPropertyNames(distributorAdd));
        distributorIsCashWithdrawalDTO.setDistributorAddDTO(distributorAddDTO);
        return distributorIsCashWithdrawalDTO;
    }


    /**
     * App端分销商注册
     *
     * @param param
     * @return
     */
    @Transactional
    public R<Boolean> registerDistribution(DistributorRigisterParam param) {

        //校验验证码
        Object redisCodeObject = redisUtil.get(param.getPhone());
        System.out.println("redis中通过手机号:" + param.getPhone() + "获取到的验证码:" + redisCodeObject);
        if (redisCodeObject == null) {
            return R.error("请先获取验证码");
        } else if (!param.getVerificationCode().equals(redisCodeObject.toString())) {
            return R.error("验证码错误");
        }

        //判断分销商名是否重复
        DistributorBase distribution = distributorBaseMapper.selectOne(new QueryWrapper<DistributorBase>().eq("distributor_name", param.getDistributorName()));
        if (distribution != null) {
            DISTRIBUTION_NAME_ISEXIST.assertFail(distribution.getDistributorName());
        }
        Channel channel = channelMapper.selectById(param.getChannelId());

        //调账户接口注册  成功后进行下面的操作
        int n = 0;
        //请求接口添加用户信息
        DistributionUserSaveParam distributionUserSaveParam = new DistributionUserSaveParam();
        distributionUserSaveParam.setUsername(param.getPhone());
        distributionUserSaveParam.setStatus(1);
        distributionUserSaveParam.setPassword(param.getAccountPassword());
        distributionUserSaveParam.setMobile(param.getPhone());

        R<Long> distributorAccountR = systemUserFacade.saveDistributionUser(distributionUserSaveParam);
        System.out.println("请求账号服务返回数据：" + distributorAccountR);
        if (distributorAccountR.getCode() == 0) {
            //添加分销商基础信息
            distribution = new DistributorBase();
            BeanUtils.copyProperties(param, distribution, getNullPropertyNames(param));
            if (channel.getAppRegister().contains(ChannelEnum.APP1.getValue().toString())) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                distribution.setIntoTime(Timestamp.valueOf(fmt.format(new Date())));
                distribution.setState(DistributorEnum.EFFECTIVE.getValue());
                distribution.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
            }
            distribution.setRegisterResource(ChannelEnum.REGISTER1.getValue().toString());
            distribution.setVerifyType(DistributorEnum.NOT_AVAILABLE_DISTRIBUTOR.getValue());
            n = distributorBaseMapper.insert(distribution);
            if (n > 0) {
//                DistributorBase distributorBase = distributorBaseMapper.selectOne(new QueryWrapper<DistributorBase>().eq("distributor_name", param.getDistributorName()));
                Long distributorBaseId = distribution.getId();
                //添加分销商账号信息
                DistributorAccount distributorAccount = new DistributorAccount();
                distributorAccount.setDistributorBaseId(distributorBaseId);
                distributorAccount.setDistributorAccountId(distributorAccountR.getData());
                distributorAccount.setSendMessage(GeneConstant.BYTE_1);//默认接受短信通知
                distributorAccount.setIsdefaultPassword(DistributorEnum.IS_NOT_DEFAULT_PASSWORD.getValue());
                distributorAccount.setPhone(param.getPhone());
                distributorAccountMapper.insert(distributorAccount);
                //添加分销商联系人信息
                DistributorContact distributorContact = new DistributorContact();
                distributorContact.setDistributorBaseId(distributorBaseId);
                distributorContact.setPhone(param.getPhone());
                distributorContact.setContactName(param.getDistributorName());
                distributorContactMapper.insert(distributorContact);

                //添加分销商补充信息
                DistributorAdd distributorAdd = new DistributorAdd();
                distributorAdd.setDistributorBaseId(distributorBaseId);
                List<DistributorRigisterParam.DistribytorAddInfo> distribytorAddInfoList = param.getDistribytorAddInfoList();
                if (distribytorAddInfoList != null) {
                    for (int i = 0; i < distribytorAddInfoList.size(); i++) {
                        DistributorRigisterParam.DistribytorAddInfo distribytorAddInfo = distribytorAddInfoList.get(i);
                        if (DistributorEnum.BUSINESS_LICENSE.getValue().equals(distribytorAddInfo.type)) {
                            distributorAdd.setBusinessLicense1(distribytorAddInfo.page1);
                            distributorAdd.setBusinessLicense2(distribytorAddInfo.page2);
                        } else if (DistributorEnum.ID_CARD.getValue().equals(distribytorAddInfo.type)) {
                            distributorAdd.setIdCardPage1(distribytorAddInfo.page1);
                            distributorAdd.setIdCardPage2(distribytorAddInfo.page2);
                        } else if (DistributorEnum.DRIVER_CARD.getValue().equals(distribytorAddInfo.type)) {
                            distributorAdd.setDriverCardPage1(distribytorAddInfo.page1);
                            distributorAdd.setDriverCardPage2(distribytorAddInfo.page2);
                        } else if (DistributorEnum.GUIDE_CARD.getValue().equals(distribytorAddInfo.type)) {
                            distributorAdd.setGuideCardPage1(distribytorAddInfo.page1);
                            distributorAdd.setGuideCardPage2(distribytorAddInfo.page2);
                        } else if (DistributorEnum.ELECTRONICCONTRACT.getValue().equals(distribytorAddInfo.type)) {
                            distributorAdd.setElectronicContract(distribytorAddInfo.page1);
                        } else if (DistributorEnum.CONTRACTSACNFILE.getValue().equals(distribytorAddInfo.type)) {
                            distributorAdd.setContractSacnFile(distribytorAddInfo.page1);
                        }
                    }
                }
                if (StringUtils.isNotBlank(channel.getAppRegister()) && channel.getAppRegister().contains(ChannelEnum.APP2.getValue().toString())) {
                    distributorAdd.setVerifyState(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue());
                }
                distributorAddMapper.insert(distributorAdd);

                //分销商业务信息
                DistributorBusiness distributorBusiness = new DistributorBusiness();
                distributorBusiness.setDistributorBaseId(distributorBaseId);
                distributorBusiness.setBusinessScope(param.getBusinessScope());
                distributorBusiness.setBusinessCounterpart(param.getBusinessCounterpart());
                n = distributorBussinessMapper.insert(distributorBusiness);
            } else {
                return R.error("分销商注册失败");
            }
        }
        if (n > 0) {
            return R.success(null);
        }
        return R.error("分销商注册失败");
    }

    /**
     * 根据条件查询分销政策
     *
     * @return
     */
    public ChannelRuleDetailDTO getDistributionChannelRuleByDistribuBaseId(ChannelRuleDetailParam channelRuleDetailParam) {
        ChannelRuleDetailDTO channelRuleDetailDTO = new ChannelRuleDetailDTO();
        //查询分销商信息
        DistributorBase distributorBase = distributorBaseMapper.selectById(channelRuleDetailParam.getDistribuBaseId());

        //查询渠道政策信息
        if (distributorBase != null) {
            ChannelRule channelRule = channelRuleMapper.selectOne(new QueryWrapper<ChannelRule>().eq("channel_id", distributorBase.getChannelId()).like("rule_day", DateUtil.getFormat(channelRuleDetailParam.getRuleDay())).eq("goods_id", channelRuleDetailParam.getGoodsId()));
            if (channelRule != null) {
                BeanUtils.copyProperties(channelRule, channelRuleDetailDTO, getNullPropertyNames(channelRule));
            }
        }

        return channelRuleDetailDTO;
    }


    /**
     * 获取分销商App首页上半部分信息
     *
     * @return
     */
    public DistributorAppFirstPageHeadDTO getDistributorAppFirstPageHead() {

        DistributorAppFirstPageHeadDTO distributorAppFirstPageHeadDTO = new DistributorAppFirstPageHeadDTO();
        distributorAppFirstPageHeadDTO.setTodayShareCount(68);
        distributorAppFirstPageHeadDTO.setTodayFollowCount(68);
        distributorAppFirstPageHeadDTO.setTodayDistributeSuccessCount(79);
        ArrayList<String> messageList = new ArrayList<>();
        messageList.add("最新公告消息测试数据1");
        messageList.add("最新公告消息测试数据2");
        messageList.add("最新公告消息测试数据3");
        distributorAppFirstPageHeadDTO.setMessageList(messageList);

        ArrayList<DistributorAppFirstPageHeadDTO.BusinessUnit> businessUnitList = new ArrayList<>();
        DistributorAppFirstPageHeadDTO.BusinessUnit businessUnit1 = distributorAppFirstPageHeadDTO.getBusinessUnit();
        businessUnit1.id = 1;
        businessUnit1.name = "酒店";
        DistributorAppFirstPageHeadDTO.BusinessUnit businessUnit2 = distributorAppFirstPageHeadDTO.getBusinessUnit();
        businessUnit2.id = 2;
        businessUnit2.name = "景点门票";
        DistributorAppFirstPageHeadDTO.BusinessUnit businessUnit3 = distributorAppFirstPageHeadDTO.getBusinessUnit();
        businessUnit3.id = 3;
        businessUnit3.name = "体验项目";
        businessUnitList.add(businessUnit1);
        businessUnitList.add(businessUnit2);
        businessUnitList.add(businessUnit3);
        distributorAppFirstPageHeadDTO.setBusinessUnitList(businessUnitList);

        return distributorAppFirstPageHeadDTO;
    }

    /**
     * 获取分销商App首页下半部分信息
     *
     * @return
     */
    public R<List<DistributorAppFirstPageBottomDTO>> getDistributorAppFirstPageBottom() {

        ArrayList<DistributorAppFirstPageBottomDTO> distributorAppFirstPageBottomDTOList = new ArrayList<>();

        DistributorAppFirstPageBottomDTO distributorAppFirstPageBottomDTO1 = new DistributorAppFirstPageBottomDTO();
        distributorAppFirstPageBottomDTO1.setGoodsId(1);
        distributorAppFirstPageBottomDTO1.setGoodsName("商品名称1");
        distributorAppFirstPageBottomDTO1.setScenicName("景区名称1");
        distributorAppFirstPageBottomDTO1.setGoodsImg("http://travel.enn.cn/group1/M00/01/9F/CiaAUl6anN2AQZMtAAB6PLNCjXw07.jpeg");
        distributorAppFirstPageBottomDTO1.setMaxDiscountPrice(150D);
        distributorAppFirstPageBottomDTO1.setMinDistributePrice(120D);
        distributorAppFirstPageBottomDTO1.setOriginalPrice(30D);

        DistributorAppFirstPageBottomDTO distributorAppFirstPageBottomDTO2 = new DistributorAppFirstPageBottomDTO();
        distributorAppFirstPageBottomDTO2.setGoodsId(2);
        distributorAppFirstPageBottomDTO2.setGoodsName("商品名称2");
        distributorAppFirstPageBottomDTO2.setScenicName("景区名称2");
        distributorAppFirstPageBottomDTO2.setGoodsImg("http://travel.enn.cn/group1/M00/01/9F/CiaAUl6anN2AQZMtAAB6PLNCjXw07.jpeg");
        distributorAppFirstPageBottomDTO2.setMaxDiscountPrice(150D);
        distributorAppFirstPageBottomDTO2.setMinDistributePrice(130D);
        distributorAppFirstPageBottomDTO2.setOriginalPrice(20D);

        DistributorAppFirstPageBottomDTO distributorAppFirstPageBottomDTO3 = new DistributorAppFirstPageBottomDTO();
        distributorAppFirstPageBottomDTO3.setGoodsId(3);
        distributorAppFirstPageBottomDTO3.setGoodsName("商品名称3");
        distributorAppFirstPageBottomDTO3.setScenicName("景区名称3");
        distributorAppFirstPageBottomDTO3.setGoodsImg("http://travel.enn.cn/group1/M00/01/9F/CiaAUl6anN2AQZMtAAB6PLNCjXw07.jpeg");
        distributorAppFirstPageBottomDTO3.setMaxDiscountPrice(180D);
        distributorAppFirstPageBottomDTO3.setMinDistributePrice(150D);
        distributorAppFirstPageBottomDTO3.setOriginalPrice(30D);

        distributorAppFirstPageBottomDTOList.add(distributorAppFirstPageBottomDTO1);
        distributorAppFirstPageBottomDTOList.add(distributorAppFirstPageBottomDTO2);
        distributorAppFirstPageBottomDTOList.add(distributorAppFirstPageBottomDTO3);
        return new R<>(distributorAppFirstPageBottomDTOList);
    }


}
