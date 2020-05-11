package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.ChannelSellPriceParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ChannelSettlementPriceParam;
import cn.enn.wise.ssop.api.promotions.dto.request.ChannelsellPriceaAndSettlementPriceParam;
import cn.enn.wise.ssop.api.promotions.dto.response.ChannelPriceDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ChannelSellPriceDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ChannelSettlementPriceDTO;
import cn.enn.wise.ssop.service.promotions.consts.ChannelEnum;
import cn.enn.wise.ssop.service.promotions.consts.ChannelRuleEnum;
import cn.enn.wise.ssop.service.promotions.consts.DistributorEnum;
import cn.enn.wise.ssop.service.promotions.mapper.*;
import cn.enn.wise.ssop.service.promotions.model.*;
import cn.enn.wise.ssop.service.promotions.util.DateUtil;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.Date;
import java.util.List;

import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/*
 * @author 耿小洋
 * 渠道销售价格相关业务
 */

@Service("ChannelPriceService")
public class ChannelPriceService {


    @Autowired
    DistributorAddMapper distributorAddMapper;

    @Autowired
    DistributorBaseMapper distributorBaseMapper;


    @Autowired
    ChannelMapper channelMapper;

    @Autowired
    ChannelRuleMapper channelRuleMapper;

    @Autowired
    DistributorBussinessMapper distributorBussinessMapper;

    @Autowired
    DistributorAccountMapper distributorAccountMapper;

    /**
     * 根据商品详细信息和分销商id计算商品的渠道价格
     *
     * @param channelSellPriceParam
     * @return
     */
    public ChannelSellPriceDTO getChannelPrice(ChannelSellPriceParam channelSellPriceParam) {
        ChannelSellPriceDTO channelSellPriceDTO = new ChannelSellPriceDTO();
        ChannelsellPriceaAndSettlementPriceParam channelsellPriceaAndSettlementPriceParam = new ChannelsellPriceaAndSettlementPriceParam();
        BeanUtils.copyProperties(channelSellPriceParam, channelsellPriceaAndSettlementPriceParam,getNullPropertyNames(channelSellPriceParam));
        channelsellPriceaAndSettlementPriceParam.setSellPriceOrSettlementPrice(1);
        channelsellPriceaAndSettlementPriceParam.setRuleDay(new Date());
        R resR = getChannelPriceOrSettlementPrice(channelsellPriceaAndSettlementPriceParam);
        if (resR.getCode() == 1) {
            BeanUtils.copyProperties(channelSellPriceParam, channelSellPriceDTO,getNullPropertyNames(channelSellPriceParam));
            channelSellPriceDTO.setIsChannelSellPrice(resR.getCode());
            channelSellPriceDTO.setMessage("success");
            channelSellPriceDTO.setChannelPrice(Integer.valueOf(resR.getData().toString()));
        } else {
            channelSellPriceDTO.setIsChannelSellPrice(resR.getCode());
            channelSellPriceDTO.setMessage(resR.getMessage());
        }
        return channelSellPriceDTO;
    }


    /**
     * 根据商品详细信息和分销商id计算商品的结算价格
     *
     * @param channelSettlementPriceParam
     * @return
     */
    public ChannelSettlementPriceDTO getSettlementPrice(ChannelSettlementPriceParam channelSettlementPriceParam) {
        ChannelSettlementPriceDTO channelSettlementPriceDTO = new ChannelSettlementPriceDTO();
        //设置请求参数
        ChannelsellPriceaAndSettlementPriceParam channelsellPriceaAndSettlementPriceParam = new ChannelsellPriceaAndSettlementPriceParam();
        BeanUtils.copyProperties(channelSettlementPriceParam, channelsellPriceaAndSettlementPriceParam,getNullPropertyNames(channelSettlementPriceParam));
        channelsellPriceaAndSettlementPriceParam.setSellPriceOrSettlementPrice(2);
        //计算价格
        R resR = getChannelPriceOrSettlementPrice(channelsellPriceaAndSettlementPriceParam);
        if (resR.getCode() == 1) {
            BeanUtils.copyProperties(channelSettlementPriceParam, channelSettlementPriceDTO,getNullPropertyNames(channelSettlementPriceParam));
            channelSettlementPriceDTO.setIsChannelSellPrice(resR.getCode());
            channelSettlementPriceDTO.setMessage("success");
            channelSettlementPriceDTO.setSettlementPrice(Integer.valueOf(resR.getData().toString()));
        } else {
            channelSettlementPriceDTO.setIsChannelSellPrice(resR.getCode());
            channelSettlementPriceDTO.setMessage(resR.getMessage());
        }
        return channelSettlementPriceDTO;
    }


