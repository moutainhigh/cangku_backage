package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.ProdComm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 项目评价Vo
 *
 * @author baijie
 * @date 2019-11-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectCommentVo extends ProdComm {

    /**
     * 评价标签列表
     */
    private List<String> labelList;

    /**
     * 评价值
     */
    private String evaluateValue;

    /**
     * 头像
     */
    private String headImg;
}
