package cn.enn.wise.platform.mall.constants;

/**
 * 常量定义
 */
public interface AppConstants {

    /**
     * 热气球所有票默认为单一商品
     */
    long RQQ_CATEGORY_ID = 1;
    /**
     * 项目Id
     */
    long RQQ_PROJECT_ID = 1;
    /**
     * 资源类别Id
     */
    long RQQ_RESOURCE_ID = 1;

    /**
     * 今天
     */
    Integer TODAY = 1;
    /**
     * 明天
     */
    Integer TOMORROW = 2;
    /**
     * 后天
     */
    Integer ACQUIRED = 3;

    /**
     * 检查用户分销身份
     */
    String CHECKUSER_SERVICE_URL = "CHECKUSER_SERVICE_URL";

    /**
     * 保存分销订单
     */
    String SAVEDISTRIBUTEORDER_SERVICE_URL = "SAVEDISTRIBUTEORDER_SERVICE_URL";

    /**
     * 员工token验证
     */
    String STAFF_TOKEN_URL = "STAFF_TOKEN_URL";
    /**
     * 获取线路
     */
    String scenic_service_url = "scenic_service_url";

    /**
     * 检查策略
     */
    String CHECK_STRATEGY_URL = "CHECK_STRATEGY_URL";

    /**
     * openid
     */
    String OPENID_SERVICE_URL = "OPENID_SERVICE_URL";

    /**
     * 根据用户id查询分销商信息
     */
    String getDistributeByUserId = "getDistributeByUserId";

    /**
     * 访问distribute项目公共路径
     */
    String DISTRIBUTE_URL = "DISTRIBUTE_URL";

    /**
     * 查询分销商可分销的商品
     */
    String GET_GOODSID_BYROLEID = "GET_GOODSID_BYROLEID";

    /**
     * 日期格式化 中文
     */
    String DATE_FORMAT_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 获取用户信息url
     */
    String GET_MEMBER_INFO_URL = "GET_MEMBER_INFO_URL";
    /**
     * 更新分销订单策略
     */
    String UPDATE_STRATEGY_ITEM = "UPDATE_STRATEGY_ITEM";

    /**
     * 根据是否是景区接送服务更新分销策略
     */
    String UPDATE_STRATEGY_ITEM_BY_IS_SERVICE = "UPDATE_STRATEGY_ITEM_BY_IS_SERVICE";


    /**
     * 根据项目Id集合获取项目基本信息
     */
    String GET_PROJECT_INFO = "GET_PROJECT_INFO";

    /**
     * 根据项目Id获取项目基本信息
     */
    String GET_PROJECT_INFO_LIST = "GET_PROJECT_INFO_LIST";

    /**
     * result 字符串
     */
    String RESULT = "result";
    /**
     * value 字符串
     */
    String VALUE = "value";

    String LIST ="list";

    /**
     * 船票下单验证信息前缀
     */
    String TICKET_MESSAGE_CODE = "TICKET_MESSAGE_CODE_%s";

    /**
     * 短信验证码
     */
    String SEND_CODE_TEMPLATE = "您好! 您的验证码是%s,请勿泄露!";

    String ORDER_SMS_ALERT = "短信预警,订单号%s,订单支付成功，未获取到座位号信息，请排除问题！";
}
