package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.PromotionAndGoodsBo;
import cn.enn.wise.platform.mall.bean.bo.PromotionBo;
import cn.enn.wise.platform.mall.bean.bo.group.GroupPromotionGoodsBo;
import cn.enn.wise.platform.mall.bean.param.GroupPromotionParam;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.GroupPromotionVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.PromotionAndGoodsService;
import cn.enn.wise.platform.mall.service.PromotionService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * 活动请求参数
 * @author anhui
 * @since 2019/9/12
 */
@Service
public class PromotionServiceImpl extends ServiceImpl<PromotionMapper, PromotionBo> implements PromotionService {


    @Resource
    GroupPromotionGoodsMapper groupPromotionGoodsMapper;

    @Autowired
    PromotionAndGoodsService promotionAndGoodsService;

    @Value("${companyId}")
    private String companyId;

    @Override
    public ResponseEntity<ResPageInfoVO<List<GroupPromotionVo>>> listByPage(ReqPageInfoQry<GroupPromotionParam> param) {
        GroupPromotionParam  params =param.getReqObj();
        QueryWrapper<PromotionBo> wrapper = new QueryWrapper<>();

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
        Page<PromotionBo> pageInfo = new Page<>(param.getPageNum(),param.getPageSize());
        wrapper.orderByDesc("start_time");
        IPage<PromotionBo> promotionBoList = this.page(pageInfo,wrapper);

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

    /**
     * 组装Vo类
     * @param records
     * @return
     */
    private List<GroupPromotionVo> getPromotionVoList(List<PromotionBo> records) {
        List<GroupPromotionVo> promotionVoList =new ArrayList<>();
        for (PromotionBo record:records){
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
    private GroupPromotionVo getGroupPromotionVo(PromotionBo record) {
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
        if("1".equals(record.getGroupType())) {
            QueryWrapper<GroupPromotionGoodsBo> wrapper = new QueryWrapper<>();
            wrapper.eq("group_promotion_id", record.getId());
            promotionVo.setGoodsCount(groupPromotionGoodsMapper.selectCount(wrapper));
        }else{
            QueryWrapper<PromotionAndGoodsBo> wrapper = new QueryWrapper<>();
            wrapper.eq("promotion_id", record.getId());
            promotionVo.setGoodsCount(promotionAndGoodsService.count(wrapper));
        }
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
