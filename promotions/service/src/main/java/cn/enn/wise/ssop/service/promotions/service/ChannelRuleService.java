package cn.enn.wise.ssop.service.promotions.service;


import cn.enn.wise.ssop.api.goods.dto.response.GoodsExtendAllDTO;
import cn.enn.wise.ssop.api.goods.dto.response.GoodsGroupPriceDTO;
import cn.enn.wise.ssop.api.goods.facade.GoodsExtendFacade;
import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.ChannelDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ChannelPriceDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.ChannelRuleDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.GoodsExtendDTO;
import cn.enn.wise.ssop.service.promotions.consts.ChannelEnum;
import cn.enn.wise.ssop.service.promotions.consts.ChannelRuleEnum;
import cn.enn.wise.ssop.service.promotions.consts.DistributorEnum;
import cn.enn.wise.ssop.service.promotions.controller.applets.GroupOrderAppletsController;
import cn.enn.wise.ssop.service.promotions.mapper.ChannelMapper;
import cn.enn.wise.ssop.service.promotions.mapper.ChannelRuleMapper;
import cn.enn.wise.ssop.service.promotions.model.Channel;
import cn.enn.wise.ssop.service.promotions.model.ChannelRule;
import cn.enn.wise.ssop.service.promotions.model.DistributorBase;
import cn.enn.wise.ssop.service.promotions.model.DistributorBusiness;
import cn.enn.wise.ssop.service.promotions.util.DateUtil;
import cn.enn.wise.ssop.service.promotions.util.GeneConstant;
import cn.enn.wise.ssop.service.promotions.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;


/**
 * @author jiabaiye
 * 渠道管理
 */
@Service("channelRuleService")
public class ChannelRuleService extends ServiceImpl<ChannelRuleMapper, ChannelRule> {
    private static final Logger logger = LoggerFactory.getLogger(GroupOrderAppletsController.class);

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ChannelRuleMapper channelRuleMapper;

    @Autowired
    GoodsExtendFacade goodsExtendFacade;

    @Autowired
    ChannelMapper channelMapper;


    public Boolean addChannelRule(ChannelRuleParam channelRuleParam) {
        if (channelRuleParam.getSaleGoodsType() == GeneConstant.BYTE_1) {
            //获取商品id，初始化在channelRuleParam.setGoodsId()
        }
        channelMapper.update(null, new UpdateWrapper<Channel>().set("sale_goods_type", channelRuleParam.getSaleGoodsType()).set("ishave_rule", 1).set("goods_number", channelRuleParam.getGoodsIds().split(",").length).eq("id", channelRuleParam.getChannelId()));
        String date = DateUtil.getNowStringDate("yyyy-MM-dd");
        boolean flag = true;
        List<ChannelRule> channelRuleList = channelRuleMapper.selectList(new QueryWrapper<ChannelRule>().eq("channel_id", channelRuleParam.getChannelId()).gt("rule_day", date).eq("isdelete", 1));

        String goodsIds = channelRuleParam.getGoodsIds();
        //ruleday 加判断为空数组
        for (String day : channelRuleParam.getRuleDay()) {
            for (String goodsId : goodsIds.split(",")) {
                ChannelRule channelRuleBo = new ChannelRule();
                channelRuleBo.setGoodsId(Long.parseLong(goodsId));
                channelRuleBo.setRuleDay(DateUtil.parse(day));
                channelRuleBo.setChannelId(channelRuleParam.getChannelId());
                channelRuleBo.setAwardRule(channelRuleParam.getAwardRule());
                channelRuleBo.setBaseRule(channelRuleParam.getBaseRule());
                channelRuleBo.setRebateFormat(channelRuleParam.getRebateFormat());
                channelRuleBo.setRebateUnit(channelRuleParam.getRebateUnit());
                channelRuleBo.setIsdistribuorLevel(channelRuleParam.getIsdistribuorLevel());
                channelRuleBo.setMultipleServer(channelRuleParam.getMultipleServer());
                channelRuleBo.setAwardDistribuorLevel(channelRuleParam.getAwardDistribuorLevel());
                for (ChannelRule channelRule : channelRuleList) {
                    if (DateUtil.getDayStartTime(day).equals(channelRule.getRuleDay()) && goodsId.equals(channelRule.getGoodsId().toString())) {
                        channelRuleBo.setId(channelRule.getId());
                        break;
                    }
                }
                this.saveOrUpdate(channelRuleBo);
            }
        }
        channelRuleList = channelRuleMapper.selectList(new QueryWrapper<ChannelRule>().select("goods_id").eq("channel_id", channelRuleParam.getChannelId()).gt("rule_day", date).eq("isdelete", 1).groupBy("goods_id"));
        channelMapper.update(null,new UpdateWrapper<Channel>().set("goods_number",channelRuleList.size()).eq("id",channelRuleParam.getChannelId()));
        //List<ChannelRule> channelRuleLists = channelRuleMapper.selectList(new QueryWrapper<ChannelRule>().select("channel_id","goods_id").eq("channel_id",channelRuleParam.getChannelId()).gt("rule_day",date).groupBy("channel_id","goods_id"));
        return true;
    }


