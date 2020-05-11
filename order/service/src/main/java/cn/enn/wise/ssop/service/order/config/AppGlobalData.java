package cn.enn.wise.ssop.service.order.config;

public class AppGlobalData {

    /** 创建人ID **/
    public static ThreadLocal<Long> localCreatorId = new ThreadLocal();

    /** 创建人名称 **/
    public static ThreadLocal<String> localCreatorName = new ThreadLocal();

    /** 租户ID **/
    public static ThreadLocal<Long> localCompanyId = new ThreadLocal();

}
