package cn.enn.wise.platform.mall.bean.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 项目运营数据
 *
 * @author baijie
 * @date 2019-08-20
 */
@Data
public class ProjectOperationReason {

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 运营状态
     */
    private List<Map<String,Object>> status;

    /**
     * 运营不正常的原因
     */
    private List<String> reason;
}