    public List<ChannelRuleDTO> getChannelRule(ChannelRuleQueryParam channelRuleParam) {
        List<ChannelRule> channelRuleLists = channelRuleMapper.selectList(new QueryWrapper<ChannelRule>().eq("channel_id", channelRuleParam.getChannelId()).eq("rule_day", DateUtil.getDayStartTime(channelRuleParam.getRuleDay())).eq("goods_id", channelRuleParam.getGoodsId()).eq("isdelete", 1));
        List<ChannelRuleDTO> channelRuleDTOS = new ArrayList<>();
        channelRuleLists.forEach(c -> {
            ChannelRuleDTO channelRuleDTO = new ChannelRuleDTO();
            BeanUtils.copyProperties(c, channelRuleDTO,getNullPropertyNames(c));
            channelRuleDTOS.add(channelRuleDTO);
        });
        return channelRuleDTOS;
    }


    //查询渠道商品
    public R<List<GoodsExtendDTO>> getChannelGoods(ChannelGoodsQueryParam channelGoodsQueryParam) {
        //从产品服务查询商品列表
        logger.info("=============渠道查询商品接口开始===============");

        R<List<GoodsExtendAllDTO>> goodsExtendAllDTOListR = goodsExtendFacade.getGoodsExtendListGroup("", channelGoodsQueryParam.getGoodsName(), channelGoodsQueryParam.getProjectId(),GeneConstant.BYTE_1);
        logger.info("=============渠道查询商品接口结束 返回结果" + goodsExtendAllDTOListR + "===============");
        List<GoodsExtendDTO> result = null;
        //查询渠道对应的商品id集合
        String date = DateUtil.getNowStringDate("yyyy/MM/dd");
        List<ChannelRule> channelRuleLists = channelRuleMapper.selectList(new QueryWrapper<ChannelRule>().select("channel_id", "goods_id").eq("channel_id", channelGoodsQueryParam.getChannelId()).gt("rule_day", DateUtil.getDayStartTime(date)));
        result = new ArrayList<>();
        List<GoodsExtendAllDTO> goodsExtendAllDTOList = goodsExtendAllDTOListR.getData();
        for (GoodsExtendAllDTO goodsExtendAllDTO : goodsExtendAllDTOList) {
            GoodsExtendDTO goodsExtendDTO = new GoodsExtendDTO();
            BeanUtils.copyProperties(goodsExtendAllDTO, goodsExtendDTO,getNullPropertyNames(goodsExtendAllDTO));
            boolean isFalse = false;
            for (ChannelRule channelRule : channelRuleLists) {
                //如果产品那边返回的goodsId和渠道的goods相同 至为选中状态
                if (goodsExtendDTO.getGoodsExtendId().equals(channelRule.getGoodsId())) {
                    isFalse = true;
                    break;
                }
            }
            if (isFalse) {
                goodsExtendDTO.setState(1);
            } else {
                goodsExtendDTO.setState(2);
            }
            result.add(goodsExtendDTO);
        }


        return new R<>(result);
    }

    public Boolean deleteChannelRule(ChannelGoodsDeleteParam channelGoodsDeleteParam) {
        Channel channel = channelMapper.selectOne(new QueryWrapper<Channel>().eq("id", channelGoodsDeleteParam.getChannelId()));
        channel.setGoodsNumber(channel.getGoodsNumber() - 1);
        channelMapper.updateById(channel);
        channelRuleMapper.update(null, new UpdateWrapper<ChannelRule>().set("isdelete", 2).eq("channel_id", channelGoodsDeleteParam.getChannelId()).eq("goods_id", channelGoodsDeleteParam.getGoodsId()));

        return true;
    }