    /**
     * 根据商品详细信息和分销商id计算商品的渠道价格或者结算价
     *
     * @param channelSellPriceParam
     * @return
     */
    public R getChannelPriceOrSettlementPrice(ChannelsellPriceaAndSettlementPriceParam channelSellPriceParam) {
        R<Object> resR = new R<>();

        DistributorAccount distributorAccount = distributorAccountMapper.selectOne(new QueryWrapper<DistributorAccount>().eq("phone", channelSellPriceParam.getPhone()));
        if(distributorAccount==null){
            resR.setCode(2);
            resR.setMessage("分销商账号异常");
            return resR;
        }
        Long distributorBaseId = distributorAccount.getDistributorBaseId();
        //判断分销商状态是否正常（身份审核和补充信息审核是否正常）
        DistributorBase distributorBase = distributorBaseMapper.selectById(distributorBaseId);

        if (distributorBase == null || distributorBase.getState().equals(DistributorEnum.INVALID.getValue()) || !distributorBase.getVerifyState().equals(DistributorEnum.BASE_ADD_AUDIT_PASS.getValue())) {
            resR.setCode(2);
            resR.setMessage("分销商账号异常");
            return resR;
        }
        //根据分销商id获取渠道和渠道政策信息（检查分销商政策是否正常）
        Channel channel = channelMapper.selectById(distributorBase.getChannelId());
        if (channel == null || channel.getState().equals(ChannelEnum.INVALID.getValue())) {
            resR.setCode(2);
            resR.setMessage("渠道账号异常");
            return resR;
        }

        //判断若有指定商品 判断当前商品是否是该渠道的指定商品saleGoodsType
        ChannelRule channelRule = channelRuleMapper.selectOne(new QueryWrapper<ChannelRule>().eq("channel_id", channel.getId()).like("rule_day", DateUtil.getFormat(channelSellPriceParam.getRuleDay())).eq("goods_id", channelSellPriceParam.getGoodsId()));
        if ("2".equals(channel.getSaleGoodsType())) {
            //根据渠道id获取指定商品集合
            if (channelRule == null || "".equals(channelRule.getGoodsId() + "")) {
                resR.setCode(2);
                resR.setMessage("该商品不是渠道的指定商品");
                return resR;
            }

        }

        //查询渠道对应的政策进行计算
        int sellPrice = 0;
        if (channelRule != null) {
            String baseRule = channelRule.getBaseRule();

            JSONArray baseRules = JSONArray.parseArray(baseRule);
            DistributorBusiness distributorBusiness = distributorBussinessMapper.selectOne(new QueryWrapper<DistributorBusiness>().eq("distributor_base_id", distributorBaseId));
            int price = 0;
            sellPrice = 0;

            for (int i = 0; i < baseRules.size(); i++) {
                JSONObject baseRuleJson = baseRules.getJSONObject(i);
                if (distributorBusiness.getLevel().toString().equals(baseRuleJson.getString("channelLevel"))) {
                    //计算
                    JSONObject settlementPriceObj = null;
                    if (channelSellPriceParam.getSellPriceOrSettlementPrice() == 1) {
                        settlementPriceObj = baseRuleJson.getJSONObject("sellPriceObj");
                    } else {
                        settlementPriceObj = baseRuleJson.getJSONObject("settlementPriceObj");
                    }
                    String basePrice = settlementPriceObj.getString("basePrice");
                    String methods = settlementPriceObj.getString("methods");
                    String markupType = settlementPriceObj.getString("markupType");
                    Integer priceValue = settlementPriceObj.getInteger("priceValue");

                    if (basePrice.equals(ChannelRuleEnum.basePrice1.getValue().toString())) {
                        price = channelSellPriceParam.getCostPrice();
                    } else {
                        price = channelSellPriceParam.getSellPrice();

                    }
                    if (methods.equals(ChannelRuleEnum.computeMethod1.getValue().toString())) {
                        if (markupType.equals(ChannelRuleEnum.sign1.getValue().toString())) {
                            sellPrice = price + priceValue;
                        } else {
                            sellPrice = price - priceValue;
                        }
                    } else {
                        if (markupType.equals(ChannelRuleEnum.sign1.getValue().toString())) {
                            sellPrice = price + price * priceValue / 100;
                        } else {
                            sellPrice = price - price * priceValue / 100;
                        }
                    }
                }

            }
            if (sellPrice == 0) {
                resR.setCode(2);
                resR.setMessage("渠道政策异常");
                return resR;
            }
        } else {
            resR.setCode(2);
            resR.setMessage("渠道政策异常");
            return resR;
        }
        resR.setCode(1);
        resR.setData(sellPrice);
        return resR;
    }

}
