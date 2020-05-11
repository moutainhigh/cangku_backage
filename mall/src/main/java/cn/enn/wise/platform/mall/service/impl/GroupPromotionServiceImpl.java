package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.Goods;
import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.bo.GroupPromotionBo;
import cn.enn.wise.platform.mall.bean.bo.group.GroupPromotionGoodsBo;
import cn.enn.wise.platform.mall.bean.param.GroupPromotionParam;
import cn.enn.wise.platform.mall.bean.param.PromotionInvalidParam;
import cn.enn.wise.platform.mall.bean.param.PromotionParam;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.GroupPromotionDetailVo;
import cn.enn.wise.platform.mall.bean.vo.GroupPromotionGoodsVo;
import cn.enn.wise.platform.mall.bean.vo.GroupPromotionVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.GroupPromotionService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 活动请求参数
 * @author anhui
 * @since 2019/9/12
 */
@Service
public class GroupPromotionServiceImpl extends ServiceImpl<GroupPromotionMapper, GroupPromotionBo> implements GroupPromotionService {


    @Resource
    GroupPromotionGoodsMapper groupPromotionGoodsMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    GroupRuleMapper groupRuleMapper;

    @Resource
    GoodsProjectMapper goodsProjectMapper;

    @Value("${companyId}")
    private String companyId;

