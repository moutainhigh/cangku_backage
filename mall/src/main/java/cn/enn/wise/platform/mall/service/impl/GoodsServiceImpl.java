package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.config.SystemAdapter;
import cn.enn.wise.platform.mall.constants.AppConstants;
import cn.enn.wise.platform.mall.constants.GoodsConstants;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.*;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import cn.enn.wise.platform.mall.util.thirdparty.BaiBangDaHttpApiUtil;
import cn.enn.wise.platform.mall.util.thirdparty.nxj.NxjSoapUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Goods服务实现类
 *
 * @author caiyt
 * @since 2019-05-22
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    private Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private  GoodsProjectService goodsProjectService;

    @Autowired
    private GoodsExtendMapper goodsExtendMapper;

    @Autowired
    private GoodsRelatedBBDMapper goodsRelatedBBDMapper;

    @Autowired
    private GoodsPackageItemService goodsPackageItemService;

    @Autowired
    private GoodsExtendService goodsExtendService;


    @Autowired
    private GoodsProjectPeriodService goodsProjectPeriodService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsProjectMapper goodsProjectMapper;

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private RouteService routeService;

    @Autowired
    private ServicePlaceService servicePlaceService;

    @Autowired
    private ServicePlaceMapper servicePlaceMapper;

    @Autowired
    private MallAdminServiceImpl mallAdminService;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private WzdGoodsAppletsMapper wzdGoodsAppletsMapper;

    @Autowired
    private GoodsPackageItemMapper goodsPackageItemMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsProjectOperationMapper goodsProjectOperationMapper;

    @Autowired
    private OrderService orderService;

    @Value("${companyId}")
    private String companyId;


    @Override
    public boolean saveBatch(Collection<Goods> entityList) {
        return false;
    }

    @Override
    public ResponseEntity<ResPageInfoVO<List<GoodsResVO>>> listGoods(ReqPageInfoQry<GoodsReqParam> goodsPageQry) {
        GoodsReqParam goodsReqQry = goodsPageQry.getReqObj();
        // 封装请求参数
        QueryWrapper<Goods> qryWapper = new QueryWrapper<>();
        if (goodsReqQry != null) {
            Goods goodsQry = new Goods();
            BeanUtils.copyProperties(goodsReqQry, goodsQry);
            // 商品名称要模糊匹配
            String goodsName = goodsQry.getGoodsName();
            if (goodsName != null) {
                goodsQry.setGoodsName(null);
            }
            qryWapper = new QueryWrapper<>(goodsQry);
            if (goodsName != null) {
                qryWapper.lambda().like(Goods::getGoodsName, goodsName);
            }
            if (goodsReqQry.getFromType()!=null && goodsReqQry.getFromType() == 2L) {
                qryWapper.lambda().ne(Goods::getProjectId, 10L);
            }
        }
        qryWapper.lambda().ne(Goods::getGoodsStatus, GoodsConstants.GoodsStatusEnum.DELETED.value());
        qryWapper.lambda().orderByDesc(Goods::getId);
        // 分页参数
        Page<Goods> pageParam = new Page<>(goodsPageQry.getPageNum(), goodsPageQry.getPageSize());
        // 根据条件查询数据库
        IPage<Goods> dbPageInfo = this.page(pageParam, qryWapper);
        ResPageInfoVO resPageInfo = new ResPageInfoVO();
        // 分页信息
        resPageInfo.setPageNum(dbPageInfo.getCurrent());
        resPageInfo.setPageSize(dbPageInfo.getSize());
        resPageInfo.setTotal(dbPageInfo.getTotal());
        List<Goods> goodsList = dbPageInfo.getRecords();
        // 如果无数据则直接返回
        if (goodsList == null || goodsList.isEmpty()) {
            return new ResponseEntity<ResPageInfoVO<List<GoodsResVO>>>(resPageInfo);
        }
        //获取所有项目信息
        List<GoodsProject> goodsProjectList = goodsProjectMapper.getAllProjectName();
        // 将数据库中的数据封装为api格式的数据返回
        List<GoodsResVO> records = new LinkedList<>();
        goodsList.stream().forEach(x -> {
            GoodsResVO record = new GoodsResVO();
            BeanUtils.copyProperties(x, record);
            record.setGoodsId(x.getId());
            //判断为套票，项目名称拼接用、链接
            if(x.getIsSuit()==GeneConstant.BYTE_1){
                List<GoodsPackageItemsParams> goodsPackageItemsParams = goodsPackageItemMapper.selectItemByGoodsId(x.getId());
                String projectNname = "";
                boolean first = true;
                for(GoodsPackageItemsParams param:goodsPackageItemsParams){
                    if(first){
                        if(param.getProjectName()!=null&&!"".equals(param.getProjectName())){
                            projectNname=param.getProjectName();
                            first = false;
                        }
                    }else{
                        if(param.getProjectName()!=null&&!"".equals(param.getProjectName())){
                            projectNname=projectNname+"、"+param.getProjectName();
                        }
                    }
                }
                record.setProjectName(projectNname);
            }else{
                for(GoodsProject project : goodsProjectList){
                    if(record.getProjectId().equals(project.getId())){
                        record.setProjectName(project.getName());
                        break;
                    }
                }
            }
            records.add(record);
        });
        resPageInfo.setRecords(records);
        return new ResponseEntity<ResPageInfoVO<List<GoodsResVO>>>(resPageInfo);
    }

    @Override
    public ResponseEntity<GoodsResVO> getGoodById(long id) {
        // 先查询商品基本信息
        Goods goods = this.getById(id);
        if (goods == null) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.NO_MATCH_RECORDS_FOUND.value());
        }
        GoodsProject goodsProject = goodsProjectService.getProjectById(goods.getProjectId());
        GoodsResVO goodsResVO = new GoodsResVO();
        goodsResVO.setProjectName(goodsProject.getName());
        BeanUtils.copyProperties(goods, goodsResVO);
        goodsResVO.setGoodsId(goodsResVO.getId());

        List<Long> rids=new ArrayList<>();
        for(String route:goods.getServicePlace().split(",")){
            rids.add(Long.parseLong(route));
        }
        goodsResVO.setServicePlace(rids);

        goodsResVO.setServiceRoute(Long.parseLong(goods.getServiceRoute()));

        if(goods.getServiceRoute()!=null&&!goods.getServiceRoute().equals("")){
            List<Route> routeList = routeMapper.selectRouteByIds(goods.getServiceRoute());
            if(routeList!=null&&routeList.size()>0){
                String routeName = routeList.get(0).getName();
                for(int i=1;i<routeList.size();i++){
                    routeName=routeName+","+routeList.get(i).getName();
                }
                goodsResVO.setServiceRouteName(routeName);
            }
        }


        if(goods.getServicePlace()!=null&&!goods.getServicePlace().equals("")){
            List<ServicePlace> servicePlaceList = servicePlaceMapper.selectServicePlaceByIds(goods.getServicePlace());

            if(servicePlaceList!=null&&servicePlaceList.size()>0){
                String placeName = servicePlaceList.get(0).getServicePlaceName();
                for(int i=1;i<servicePlaceList.size();i++){
                    placeName=placeName+","+servicePlaceList.get(i).getServicePlaceName();
                }
                goodsResVO.setServicePlaceName(placeName);
            }
        }

        // 查询商品扩展数据
        List<GoodsExtendResVo> goodsExtendResVoList = goodsExtendService.getGoodsExtendInfoByGoodsId(id);
        goodsResVO.setGoodsExtendResVoList(goodsExtendResVoList);

        return new ResponseEntity(goodsResVO);
    }



    @Override
    public ResponseEntity<AddGoodsPackageParams> getGoodPackageById(long id) {
        // 先查询商品基本信息
        Goods goods = this.getById(id);
        if (goods == null) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.NO_MATCH_RECORDS_FOUND.value());
        }

        AddGoodsPackageParams addGoodsPackageParams = new AddGoodsPackageParams();
        GoodsReqParam goodsResVO =new GoodsReqParam();

        BeanUtils.copyProperties(goods, goodsResVO);

        addGoodsPackageParams.setGoods(goodsResVO);
        // 查询商品组合信息
        List<GoodsPackageItemsParams> goodsPackageItemBos  = goodsPackageItemMapper.selectGoodsPackageItemByGoodsId(id);

        List<GoodsProjectResVo> list = new ArrayList<>();
        for(GoodsPackageItemsParams goodsPackageItemsParams :goodsPackageItemBos) {
            GoodsProjectResVo goodsProjectResVo = goodsService.getServicePlaceAndLineById(goodsPackageItemsParams.getProjectId());
            String []servicePlaces = goodsPackageItemsParams.getServicePlaceId().split(",");
            List<Long> longList = new ArrayList<>();
            for(String str :servicePlaces){
                longList.add(Long.parseLong(str));
            }
            goodsProjectResVo.setPlacesCheckedIds(longList);
            goodsProjectResVo.setRouteCheckedId(goodsPackageItemsParams.getRouteId());
            Boolean canAdd=true;
            for(GoodsProjectResVo item:list){
                if(item.getProjectId().equals(goodsPackageItemsParams.getProjectId())){
                    canAdd=false;
                }
            }
            if(canAdd) {
                list.add(goodsProjectResVo);
            }
        }
        addGoodsPackageParams.setGoodsProjectResVos(list);
        addGoodsPackageParams.setItems(goodsPackageItemBos);
        return new ResponseEntity(addGoodsPackageParams);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateGoodsPrice(Goods goodsTmp){

        //根据楠溪江商品查询我们的商品信息
        List<GoodsRelatedBBDBo>  list = goodsRelatedBBDMapper.selectList(new QueryWrapper<GoodsRelatedBBDBo>().eq("bbd_goods_id",goodsTmp.getId()));
        if(list!=null&&list.size()>0){
            Goods goods =  goodsMapper.selectOne(new QueryWrapper<Goods>().eq("id",list.get(0).getGoodsId()));
            if(goods!=null){
                goods.setBasePrice(goodsTmp.getBasePrice());
                goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                this.updateById(goods);

            }
        }
        return new ResponseEntity();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateGoods(GoodsReqParam goodsReqParam, SystemStaffVo staffVo,String token) {
        // 校验请求参数
        GoodsReqParam.validateUpdateGoodsReqParam(goodsReqParam);
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsReqParam, goods);
        goods.setId(goodsReqParam.getGoodsId());
        String ids ="";
        for(String place:goodsReqParam.getServicePlace()){
            ids+=place+",";
        }
        ids  = ids.substring(0,ids.length()-1);
        goods.setServicePlace(ids);
        goods.setServiceRoute(String.valueOf(goodsReqParam.getServiceRoute()));

        Goods goodsOriginal = this.getById(goods.getId());


        if (goodsOriginal == null) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.NO_MATCH_RECORDS_FOUND.value());
        }
        // 商品只有下架状态才可以编辑
        if (!goodsOriginal.getGoodsStatus().equals(GoodsConstants.GoodsStatusEnum.OFF_SHELF.value())) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.ONLY_OFF_SHELF_CAN_MODIFIEF.value());
        }
        // 如果商品名称被修改且新值在数据库中已存在，则抛出异常提示
        if (!goodsOriginal.getGoodsName().equals(goods.getGoodsName()) && isGoodsNameExist(goods.getGoodsName())) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.GOODS_NAME_EXIST.value());
        }
        // 更新商品基本信息
        goods.setUpdateUserName(staffVo.getName());
        goods.setUpdateUserId(staffVo.getId());
        goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        this.updateById(goods);

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("basePrice", goodsReqParam.getBasePrice().toString());
        map.put("retailPrice", goodsReqParam.getRetailPrice().toString());
        map.put("goodsId", goodsReqParam.getGoodsId().toString());
        map.put("goodsName", goodsReqParam.getGoodsName());

        Map<String, String> urlConfig = SystemAdapter.URL_MAP.get(Long.valueOf(companyId));
        String url = urlConfig.get(AppConstants.DISTRIBUTE_URL)+"/distribute/common/goodsprice/update";
        HttpClientUtil.post(url, map);

        // 如果项目没变，则商品时间段信息不做更新
        if ((goods.getProjectId() == null || goodsOriginal.getProjectId().equals(goods.getProjectId()))&&goods.getIsByPeriodOperation()!=2) {
            return new ResponseEntity();
        }
        // 套装不分时段运营
        if(goods.getProjectId()==10L){
            return new ResponseEntity();
        }
        // 禁用老的归属项目的时间段信息
        goodsExtendService.disableByGoodsId(goods.getId(), staffVo);
        // 增加新的归属项目的时间段信息
        this.saveGoodsExtends(goods, staffVo);
        //更新goods_extend表中的ship_line_info
        if(goods.getIsByPeriodOperation() == 2){
            List<GoodsExtend> oldGoodsExtends = goodsExtendMapper.selectList(
                    new QueryWrapper<GoodsExtend>().eq("goods_id",goods.getId()).isNotNull("ship_line_info").orderByDesc("id"));
            GoodsExtend newGoodsExtend = goodsExtendMapper.selectOne(
                    new QueryWrapper<GoodsExtend>().eq("goods_id",goods.getId()).isNull("ship_line_info"));
            newGoodsExtend.setShipLineInfo(oldGoodsExtends.get(0).getShipLineInfo());
            goodsExtendMapper.updateById(newGoodsExtend);
        }
        return new ResponseEntity();
    }

    private void syncGoodsPrice(Long projectId) {
        logger.info("sync projectId:"+projectId);

        Map<String,Object> result  = mallAdminService.getMinPriceByProjectId(projectId);
        logger.info("查询最低价结果:"+ JSONObject.toJSONString(result));

        if(result != null){
            String minPrice = result.get("price").toString();
            String distributionPrice = result.get("distributionPrice").toString();
            //#region 发送消息
            messageSender.sendGoodsPrice(projectId,new BigDecimal(minPrice),new BigDecimal(distributionPrice));
        }
    }

    @Override
    public boolean isGoodsNameExist(String goodsName) {
        QueryWrapper<Goods> qryWapper = new QueryWrapper<>();
        qryWapper.lambda().ne(Goods::getGoodsStatus, GoodsConstants.GoodsStatusEnum.DELETED.value())
                .eq(Goods::getGoodsName, goodsName);
        return this.count(qryWapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity saveGoods(GoodsReqParam goodsReqParam, SystemStaffVo staffVo) {
        // 校验请求参数
        GoodsReqParam.validateSaveGoodsReqParam(goodsReqParam);
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsReqParam, goods);
        String places ="";
        for(String id:goodsReqParam.getServicePlace()){
            places +=id+",";
        }
        goods.setServicePlace(places.substring(0,places.length()-1));
        goods.setServiceRoute(String.valueOf(goodsReqParam.getServiceRoute()));
        // 判断商品名称是否已存在
        if (this.isGoodsNameExist(goods.getGoodsName())) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.GOODS_NAME_EXIST.value());
        }
        // 保存商品基本信息
        goods.setGoodsStatus(GoodsConstants.GoodsStatusEnum.OFF_SHELF.value());
        goods.setGoodsType(goods.getProjectId().intValue());
        goods.setCreateUserId(staffVo.getId());
        goods.setCreateUserName(staffVo.getName());
        goods.setCreateTime(new Timestamp(System.currentTimeMillis()));
        goods.setIsSuit(GeneConstant.BYTE_2);
        this.save(goods);
        // 更新商品编码
        GoodsProject goodsProject = this.saveGoodsExtends(goods, staffVo);
        goods.setGoodsCode(goodsProject.getProjectCode().concat("-").concat(String.format("%06d", goods.getId())));
        this.updateById(goods);
        syncGoodsPrice(goods.getProjectId());
        return new ResponseEntity();
    }

    public ResponseEntity saveGoodsBBD(GoodsReqParam goodsReqParam) {
        // 校验请求参数
        GoodsReqParam.validateSaveGoodsReqParam(goodsReqParam);
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsReqParam, goods);
        String places ="";
        for(String id:goodsReqParam.getServicePlace()){
            places +=id+",";
        }
        goods.setServicePlace(places.substring(0,places.length()-1));
        if(goodsReqParam.getServiceRoute()==null){
            goods.setServiceRoute("");
        }else{
            goods.setServiceRoute(String.valueOf(goodsReqParam.getServiceRoute()));
        }

        // 判断商品名称是否已存在
        if (this.isGoodsNameExist(goods.getGoodsName())) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.GOODS_NAME_EXIST.value());
        }
        // 保存商品基本信息
        goods.setGoodsStatus(goodsReqParam.getGoodsStatus());
        goods.setGoodsType(3);
