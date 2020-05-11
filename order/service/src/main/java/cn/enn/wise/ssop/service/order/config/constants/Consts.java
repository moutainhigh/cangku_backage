package cn.enn.wise.ssop.service.order.config.constants;

public class Consts {


    /** HEADER中的值，创建人ID标示 **/
    public static final String CREATOR_ID = "creator_id";

    /** HEADER中的值，创建人名称标示 **/
    public static final String CREATOR_NAME = "creator_name";

    /** HEADER中的值，租户ID标示 **/
    public static final String COMPANY_ID = "company_id";

    /**
     *　审核短信验证码　redis key
     * $s 手机号
     */
    public final static String AUDITSMS_CODE = "MALL_AUDITSMS_CODE_%s";

    /**
     * 短长链接redis key
     * $s 短链接
     */
    public final static String AUDIT_SHORTLINK = "MALL_AUDIT_SHORTLINK_%s";

    /** 调试模式 **/
    public static final boolean DEBUG_MODE = false;

    /** 父订单id标记 **/
    public static final int PARENT_ORDER_ID_FLAG = 1;

    /**
     *
     */
    public static final String mqFrom = "order";

}
