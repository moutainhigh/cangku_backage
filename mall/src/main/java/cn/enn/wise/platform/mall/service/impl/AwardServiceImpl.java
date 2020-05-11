package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.GoodsCouponBo;
import cn.enn.wise.platform.mall.bean.bo.GoodsCouponRuleBo;
import cn.enn.wise.platform.mall.bean.bo.UserOfCouponBo;
import cn.enn.wise.platform.mall.bean.bo.autotable.AwardRule;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.AwardMapper;
import cn.enn.wise.platform.mall.mapper.GoodsCouponMapper;
import cn.enn.wise.platform.mall.mapper.UserOfCouponMapper;
import cn.enn.wise.platform.mall.service.AwardService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.RemoteServiceUtil;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/3/31 17:03
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Service
@Slf4j
public class AwardServiceImpl extends ServiceImpl<AwardMapper, AwardRule> implements AwardService {

    @Autowired
    private AwardMapper awardMapper;

    @Autowired
    private UserOfCouponMapper userOfCouponMapper;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;

    @Autowired
    private GoodsCouponMapper goodsCouponMapper;


    @Override
    public UserDrawVo userDraw(User user, String openId) {
        UserDrawVo userDrawVo = new UserDrawVo();
        QueryWrapper<UserOfCouponBo> userOfCouponQueryWrapper = new QueryWrapper<>();
        userOfCouponQueryWrapper.lambda().eq(UserOfCouponBo::getOpenId, openId);
        userOfCouponQueryWrapper.lambda().gt(UserOfCouponBo::getCreateTime, "2020-04-16");
        List<UserOfCouponBo> userOfCouponBos = userOfCouponMapper.selectList(userOfCouponQueryWrapper);
        if (CollectionUtils.isNotEmpty(userOfCouponBos)) {
            userDrawVo.setWhetherDraw(2);
            return userDrawVo;
        }

        List<AwardRule> awardRules = awardMapper.selectList(null);
        if (CollectionUtils.isEmpty(awardRules)) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "请先初始化中奖概率！！");
        }

        List<AwardRule> collect1 = awardRules.stream().filter(awardRule -> awardRule.getAmount().equals(0)).collect(Collectors.toList());
        if (collect1.size() == awardRules.size()) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "活动到此结束,感谢您的参与！");
        }

        List<String> awardNameList = getCoupon(awardRules);
        log.info("优惠券名称" + awardNameList);

        List<UserDrawInfoVo> userDrawInfoVoList = new ArrayList<>();
        awardNameList.stream().forEach(awardName -> {
            UserDrawInfoVo userDrawInfoVo = new UserDrawInfoVo();
            List<AwardRule> collect = awardRules.stream().filter(awardRule -> awardRule.getAwardName().equals(awardName)).collect(Collectors.toList());
            List<UserOfCouponBo> userOfCouponBos1 = addUserOfCoupon(user, openId, collect);
            QueryWrapper<AwardRule> awardRuleQueryWrapper = new QueryWrapper<>();
            awardRuleQueryWrapper.lambda().eq(AwardRule::getAwardName, awardName);
            awardMapper.update(AwardRule.builder().amount(collect.get(0).getAmount() - 1).probability(collect.get(0).getProbability()).build(), awardRuleQueryWrapper);
            userDrawInfoVo.setAwardName(awardName);
            userDrawInfoVo.setUserOfCouponId(userOfCouponBos1.get(0).getId());
            userDrawInfoVo.setGoodsCouponId(userOfCouponBos1.get(0).getGoodsCouponId());
            userDrawInfoVoList.add(userDrawInfoVo);
        });

        userDrawVo.setWhetherDraw(1);
        QueryWrapper<GoodsCouponBo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(GoodsCouponBo::getId, userDrawInfoVoList.stream().map(userDrawInfoVo -> userDrawInfoVo.getGoodsCouponId()).collect(Collectors.toList()));
        List<GoodsCouponBo> goodsCouponBos = goodsCouponMapper.selectList(queryWrapper);
        userDrawInfoVoList.stream().forEach(userDrawInfoVo -> {
            goodsCouponBos.stream().forEach(goodsCouponBo -> {
                if (userDrawInfoVo.getGoodsCouponId().equals(goodsCouponBo.getId())) {
                    userDrawInfoVo.setCouponPrice(goodsCouponBo.getPrice());
                    userDrawInfoVo.setRemark(goodsCouponBo.getRemark());
                    userDrawInfoVo.setKind(goodsCouponBo.getKind());
                }
            });
        });
        userDrawVo.setUserDrawInfoVoList(userDrawInfoVoList);
        //优惠券扣除
        goodsCouponMapper.updateGetedSize(userDrawInfoVoList.stream().map(userDrawInfoVo -> userDrawInfoVo.getGoodsCouponId()).collect(Collectors.toList()));
        return userDrawVo;
    }

    @Override
    public List<DrawCouponVo> findDrawCouponList(User user, String openId) {
        List<DrawCouponVo> drawCouponVoList = userOfCouponMapper.findUserDrawCouponList(openId);
        if (CollectionUtils.isNotEmpty(drawCouponVoList)) {
            List<SysStaff> listBusinesss = remoteServiceUtil.getListBusinesss();
            drawCouponVoList.stream().forEach(drawCouponVo -> {
                List<SysStaff> collect = listBusinesss.stream().filter(sysStaff -> sysStaff.getBusinessType() != null && sysStaff.getBusinessType().equals(drawCouponVo.getKind())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    List<String> list = new ArrayList<>();
                    collect.stream().forEach(c -> {
                        list.add(c.getName());
                    });
                    drawCouponVo.setUseScope(StringUtils.join(list, ","));
                }
            });
            return drawCouponVoList;
        }
        return drawCouponVoList;
    }

    private List<String> getCoupon(List<AwardRule> awardRuleList) {
        Integer ticketAmount = awardRuleList.stream().filter(awardRule -> awardRule.getKind() == 1).collect(Collectors.summingInt(AwardRule::getAmount));
        Integer hotelAmount = awardRuleList.stream().filter(awardRule -> awardRule.getKind() == 2).collect(Collectors.summingInt(AwardRule::getAmount));
        Integer foodAmount = awardRuleList.stream().filter(awardRule -> awardRule.getKind() == 3).collect(Collectors.summingInt(AwardRule::getAmount));
        Map<Integer, List<AwardRule>> awardRuleMap = awardRuleList.stream().collect(Collectors.groupingBy(AwardRule::getKind));
        List<String> awardNameList = new ArrayList<>();
        awardRuleMap.entrySet().stream().forEach(awardRuleMaps -> {
            if (awardRuleMaps.getKey().equals(1)) {
                if (ticketAmount != 0) {
                    String awardName = lottery(awardRuleMaps.getValue(), ticketAmount).getAwardName();
                    awardNameList.add(awardName);
                }
            } else if (awardRuleMaps.getKey().equals(2)) {
                if (hotelAmount != 0) {
                    String awardName = lottery(awardRuleMaps.getValue(), hotelAmount).getAwardName();
                    awardNameList.add(awardName);
                }
            } else if (awardRuleMaps.getKey().equals(3)) {
                if (foodAmount != 0) {
                    String awardName = lottery(awardRuleMaps.getValue(), foodAmount).getAwardName();
                    awardNameList.add(awardName);
                }
            }
        });
        return awardNameList;
    }

    @Override
    public DrawCouponVo judgeCouponUsable(User user, String openId, Integer goodsId, Integer couponId) {
        DrawCouponVo drawCouponVo = new DrawCouponVo();
        try {
            List<DrawCouponVo> drawCouponList = findDrawCouponList(user, openId);
            drawCouponList.stream().filter(drawCouponVo1 -> drawCouponVo1.getId().equals(couponId)).collect(Collectors.toList());
            drawCouponVo.setCouponPrice(drawCouponList.get(0).getCouponPrice());
            drawCouponVo.setUseScope(drawCouponList.get(0).getUseScope());
            drawCouponVo.setRemark(drawCouponList.get(0).getRemark());
            drawCouponVo.setId(drawCouponList.get(0).getId());
            drawCouponVo.setEndTime(drawCouponList.get(0).getEndTime());
            drawCouponVo.setCouponSts(drawCouponList.get(0).getCouponSts());
            drawCouponVo.setKind(drawCouponList.get(0).getKind());
            drawCouponVo.setCodeUrl(drawCouponList.get(0).getCodeUrl());
            drawCouponVo.setInfoId(drawCouponList.get(0).getInfoId());
            DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());
            Date now = simpleDateFormat.parse(format);
            Date endTime = drawCouponList.get(0).getEndTime();
            int result = now.compareTo(endTime);
            if (1 == result) {
                drawCouponVo.setUseSts(2);
                return drawCouponVo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        QueryWrapper<UserOfCouponBo> userOfCouponQueryWrapper = new QueryWrapper<>();
        userOfCouponQueryWrapper.lambda().eq(UserOfCouponBo::getOpenId, openId);
        userOfCouponQueryWrapper.lambda().eq(UserOfCouponBo::getGoodsCouponId, couponId);
        List<UserOfCouponBo> userOfCouponBos = userOfCouponMapper.selectList(userOfCouponQueryWrapper);
        if (userOfCouponBos.get(0).getStatus() == 2) {
            drawCouponVo.setUseSts(2);
            return drawCouponVo;
        } else {
            List<Map<String, Object>> mapList = awardMapper.judgeCouponUsable(goodsId, couponId);
            if (CollectionUtils.isEmpty(mapList)) {
                drawCouponVo.setUseSts(2);
                return drawCouponVo;
            }
            return drawCouponVo;
        }
    }


    private List<UserOfCouponBo> addUserOfCoupon(User user, String openId, List<AwardRule> awardRuleList) {
        log.info("概率集合:" + awardRuleList);
        GoodsCouponRuleBo goodsCouponRuleBo = awardMapper.judgePromotionUsable(awardRuleList.get(0).getCouponId());

        log.info("活动信息:" + goodsCouponRuleBo);

        UserOfCouponBo userOfCouponBo = new UserOfCouponBo();
        userOfCouponBo.setUserId(user.getId());
        userOfCouponBo.setOpenId(openId);
        userOfCouponBo.setStatus(GeneConstant.BYTE_1);
        userOfCouponBo.setGoodsCouponId(awardRuleList.get(0).getCouponId().longValue());
        userOfCouponBo.setCouponResource(1);
        userOfCouponBo.setValidityTime(awardRuleList.get(0).getValidityTime());
        userOfCouponBo.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        if (goodsCouponRuleBo != null) {
            userOfCouponBo.setPromotionId(goodsCouponRuleBo.getPromotionId());
        }
        userOfCouponMapper.insert(userOfCouponBo);
        return new ArrayList() {{
            add(userOfCouponBo);
        }};
    }


    public static AwardRule lottery(List<AwardRule> awards, int amount) {
        //总的概率区间
        float totalPro = 0f;
        //遍历每个奖品，设置概率区间，总的概率区间为每个概率区间的总和
        List<Float> proSection = new ArrayList<Float>();
        proSection.add(0f);
        //遍历每个奖品，设置概率区间，总的概率区间为每个概率区间的总和
        for (AwardRule award : awards) {
            //每个概率区间为奖品概率乘以1000（把三位小数转换为整）再乘以剩余奖品数量
            totalPro += award.getProbability() * 10 * amount;
            proSection.add(totalPro);
        }
        //获取总的概率区间中的随机数
        Random random = new Random();
        System.out.println(totalPro);
        float randomPro = (float) random.nextInt((int) totalPro);
        //判断取到的随机数在哪个奖品的概率区间中
        for (int i = 0, size = proSection.size(); i < size; i++) {
            if (randomPro >= proSection.get(i)
                    && randomPro < proSection.get(i + 1)) {
                return awards.get(i);
            }
        }
        return null;
    }

}