//        goods.setCreateUserId(staffVo.getId());
//        goods.setCreateUserName(staffVo.getName());
        goods.setCreateTime(new Timestamp(System.currentTimeMillis()));
        goods.setIsSuit(GeneConstant.BYTE_2);
        this.save(goods);
        // 更新商品编码
        GoodsProject goodsProject = this.saveGoodsExtendsBBD(goods);
        goods.setGoodsCode(goodsProject.getProjectCode().concat("-").concat(String.format("%06d", goods.getId())));
        this.updateById(goods);
        syncGoodsPrice(goods.getProjectId());
        return new ResponseEntity();
    }
    /**
     * 保存商品时段信息
     *
     * @param goods
     * @return
     */
    private GoodsProject saveGoodsExtends(Goods goods, SystemStaffVo staffVo) {
        // 判断商品归属项目是否存在
        GoodsProject goodsProject = goodsProjectService.getById(goods.getProjectId());
        if (goodsProject == null) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.GOODS_PROJECT_NOT_EXIST.value());
        }
        //判断是否分时段 2不分 1分时段
        if(goods.getIsByPeriodOperation()!=null&&goods.getIsByPeriodOperation()==2){
            saveGoodsExtend(goods, staffVo, "", Byte.parseByte("1"), 0L);
            return goodsProject;
        }
        // 查询商品归属项目的时段列表
        List<GoodsProjectPeriod> goodsProjectPeriodList = goodsProjectPeriodService.listByProjectId(goods.getProjectId(), GoodsConstants.GoodsExtendStatus.ENABLE.value());
        if (goodsProjectPeriodList == null || goodsProjectPeriodList.isEmpty()) {
            return goodsProject;
        }
        // 保存商品时段信息
        goodsProjectPeriodList.stream().forEach(goodsProjectPeriod -> {
            GoodsExtend goodsExtend = saveGoodsExtend(goods, staffVo, goodsProjectPeriod.getTitle(), goodsProjectPeriod.getOrderby(), goodsProjectPeriod.getId());
        });
        return goodsProject;
    }

    private GoodsProject saveGoodsExtendsBBD(Goods goods) {
        // 判断商品归属项目是否存在
        GoodsProject goodsProject = goodsProjectService.getById(goods.getProjectId());
        if (goodsProject == null) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.GOODS_PROJECT_NOT_EXIST.value());
        }
        //判断是否分时段 2不分 1分时段
        if(goods.getIsByPeriodOperation()!=null&&goods.getIsByPeriodOperation()==2){
            saveGoodsExtendBBD(goods,  "", Byte.parseByte("1"), 0L);
            return goodsProject;
        }
        // 查询商品归属项目的时段列表
        List<GoodsProjectPeriod> goodsProjectPeriodList = goodsProjectPeriodService.listByProjectId(goods.getProjectId(), GoodsConstants.GoodsExtendStatus.ENABLE.value());
        if (goodsProjectPeriodList == null || goodsProjectPeriodList.isEmpty()) {
            return goodsProject;
        }
        // 保存商品时段信息
        goodsProjectPeriodList.stream().forEach(goodsProjectPeriod -> {
            GoodsExtend goodsExtend = saveGoodsExtendBBD(goods, goodsProjectPeriod.getTitle(), goodsProjectPeriod.getOrderby(), goodsProjectPeriod.getId());
        });
        return goodsProject;
    }
    /**
     * 保存商品时段信息
     *
     * @param goods
     * @return
     */
    private GoodsExtend savePackageGoodsExtends(Goods goods, SystemStaffVo staffVo) {
        //判断是否分时段 2不分 1分时段
        if(goods.getIsByPeriodOperation()!=null&&goods.getIsByPeriodOperation()==2){
            return saveGoodsExtend(goods, staffVo, "", Byte.parseByte("1"), 0L);
        }
        return null;
    }

    private GoodsExtend saveGoodsExtend(Goods goods, SystemStaffVo staffVo, String s, byte b, long l) {
        GoodsExtend goodsExtend = new GoodsExtend(goods.getId(), s, b, goods.getBasePrice(), l);
        goodsExtend.setStatus(GoodsConstants.GoodsExtendStatus.ENABLE.value());
        goodsExtend.setCreateUserId(staffVo.getId());
        goodsExtend.setCreateUserName(staffVo.getName());
        goodsExtend.setCreateTime(new Timestamp(System.currentTimeMillis()));
        goodsExtendService.saveGoodExtend(goodsExtend);
        return goodsExtend;
    }

    private GoodsExtend saveGoodsExtendBBD(Goods goods,  String s, byte b, long l) {
        GoodsExtend goodsExtend = new GoodsExtend(goods.getId(), s, b, goods.getBasePrice(), l);
        goodsExtend.setStatus(GoodsConstants.GoodsExtendStatus.ENABLE.value());
//        goodsExtend.setCreateUserId(staffVo.getId());
//        goodsExtend.setCreateUserName(staffVo.getName());
        goodsExtend.setCreateTime(new Timestamp(System.currentTimeMillis()));
        goodsExtendService.saveGoodExtend(goodsExtend);
        return goodsExtend;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity batchSwitchGoodsStatus(List<Long> goodsIds, byte status, SystemStaffVo staffVo) {
        if (goodsIds == null || goodsIds.isEmpty()) {
            return new ResponseEntity();
        }
        List<Goods> goods = goodsIds.stream().map(goodsId -> {
            Goods good = new Goods();
            good.setId(goodsId);
            good.setGoodsStatus(status);
            good.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            good.setUpdateUserId(staffVo.getId());
            good.setUpdateUserName(staffVo.getName());
            return good;
        }).collect(Collectors.toList());
        this.updateBatchById(goods, goods.size());
        //上下架商品时同步商品价格
        logger.info("===开始同步商品价格===");
        List<Long> projectIds = goodsMapper.selectProjectIdByIdAndStatus(goodsIds);
        logger.info("查询项目id"+projectIds);
        if(projectIds != null){
            for (Long projectId : projectIds) {
                syncGoodsPrice(projectId);
            }
        }
        logger.info("同步商品价格结束");
        return new ResponseEntity();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity batchDeleteGoods(List<Long> goodsIds, SystemStaffVo staffVo) {
        // 判断是否有已上架的商品，已上架的商品不可做删除操作
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Goods::getGoodsStatus, GoodsConstants.GoodsStatusEnum.ON_SHELF.value())
                .in(Goods::getId, goodsIds);
        int onShelfCount = this.count(queryWrapper);
        if (onShelfCount > 0) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, "已上架的商品不可做删除操作");
        }
        // 将商品信息逻辑删除
        this.batchSwitchGoodsStatus(goodsIds, GoodsConstants.GoodsStatusEnum.DELETED.value(), staffVo);
        // 将与商品关联的时段信息逻辑删除
        goodsExtendService.batchDeleteByGoodsId(goodsIds, staffVo);
        return new ResponseEntity();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity goodsExtendOperation(long goodsId, List<GoodsExtendReqParam> goodsExtendReqParamList, SystemStaffVo staffVo) {


        Goods goodsOriginal = this.getById(goodsId);
        if (goodsOriginal == null) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.NO_MATCH_RECORDS_FOUND.value());
        }
        // 商品只有下架状态才可以编辑
        if (!goodsOriginal.getGoodsStatus().equals(GoodsConstants.GoodsStatusEnum.OFF_SHELF.value())) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.ONLY_OFF_SHELF_CAN_MODIFIEF.value());
        }
        // 获取数据库中原始的时间段数据
        List<GoodsExtend> goodsExtendList = goodsExtendService.getGoodsExtendByGoodsId(goodsId);

        Map<Long, GoodsExtend> goodsPeriodIdOld = goodsExtendList.stream().collect(Collectors.toMap(GoodsExtend::getPeriodId, Function.identity()));
        // 封装管理平台传递的数据
        Set<Long> goodsPeriodIdNew = goodsExtendReqParamList.stream().map(GoodsExtendReqParam::getPeriodId).collect(Collectors.toSet());
        // 先处理老数据
        goodsExtendList.stream().forEach(goodsExtend -> {
            // 如果新传递的数据中不含老数据，则状态变更为“禁用”
            if (!goodsPeriodIdNew.contains(goodsExtend.getPeriodId())) {
                goodsExtend.setStatus(GoodsConstants.GoodsExtendStatus.DISABLE.value());
                goodsExtend.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                goodsExtend.setUpdateUserId(staffVo.getId());
                goodsExtend.setUpdateUserName(staffVo.getName());
                goodsExtendService.updateById(goodsExtend);
            }
        });
        // 按时间区间排序
        goodsExtendReqParamList.sort(Comparator.comparing(GoodsExtendReqParam::getTimespan));
        // 再处理新数据
        byte orderBy = 0;
        for (int i = 0; i < goodsExtendReqParamList.size(); ++i) {

            logger.info("index:"+i);
            GoodsExtendReqParam goodsExtendReqParam = goodsExtendReqParamList.get(i);
            goodsExtendReqParam.setOrderby(++orderBy);
            long periodId = goodsExtendReqParam.getPeriodId();
            // 如果新老数据重叠
            if (goodsPeriodIdOld!=null&&goodsPeriodIdOld.containsKey(periodId)) {
                GoodsExtend goodsExtend = goodsPeriodIdOld.get(periodId);
                // 如果有变化则直接更新
                if ((goodsExtend.getSalePrice()!=null&&!goodsExtend.getSalePrice().equals(goodsExtendReqParam.getSalePrice()))
                        ||(goodsExtend.getTimespan()!=null&& !goodsExtend.getTimespan().equals(goodsExtendReqParam.getTimespan()))
                        || (goodsExtendReqParam.getTimeLabel()!=null&&!goodsExtendReqParam.getTimeLabel().equals(goodsExtend.getTimeLabel()))) {
                    // 如果有ID则直接根据ID进行修改价格
                    if (goodsExtendReqParam.getId() != null) {
                        goodsExtend.setId(goodsExtendReqParam.getId());
                        goodsExtend.setSalePrice(goodsExtendReqParam.getSalePrice());
                        goodsExtend.setTimeLabel(goodsExtendReqParam.getTimeLabel());
                        goodsExtend.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        goodsExtend.setUpdateUserId(staffVo.getId());
                        goodsExtend.setUpdateUserName(staffVo.getName());
                        goodsExtendService.updateById(goodsExtend);
                    } else { // 如果ID未传过来则根据商品ID和项目时段ID进行价格的修改
                        goodsExtendService.update(goodsId, periodId, goodsExtendReqParam.getTimeLabel(),
                                goodsExtendReqParam.getSalePrice(), staffVo);
                    }
                }
            } else {
                // 如果未重叠则为新增数据
                // 根据时间段ID查询时间段数据
                GoodsProjectPeriod goodsProjectPeriod = goodsProjectPeriodService.getById(goodsExtendReqParam.getPeriodId());
                if (goodsProjectPeriod == null) {
                    throw new BusinessException(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.GOODS_PROJECT_PERIOD_NOT_EXIT.value());
                }
                GoodsExtend goodsExtend = new GoodsExtend(goodsId, goodsProjectPeriod.getTitle(), goodsExtendReqParam.getOrderby(),
                        goodsExtendReqParam.getSalePrice(), goodsProjectPeriod.getId());
                goodsExtend.setTimeLabel(goodsExtendReqParam.getTimeLabel());
                goodsExtend.setCreateTime(new Timestamp(System.currentTimeMillis()));
                goodsExtend.setCreateUserId(staffVo.getId());
                goodsExtend.setCreateUserName(staffVo.getName());
                goodsExtend.setStatus(GoodsConstants.GoodsExtendStatus.ENABLE.value());
                goodsExtendService.saveGoodExtend(goodsExtend);
            }
        }
        return new ResponseEntity();
    }

    @Override
    public ResponseEntity<List<GoodsExtendOperationResVo>> getGoodsExtendOperationInfo(long goodsId) {
        Goods goods = this.getById(goodsId);
        if (goods == null) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.NO_MATCH_RECORDS_FOUND.value());
        }
        // 商品的运营时段信息
        List<GoodsExtendResVo> goodsExtendResVoList = goodsExtendService.getGoodsExtendInfoByGoodsId(goods.getId());
        // 商品运营时段信息字典，时段信息ID为key，运营时段信息为value
        Map<Long, GoodsExtendResVo> goodsExtendResVoMap = goodsExtendResVoList.stream().collect(Collectors.toMap(GoodsExtendResVo::getPeriodId, Function.identity()));
        // 商品归属项目的所有运营时段信息
        List<GoodsProjectPeriod> goodsProjectPeriodList = goodsProjectPeriodService.listByProjectId(goods.getProjectId(), GoodsConstants.GoodsExtendStatus.ENABLE.value());
        // 将以上信息封装返回到调用方
        List<GoodsExtendOperationResVo> goodsExtendOperationResVoList = new LinkedList<>();
        goodsProjectPeriodList.stream().forEach(goodsProjectPeriod -> {
            GoodsExtendOperationResVo goodsExtendOperationResVo = new GoodsExtendOperationResVo();
            GoodsExtendResVo goodsExtendResVo = goodsExtendResVoMap.get(goodsProjectPeriod.getId());
            if (goodsExtendResVo != null) {
                BeanUtils.copyProperties(goodsExtendResVo, goodsExtendOperationResVo);
                // 该时段是否启用
                goodsExtendOperationResVo.setOperationFlag(true);
            } else {
                goodsExtendOperationResVo.setGoodsId(goodsId);
                goodsExtendOperationResVo.setOrderby(goodsProjectPeriod.getOrderby());
                goodsExtendOperationResVo.setPeriodId(goodsProjectPeriod.getId());
                goodsExtendOperationResVo.setSalePrice(goods.getBasePrice());
                // 该时段是否启用
                goodsExtendOperationResVo.setOperationFlag(false);
                goodsExtendOperationResVo.setTimespan(goodsProjectPeriod.getTitle());
            }
            goodsExtendOperationResVoList.add(goodsExtendOperationResVo);
        });
        return new ResponseEntity(goodsExtendOperationResVoList);
    }


    @Override
    public Map<String, Object> getGoodsList(GoodsReqParam goodsReqQry) {
        logger.info("===开始组装数据===");
        List<GoodsApiResVO> goodsList = goodsMapper.getGoodsList(goodsReqQry);


        logger.info("===按照是否是多人票分组===");
        //按照是否是多人票分组

        //多人票集合
        List<GoodsApiResVO> packageTicketList = new ArrayList<>();
        List<GoodsApiResVO> singleTicketList = new ArrayList<>();

        for (GoodsApiResVO goodsApiResVO : goodsList) {
            if (goodsApiResVO.getIsPackage() == 0) {
                singleTicketList.add(goodsApiResVO);
            } else {
                packageTicketList.add(goodsApiResVO);
            }
        }

        //多种单票
        List<GoodsApiResVO> singleResultList = new ArrayList<>();
        List<GoodsApiResVO> packageResultList = new ArrayList<>();

        BigDecimal basePrice;
        if (CollectionUtils.isEmpty(singleTicketList)) {
            logger.info("===无单人票可用===设置默认用来计算优惠金额的价格===");
            basePrice = new BigDecimal("0");
        } else {
            basePrice = singleTicketList.get(0).getBasePrice();
        }

        for (GoodsApiResVO goodsApiResVO : singleTicketList) {
            singleResultList.add(completionSingleTicketInfo(goodsApiResVO,basePrice));
        }
        for (GoodsApiResVO goodsApiResVO : packageTicketList) {
            packageResultList.add(completionSingleTicketInfo(goodsApiResVO,basePrice));
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("singleTicketList", singleResultList);
        resultMap.put("packageTicketList", packageResultList);
        return resultMap;

    }


    /**
     * 组装票信息
     *
     * @param goodsApiResVO
     */
    public GoodsApiResVO completionSingleTicketInfo(GoodsApiResVO goodsApiResVO,BigDecimal basePrice) {

        if (goodsApiResVO == null) {
            logger.info("===时段信息为null===");
            return new GoodsApiResVO();
        }
        List<GoodsApiExtendResVo> goodsApiExtendResVoList = goodsApiResVO.getGoodsApiExtendResVoList();
        if (CollectionUtils.isEmpty(goodsApiExtendResVoList)) {
            return null;
        }

        //单品按照价格分组
        Map<BigDecimal, List<GoodsApiExtendResVo>> collect = goodsApiExtendResVoList.stream().collect(Collectors.groupingBy(g -> g.getSalePrice()));

        List<GoodsApiSkuVo> resultList = new ArrayList<>();

        Set<Map.Entry<BigDecimal, List<GoodsApiExtendResVo>>> entries = collect.entrySet();
        //遍历所有的价格
        for (Map.Entry<BigDecimal, List<GoodsApiExtendResVo>> entry : entries) {
            BigDecimal key = entry.getKey();
            BigDecimal tips = new BigDecimal(0);
            String timeLable = "";
            List<GoodsApiExtendResVo> value = entry.getValue();
            //关联sku信息
            for (GoodsApiExtendResVo goodsApiExtendResVo : value) {
                goodsApiExtendResVo.setId(goodsApiResVO.getId());
                tips = basePrice.multiply(new BigDecimal(goodsApiResVO.getMaxNum())).subtract(goodsApiExtendResVo.getSalePrice());
                timeLable = goodsApiExtendResVo.getTimeLabel();
                goodsApiExtendResVo.setRealTips(GeneUtil.formatBigDecimal(tips).toString());
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
        List<GoodsApiSkuVo> resVoList = new ArrayList<>();

        if (resultList.size() > 0) {
            for (int i = 0; i < resultList.size(); i++) {
                if (i < 3) {
                    resVoList.add(resultList.get(i));
                }
            }
        }

        goodsApiResVO.setSkuInfo(resVoList);
        goodsApiResVO.setGoodsApiExtendResVoList(null);
        return goodsApiResVO;
    }

    /**
     * 组装多人票信息
     *
     * @param list
     */
    public List<GoodsApiResVO> completionPackageTicketInfoList(List<GoodsApiResVO> list, BigDecimal basePrice) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        //多人票价格计算规则 默认单人票的基础价格乘以组合票的人数减去组合票的价格
        for (GoodsApiResVO goodsApiResVO : list) {
            completionPackageTicketInfo(basePrice, goodsApiResVO);
        }
        return list;

    }

    /**
     * 单个组装多人票
     *
     * @param basePrice
     * @param goodsApiResVO
     * @return
     */
    public GoodsApiResVO completionPackageTicketInfo(BigDecimal basePrice, GoodsApiResVO goodsApiResVO) {
        if (basePrice.compareTo(new BigDecimal("0")) != 0) {
            goodsApiResVO.setTips(basePrice.multiply(new BigDecimal(goodsApiResVO.getMaxNum())).subtract(goodsApiResVO.getBasePrice()).setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
        }

        List<GoodsApiExtendResVo> goodsApiExtendResVoList = goodsApiResVO.getGoodsApiExtendResVoList();
        for (GoodsApiExtendResVo goodsApiExtendResVo : goodsApiExtendResVoList) {
            goodsApiExtendResVo.setId(goodsApiResVO.getId());
            if (basePrice.compareTo(new BigDecimal("0")) != 0) {
                goodsApiExtendResVo.setRealTips(basePrice.multiply(new BigDecimal(goodsApiResVO.getMaxNum())).subtract(goodsApiExtendResVo.getSalePrice()).setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
            }

        }
        return goodsApiResVO;
    }


    /**
     * 根据项目获取商品列表
     * @param id
     * @return
     */
    @Override
    public List<Goods> getGoodsByGoodsProject(Long id) {

        return goodsMapper.getGoodsByGoodsProject(id);
    }

    @Override
    public Map<String,Object> getGoodsPeriodIdByGoodsIdAndOperationDate(Long goodsId,String date,Integer servicePlaceId) {
        Map<String,Object> map = new HashMap<>();
        Goods goods = goodsMapper.selectById(goodsId);
        Integer isByPeriodOperation = goods.getIsByPeriodOperation();
        map.put("isByPeriodOperation",isByPeriodOperation);
        //分时段售卖的商品
        if(isByPeriodOperation == 1){
            List<GoodsExtendVo> goodsPeriodIdByGoodsIdAndOperationDate = goodsMapper.getGoodsPeriodIdByGoodsIdAndOperationDate(goodsId, date, servicePlaceId);
            map.put("periodInfo",goodsPeriodIdByGoodsIdAndOperationDate);
            if(CollectionUtils.isEmpty(goodsPeriodIdByGoodsIdAndOperationDate)){
                throw new BusinessException(GeneConstant.BUSINESS_ERROR,"该商品无时段配置,请检查商品时段配置");
            }
        }else {
            //不分时段售卖的商品
            map.put("periodInfo",null);
        }

        return map;
    }

    @Override
    public GoodsProjectResVo getServicePlaceAndLineById(Long id) {

        // 所有服务地点
        List<ServicePlace> servicePlaceList  = servicePlaceService.list();
        // 所有路线
        List<Route> routeList = routeService.list();

        List<GoodsProjectResVo> list = goodsMapper.getLineAndServicePlaceByProjectId(id);
        if(list == null || list.size() == 0){
            return null;
        }
        // 选中的服务地点
        GoodsProjectResVo goodsProjectResVo = list.get(0);

        // 路线
        List<RouteVo> routes =  new ArrayList<>();
        // 服务
        List<ServicePlaceVo> servicePlaces = new ArrayList<>();
        List<Long> ids  = new ArrayList<>();

        routeList.forEach(x-> routes.add(new RouteVo(){{
            setId(x.getId());
            setName(x.getName());
        }}));
        servicePlaceList.forEach(x-> servicePlaces.add(new ServicePlaceVo(){{
            setId(x.getId());
            setServicePlaceName(x.getServicePlaceName());
        }}));

        for (GoodsProjectResVo projectResVo : list) {
            Integer type = projectResVo.getType();
            if(type == 2){
                ids.add(projectResVo.getLineId());
            }
        }

        GoodsProject project = goodsProjectService.getById(id);
        goodsProjectResVo.setRouteCheckedId(goodsProjectResVo.getLineId());
        goodsProjectResVo.setPlacesCheckedIds(ids);
        goodsProjectResVo.setRoutes(routes);
        goodsProjectResVo.setServicePlaces(servicePlaces);
        goodsProjectResVo.setDayOperationTime(project.getDayOperationTime());
        goodsProjectResVo.setMaxServiceAmount(project.getMaxServiceAmount());
        goodsProjectResVo.setProjectId(id);
        goodsProjectResVo.setProjectName(project.getName());

        return goodsProjectResVo;
    }

    @Override
    public Map<String, Object> getGoodsAndServicePlaceIdByProject(Long projectId) {

        //查询商品
        List<Goods> goodsByGoodsProject = goodsMapper.getGoodsByGoodsProject(projectId);
        List<Map<String,Object>> goodsListMap = new ArrayList<>();
       goodsByGoodsProject.stream().forEach(x -> goodsListMap.add(new HashMap<String,Object>(){{

           put("label",x.getGoodsName());
           put("value",x.getId());
       }}));

        //查询服务场地
        List<ServicePlace> servicePlaceByProjectId = servicePlaceMapper.getServicePlaceByProjectId(projectId);
        List<Map<String,Object>> servicePlaceListMap = new ArrayList<>();
        servicePlaceByProjectId.stream().forEach(x -> servicePlaceListMap.add(new HashMap<String,Object>(){{

            put("label",x.getServicePlaceName());
            put("value",x.getId());
        }}));

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("goods",goodsListMap);
        resultMap.put("servicePlace",servicePlaceListMap);

        return resultMap;
    }

    @Override
    public List<GoodsPackageItemVo> getGoodsProjectId(Long[] projectIds) {
        List<GoodsPackageItemVo> list =new ArrayList<>();

        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.in("project_id",projectIds);
        wrapper.eq("goods_status",1);
        wrapper.eq("is_suit",2);

        List<Goods> goods =this.list(wrapper);
        if(goods.size()==0){
            return list;
        }
        QueryWrapper<GoodsExtend> wrapperGoodsExtend = new QueryWrapper<>();
        for(Goods item:goods){
            wrapperGoodsExtend.or(w->w.eq("goods_id",item.getId()).eq("status",1));
        }
        wrapperGoodsExtend.groupBy("goods_id");
        List<GoodsExtend> goodsExtendList  = goodsExtendService.list(wrapperGoodsExtend);
        goodsExtendList.forEach((GoodsExtend x) ->{
            Goods good = goodsMapper.selectById(x.getGoodsId());
            GoodsProject project  = goodsProjectMapper.selectById(good.getProjectId());
            GoodsPackageItemVo item = new GoodsPackageItemVo();
            item.setBaseSalePrice(good.getBasePrice());
            item.setGoodsExtendId(x.getId());
            item.setGoodsName(good.getGoodsName());
            item.setProjectId(good.getProjectId());
            item.setBaseRetailPrice(good.getRetailPrice());
            item.setGoodsId(good.getId());
            item.setProjectName(project.getName());
            item.setRetailPrice(null);
            item.setSalePrice(null);
            item.setServicePlaceId(good.getServicePlace());
            list.add(item);
        });
        return list;
    }

    @Override
    public ResponseEntity savePackage(AddGoodsPackageParams goodsReqParam, SystemStaffVo staffVo) {
        // 校验请求参数
        GoodsReqParam.validateSaveGoodsReqParam(goodsReqParam.getGoods());
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsReqParam.getGoods(), goods);
        // 判断商品名称是否已存在
        if (this.isGoodsNameExist(goods.getGoodsName())) {
            throw new BusinessException(GeneConstant.BUSINESS_ERROR, GoodsConstants.GoodsResCodeEnum.GOODS_NAME_EXIST.value());
        }
        // 保存商品基本信息
        goods.setGoodsStatus(GoodsConstants.GoodsStatusEnum.OFF_SHELF.value());
        goods.setGoodsType(3);
        goods.setProjectId(10L);
        goods.setCreateUserId(staffVo.getId());
        goods.setCreateUserName(staffVo.getName());
        goods.setCreateTime(new Timestamp(System.currentTimeMillis()));
        goods.setIsSuit(GeneConstant.BYTE_1);
        this.save(goods);
        // 更新商品编码

        // 套装不分时段运营
        goods.setIsByPeriodOperation(2);
        GoodsExtend goodsExtend = this.savePackageGoodsExtends(goods, staffVo);
        goods.setGoodsCode("TZ".concat("-").concat(String.format("%06d", goods.getId())));
        this.updateById(goods);
        syncGoodsPrice(goods.getProjectId());

        // 添加套装明细
        addPackageItems(goodsReqParam.getItems(),goodsExtend.getId(),0L,"套装",goodsReqParam.getProjects());

        return new ResponseEntity();
    }

    @Override
    public ResponseEntity updatePackage(AddGoodsPackageParams goodsReqParam, SystemStaffVo staffVo) {
        goodsReqParam.getGoods().setProjectId(10L);
        goodsReqParam.getGoods().setIsByPeriodOperation(2);
        updateGoods(goodsReqParam.getGoods(),staffVo,staffVo.getToken());

        for (GoodsPackageItemsParams item : goodsReqParam.getItems()) {
            // 更新spu
            GoodsPackageItemBo itemBo = goodsPackageItemService.getById(item.getId());
            itemBo.setSalePrice(item.getSalePrice());
            itemBo.setRetailPrice(item.getRetailPrice());
            goodsPackageItemService.updateById(itemBo);

            // 更新sku
            GoodsExtend sku = goodsExtendService.getById(itemBo.getPackageId());
            sku.setSalePrice(goodsReqParam.getGoods().getBasePrice());
            goodsExtendService.updateById(sku);
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"修改成功");
    }


    // 添加套装明细
    private void addPackageItems(List<GoodsPackageItemsParams> items,Long goodsExtendId,Long projectId,String projectName,List<GoodsProjectParams> projects) {
        List<GoodsPackageItemBo> goodsPackageItemBoList  =  new ArrayList<>();
        items.forEach(x->{

            final Long[] routeId = {0L};
            final String[] placeId = {""};
            final Long[] projectsId={0L};
            final String[] projectsName={""};
            projects.forEach(project->{
                if(project.getProjectId().equals(x.getProjectId())){
                    routeId[0] =project.getRouteCheckedId();
                    projectsId[0]= project.getProjectId();
                    projectsName[0] = project.getProjectName();
                    project.getPlacesCheckedIds().forEach(place->{
                        placeId[0] += place +",";
                    });
                    placeId[0] = placeId[0].substring(0,placeId[0].length()-1);
                }
            });

            GoodsPackageItemBo bo = new GoodsPackageItemBo();
            bo.setBaseRetailPrice(x.getBaseRetailPrice());
            bo.setBaseSalePrice(x.getBaseSalePrice());
            bo.setGoodsExtendId(x.getGoodsExtendId());
            bo.setGoodsId(x.getGoodsId());
            bo.setGoodsName(x.getGoodsName());
            bo.setPackageId(goodsExtendId);
            bo.setProjectId(projectsId[0]);
            bo.setProjectName(projectsName[0]);
            bo.setRetailPrice(x.getRetailPrice());
            bo.setSalePrice(x.getSalePrice());
            bo.setRouteId(routeId[0]);
            bo.setServicePlaceId(placeId[0]);
            goodsPackageItemBoList.add(bo);
        });
        goodsPackageItemService.saveBatch(goodsPackageItemBoList);
    }

    /**
     * 自动同步佰邦达商品和时段信息
     */
    @Scheduled(cron = "0 */1 *  * * * ")
    public void autoBaiBangDaGoods() {
        String str = BaiBangDaHttpApiUtil.getGoodsList();
        com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSONObject.parseObject(str);
        String a = object.get("data").toString();
        com.alibaba.fastjson.JSONObject object1 = com.alibaba.fastjson.JSONObject.parseObject(a);
        String re = object1.get("records").toString();
        com.alibaba.fastjson.JSONArray object2 = com.alibaba.fastjson.JSONObject.parseArray(re);
        for (int i = 0; i < object2.size(); i++) {
            com.alibaba.fastjson.JSONObject object3 = object2.getJSONObject(i);
            String productId = object3.getString("productId");
            String productName = object3.getString("productName");
            String price = object3.getString("price");
            //团客价格作为分销商价格(待佰邦达分销价格统一后调整为正规的分销价格)
            String groupPrice = object3.getString("groupPrice");
            String saleType = object3.getString("saleType");
            String productType = object3.getString("productType");
            String productMinute = object3.getString("productMinute");

            GoodsReqParam goodsReqParam =new GoodsReqParam();

            String projectName = BaiBangDaHttpApiUtil.getGoodsType(productType);
            GoodsProject project = goodsProjectMapper.selectOne(new QueryWrapper<GoodsProject>().eq("name",projectName).eq("project_status","1"));
            if(project==null){
                logger.error("没有此项目:"+productType);
                continue;
            }else{
                goodsReqParam.setProjectId(project.getId());
            }
            goodsReqParam.setGoodsName(productName);
            goodsReqParam.setIsPackage(0);
            goodsReqParam.setBasePrice(new BigDecimal(price));
            goodsReqParam.setSingleServiceDuration(Integer.parseInt(productMinute));
            //团客价格作为分销商价格(待佰邦达分销价格统一后调整为正规的分销价格)
            goodsReqParam.setRetailPrice(new BigDecimal(groupPrice));
            goodsReqParam.setSalesUnit("1");
            List<ServicePlace> servicePlaceList = servicePlaceMapper.selectList(new QueryWrapper<ServicePlace>());
            goodsReqParam.setServicePlace(servicePlaceList.get(0).getId().toString().split(" "));
            goodsReqParam.setMaxNum(1);
            goodsReqParam.setResourceId(4L);
            goodsReqParam.setCategoryId(1L);
            if("SEA".equals(saleType)){
                goodsReqParam.setIsByPeriodOperation(1);
            }else{
                goodsReqParam.setIsByPeriodOperation(1);
            }
            goodsReqParam.setServiceRoute(1L);
            List<String> statusList = new ArrayList<>();
            statusList.add("1");
            statusList.add("2");
            Goods goods =goodsMapper.selectOne(new QueryWrapper<Goods>().eq("goods_name",productName).in("goods_status",statusList));
            if(goods==null){
                saveGoodsBBD(goodsReqParam);
                goods =goodsMapper.selectOne(new QueryWrapper<Goods>().eq("goods_name",productName).in("goods_status",statusList));
            }
            if("SEA".equals(saleType)){
                goodsRelatedBBDMapper.delete(new QueryWrapper<GoodsRelatedBBDBo>().eq("bbd_goods_id",productId));
                GoodsRelatedBBDBo goodsRelatedBBDBo = new GoodsRelatedBBDBo();
                goodsRelatedBBDBo.setBbdGoodsId(productId);
                goodsRelatedBBDBo.setGoodsId(goods.getId());
                goodsRelatedBBDMapper.insert(goodsRelatedBBDBo);
//                List<GoodsExtend> goodsExtends = goodsExtendMapper.selectList(new QueryWrapper<GoodsExtend>().eq("goods_id",goods.getId()).eq("status",1));
//                if(goodsExtends==null||goodsExtends.size()==0){
//                    continue;
//                }
//                List<GoodsRelatedBBDBo> list =goodsRelatedBBDMapper.selectList(new QueryWrapper<GoodsRelatedBBDBo>().eq("goods_id",goodsExtends.get(0).getId()).eq("bbd_goods_id",productId));
//                if(list==null||list.size()==0){
//                    GoodsRelatedBBDBo goodsRelatedBBDBo = new GoodsRelatedBBDBo();
//                    goodsRelatedBBDBo.setBbdGoodsId(productId);
//                    goodsRelatedBBDBo.setGoodsId(goodsExtends.get(0).getId());
//                    goodsRelatedBBDMapper.insert(goodsRelatedBBDBo);
//                }
            }
        }
    }

    //@Scheduled(cron = "0 */1 *  * * * ")
    public void autoBaiBangPeriod() throws ParseException {

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        for (Integer i =0 ;i<3;i++){
            c.setTime(date);
            c.add(Calendar.HOUR,24*i);
            Date targetDate = c.getTime();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String day = sdf.format(targetDate);
            autoBaiBangPeriod(day);
        }
    }


    /**
     * 日期
     * @param time
     * @throws ParseException
     */
    public void autoBaiBangPeriod(String time) throws ParseException {
        String str = BaiBangDaHttpApiUtil.getGoodsList();
        Map<String, Object> ticketMap = MallUtil.toMap(str);
        Map<String, Object> dataMap = (Map<String, Object>) ticketMap.get("data");
        if (dataMap == null || dataMap.size() == 0) {
            return;
        }
        List<Map<String, Object>> productList = (List<Map<String, Object>>) dataMap.get("records");
        if (productList == null || productList.size() == 0) {
            return;
        }
        for (Map<String, Object> map : productList) {
            //region 处理过程
            String saleType = map.get("saleType").toString().replaceAll("\"", "");
            // 非排班产品，返回
            if ("SEA".equals(saleType)) {
                continue;
            }
            String id = map.get("productId").toString();
            id = id.replace("\"", "");
            Map<String, Object> flightMap = MallUtil.toMap(BaiBangDaHttpApiUtil.getGoodsFlightList(time, "0100", id));
            List<Map<String, Object>> list = (List<Map<String, Object>>) flightMap.get("data");
            // 无排班返回
            if (list.size() == 0 || list.isEmpty()) {
                continue;
            }
            String productTypeId = map.get("productType").toString();
            String productTypeName = BaiBangDaHttpApiUtil.getGoodsType(productTypeId);
            QueryWrapper<GoodsProject> wrapper = new QueryWrapper<>();
            wrapper.eq("name", productTypeName);
            wrapper.last(" limit 1");
            GoodsProject project = goodsProjectService.getOne(wrapper);
            // 项目不存在，返回
            if (project == null) {
                continue;
            }
            //1 创建时段
            List<String> startTimeList = new ArrayList<>();
            for (Map<String, Object> item : list) {
                String minute = map.get("productMinute").toString();
                String startTime = item.get("departureTime").toString();
                String endTime = getEndTime(startTime, minute);
                String flightId =item.get("id").toString();
                String timeLabel = startTime + "-" + endTime;
                if (!startTimeList.contains(timeLabel+"-"+flightId)) {
                    startTimeList.add(timeLabel+"-"+flightId);
                }
            }
            // 商品不存在，创建商品
            QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
            goodsQueryWrapper.eq("goods_name", map.get("productName")).eq("goods_status",1).last(" limit 1");
            Goods goods = goodsService.getOne(goodsQueryWrapper);
            // 添加商品
            if (goods == null) {
                saveGoods(map, project, goods);
            }
            // 添加时段
            for (String times : startTimeList) {
                QueryWrapper<GoodsProjectPeriod> periodQueryWrapper = new QueryWrapper<>();
                periodQueryWrapper.eq("project_id", project.getId()).eq("title", times.split("-")[0]+"-"+times.split("-")[1]).last(" limit 1");
                GoodsProjectPeriod period = goodsProjectPeriodService.getOne(periodQueryWrapper);
                if (period == null) {
                    savePeriod(project, times);
                }
            }
            // 根据产品列表 和 时段列表 初始化 关联关系
            goodsQueryWrapper = new QueryWrapper<>();
            goodsQueryWrapper.eq("project_id", project.getId());
            List<Goods> goodsList = goodsService.list(goodsQueryWrapper);
            QueryWrapper<GoodsProjectPeriod> periodQueryWrapper = new QueryWrapper<>();
            periodQueryWrapper.eq("project_id", project.getId());
            List<GoodsProjectPeriod> periodList = goodsProjectPeriodService.list(periodQueryWrapper);
            //添加sku
            for (Goods item : goodsList) {
                for (GoodsProjectPeriod period : periodList) {
                    saveGoodsExtend(map, item, period);
                }
            }
            // 和佰邦达关联
            QueryWrapper<GoodsExtend> goodsExtendQueryWrapper = new QueryWrapper<>();
            goodsExtendQueryWrapper.eq("goods_id", goods.getId()).eq("status","1");
            List<GoodsExtend> goodsExtendList = goodsExtendService.list(goodsExtendQueryWrapper);
            for (GoodsExtend extend : goodsExtendList) {
                for(String item :startTimeList){
                    // 和佰邦达建立关系，没有就创建，有就更新
                    createRelationToBaiBangDa(extend, item,time);
                }
            }

            // 关闭当天无效时段
            updateProjectOperation(time,goods.getId());
        }
    }

    /**
     * 建立时段
     * @param project
     * @param times
     */
    private void savePeriod(GoodsProject project, String times) {
        GoodsProjectPeriod goodsProjectPeriod = new GoodsProjectPeriod();
        goodsProjectPeriod.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        goodsProjectPeriod.setUpdateUserId(-1L);
        goodsProjectPeriod.setUpdateUserName("wzdadmin");
        goodsProjectPeriod.setCreateTime(new Timestamp(System.currentTimeMillis()));
        goodsProjectPeriod.setCreateUserId(-1L);
        goodsProjectPeriod.setCreateUserName("wzdadmin");
        goodsProjectPeriod.setProjectId(project.getId());
        goodsProjectPeriod.setStatus(GeneConstant.BYTE_1);
        String[] time = times.split("-");
        goodsProjectPeriod.setEndTime(time[1]);
        goodsProjectPeriod.setOrderby(GeneConstant.BYTE_1);
        goodsProjectPeriod.setStartTime(time[0]);
        goodsProjectPeriod.setTitle(time[0]+"-"+time[1]);
        goodsProjectPeriodService.save(goodsProjectPeriod);
    }

    /**
     * 保存商品
     * @param map
     * @param project
     * @param goods
     */
    private void saveGoods(Map<String, Object> map, GoodsProject project, Goods goods) {
        goods.setProjectId(project.getId());
        goods.setServicePlace("1");
        goods.setServiceRoute("1");
        goods.setIsSuit(GeneConstant.BYTE_2);
        goods.setIsByPeriodOperation(2);
        goods.setGoodsStatus(GeneConstant.BYTE_2);
        goods.setCreateTime(new Timestamp(System.currentTimeMillis()));
        goods.setGoodsStatus(GeneConstant.BYTE_1);
        goods.setGoodsCode(project.getProjectCode().concat("-").concat(String.format("%06d", goods.getId())));
        goods.setGoodsType(1);
        goods.setCreateUserId(-1L);
        goods.setCreateUserName("wzdadmin");
        goods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        goods.setUpdateUserId(-1L);
        goods.setUpdateUserName("wzdadmin");
        goods.setGoodsName(map.get("productName").toString());
        goods.setCategoryId(1L);
        BigDecimal price = BigDecimal.valueOf(Double.valueOf(map.get("price").toString()));
        goods.setBasePrice(price);
        goods.setDayOperationTime(80);
        goods.setDescription("");
        goods.setImg("");
        goods.setIsPackage(0);
        goods.setMaxNum(10);
        goods.setMaxServiceAmount(10);
        goods.setResourceId(1L);
        goods.setRetailPrice(price);
        goods.setRules("");
        goods.setSalesUnit("");
        goods.setSingleServiceDuration(30);
        goods.setSynopsis("{}");
        goodsService.save(goods);
    }

    /**
     * 添加sku
     * @param map
     * @param item
     * @param period
     */
    private void saveGoodsExtend(Map<String, Object> map, Goods item, GoodsProjectPeriod period) {
        QueryWrapper<GoodsExtend> goodsExtendQueryWrapper = new QueryWrapper<>();
        goodsExtendQueryWrapper.eq("goods_id", item.getId());
        goodsExtendQueryWrapper.eq("period_id", period.getId());
        goodsExtendQueryWrapper.last(" limit 1");
        GoodsExtend goodsExtend = goodsExtendService.getOne(goodsExtendQueryWrapper);

        if (goodsExtend == null) {
            goodsExtend = new GoodsExtend();
            BigDecimal price = BigDecimal.valueOf(Double.valueOf(map.get("price").toString()));
            goodsExtend.setSalePrice(price);
            goodsExtend.setStatus(GeneConstant.BYTE_1);
            goodsExtend.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            goodsExtend.setUpdateUserId(-1L);
            goodsExtend.setUpdateUserName("wzdadmin");
            goodsExtend.setCreateTime(new Timestamp(System.currentTimeMillis()));
            goodsExtend.setTimeLabel("南湾海洋运动公园");
            goodsExtend.setCreateUserId(-1L);
            goodsExtend.setCreateUserName("wzdadmin");
            goodsExtend.setTimespan(period.getTitle());
            goodsExtend.setPeriodId(period.getId());
            goodsExtend.setMaxNum(10);
            goodsExtend.setDuration(60);
            goodsExtend.setStatus(GeneConstant.BYTE_1);
            goodsExtend.setGoodsId(item.getId());
            goodsExtendService.save(goodsExtend);
        }
    }

    /**
     * 关联佰邦达
     * @param extend
     * @param item format "startTime-endTime-flightId"
     */
    private void createRelationToBaiBangDa(GoodsExtend extend, String item,String time) {
        String startTime = item.split("-")[0];
        String endTime = item.split("-")[1];
        String flightId = item.split("-")[2];
        String timeSpan = startTime+"-"+endTime;
        if(timeSpan.equals(extend.getTimespan())){
            QueryWrapper<GoodsRelatedBBDBo> bbdQueryWrapper = new QueryWrapper<>();
            bbdQueryWrapper.eq("goods_id", extend.getId()).eq("goods_day",time).last(" limit 1");
            GoodsRelatedBBDBo relatedBBDBo = goodsRelatedBBDMapper.selectOne(bbdQueryWrapper);
            if(relatedBBDBo==null){
                relatedBBDBo = new GoodsRelatedBBDBo();
                relatedBBDBo.setGoodsId(extend.getId());
                relatedBBDBo.setBbdGoodsId(flightId);
                relatedBBDBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
                relatedBBDBo.setCreateUserId(-1L);
                relatedBBDBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                relatedBBDBo.setUpdateUserId(-1L);
                relatedBBDBo.setGoodsDay(time);
                goodsRelatedBBDMapper.insert(relatedBBDBo);
            }else{
                relatedBBDBo.setGoodsDay(time);
                relatedBBDBo.setBbdGoodsId(flightId);
                relatedBBDBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                goodsRelatedBBDMapper.updateById(relatedBBDBo);
            }
        }
    }

    /**
     * 计算结束时间
     * @param startTime
     * @param minute
     * @return
     * @throws ParseException
     */
    private static String getEndTime(String startTime,String minute) throws ParseException {


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = sdf.parse("1970-01-01 "+startTime);
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE,Integer.parseInt(minute));

        sdf=new SimpleDateFormat("HH:mm");

        return sdf.format(c.getTime());
    }

    /**
     * 关闭当天无效时段
     * @param date
     * @param goodsId
     */
    private void updateProjectOperation(String date,Long goodsId){

        QueryWrapper<GoodsExtend> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",goodsId).eq("status",1);
        List<GoodsExtend> list = goodsExtendService.list(wrapper);

        QueryWrapper<GoodsRelatedBBDBo> goodsRelatedBBDBoQueryWrapper  = new QueryWrapper<>();
        for (GoodsExtend item : list) {
            goodsRelatedBBDBoQueryWrapper.eq("goods_id",item.getId()).eq("goods_day",date);
            goodsRelatedBBDBoQueryWrapper.or();
        }
        List<GoodsRelatedBBDBo> goodsRelatedBBDBoList  = goodsRelatedBBDMapper.selectList(goodsRelatedBBDBoQueryWrapper);

        // 如果不存在排班，禁用今天时段
        if(goodsRelatedBBDBoList.isEmpty()){
            for (GoodsExtend goodsExtend : list) {
                QueryWrapper<GoodsProjectOperationBo> goodsProjectPeriodQueryWrapper = new QueryWrapper<>();
                goodsProjectPeriodQueryWrapper.eq("period_id",goodsExtend.getPeriodId()).eq("operation_date",date).last(" limit 1 ");
                GoodsProjectOperationBo period = goodsProjectOperationMapper.selectOne(goodsProjectPeriodQueryWrapper);
                if(period != null ){
                    period.setStatus(GeneConstant.BYTE_3);
                    goodsProjectOperationMapper.updateById(period);
                }
            }
        }

        // 存在排班，计算可用时段
        for (GoodsExtend goodsExtend : list) {
            Boolean canUse = false;
            for (GoodsRelatedBBDBo goodsRelatedBBDBo : goodsRelatedBBDBoList) {
                if(goodsRelatedBBDBo.getGoodsId().equals(goodsExtend.getId())){
                    canUse = true;
                }
            }
            if(!canUse){
                QueryWrapper<GoodsProjectOperationBo> goodsProjectPeriodQueryWrapper = new QueryWrapper<>();
                goodsProjectPeriodQueryWrapper.eq("period_id",goodsExtend.getPeriodId()).eq("operation_date",date).last(" limit 1 ");
                GoodsProjectOperationBo period = goodsProjectOperationMapper.selectOne(goodsProjectPeriodQueryWrapper);
                if(period != null ){
                    period.setStatus(GeneConstant.BYTE_3);
                    goodsProjectOperationMapper.updateById(period);
                }
            }
        }
    }

    /**
     * 自动同步票付通商品（票，不分时段）
     */
    //@Scheduled(cron = "0 */5 * * * * ")
    public void autoPiaoFuTongGoods() {
        logger.info("自动同步票付通商品（票，不分时段）--开始");
        //获取票付通票数据
        List<Object> resultList  = NxjSoapUtil.getGoodList();

        for (int i = 0; i < resultList.size(); i++) {
            Object obj = resultList.get(i);
            com.alibaba.fastjson.JSONObject object = (JSONObject)com.alibaba.fastjson.JSONObject.toJSON(obj);

            String productId = object.get("UUid").toString();
            logger.info("自动同步票付通商品--productId："+productId);
            String productName = object.get("UUtitle").toString();
            logger.info("自动同步票付通商品--productName："+productName);
            String price = object.get("UUtprice").toString();
            //todo 待确定
            String groupPrice = object.get("UUtprice").toString();
            //String saleType = object.get("UUbuy_limit").toString();
            String productType = object.get("UUbuy_limit_date").toString();
            String productMinute = object.get("UUpay").toString();
            String goodsStatus = object.get("UUstatus").toString();

            GoodsReqParam goodsReqParam =new GoodsReqParam();

            // 确定projectName和projectId
            String projectName = "门票";
            GoodsProject project = goodsProjectMapper.selectOne(new QueryWrapper<GoodsProject>().eq("name",projectName).eq("project_status","1"));
            if(project==null){
                logger.error("没有此项目:"+productType);
                continue;
            }else{
                goodsReqParam.setProjectId(project.getId());
            }
            //设置上下架状态
            if(goodsStatus.equals("0")){
                goodsReqParam.setGoodsStatus((byte)1);
            }else {
                goodsReqParam.setGoodsStatus((byte)2);
            }
            goodsReqParam.setGoodsName(productName);
            goodsReqParam.setIsPackage(0);
            goodsReqParam.setBasePrice(new BigDecimal(price));
            goodsReqParam.setSingleServiceDuration(Integer.parseInt(productMinute));
            //todo 确定分销商价格
            goodsReqParam.setRetailPrice(new BigDecimal(groupPrice));
            goodsReqParam.setSalesUnit("1");
            //确定ServicePlace
            List<ServicePlace> servicePlaceList = servicePlaceMapper.selectList(new QueryWrapper<ServicePlace>());
            goodsReqParam.setServicePlace(servicePlaceList.get(0).getId().toString().split(" "));
            goodsReqParam.setMaxNum(1);
            goodsReqParam.setResourceId(4L);
            goodsReqParam.setCategoryId(1L);
            //设置不分时段
            goodsReqParam.setIsByPeriodOperation(2);
            goodsReqParam.setServiceRoute(1L);
            List<String> statusList = new ArrayList<>();
            statusList.add("1");
            statusList.add("2");
            Goods goods =goodsMapper.selectOne(new QueryWrapper<Goods>().eq("goods_name",productName).in("goods_status",statusList));
            if(goods==null){
                saveGoodsBBD(goodsReqParam);
                goods =goodsMapper.selectOne(new QueryWrapper<Goods>().eq("goods_name",productName).in("goods_status",statusList));
            }
            goodsRelatedBBDMapper.delete(new QueryWrapper<GoodsRelatedBBDBo>().eq("bbd_goods_id",productId));
            GoodsRelatedBBDBo goodsRelatedBBDBo = new GoodsRelatedBBDBo();
            goodsRelatedBBDBo.setBbdGoodsId(productId);
            goodsRelatedBBDBo.setGoodsId(goods.getId());
            goodsRelatedBBDMapper.insert(goodsRelatedBBDBo);
            //在goods_extends表中保存完整的json格式数据
            List<GoodsExtend> goodsExtends = goodsExtendMapper.selectList(new QueryWrapper<GoodsExtend>().eq("goods_id",goods.getId()).orderByAsc("id"));
            if(CollectionUtils.isEmpty(goodsExtends)){
                GoodsExtend goodsExtend = new GoodsExtend();
                goodsExtend.setGoodsId(goods.getId());
                goodsExtend.setSalePrice(new BigDecimal(price));
                goodsExtend.setTimespan("");
                goodsExtend.setStatus((byte)1);
                goodsExtend.setShipLineInfo(object.toJSONString());
                goodsExtendMapper.insert(goodsExtend);
            } else {
                GoodsExtend goodsExtend =goodsExtends.get(0);
                goodsExtend.setShipLineInfo(object.toJSONString());
                goodsExtendMapper.updateById(goodsExtend);
            }
        }
    }

    @Override
    public void updateGoodsOrderBy(Long id, Integer orderBy) {
        goodsMapper.updateGoodsOrderBy(id,orderBy);

    }
}
