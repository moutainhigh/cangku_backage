package cn.enn.wise.ssop.service.cms.consts;

/**
 * 内容服务Redis Key 常量
 * 规则：
 * 服务名:表名:描述
 * 服务名:表名:描述:id
 * 使用 %S定位参数
 * String.format格式化 key
 * String.format(key,args)
 *
 *
 */
public interface RedisKey {


    String ServerName = "CMS::";

    /**
     * 攻略详情
     * 参数1 攻略id
     * value
     * @see
     */
    String STATEGY_DETAIL = "STATEGY:DETAIL:%S";

    /**
     * 知识详情
     * 参数1 知识id
     * value
     * @see
     */
    String KNOWLEDGE_DETAIL = "KNOWLEDGE:DETAIL:%S";

    /**
     * 攻略点赞数据hash
     * 参数1 攻略id
     * value hash  member_id->voteTime
     * @see
     */
    String STATEGY_VOTE = "STATEGY:VOTE:%S";

    /**
     * 攻略ids
     * 参数1 租户id ;
     * 参数2 攻略id;
     */
    String STATEGY_IDS = "STATEGY:%S:%S:IDS";

    /**
     * 攻略列表DTO
     * 参数1 攻略id;
     */
    String STRATEGY_LIST_DTO = "STRATEGY:LIST:DTO:%S";

    /**
     * 知识点赞数据hash
     * 参数1 知识id
     * value hash  member_id->voteTime
     * @see
     */
    String KNOWLEDGE_VOTE = "KNOWLEDGE:VOTE:%S";

    /**
     * 公告列表
     * 参数1 公告id
     */
    String ANNOUNCEMENT_LIST = "ANNOUNCEMENT:LIST";

    /**
     * 公告详情
     * 参数1 公告ID
     * @see
     */
    String ANNOUNCEMENT_DETAIL = "ANNOUNCEMENT:DETAIL:%S";

    /**
     * 广告列表
     *
     */
    String ADVERTISE_LIST = "ADVERTISE:LIST";

    /**
     * 公告 点击/加载 量
     * 参数1  加载1 点击2
     * @see
     */
    String ADVERTISE_CLICK_LOAD = "ADVERTISE:CLICK:LOAD:%S";

    /**
     * 广告列表
     *参数1 广告id
     */
    String ADVERTISE_DETAIL = "ADVERTISE:DETAIL:%S";

    /**
     * 景点详情
     * 参数1 租户ID
     * @see
     */
    String SCENICS_DETAIL = "SCENICS:DETAIL:%S";

    /**
     * 景点收藏
     * 参数1 租户ID ;
     * 参数2 文章ID
     * @see
     */
    String SCENICS_ENSHRINE = "SCENICSIIF:ENSHRINE:%S:%S";

    /**
     * 路线收藏
     * 参数1 租户ID ;
     * 参数2 文章ID
     * @see
     */
    String PATH_ENSHRINE = "PATH:ENSHRINE:%S:%S";

    /**
     * 浏览量
     * 参数1 （2攻略 3知识） ;
     * @see
     */
    String PAGE_VIEW = "PAGE:VIEW:%S";


    /**
     * 定时任务 浏览量Key
     * @see
     */
    String PAGE_VIEW_TASK = "PAGE_VIEW_TASK";

    /**
     * 定时任务 浏览量Key失效时间
     * @see
     */
    Long PAGE_VIEW_TASK_TIME = 60L * 10;




}
