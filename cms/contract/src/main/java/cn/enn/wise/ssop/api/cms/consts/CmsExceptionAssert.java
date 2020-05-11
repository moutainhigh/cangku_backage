package cn.enn.wise.ssop.api.cms.consts;

import cn.enn.wise.uncs.base.exception.assertion.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义异常
 *
 * @author shizhai
 * @date 2019.09.17
 *
 *
 * 会员服务 10000-10999
 * 权限服务 11000-11999
 * 订单服务 12000-12999
 * 商品服务 13000-13999
 * 网关服务 14000-14999
 * 支付服务 15000-15999
 * 营销服务 16000-16999
 * 场景服务 17000-17999
 * 内容服务 18000-18999
 *
 *
 */
@Getter
@AllArgsConstructor
public enum CmsExceptionAssert implements BusinessExceptionAssert {

    /**
     *全局
     */
    CATEGORY_NAME_ISEXIST(14000, "内容分类名称已经存在,name=[{0}]"),

    /**
     * 攻略
     */
    SATEGORY_STATUS_ISENABLE(14001, "攻略状态为开启，不能删除，请先禁用攻略,id=[{0}]"),
    SATEGORY_TITLE_ISEXIST(14002, "攻略标题重复,title=[{0}]"),
    /**
     * 公告
     */
    ANNOUNCEMENT_STATUS_ISENABLE(14003, "公告状态为开启，不能删除，请先禁用攻略,id=[{0}]"),

    /**
     * 知识
     */
    KNOWLEDGE_STATUS_ISENABLE(14004, "知识状态为开启，不能删除，请先禁用攻略,id=[{0}]"),

    KNOWLEDGE_TITLE_ISEXIST(14005, "知识标题重复,title=[{0}]"),

    CATEGORY_ASSOCIATED_ISEXIST(14006, "分类被依赖，无法删除"),


    ;



    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;

}