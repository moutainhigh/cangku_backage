package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.KnowledgeBo;
import cn.enn.wise.platform.mall.bean.param.AddKnowledgeParams;
import cn.enn.wise.platform.mall.bean.param.KnowledgeParams;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.KnowledgeVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 17:10
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Service
public interface KnowledgeService  extends IService<KnowledgeBo> {

    /**
     * 分页获取知识库列表
     * @param pageQry
     * @return
     */
    ResponseEntity<ResPageInfoVO<List<KnowledgeVo>>> listKnowledge(ReqPageInfoQry<KnowledgeParams> pageQry);

    /**
     * 添加知识库
     * @param params
     * @return
     */
    ResponseEntity<KnowledgeBo> addKnowledge(AddKnowledgeParams params);

    /**
     * 删除知识库
     * @param params
     * @return
     */
    ResponseEntity deleteKnowledge(List<Long> params);

    /**
     * 详情
     * @param id
     * @return
     */
    ResponseEntity<KnowledgeVo> detailKnowledge(Long id);

    /**
     * 列表
     * @return
     */
    ResponseEntity<List<KnowledgeBo>> findBusinessCategory(Integer businessType);


    /**
     *
     * @param Date
     * @return
     */
    String[] getWeatherByDate(String Date);
}
