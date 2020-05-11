package cn.enn.wise.ssop.service.cms.consts;

/**
 * 内容服务Task Key 常量
 * 规则：
 * 服务名:表名:描述
 * 服务名:表名:描述:id
 * 使用 %S定位参数
 * String.format格式化 key
 * String.format(key,args)
 *
 *
 */
public interface TaskKey {


    /**
     * 定时任务 浏览量Key
     * @see
     */
    String PAGE_VIEW_TASK = "PAGE_VIEW_TASK";

    /**
     * 定时任务 浏览量Key失效时间
     * @see
     */
    Long PAGE_VIEW_TASK_TIME = 60L * 9;


    /**
     * 定时任务 击/加载量 Key
     * @see
     */
    String ADVERTISE_LOAD_CLICK_TASK = "LOAD_CLICK_TASK";

    /**
     * 定时任务 击/加载量Key失效时间
     * @see
     */
    Long ADVERTISE_LOAD_CLICK_TASK_TIME = 60L * 9;


}