    public R getChannelPrice(ChannelPriceQueryParam channelPriceQueryParam) {
        String date = DateUtil.getNowStringDate("yyyy/MM/dd");

        //查商品信息根据id和景区id查redis，如果没有查商品 接口，保存redis
        List<ChannelRule> channelRuleLists = channelRuleMapper.selectList(new QueryWrapper<ChannelRule>().eq("channel_id", channelPriceQueryParam.getChannelId()).eq("isdelete", 1).gt("rule_day", DateUtil.getDayStartTime(date)).eq("goods_id", channelPriceQueryParam.getGoodsId()));

        List<ChannelPriceDTO> channelPriceDTOS = new ArrayList<>();
        if (!channelPriceQueryParam.getGoodsId().contains(",")) {
            R<GoodsGroupPriceDTO> goodsExtendPrice = goodsExtendFacade.getGoodsExtendPrice(Long.valueOf(channelPriceQueryParam.getGoodsId()));
            GoodsGroupPriceDTO goodsGroupPriceDTO = goodsExtendPrice.getData();
            for (ChannelRule channelRule : channelRuleLists) {
                R salePrice = getChannelPriceOrSettlementPrice(channelRule, 1, goodsGroupPriceDTO.getCostPrice(), goodsGroupPriceDTO.getSalePrice());//销售价
                R settlemnetPrice = getChannelPriceOrSettlementPrice(channelRule, 2, goodsGroupPriceDTO.getCostPrice(), goodsGroupPriceDTO.getSalePrice());//计算结算价
                ChannelPriceDTO channelPriceDTO = new ChannelPriceDTO();
                if (salePrice.getCode() == 1) {
                    HashMap<String, Integer> data = (HashMap<String, Integer>) salePrice.getData();
                    channelPriceDTO.setSalePrice("售价：" + data.get("price"));
                }
                HashMap<String, Integer> data = null;
                if (settlemnetPrice.getCode() == 1) {
                    data = (HashMap<String, Integer>) settlemnetPrice.getData();
                    channelPriceDTO.setSettlementPrice("结算价：" + data.get("price"));
                }
                if (ChannelRuleEnum.rebateFormat1.getValue().equals(channelRule.getRebateFormat())) {
                    channelPriceDTO.setRebate("返利：" + data.get("rebate")+"%");
                }else {
                    channelPriceDTO.setRebate("返利：" + data.get("rebate"));
                }
                channelPriceDTO.setRuleDay(channelRule.getRuleDay());
                channelPriceDTOS.add(channelPriceDTO);
            }
        }

        return new R<>(channelPriceDTOS);
    }


    public ChannelDTO toFormat(ChannelDTO channelDTO) {
        channelDTO.setStateLabel(ChannelEnum.getName("stateLabel", channelDTO.getState().toString()));
        channelDTO.setChannelTypeLabel(ChannelEnum.getName("channelTypeLabel", channelDTO.getState().toString()));
        if (channelDTO.getTag() != null && !"".equals(channelDTO.getTag())) {
            channelDTO.setTag(ChannelEnum.getName("tag", channelDTO.getTag()));
        }
        if (channelDTO.getRegisterResource() != null && !"".equals(channelDTO.getRegisterResource())) {
            channelDTO.setRegisterResource(ChannelEnum.getName("registerResource", channelDTO.getRegisterResource()));
        }
        if (channelDTO.getAppRegister() != null && !"".equals(channelDTO.getAppRegister())) {
            channelDTO.setAppRegister(ChannelEnum.getName("appRegister", channelDTO.getAppRegister()));
        }
        if (channelDTO.getOperation() != null && !"".equals(channelDTO.getOperation())) {
            channelDTO.setOperation(ChannelEnum.getName("operation", channelDTO.getOperation()));
        }
        if (channelDTO.getDocking() != null && !"".equals(channelDTO.getDocking())) {
            channelDTO.setDocking(ChannelEnum.getName("docking", channelDTO.getDocking()));
        }
        if (channelDTO.getSettlement() != null && !"".equals(channelDTO.getSettlement())) {
            channelDTO.setSettlement(ChannelEnum.getName("settlement", channelDTO.getSettlement()));
        }
        return channelDTO;
    }


    /**
     * 根据商品详细信息和分销商id计算商品的渠道价格或者结算价
     *
     * @param channelRule 渠道政策 计算渠道销售价格或者结算价格 1 销售价格 2 结算价格 costPriceParam 成本价 sellPriceParam销售价
     * @return
     */
    public R getChannelPriceOrSettlementPrice(ChannelRule channelRule, int sellPriceOrSettlementPrice, int costPriceParam, int sellPriceParam) {
        R<Object> resR = new R<>();
        //查询渠道对应的政策进行计算
        int sellPrice = 0;
        Integer rebate = 0;
        if (channelRule != null) {
            String baseRule = channelRule.getBaseRule();

            JSONArray baseRules = JSONArray.parseArray(baseRule);
            int price = 0;
            JSONObject baseRuleJson = baseRules.getJSONObject(0);
            rebate = Integer.valueOf(baseRuleJson.getString("rebate"));
            //计算
            JSONObject settlementPriceObj = null;
            if (sellPriceOrSettlementPrice == 1) {
                settlementPriceObj = baseRuleJson.getJSONObject("sellPriceObj");
            } else {
                settlementPriceObj = baseRuleJson.getJSONObject("settlementPriceObj");
            }
            String basePrice = settlementPriceObj.getString("basePrice");
            String methods = settlementPriceObj.getString("methods");
            String markupType = settlementPriceObj.getString("markupType");
            Integer priceValue = settlementPriceObj.getInteger("priceValue");

            if (basePrice.equals(ChannelRuleEnum.basePrice1.getValue().toString())) {
                price = costPriceParam;
            } else {
                price = sellPriceParam;

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
        HashMap<String, Integer> resMap = new HashMap<>();
        resMap.put("price", sellPrice);
        resMap.put("rebate", rebate);
        resR.setData(resMap);
        return resR;
    }


}