    @Override
    public ResponseEntity<ResPageInfoVO<List<GroupPromotionVo>>> listByPage(ReqPageInfoQry<GroupPromotionParam> param) {
        GroupPromotionParam  params =param.getReqObj();
        QueryWrapper<GroupPromotionBo> wrapper = new QueryWrapper<>();

        if(params!=null){
            // 名称
            if(StringUtils.isNotEmpty(params.getName())){
                wrapper.like("name",params.getName());
            }
            // 负责人
            if(StringUtils.isNotEmpty(params.getManager())){
                wrapper.like("group_manager",params.getManager());
            }
            // 活动类型
            if(params.getPromotionType() != null && params.getPromotionType().size()>0){
                wrapper.in("group_type",params.getPromotionType());
            }
            // 状态
            if(params.getStatus()!=null && params.getStatus().size()>0){
                wrapper.in("status",params.getStatus());
            }
            // 开始时间
            if(StringUtils.isNotEmpty(params.getStartTime())){
                wrapper.ge("start_time",params.getStartTime());

            }
            // 结束时间
            if(StringUtils.isNotEmpty(params.getEndTime())){
                wrapper.le("end_time",params.getEndTime());
            }
        }

        // mybatis 查询数据
        Page<GroupPromotionBo> pageInfo = new Page<>(param.getPageNum(),param.getPageSize());
        wrapper.orderByDesc("id");
        IPage<GroupPromotionBo> promotionBoList = this.page(pageInfo,wrapper);

        //分页
        ResPageInfoVO resPageInfoVO = new ResPageInfoVO();
        if(promotionBoList==null){
            resPageInfoVO.setPageNum(0L);
            resPageInfoVO.setPageSize(0L);
            resPageInfoVO.setTotal(0L);
            return new ResponseEntity<ResPageInfoVO<List<GroupPromotionVo>>>(resPageInfoVO);
        }
        // 分页
        resPageInfoVO.setPageNum(promotionBoList.getCurrent());
        resPageInfoVO.setPageSize(promotionBoList.getSize());
        resPageInfoVO.setTotal(promotionBoList.getTotal());

        List<GroupPromotionVo> list = getPromotionVoList(promotionBoList.getRecords());

        // 如果无数据则直接返回
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<ResPageInfoVO<List<GroupPromotionVo>>>(resPageInfoVO);
        }
        resPageInfoVO.setRecords(list);
        return new ResponseEntity<>(resPageInfoVO);
    }

    @Override
    public ResponseEntity<GroupPromotionDetailVo> getPromotionById(Integer id) throws Exception {
        GroupPromotionBo bo = this.getById(id);
        return new ResponseEntity<>(getGroupPromotionDetailVo(bo));
    }

    /**
     * 获取活动详情
     * @param record
     * @return
     * @throws Exception
     */
    private GroupPromotionDetailVo getGroupPromotionDetailVo(GroupPromotionBo record) throws Exception {
        if (record == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GroupPromotionDetailVo promotionVo = new GroupPromotionDetailVo();
        promotionVo.setCode(record.getCode());
        promotionVo.setCost(record.getCost().toString());
        promotionVo.setCreateBy(record.getCreateBy().toString());
        promotionVo.setCreateTime(sdf.format(record.getCreateTime()));
        promotionVo.setEndTime(sdf.format(record.getEndTime()));
        QueryWrapper<GroupPromotionGoodsBo> wrapper = new QueryWrapper<>();
        wrapper.eq("group_promotion_id", record.getId());
        promotionVo.setGoodsCount(groupPromotionGoodsMapper.selectCount(wrapper));
        promotionVo.setGroupRuleId(record.getRuleId());
        promotionVo.setId(record.getId());
        promotionVo.setManager(record.getGroupManager());
        promotionVo.setName(record.getName());
        promotionVo.setStartTime(sdf.format(record.getStartTime()));
        promotionVo.setStatus(record.getStatus().toString());
        promotionVo.setType(record.getGroupType());
        promotionVo.setRemark(record.getRemark());
        promotionVo.setOrgName(record.getOrgName());
        promotionVo.setRejectPromotion(record.getRejectPromotion());
        promotionVo.setPromotionRejectStatus(record.getPromotionRejectStatus());
        promotionVo.setPromotionCrowdStatus(record.getPromotionCrowdStatus());
        promotionVo.setCrowdPromotion(record.getCrowdPromotion());
        promotionVo.setReason(record.getReason());

        promotionVo.setRuleInfo(groupRuleMapper.selectById(record.getRuleId()));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("group_promotion_id", record.getId());
        List<GroupPromotionGoodsBo> goodsList = groupPromotionGoodsMapper.selectList(queryWrapper);

        List<GroupPromotionGoodsVo> goodsVoList = new ArrayList<>();

        for (GroupPromotionGoodsBo goods : goodsList) {
            GroupPromotionGoodsVo vo = new GroupPromotionGoodsVo();
            Goods g = goodsMapper.selectById(goods.getGoodsId());
            GoodsProject project = goodsProjectMapper.selectById(g.getProjectId());
            vo.setProjectName(project.getName());
            vo.setPinPrice(goods.getGroupPrice());
            vo.setGoodsCost(goods.getGoodsCost());
            vo.setGoodsId(goods.getGoodsId());
            vo.setGoodsName(goods.getGoodsName());
            vo.setGoodsNum(goods.getGoodsNum());
            vo.setGoodsPrice(goods.getGoodsPrice());
            vo.setGroupPrice(goods.getGroupPrice());
            vo.setGroupPromotionId(goods.getGroupPromotionId());
            vo.setId(goods.getId());
            vo.setProjectId(goods.getProjectId());
            vo.setRetailPrice(goods.getRetailPrice());
            vo.setGoodsCode(goods.getGoodsNum());
            vo.setBasePrice(goods.getGoodsPrice());
            vo.setGoodsStatus(g.getGoodsStatus().toString());
            goodsVoList.add(vo);
        }
        promotionVo.setGoodsList(goodsVoList);
        return promotionVo;
    }

    @Override
    public ResponseEntity addPromotion(GroupPromotionParam param) throws ParseException {

        // 1 添加基本信息
        GroupPromotionBo promotion;
        if(param.getId()==0){
            //#region 添加活动
            // 1 验证名称
            QueryWrapper<GroupPromotionBo> wrapper = new QueryWrapper<>();
            wrapper.eq("name",param.getName());

            List<GroupPromotionBo> promotionBoList = this.list(wrapper);
            if(promotionBoList.size()>0){
                return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"名称已经存在",null);
            }

            // 2 验证商品
            for(GroupPromotionGoodsVo good: param.getGoodsList()){
                Map<String,Object>  totalMap= groupPromotionGoodsMapper.listPromotionGoodsByGoodsId(good.getGoodsId(),0L,param.getStartTime(),param.getEndTime());
                if(totalMap!=null && totalMap.get("total")!=null){
                    Long total = Long.parseLong(totalMap.get("total").toString());
                    if(total>0){
                        return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"同一商品不能同时存在于两个活动",null);
                    }
                }
            }
            // 2.1 添加活动
            promotion = addGroupPromotionBo(param);
            // 2.2 添加商品
            for(GroupPromotionGoodsVo vo: param.getGoodsList()){
                addPromotionGoodsVo(promotion, vo);
            }

            //endregion
        }else{

            //#region 编辑活动
            // 1 更新活动
            //TODO 配置从配置文件里读取
            //TODO 代码超过80行，有重复代码
            promotion = updateGroupPromotionBo(param);

            // 0 获取现有商品列表
            QueryWrapper<GroupPromotionGoodsBo> wrapper = new QueryWrapper<>();
            wrapper.eq("group_promotion_id",param.getId());
            List<GroupPromotionGoodsBo> groupPromotionGoodsBos = groupPromotionGoodsMapper.selectList(wrapper);
            // 0.1 删除现有的
            for(GroupPromotionGoodsBo groupPromotionGoodsBo:groupPromotionGoodsBos){
                groupPromotionGoodsMapper.deleteById(groupPromotionGoodsBo.getId());
            }

            // 1 记录新增商品
            List<GroupPromotionGoodsVo> addList = param.getGoodsList();

            // 1.0 验证
            for(GroupPromotionGoodsVo good: addList){
                Map<String,Object>  totalMap= groupPromotionGoodsMapper.listPromotionGoodsByGoodsId(good.getGoodsId(),param.getId(),param.getStartTime(),param.getEndTime());
                if(totalMap!=null && totalMap.get("total")!=null){
                    Long total = Long.parseLong(totalMap.get("total").toString());
                    if(total>0){
                        return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"商品Id"+good.getId()+"不能同时存在两个时段",null);
                    }
                }
            }

            // 2.2 添加商品
            for(GroupPromotionGoodsVo vo: addList){
                addPromotionGoodsVo(promotion, vo);
            }
            //endregion
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"操作成功",null);
    }

    /**
     * 更新活动
     * @param param
     * @return
     * @throws ParseException
     */
    private GroupPromotionBo updateGroupPromotionBo(GroupPromotionParam param) throws ParseException {
        GroupPromotionBo promotion;
        promotion = this.getById(param.getId());
        promotion.setCompanyId(Long.parseLong(companyId));
        promotion.setCompanyName("");
        promotion.setCost(param.getCost());
        promotion.setCreateBy(-1L);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        promotion.setStartTime(new Timestamp(simpleDateFormat.parse(param.getStartTime()).getTime()));
        promotion.setEndTime(new Timestamp(simpleDateFormat.parse(param.getEndTime()).getTime()));

        promotion.setGroupManager(param.getManager());
        promotion.setGroupType(param.getPromotionType().get(0).toString());
        promotion.setRuleId(Long.valueOf(param.getRuleId()));
        promotion.setName(param.getName());
        promotion.setScenicSpots(param.getScenicSpots());
        promotion.setRejectPromotion(JSON.toJSONString(param.getRejectPromotion()));
        promotion.setPromotionRejectStatus(param.getPromotionReject());
        promotion.setPromotionCrowdStatus(param.getPromotionCrowd());
        promotion.setCrowdPromotion(JSON.toJSONString(param.getRejectPromotion()));
        promotion.setReason(param.getReason());
        promotion.setRemark(param.getRemark());
        String orgName = "";
        for(String name :param.getOrgName()){
            orgName+=name+",";
        }
        promotion.setOrgName(orgName);
        this.saveOrUpdate(promotion);
        return promotion;
    }

    /**
     * 添加活动
     * @param param
     * @return
     * @throws ParseException
     */
    private GroupPromotionBo addGroupPromotionBo(GroupPromotionParam param) throws ParseException {
        GroupPromotionBo promotion;
        promotion = new GroupPromotionBo();
        // TODO 从配置文件读取,2019.11.11 安辉修复
        promotion.setCompanyId(Long.parseLong(companyId));
        promotion.setCompanyName("");
        promotion.setCost(param.getCost());
        promotion.setCreateBy(-1L);
        promotion.setRemark(param.getRemark());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        promotion.setStartTime(new Timestamp(sdf.parse(param.getStartTime()).getTime()));
        promotion.setEndTime(new Timestamp(sdf.parse(param.getEndTime()).getTime()));
        promotion.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Byte status = new Byte(String.valueOf(1));
        promotion.setStatus(status);
        promotion.setGroupManager(param.getManager());
        promotion.setGroupType(param.getPromotionType().get(0).toString());
        promotion.setRuleId(Long.valueOf(param.getRuleId()));
        promotion.setName(param.getName());
        promotion.setScenicSpots(param.getScenicSpots());
        promotion.setRejectPromotion(JSON.toJSONString(param.getRejectPromotion()));
        promotion.setPromotionRejectStatus(param.getPromotionReject());
        promotion.setPromotionCrowdStatus(param.getPromotionCrowd());
        promotion.setCrowdPromotion(JSON.toJSONString(param.getRejectPromotion()));
        promotion.setReason(param.getReason());
        QueryWrapper<GroupPromotionBo> promotionBoQueryWrapper = new QueryWrapper<>();
        Integer count =this.baseMapper.selectCount(promotionBoQueryWrapper)+1;
        String code ="M00"+count;
        promotion.setCode(code);
        String orgName = "";
        for(String name :param.getOrgName()){
            orgName+=name+",";
        }
        promotion.setOrgName(orgName);
        this.saveOrUpdate(promotion);
        return promotion;
    }

    private void addPromotionGoodsVo(GroupPromotionBo promotion, GroupPromotionGoodsVo vo) {
        GroupPromotionGoodsBo goodsBo = new GroupPromotionGoodsBo();
        Goods goods = goodsMapper.selectById(vo.getGoodsId());

        goodsBo.setGoodsCost(goods.getBasePrice());
        goodsBo.setGoodsId(goods.getId());
        goodsBo.setGoodsName(goods.getGoodsName());
        goodsBo.setGoodsNum(goods.getGoodsCode());
        goodsBo.setGoodsPrice(goods.getBasePrice());
        goodsBo.setGroupPromotionId(promotion.getId());
        goodsBo.setGroupPrice(goods.getBasePrice());
        goodsBo.setRetailPrice(goods.getRetailPrice());
        goodsBo.setGroupPrice(vo.getPinPrice());
        // TODO project ??
        goodsBo.setProjectId(goods.getProjectId());

        groupPromotionGoodsMapper.insert(goodsBo);
    }

    @Override
    public ResponseEntity invalid(PromotionInvalidParam param) {

        try {
            List<GroupPromotionBo> list= this.baseMapper.selectBatchIds(param.getIds());
            for(GroupPromotionBo item:list){
                item.setStatus(new Byte("4"));
                item.setReason(param.getReason());
                this.updateById(item);
            }
            return new ResponseEntity<>(GeneConstant.SUCCESS_CODE,"更新成功");
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,"更新失败");
        }
    }

    @Override
    public ResponseEntity<List<GroupPromotionVo>> listByFilter(PromotionParam params) {

        QueryWrapper<GroupPromotionBo> wrapper = new QueryWrapper<>();
        if(params!=null){
            // 名称
            if(StringUtils.isNotEmpty(params.getName())){
                wrapper.like("name",params.getName());
            }
            // id
            if(params.getId()!=0){
                wrapper.ne("id",params.getId());
            }
            // 活动类型
            if(params.getPromotionType() != null && params.getPromotionType().size()>0){
                wrapper.in("group_type",params.getPromotionType());
            }
            // 状态
            if(params.getStatus()!=null && params.getStatus().size()>0){
                wrapper.in("status",params.getStatus());
            }
        }

        // mybatis 查询数据
        wrapper.orderByDesc("id");
        return new ResponseEntity(this.baseMapper.selectList(wrapper));
    }

    @Override
    public List<GroupPromotionGoodsBo> listActivePromotionGoodsList() {
        List<GroupPromotionGoodsBo> result = new ArrayList<>();
        QueryWrapper<GroupPromotionBo> wrapper = new QueryWrapper<>();
        wrapper.eq("status",2);
        List<GroupPromotionBo> list = this.list(wrapper);
        for (GroupPromotionBo x:list) {
            QueryWrapper<GroupPromotionGoodsBo> goodsWrapper = new QueryWrapper<>();
            goodsWrapper.eq("group_promotion_id", x.getId());
            result.addAll(groupPromotionGoodsMapper.selectList(goodsWrapper));
        }
        return result;
    }

    /**
     * 组装Vo类
     * @param records
     * @return
     */
    private List<GroupPromotionVo> getPromotionVoList(List<GroupPromotionBo> records) {
        List<GroupPromotionVo> promotionVoList =new ArrayList<>();
        for (GroupPromotionBo record:records){
            GroupPromotionVo promotionVo = getGroupPromotionVo(record);
            promotionVoList.add(promotionVo);
        }
        return promotionVoList;
    }

    /**
     * 转化实体类
     * @param record
     * @return
     */
    private GroupPromotionVo getGroupPromotionVo(GroupPromotionBo record) {
        if(record==null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GroupPromotionVo promotionVo = new GroupPromotionVo();
        promotionVo.setCode(record.getCode());
        promotionVo.setCost(record.getCost().toString());
        promotionVo.setCreateBy(record.getCreateBy().toString());
        promotionVo.setCreateTime(sdf.format(record.getCreateTime()));
        promotionVo.setEndTime( sdf.format(record.getEndTime()));
        QueryWrapper<GroupPromotionGoodsBo> wrapper = new QueryWrapper<>();
        wrapper.eq("group_promotion_id",record.getId());
        promotionVo.setGoodsCount(groupPromotionGoodsMapper.selectCount(wrapper));
        promotionVo.setGroupRuleId(record.getRuleId());
        promotionVo.setId(record.getId());
        promotionVo.setManager(record.getGroupManager());
        promotionVo.setName(record.getName());
        promotionVo.setStartTime(sdf.format(record.getStartTime()));
        promotionVo.setStatus(record.getStatus().toString());
        promotionVo.setType(record.getGroupType());
        promotionVo.setRemark(record.getRemark());
        return promotionVo;
    }
}
