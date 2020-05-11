package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.Goods;
import cn.enn.wise.platform.mall.bean.bo.group.GroupPromotionGoodsBo;
import cn.enn.wise.platform.mall.bean.param.GoodsProjectParam;
import cn.enn.wise.platform.mall.bean.param.GoodsReqParam;
import cn.enn.wise.platform.mall.bean.param.TicketInfoQueryParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.*;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.GeneUtil;
import cn.enn.wise.platform.mall.util.RemoteServiceUtil;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import cn.enn.wise.platform.mall.util.thirdparty.LalyoubaShipHttpApiUtil;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.ShiftInfo;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * @author baijie
 * @date 2019-07-31
 */
@Service
public class WzdGoodsAppletsServiceImpl implements WzdGoodsAppletsService {

    private Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);
    @Value("${spring.profiles.active}")
    private String profiles;

    @Resource
    private WzdGoodsAppletsMapper wzdGoodsAppletsMapper;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;


    @Resource
    private GroupPromotionMapper groupPromotionMapper;

    @Resource
    private GroupPromotionGoodsMapper groupPromotionGoodsMapper;

    @Autowired
    private ProjectOperationStatusService projectOperationStatusService;

    @Resource
    private GroupOrderMapper groupOrderMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsProjectMapper goodsProjectMapper;

    @Resource
    private GoodsProjectPeriodMapper goodsProjectPeriodMapper;

    @Resource
    private TagCategoryService tagCategoryService;

    @Resource
    private TagAppletsMapper tagAppletsMapper;

    @Value("${companyId}")
    private String companyId;

    @Autowired
    private GoodsCouponService goodsCouponService;
    @Override
    public Map<String, Object> getGoodsList(GoodsReqParam goodsReqQry) {

        //查询分时段运营商品数据
        List<GoodsApiResVO> goodsList = wzdGoodsAppletsMapper.getGoodsList(goodsReqQry);
        //查询不分时段运营商品数据

        List<GoodsApiResVO> listIsByPeriodOperation = wzdGoodsAppletsMapper.getGoodsListIsByPeriodOperation(goodsReqQry.getProjectId(),null);

        goodsList.addAll(listIsByPeriodOperation);

        List<GoodsApiResVO> packageTicketList = new ArrayList<>();
        List<GoodsApiResVO> singleTicketList = new ArrayList<>();

        for (GoodsApiResVO goodsApiResVO : goodsList) {

            if (goodsApiResVO.getIsPackage() == 0) {

                singleTicketList.add(goodsApiResVO);
                logger.info("单人票数量:" + singleTicketList.size());

            } else {

                packageTicketList.add(goodsApiResVO);
                logger.info("多人票数量:" + packageTicketList.size());
            }
        }

        //多种单票
        List<GoodsApiResVO> singleList = new ArrayList<>();

        List<GoodsApiResVO> packageList = new ArrayList<>();

        List<String> goodsIdByPhone = remoteServiceUtil.getGoodsIdByPhone(goodsReqQry.getPhone(), goodsReqQry.getCompanyId());


        for (GoodsApiResVO goodsApiResVO : singleTicketList) {
            singleList.add(completionSingleTicketInfo(goodsApiResVO));
            setIsDistributeGood(goodsIdByPhone, goodsApiResVO);
        }
        for (GoodsApiResVO goodsApiResVO : packageTicketList) {
            packageList.add(completionSingleTicketInfo(goodsApiResVO));
            setIsDistributeGood(goodsIdByPhone, goodsApiResVO);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("singleTicketList", singleList);
        resultMap.put("packageTicketList", packageList);
        return resultMap;

    }

    private void setIsDistributeGood(List<String> goodsIdByPhone, GoodsApiResVO goodsApiResVO) {
        if (goodsIdByPhone != null) {
            for (String s : goodsIdByPhone) {
                if (String.valueOf(goodsApiResVO.getId()).equals(s)) {
                    goodsApiResVO.setIsDistributeGoods(1);
                }
            }
        }else {
            goodsApiResVO.setIsDistributeGoods(2);
        }
    }

    @Override
    public TicketResVo getGoodInfoById(GoodsReqParam goodsReqParam) {

        GoodsApiResVO goodsById;
          //商品分时段运营

            goodsById = wzdGoodsAppletsMapper.getGoodsById(goodsReqParam);

            if(goodsById == null){
            //商品不分时段运营
            goodsById = wzdGoodsAppletsMapper.getGoodsInfoById(goodsReqParam.getGoodsId());
            }



        logger.info("===获取到票信息==" + JSONObject.toJSONString(goodsById));

        if (goodsById == null) {

            logger.info("===要预定的商品不存在===");
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "要预定的商品不存在");

        }
        goodsById.setIsDistributeGoods(2);
        List<String> goodsIdByPhone = remoteServiceUtil.getGoodsIdByPhone(goodsReqParam.getPhone(), goodsReqParam.getCompanyId());

        setIsDistributeGood(goodsIdByPhone, goodsById);

        GoodsApiExtendResVo goodsApiExtendResVo = goodsById.getGoodsApiExtendResVoList().get(0);

        if (goodsApiExtendResVo == null) {

            logger.info("===该票没有运营时段信息===");
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "该时段没有票信息");
        }


        TicketResVo ticketResVo = new TicketResVo();
        //二销产品
        //门票类型
        ticketResVo.setGoodsId(goodsById.getId());
        //判断是单人票还是多人票
        if (goodsById.getIsPackage() == 1) {
            //多人票
            ticketResVo.setAmount(goodsById.getMaxNum());
        } else {
            //        默认数量为1
            ticketResVo.setAmount(GeneConstant.INT_1);
        }

        ticketResVo.setGoodsName(goodsById.getGoodsName());

        String timespan = goodsReqParam.getIsByPeriodOperation() == 1 ? goodsApiExtendResVo.getTimespan() : "";
        //时间
        ticketResVo.setExperienceTime(goodsReqParam.getOperationDate() + " " + timespan);
        //时段价格
        ticketResVo.setSiglePrice(goodsApiExtendResVo.getSalePrice().toString());
        ticketResVo.setPeriodId(goodsReqParam.getPeriodId());
        ticketResVo.setTimeFrame(goodsReqParam.getTimeFrame());
        ticketResVo.setGoodsType(goodsById.getGoodsType());
        //设置分销价格
        BigDecimal retailPrice = goodsById.getRetailPrice();
        ticketResVo.setRetailPrice(retailPrice);
        ticketResVo.setProjectId(goodsReqParam.getProjectId());
        ticketResVo.setIsDistributeGoods(goodsById.getIsDistributeGoods());
        ticketResVo.setProjectName(goodsById.getProjectName());


        ProjectInfoVo info = remoteServiceUtil.getProjectInfoById(goodsReqParam.getProjectId());
        if(info != null){
            ticketResVo.setHeadImg(info.getHeadImage());
        }

        logger.info("===预定门票结束===");

        return ticketResVo;
    }


    @Override
    public TicketResVo getGroupInfoById(GoodsReqParam goodsReqParam) {
        TicketResVo.TicketResVoBuilder builder = TicketResVo.builder();
        Long goodsId = goodsReqParam.getGoodsId();
        Goods goods = goodsMapper.selectById(goodsId);
        Assert.notNull(goods,"商品查询为空");
        Long projectId = goodsReqParam.getProjectId();
        builder.goodsId(goodsId)
                .goodsName(goods.getGoodsName())
                .projectId(projectId)
                .experienceTime(goodsReqParam.getOperationDate())
                .siglePrice(GeneUtil.formatBigDecimal(goods.getBasePrice()).toString());
        //判断是单人票还是多人票
        if (goods.getIsPackage() == 1) {
            //多人票
            builder.amount(goods.getMaxNum());
        } else {
            //        默认数量为1
            builder.amount(GeneConstant.INT_1);
        }
        Long promotionId = goodsReqParam.getPromotionId();

            GroupPromotionGoodsBo promotionGoodByGoodsId = groupPromotionGoodsMapper.getPromotionGoodByGoodsId(promotionId, goodsId);
            //设置拼团价格
           Assert.notNull(promotionGoodByGoodsId,"拼团商品查询失败");
                builder.groupPrice(GeneUtil.formatBigDecimal(promotionGoodByGoodsId.getGroupPrice()))
                       .groupLimit(promotionGoodByGoodsId.getGroupLimit());




        ProjectInfoVo info = remoteServiceUtil.getProjectInfoById(projectId);
        if(info != null){
            builder.headImg(info.getHeadImage());
        }


        return builder.build();
    }

    @Override
    public Goods getGoodsInfoById(Long goodsId) {
        return goodsMapper.selectById(goodsId);
    }

    /**
     * @param goodsApiResVO
     * @return
     */
    public GoodsApiResVO completionSingleTicketInfo(GoodsApiResVO goodsApiResVO) {

        ProjectOperationStatusResponseAppVO appVO = projectOperationStatusService.getProjectOperationStatusResponseAppVOByProjectId(Long.valueOf(goodsApiResVO.getProjectId()), 1);


        if (goodsApiResVO == null) {

            logger.info("===时段信息为null===");
            return new GoodsApiResVO();

        }
        List<GoodsApiExtendResVo> extendResVoList = goodsApiResVO.getGoodsApiExtendResVoList();

        if (CollectionUtils.isEmpty(extendResVoList)) {
            return null;
        }

        //单品按照价格分组
        Map<BigDecimal, List<GoodsApiExtendResVo>> collect = extendResVoList.stream().collect(Collectors.groupingBy(g -> g.getSalePrice()));

        List<GoodsApiSkuVo> resultList = new ArrayList<>();

        Set<Map.Entry<BigDecimal, List<GoodsApiExtendResVo>>> entries = collect.entrySet();
        //遍历所有的价格
        for (Map.Entry<BigDecimal, List<GoodsApiExtendResVo>> entry : entries) {
            BigDecimal key = entry.getKey();
            BigDecimal tips = new BigDecimal(0);
            List<GoodsApiExtendResVo> value = entry.getValue();
            String timeLable = "";
            //关联sku信息
            for (GoodsApiExtendResVo goodsApiExtendResVo : value) {
                goodsApiExtendResVo.setId(goodsApiResVO.getId());
                timeLable = goodsApiExtendResVo.getTimeLabel();
                goodsApiExtendResVo.setRetailPrice(goodsApiResVO.getRetailPrice());
                if (appVO != null) {
                    goodsApiExtendResVo.setIsShowApplet(appVO.getIsShowApplet());
                    goodsApiExtendResVo.setOperationStatusType(appVO.getOperationStatusType());
                    goodsApiExtendResVo.setLabel(appVO.getLabel());
                } else {
                    goodsApiExtendResVo.setIsShowApplet(2);
                }

            }
            //按照价格分档信息
            GoodsApiSkuVo goodsApiSkuVo = new GoodsApiSkuVo();
            goodsApiSkuVo.setPriceRange(key);
            goodsApiSkuVo.setTimeLabel(timeLable);
            goodsApiSkuVo.setTips(GeneUtil.formatBigDecimal(tips));
            goodsApiSkuVo.setSkuInfo(value);
            resultList.add(goodsApiSkuVo);
        }
//        //排序取前三
        resultList.sort((a, b) -> a.getPriceRange().compareTo(b.getPriceRange()));
        goodsApiResVO.setSkuInfo(resultList);
        goodsApiResVO.setGoodsApiExtendResVoList(null);
        return goodsApiResVO;
    }


    @Override
    public List<GoodsProjectVo> listByTag(GoodsProjectParam goodsProjectParam) {

        // setup1 tag在商品上的属性封装
        //初始化项目入口
        initEntrance(goodsProjectParam);
        // 一个商品不能既参与分销，又参与拼团
        // 如果参与分销和拼团，则从分销入口进入 按照分销活动来，从正常入口来，按照拼团活动来
        // 根据tag获取商品和项目的基本信息 List
        List<Long> tagIdList = getTagIdList(goodsProjectParam.getTagId(), goodsProjectParam.getCategoryId());

        List<GoodsProjectVo> projectVoList = wzdGoodsAppletsMapper.getByTag(tagIdList);

        if (!CollectionUtils.isEmpty(projectVoList)) {

        // 商品Id集合
        List<Long> goodsIdList = new ArrayList<>();

        Set<Long> projectIdList = new HashSet<>();
        projectVoList.stream().forEach(x -> {
            //商品简介
            x.setProjectSummary(x.getSynopsis());
            x.setImageUrl(StringUtils.isEmpty(x.getImg())?"":x.getImg().split(",")[0]);
            goodsIdList.add(x.getGoodsId());
            projectIdList.add(x.getProjectId());
        });

        //查询商品的tag标签
       List<TagBean> operationTag = tagAppletsMapper.getGoodsOperationTag(goodsIdList);
       if(!CollectionUtils.isEmpty(operationTag)){
           projectVoList.parallelStream().forEach(x -> {
              operationTag.parallelStream().forEach( y -> {
                  if(x.getGoodsId().equals(y.getGoodsId())){
                      x.setTagName(y.getTagName());
                  }
              });
           });
       }


        // 查询参与拼团活动的商品
        List<GroupPromotionGoodsBo> promotionGoods = groupPromotionGoodsMapper.getPromotionGoods();
        //参与拼团活动商品的Id集合
        List<Long> promotionGoodsIdList = new ArrayList<>();
        promotionGoods.stream().forEach(promotionGood -> promotionGoodsIdList.add(promotionGood.getGoodsId()));

        // 正常入口
        if (!goodsProjectParam.getIsDistribute()) {

            //映射商品id和信息
            Map<Long, GroupPromotionGoodsBo> collect = promotionGoods.stream().collect(Collectors.toMap(GroupPromotionGoodsBo::getGoodsId, self -> self));

            //求参加活动并且在打tag的商品集合
            List intersection = GeneUtil.intersection(goodsIdList, promotionGoodsIdList);
            // 构建拼团商品对象
            projectVoList.stream().forEach(x -> {
                Long goodsId = x.getGoodsId();
                if (intersection.contains(goodsId)) {
                    x.setActivityType(2);
                    x.setActivityPrice(collect.get(goodsId).getGroupPrice());
                    x.setPromotionId(collect.get(goodsId).getGroupPromotionId());

                }

                //套餐订单属性封装
                if(x.getIsSuit() != null && x.getIsSuit() == 1){
                    x.setActivityType(5);
                }
            });

        } else {
            // 分销入口
            // 查询参与分销活动的商品Id，
            List intersection = GeneUtil.intersection(goodsIdList, goodsProjectParam.getList());
//            Iterator<GoodsProjectVo> iterator = projectVoList.iterator();
//            while (iterator.hasNext()) {
//                GoodsProjectVo next = iterator.next();
//                if (promotionGoodsIdList.contains(next.getGoodsId())) {
//                    iterator.remove();
//                }
//            }
            projectVoList.stream().forEach(x -> {
                if (intersection.contains(x.getGoodsId())) {
                    x.setActivityType(3);
                }
            });

            //参与分销的，无法显示关于拼团的任何商品，项目
        }

        // 查询参与拼团活动的商品 与 list求交集  build 拼团商品对象
        // 查询参与分销活动的商品 与 list求交集  build 分销商品对象
        // list 与拼团活动和分销活动的差集 就是普通商品 build 普通商品对象 看是否分时段运营，分时段运营的就查询时段的最低价
        }
        if(projectVoList == null){
            projectVoList = new ArrayList<>();
        }



        List<GoodsProjectVo> projectList = projectListByTag(goodsProjectParam);
        projectVoList.addAll(projectList == null ? new ArrayList<>() : projectList);
//        // setup2 tag在项目上的属性封装
//
//        //remoteService // 调用石斋接口，返回图片参数
//        Set idSet = new HashSet();
//        projectVoList.stream().forEach(x -> idSet.add(x.getProjectId()));
//        List<GoodsProjectVo> goodsProjectVos = buildImageInfo(projectVoList, GeneUtil.listToString(idSet));
        return projectVoList;
    }


    /**
     * 初始化项目入口
     *
     * @param goodsProjectParam
     */
    @Override
    public void initEntrance(GoodsProjectParam goodsProjectParam) {

        String distributePhone = goodsProjectParam.getPhone();

        //手机号不为空，分销入口
        if (StringUtils.isNotEmpty(distributePhone)) {
            List<String> list = remoteServiceUtil.getGoodsIdByPhone(distributePhone, goodsProjectParam.getCompanyId());
            if(CollectionUtils.isEmpty(list)){
                goodsProjectParam.setIsDistribute(false);
            }else {
                goodsProjectParam.setIsDistribute(true);
            }
            List<Long> idList = new ArrayList<>();
            list.forEach(x -> idList.add(Long.parseLong(x)));
            goodsProjectParam.setList(idList);

        }

    }

    @Override
    public List<GoodsProjectVo> projectListByTag(GoodsProjectParam goodsProjectParam) {


        List<Long> tagIdList = getTagIdList(goodsProjectParam.getTagId(), goodsProjectParam.getCategoryId());
        // 根据标签查询项目列表
        List<GoodsProjectVo>  goodsProjects = goodsProjectMapper.listByTag(tagIdList);

        if (CollectionUtils.isEmpty(goodsProjects)) {
            return null;
        }
        //项目Id集合
        List<Long> projectIdList = new ArrayList<>();
        goodsProjects.stream().forEach(x -> projectIdList.add(x.getId()));

        //查询项目的tag标签
        List<TagBean> operationTag = tagAppletsMapper.getProjectOperationTag(projectIdList);
        if(!CollectionUtils.isEmpty(operationTag)){
           goodsProjects.parallelStream().forEach( x-> {
               operationTag.parallelStream().forEach( y-> {
                   if(x.getId().equals(y.getProjectId())){
                       x.setTagName(y.getTagName());
                   }
               });
           }); }

        //查询项目Id和最低价
        List<MinPriceVo> priceVos = goodsProjectPeriodMapper.selectMinPriceByProjectIdList(projectIdList);
        //项目Id和价格对应
        Map<Long, MinPriceVo> collect = priceVos.stream().collect(Collectors.toMap(MinPriceVo::getProjectId, min -> min));

//        //查询拼团活动的项目集合
//        List<GroupPromotionGoodsBo> promotionGoods = groupPromotionGoodsMapper.getPromotionGoods();
//        //拼团活动的Id集合
//        List<Long> promotionIdList = new ArrayList<>();
//        promotionGoods.stream().forEach(x -> promotionIdList.add(x.getProjectId()));

//        Map<Long, GroupPromotionGoodsBo> goodsBoMap = promotionGoods.stream().collect(Collectors.toMap(GroupPromotionGoodsBo::getProjectId, self -> self));

        initEntrance(goodsProjectParam);

        List<Long> removeIdList = new ArrayList<>();
        //判断项目入口
        if (goodsProjectParam.getIsDistribute()) {
            //是分销的入口
//            //不展示拼团
//            projectIdList.removeAll(promotionIdList);
            //可分销商品的id
            List<Long> distributeGoodsIdList = goodsProjectParam.getList();

            if (CollectionUtils.isEmpty(projectIdList)) {
                return new ArrayList<>();
            }
            // 有可分销的商品
            // 根据商品Id获取项目Id
            List<Goods> goods = CollectionUtils.isEmpty(distributeGoodsIdList) ? new ArrayList<>() : goodsMapper.selectBatchIds(distributeGoodsIdList);

            List<Long> distributeProjectId = new ArrayList<>();
            goods.stream().forEach(x -> distributeProjectId.add(x.getProjectId()));

            // 求交集 交集中的项目Id 的最低价取 分销的最低价 ，其他的取普通价的最低价即可
      //      List intersection = GeneUtil.intersection(projectIdList, distributeProjectId);
            //构建数据
            projectIdList.stream().forEach(x -> {
                goodsProjects.stream().forEach(y -> {
                    y.setProjectId(y.getId());
                    if (y.getId().equals(x)) {
                        MinPriceVo minPriceVo = collect.get(x);
                        if(minPriceVo == null){
                            removeIdList.add(x);
                            return;
                        }
                        y.setType(1);
                        y.setActivityType(1);
//                        if (intersection.contains(x)) {
//
//                            y.setMinPrice(minPriceVo.getMinDistributePrice());
//                        } else {
//
//                            y.setMinPrice(minPriceVo.getMinPrice());
//                        }
                        y.setMinPrice(minPriceVo.getMinDistributePrice());

                    }

                });

            });



        } else {
            // 普通入口
            // 如果是拼团项目则需要对比拼团价和普通价格，取最低价，其他的直接取最低价
           // List intersection = GeneUtil.intersection(projectIdList, promotionIdList);

            goodsProjects.stream().forEach(x -> {
                x.setType(1);
                Long projectId = x.getId();
                x.setProjectId(projectId);
                MinPriceVo minPriceVo = collect.get(x.getId());
                if(minPriceVo == null){
                    removeIdList.add(x.getId());
                    return;
                }

//                if (intersection.contains(projectId)) {
//                    x.setActivityType(2);
//                    x.setMinPrice(goodsBoMap.get(projectId).getGroupPrice().compareTo(minPriceVo.getMinPrice()) < 1 ? goodsBoMap.get(projectId).getGroupPrice() : minPriceVo.getMinPrice());
//                    x.setPromotionId(goodsBoMap.get(projectId).getGroupPromotionId());
//                } else {
                    x.setActivityType(1);
                    x.setMinPrice(minPriceVo.getMinPrice());
//                }

            });

        }

        //项目下所有商品都下架了，该项目不展示
        Iterator<GoodsProjectVo> iterator = goodsProjects.iterator();
        while (iterator.hasNext()){
            GoodsProjectVo next = iterator.next();
            removeIdList.stream().forEach( id ->{
                if(id.equals(next.getId())){
                    iterator.remove();
                }

            });
        }

        List<GoodsProjectVo>  projectVoList = buildImageInfo(goodsProjects, GeneUtil.listToString(projectIdList));

        return projectVoList;
    }

    /**
     * 封装项目图片信息
     * @param infoList
     * @param ids
     * @return
     */
    private  List<GoodsProjectVo> buildImageInfo(List<GoodsProjectVo> infoList,String ids){


        List<ProjectInfoVo> projectInfoVoByIds = remoteServiceUtil.getProjectInfoVoByIds(ids);

        infoList.stream().forEach(x -> {
            x.setTagNameList(GeneUtil.stringToList(x.getTagName()));
            if (!CollectionUtils.isEmpty(projectInfoVoByIds)) {
                projectInfoVoByIds.stream().forEach(y -> {
                    if (x.getProjectId().equals(Long.parseLong(y.getCode()))) {
                        x.setImageUrl(y.getHeadImage());
                        x.setProjectSummary(y.getDescription());
                    }

                });
            }
            //套餐商品
            if(x.getActivityType() == 5){
                String img = x.getImg();
                x.setImageUrl(StringUtils.isNotEmpty(img)?img.split(",")[0]:"");
            }
        });

        return infoList;
    }

    /**
     * 获取标签集合
     *tagId 和categoryId 不能同时为空 也不能同时存在
     * @param tagId 业务标签Id
     * @param categoryId 业务分类ID
     * @return 对应标签下的子标签
     */
    private List<Long> getTagIdList(String tagId,String categoryId) {

        List<Long> list = new ArrayList<>();

        //tagId不为空时返回tag集合
        if (StringUtils.isNotEmpty(tagId)) {

            List<String> stringList = GeneUtil.stringToList(tagId);
            stringList.parallelStream().forEach(x -> list.add(Long.parseLong(x)));
        }
        //分类Id不为空时按照分类Id查询分类下的所有子标签，返回子标签集合
        if(StringUtils.isNotEmpty(categoryId)){
            TagAppletsVo tagVoById = tagCategoryService.getTagVoById(categoryId);
            List<TagBean> tag = tagVoById.getTag();
            if(!CollectionUtils.isEmpty(tag)){
                tag.parallelStream().forEach(x -> {
                    List<String> stringList = GeneUtil.stringToList(x.getTagId());
                    stringList.parallelStream().forEach(y ->list.add(Long.parseLong(y)));
                });
            }

        }
        return list;
    }


    @Override
    public List<GoodsApiResVO> getExperienceType(Long projectId,String distributePhone) {

        //查询项目绑定的商品
        List<GoodsApiResVO> goodsByProject = goodsMapper.getGoodsByProject(projectId);
        if(!CollectionUtils.isEmpty(goodsByProject) && StringUtils.isNotEmpty(distributePhone)){
            //查询该分销商可以分销的商品
            List<String> goodsIdByPhone = remoteServiceUtil.getGoodsIdByPhone(distributePhone, Long.parseLong(companyId));
            goodsByProject.stream().forEach(x-> {
                if(!CollectionUtils.isEmpty(goodsIdByPhone)){
                    goodsIdByPhone.stream().forEach(y->{
                        if(String.valueOf(x.getId()).equals(y)){
                            x.setIsDistributeGoods(1);
                        }
                    });
                }
            });

        }


        return goodsByProject;
    }

    @Override
    public GoodsApiResVO   getExperienceTime(GoodsReqParam goodsReqParam) {

        List<GoodsApiResVO> list ;
        Long projectId = goodsReqParam.getProjectId();
        //分时段运营
        if(goodsReqParam.getIsByPeriodOperation() == 1){
            list = wzdGoodsAppletsMapper.getGoodsList(goodsReqParam);
        }else {
            //不分时段运营
            list = wzdGoodsAppletsMapper.getGoodsListIsByPeriodOperation(projectId,goodsReqParam.getGoodsId());

        }
        //查询运营状况标签

        if(!CollectionUtils.isEmpty(list)){
            ProjectOperationStatusResponseAppVO appVO = projectOperationStatusService.getProjectOperationStatusResponseAppVOByProjectId(projectId, 1);
            GoodsApiResVO goodsApiResVO = list.get(0);
            List<GoodsApiExtendResVo> goodsApiExtendResVoList = goodsApiResVO.getGoodsApiExtendResVoList();

            goodsApiExtendResVoList.stream().forEach( x -> {
                //设置商品Id
                x.setId(goodsApiResVO.getId());
                x.setRetailPrice(goodsApiResVO.getRetailPrice());
                Integer isShowApplet = appVO.getIsShowApplet();
                if (isShowApplet != null && isShowApplet == 1) {
                    x.setIsShowApplet(isShowApplet);
                    x.setOperationStatusType(appVO.getOperationStatusType());
                    x.setLabel(appVO.getLabel());
                } else {
                    x.setIsShowApplet(2);
                }

            });

            String phone = goodsReqParam.getPhone();
            List<String> goodsIdByPhone = null;
            if(StringUtils.isNotEmpty(phone)){

                goodsIdByPhone =   remoteServiceUtil.getGoodsIdByPhone(phone, goodsReqParam.getCompanyId());
            }
            setIsDistributeGood(goodsIdByPhone,goodsApiResVO);

            //设置项目图片
            ProjectInfoVo infoById = remoteServiceUtil.getProjectInfoById(projectId);
            goodsApiResVO.setHeadImg(infoById==null?"":infoById.getHeadImage());

            return goodsApiResVO;
        }else {

            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"暂无数据");

        }

    }


    @Override
    public ShipTicketInfo getShipTicketInfo(TicketInfoQueryParam param) {
        // 查询票信息
        ShipTicketInfo shipTicketInfo = wzdGoodsAppletsMapper.getShipTicketInfo(param.getLineFrom(), param.getLineTo(),
                param.getLineDate(), param.getCabinName(),param.getTimespan(),param.getShipName());
        if(shipTicketInfo == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"未查询到票信息");
        }
        List<TicketTypeInfo> ticketList = shipTicketInfo.getTicketList();
        if(CollectionUtils.isEmpty(ticketList)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"未查询到票信息");
        }
        //添加儿童票说明
        shipTicketInfo.setId(ticketList.get(0).getId());

        String shipLineInfo = shipTicketInfo.getShipLineInfo();
        if(StringUtils.isEmpty(shipLineInfo)){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"航班信息为空!");
        }
        //封装时间信息
        ShipLineInfo lineInfo = JSONObject.parseObject(shipLineInfo, ShipLineInfo.class);
        shipTicketInfo.setStartTime(lineInfo.getStartTime());
        shipTicketInfo.setEndTime(lineInfo.getArriveTime());
        shipTicketInfo.setTakeTime(lineInfo.getAfterTime());
        //实时查询票价格
        ShiftInfo shipLineTicketInfo = LalyoubaShipHttpApiUtil.getShipLineTicketInfo(profiles, lineInfo.getStartPortID().toString(), lineInfo.getArrivePortID().toString(), param.getLineDate(),lineInfo.getLineID(), param.getCabinName(), lineInfo.getShipID(),param.getTimespan());

        if(shipLineTicketInfo == null){
            throw new BusinessException(GeneConstant.BUSINESS_ERROR,"未查询到票信息");
        }
        ticketList.stream().forEach( info -> {
            if(GeneConstant.INT_101.equals(info.getTicketType())){
                if(!"0".equals(shipLineTicketInfo.getDiscountType())){
                    //成人票优惠票类型
                    //当为0时，不存在优惠票，只有全价票,当有值时不卖成人票，只卖这个票型
                    info.setSalePrice(new BigDecimal(shipLineTicketInfo.getDiscountPrice()));
                }else {
                    info.setSalePrice(new BigDecimal(shipLineTicketInfo.getTicketFullPrice()));
                }
            }else if(GeneConstant.INT_203.equals(info.getTicketType())){
                info.setSalePrice(new BigDecimal(shipLineTicketInfo.getTicketChildPrice()));
            }
        });
        ticketList.add(new TicketTypeInfo(0L,404,"携童票",new BigDecimal(0)));
        return shipTicketInfo;

    }
}
