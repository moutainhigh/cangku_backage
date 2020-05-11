package cn.enn.wise.platform.mall.util;

import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目管理参数校验类
 *
 * @author baijie
 * @date 2019-07-25
 */

public class GoodsProjectParamValidateUtil {

    private static final Logger logger = LoggerFactory.getLogger(GoodsProjectParamValidateUtil.class);

    /**
     * 校验保存项目参数
     * @param goodsProject
     */
    public static void checkSaveGoodsProject(GoodsProject goodsProject){
        logger.info("=开始检验参数:checkSaveGoodsProject=");

        if(goodsProject == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"goodsProject不能为空");
        }
        if(goodsProject.getCompanyId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"景区id不能为空");
        }
        if(StringUtils.isEmpty(goodsProject.getName())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"项目名称不能为空");
        }

        if(StringUtils.isEmpty(goodsProject.getProjectManager())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"项目负责人不能为空");
        }
        if(StringUtils.isEmpty(goodsProject.getOperationTime())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"运营时间不能为空");
        }
        if(goodsProject.getProjectStartDate() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"开始时间不能为空");
        }
        if(goodsProject.getProjectStatus() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"项目状态不能为空");
        }
        if(StringUtils.isEmpty(goodsProject.getOperationStaff())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"运营人员不能为空");

        }
        if(StringUtils.isEmpty(goodsProject.getProjectPresent())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"项目介绍不能为空");
        }

        if(goodsProject.getMaxServiceAmount() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"单词最大服务人数不能为空");
        }
        if(goodsProject.getServicePlaceId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"服务地点不能为空");
        }
        if(goodsProject.getProjectCode() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"项目编号不能为空");
        }

        if(StringUtils.isEmpty(goodsProject.getAbbreviation())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"项目简称不能为空");
        }

    }
}
