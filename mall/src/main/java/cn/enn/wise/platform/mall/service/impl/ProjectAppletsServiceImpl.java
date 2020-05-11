package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.Goods;
import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.bo.group.GroupPromotionGoodsBo;
import cn.enn.wise.platform.mall.bean.param.GoodsProjectParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.ProjectAppletsService;
import cn.enn.wise.platform.mall.service.WzdGoodsAppletsService;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 小程序项目展示业务逻辑
 *
 * @author baijie
 * @date 2019-11-04
 */
@Service
@DS("slave_1")
public class ProjectAppletsServiceImpl implements ProjectAppletsService {

    /**
     * 获取地点
     * @param companyId
     * @return
     */
    static  String getPlace(String companyId){
        Map<String,String> placeMap = new HashMap<>(8);
        placeMap.put("7","大峡谷");
        placeMap.put("10","巴松措");
        placeMap.put("11","涠洲岛");
        placeMap.put("13","楠溪江");


        return placeMap.get(companyId);
    }

    @Resource
    private GoodsProjectMapper goodsProjectMapper;

    @Value("${companyId}")
    private String companyId;

    @Autowired
    private WzdGoodsAppletsService wzdGoodsAppletsService;

    @Resource
    private GoodsProjectPeriodMapper goodsProjectPeriodMapper;

    @Resource
    private GroupPromotionGoodsMapper groupPromotionGoodsMapper;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private WzdGoodsAppletsMapper wzdGoodsAppletsMapper;

    @Override
    public ProjectVo getProjectInfoById(GoodsProjectParam goodsProjectParam) {
        Set<Long> projectIds = new HashSet<>();

        String img = null;
        String desc = null;
        String goodsName = null;
        ProjectVo.ProjectVoBuilder builder = ProjectVo.builder();
        //查询项目基本信息
        Long projectId = goodsProjectParam.getProjectId();
        projectIds.add(projectId);
        GoodsProject goodsProject = goodsProjectMapper.selectById(projectId);

        if(goodsProject == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"不存在该项目");
        }
        Long goodsId = goodsProjectParam.getGoodsId();

        builder
                .projectId(projectId)
                .projectName(goodsProject.getName())
                .operationPlace(getPlace(companyId))
                .operationSpan(goodsProject.getOperationTime())
                .goodsId(goodsId)
                .hotelLine(GeneUtil.getHotelLine(Long.parseLong(companyId)));
        // 判断项目入口
        wzdGoodsAppletsService.initEntrance(goodsProjectParam);
        //查询项目的最低价格
        Map<String, Object> minPriceMap = goodsProjectPeriodMapper.selectMinPriceByProjectId(projectId);
        BigDecimal price = new BigDecimal(minPriceMap.get("price").toString());

        //查询商品分销价
        Goods goods = goodsMapper.selectById(goodsId);

        if(goodsProjectParam.getIsDistribute()){
            //分销入口
            builder.basePrice(price)
                   .activityType(3)
                   .distributePrice(new BigDecimal(minPriceMap.get("distributionPrice").toString()));

            if(goods != null){
                builder.goodsType(goods.getGoodsType());
                List<Long> list = goodsProjectParam.getList();
                if(CollectionUtils.isNotEmpty(list) && goods != null){
                    //该商品是可分销的商品
                    if(list.contains(goods.getId())){
                        builder.distributePrice(goods.getRetailPrice());
                    }else {
                        // 按照正常价售卖
                        builder.activityType(1);
                    }
                }
            }
        }else {
            boolean flag = true;
            // 带商品Id访问项目详情，目前就只有拼团和套装
            if(goods != null){
                if(goodsProjectParam.getPromotionId() != null){

                    // 查询是否是拼团活动
                    GroupPromotionGoodsBo promotionGoodInfo = groupPromotionGoodsMapper.getPromotionGoodInfo(goodsId,goodsProjectParam.getPromotionId());
                    if(promotionGoodInfo != null){
                        flag = false;
                        //查询拼团最低价
                        builder.groupPrice(groupPromotionGoodsMapper.getPromotionGoodByGoodsId(goodsProjectParam.getPromotionId(),goodsId).getGroupPrice())
                                .basePrice(goods.getBasePrice())
                                .activityType(2)
                                .is_available(promotionGoodInfo.getPromotionStatus() == 2? 1:2)
                                .promotionId(promotionGoodInfo.getGroupPromotionId());
                    }

                }


                //查询商品是否是套装
                PackageGoodVO goodsInfoById = wzdGoodsAppletsMapper.getPackageGoodsInfoById(goodsId);
                //套装商品详情
                if(goodsInfoById != null){
                    flag = false;
                    List<Map<Integer,Object>> dateList = new ArrayList<>();
                    for (int i = 1 ;i<=3;i++){
                        HashMap<Integer, Object> map = new HashMap<>();
                        map.put(i, MallUtil.getDateByType(i));
                        dateList.add(map);
                    }
                    List<PackageGoodsItemVO> itemVOList = goodsInfoById.getItemVOList();
                    List<Long> goodsIdList = new ArrayList<>();
                    builder.activityType(5)
                            .saleDate(dateList)
                            .isByPeriodOperation(goodsInfoById.getIsByPeriodOperation())
                            .salePrice(goodsInfoById.getBasePrice())
                            .itemVOList(filterItemVO(itemVOList));
                    //非空
                    if(CollectionUtils.isNotEmpty(itemVOList)){
                        itemVOList.parallelStream().forEach( item ->  {
                            projectIds.add(item.getPackageProjectId());
                            goodsIdList.add(item.getId());
                        });
                    }

                    //查询套装商品明细基础价格
                    Map<String, Object> sumBasePrice = goodsMapper.getSumBasePrice(goodsIdList);
                    builder.basePrice(new BigDecimal(sumBasePrice.get("basePrice").toString()));
                }


            }else {
                //商品Id为null 项目最低价
                builder.activityType(1);
                builder.basePrice(price);
            }

            // 有商品Id 并且该商品不参加拼团,也不是套餐，就展示时段的最低价
            if(flag && goods != null){
                builder.goodsType(goods.getGoodsType());
                builder.activityType(1);
                // 查询时段最低价
                MinPriceVo minPriceByGoodsId = wzdGoodsAppletsMapper.getMinPriceByGoodsId(goodsId);
                builder.basePrice(minPriceByGoodsId.getMinPrice());
            }

        }



