package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.bo.GoodsProjectStaff;
import cn.enn.wise.platform.mall.bean.bo.GoodsProjectStaffPlace;
import cn.enn.wise.platform.mall.bean.bo.SysRole;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.mapper.GoodsProjectMapper;
import cn.enn.wise.platform.mall.mapper.GoodsProjectStaffMapper;
import cn.enn.wise.platform.mall.mapper.GoodsProjectStaffPlaceMapper;
import cn.enn.wise.platform.mall.mapper.ProjectOperationStatusMapper;
import cn.enn.wise.platform.mall.service.GoodsProjectService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 二销产品所属项目 服务实现类
 * </p>
 *
 * @author caiyt
 * @since 2019-05-24
 */
@Service
public class GoodsProjectServiceImpl extends ServiceImpl<GoodsProjectMapper, GoodsProject> implements GoodsProjectService {

    @Autowired
    GoodsProjectMapper goodsProjectMapper;

    @Autowired
    GoodsProjectStaffPlaceMapper goodsProjectStaffPlaceMapper;

    @Autowired
    GoodsProjectStaffMapper goodsProjectStaffMapper;

    @Autowired
    private ProjectOperationStatusMapper projectOperationStatusMapper;

    @Override
    public ResponseEntity<ResPageInfoVO<List<GoodsProject>>> listGoodsProject(ReqPageInfoQry<GoodsProject> goodsProjectReqPageInfoQry) {

        GoodsProject reqObj = goodsProjectReqPageInfoQry.getReqObj();

        QueryWrapper<GoodsProject> qrwapper = new QueryWrapper<>();
        qrwapper.eq("project_status",1);
        if(reqObj != null){
            if(StringUtils.isNotEmpty(reqObj.getName())){
                //项目名称模糊查询
                qrwapper.like("name",reqObj.getName());
            }
            Integer projectStatus = reqObj.getProjectStatus();

            if( projectStatus != null){
                //-1是查询全部
                if(projectStatus != -1){
                    qrwapper.eq("project_status",projectStatus);
                }
            }

            String projectManager = reqObj.getProjectManager();
            if(StringUtils.isNotEmpty(projectManager)){
                qrwapper.like("project_manager",projectManager);
            }

            String servicePlaceId = reqObj.getServicePlaceId();
            if(StringUtils.isNotEmpty(servicePlaceId)){

                if(!"-1".equals(servicePlaceId)){
                    qrwapper.like("service_place_id",servicePlaceId);
                }
            }
        }

        Page<GoodsProject> pageInfo = new Page<>(goodsProjectReqPageInfoQry.getPageNum(), goodsProjectReqPageInfoQry.getPageSize());

        qrwapper.orderByDesc("id");

        IPage<GoodsProject> dbPageInfo = this.page(pageInfo, qrwapper);
        //分页
        ResPageInfoVO resPageInfoVO = new ResPageInfoVO();
        if(dbPageInfo==null){
            resPageInfoVO.setPageNum(0L);
            resPageInfoVO.setPageSize(0L);
            resPageInfoVO.setTotal(0L);
            return new ResponseEntity<ResPageInfoVO<List<GoodsProject>>>(resPageInfoVO);
        }
        // 分页
        resPageInfoVO.setPageNum(dbPageInfo.getCurrent());
        resPageInfoVO.setPageSize(dbPageInfo.getSize());
        resPageInfoVO.setTotal(dbPageInfo.getTotal());

        List<GoodsProject> goodsList = dbPageInfo.getRecords();

        // 如果无数据则直接返回
        if (goodsList == null || goodsList.isEmpty()) {
            return new ResponseEntity<ResPageInfoVO<List<GoodsProject>>>(resPageInfoVO);
        }
        resPageInfoVO.setRecords(goodsList);

        return new ResponseEntity<>(resPageInfoVO);
    }

    @Override
    public List<GoodsProject> listProjectByCompanyId(long companyId){

        List<GoodsProject> list= goodsProjectMapper.selectList(new QueryWrapper<GoodsProject>().eq("company_id",companyId).eq("project_status",1));

        return list;
    }