        //请求项目其他基本信息 TODO 临时注释
//        List<ProjectInfoVo> projectInfoVoByIds = remoteServiceUtil.getProjectInfoVoByIds(GeneUtil.listToString(projectIds));
        List<ProjectInfoVo> projectInfoVoByIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(projectInfoVoByIds)){
            ProjectVo build = builder.build();
            List<PackageGoodsItemVO> itemVOList = build.getItemVOList();
            projectInfoVoByIds.parallelStream().forEach( projectInfoVo -> {
                Long infoVoProjectId = Long.parseLong(projectInfoVo.getCode());
                //判断是否是项目的基本信息
                if(projectId.equals(infoVoProjectId)){
                    builder   //图集
                            .imageUrls(projectInfoVo.getImageList())
                            //使用须知
                            .usageNotice(projectInfoVo.getInstructions())
                            //项目简介
                            .introduction(projectInfoVo.getDescription())
                            //图文介绍
                            .graphicIntroduction(projectInfoVo.getContent());
                }



                if(CollectionUtils.isNotEmpty(itemVOList)){
                itemVOList.parallelStream().forEach( item -> {
                    //封装套装商品中的项目信息
                    if(infoVoProjectId.equals(item.getPackageProjectId())){
                        item.setHeadImg(projectInfoVo.getHeadImage());
                    }
                });
                }

            });

        }

        if(goods!= null){
            builder
                    .imageUrls(GeneUtil.stringToList(goods.getImg()))
                    .usageNotice(goods.getRules())
                    .introduction(goods.getSynopsis())
                    .graphicIntroduction(goods.getDescription())
                    .projectName(goods.getGoodsName());
        }

        // 查询当前最早可预约时间
        Map<String, String> appointment = goodsProjectPeriodMapper.getProjectAppointment(projectId, DateUtil.getNowStringDate("yyyy-MM-dd"), DateUtil.getNowTime());
        if(appointment != null){
            builder.appointment(appointment.get("start_time"));
        }
        ProjectVo projectVo = builder.
                availableDates(MallUtil.getDateByType(1))
                .build();
        return projectVo;
    }

    /**
     * 过滤项目相同的商品，项目相同，多个商品只需要1个
     * @param itemVOList
     * @return
     */
    private List<PackageGoodsItemVO> filterItemVO(List<PackageGoodsItemVO> itemVOList) {
        List<PackageGoodsItemVO> list= new ArrayList<>();
        Map<Long,PackageGoodsItemVO> map = new HashMap<>(8);
        if(CollectionUtils.isNotEmpty(itemVOList)){
            itemVOList.stream().forEach(itemVo -> map.put(itemVo.getPackageProjectId(),itemVo));
        }
        Iterator<Map.Entry<Long, PackageGoodsItemVO>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            list.add(iterator.next().getValue());
        }
        return list;
    }

}