    @Override
    public List<GoodsProject> getProjectByCompanyId(long companyId){

        List<GoodsProject> list= goodsProjectMapper.getProjectByCompanyId(companyId);

        return list;
    }


    @Override
    public List<GoodsProject> getOperationProject(Long userId,Long companyId) {

        return goodsProjectMapper.getOperationProject(userId,companyId);
    }

    @Override
    public GoodsProject getProjectById(Long projectId){
        return goodsProjectMapper.getProjectById(projectId,-1L);
    }

    /***
     *
     * @param id
     * @return  keys :placeId, placeName, projectId, projectName
     */
    @Override
    public List<Map<String, Object>> getProjectListByStaffId(Long id, List<SysRole> roles) {

        Boolean isTicketChanger = false;

        for (SysRole role:roles){
            if(role.getId()==26L){
                isTicketChanger=true;
                break;
            }
        }



        // 获取项目的地点
        List<Map<String,Object>> projectMapList = goodsProjectMapper.getProjectListByStaffId(id);

        if(isTicketChanger){
            projectMapList = goodsProjectMapper.getProjectListByTicketChanger();
        }

        for(Map<String,Object> map:projectMapList){
            if(map.get("placeId") == null || map.get("placeName") == null || map.get("projectId") == null || map.get("projectName") == null){
                continue;
            }
            //region 创建项目地点列表
            List<Map<String,Object>> placeMapList = new ArrayList<>();
            String[] placeIds =map.get("placeId").toString().split(",");
            String[] placeNames =map.get("placeName").toString().split(",");

            // 如果有默认地点选择默认地点，如果没有默认地点第一个地点为默认地点
            for(Integer i =0;i<placeIds.length;i++){

                // 组织地点
                Map<String,Object> placeMap = new HashMap<>();
                placeMap.put("placeId",placeIds[i]);
                placeMap.put("placeName",placeNames[i]);
                placeMap.put("isDefault","false");

                // 查询默认地点
                Map<String,Object> params = new HashMap<>();
                params.put("staff_id",id);
                params.put("project_id",map.get("projectId"));
                List<GoodsProjectStaffPlace> placeList = goodsProjectStaffPlaceMapper.selectByMap(params);
                if(placeList.size()==0){
                    // 增加默认地点
                    GoodsProjectStaffPlace place = new GoodsProjectStaffPlace();
                    place.setPlaceId( Long.valueOf(placeIds[i]));
                    place.setProjectId(Long.valueOf(map.get("projectId").toString()));
                    place.setStaffId(id);
                    goodsProjectStaffPlaceMapper.insert(place);
                    placeMap.put("isDefault","true");

                }else{
                    for(GoodsProjectStaffPlace place:placeList){
                        // 选择默认地点
                        if(place.getPlaceId()==Long.parseLong(placeIds[i])){
                            placeMap.put("isDefault","true");
                        }
                    }
                }
                placeMapList.add(placeMap);
            }
            map.remove("placeId");
            map.remove("placeName");
            map.put("placeList",placeMapList);
            //endregion
        }

        for(Map<String,Object> map:projectMapList){
            // region 默认项目
            if(map.get("projectId") == null || map.get("projectName") == null){
                continue;
            }

            Long projectId = Long.valueOf(map.get("projectId").toString());

            // 查询默认项目
            Map<String,Object> params = new HashMap<>();
            params.put("staff_id",id);
            List<GoodsProjectStaff> projectList = goodsProjectStaffMapper.selectByMap(params);
            if(projectList.size()==0){
                // 增加默认项目
                GoodsProjectStaff project = new GoodsProjectStaff();
                project.setStaffId(id);
                project.setProjectId(projectId);
                goodsProjectStaffMapper.insert(project);
                map.put("isDefault","true");
            }else{
                for(GoodsProjectStaff project:projectList){
                    // 选择默认项目
                    if(project.getProjectId()==Long.parseLong(map.get("projectId").toString())){
                        map.put("isDefault","true");
                    }
                }
            }
            // endregion
        }
        return projectMapList;
    }

    @Override
    public List<GoodsProject> getProjectByPeriod() {
        return goodsProjectMapper.getProjectByPeriod();
    }
}
