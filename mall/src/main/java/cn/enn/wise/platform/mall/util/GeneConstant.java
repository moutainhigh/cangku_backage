package cn.enn.wise.platform.mall.util;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 19:22
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
public class GeneConstant {

    /**
     * success
     */
    public static final String SUCCESS = "success";
    /**
     * cancel
     */
    public static final String CANCEL = "cancel";
    /**
     * SUCCESS
     */
    public static final String SUCCESS_UPPERCASE = "SUCCESS";
    /**
     * 成功
     */
    public static final String SUCCESS_CHINESE = "成功";
    /**
     * error
     */
    public static final String ERROR = "error";
    /**
     * yes
     */
    public static final String YES = "yes";
    /**
     * no
     */
    public static final String NO = "no";
    public static final String OK = "OK";
    /**
     * 0
     */
    public static final int INT_0 = 0;
    /**
     * 1
     */
    public static final int INT_1 = 1;
    public static final byte BYTE_1 = 1;
    public static final Byte BYTE_2 = 2;
    public static final byte BYTE_3 = 3;
    public static final byte BYTE_4 = 4;
    public static final byte BYTE_5 = 5;
    /**
     * 2
     */
    public static final int INT_2 = 2;
    public static final Integer INTEGER_2 = 2;
    public static final String STRING_2 = "2";

    public static final Integer INT_308 = 308;
    public static final Integer INT_404 = 404;
    public static final Integer INT_101 = 101;
    public static final Integer INT_203 = 203;
    /**
     * 3
     */
    public static final int INT_3 = 3;
    /**
     * 4
     */
    public static final int INT_4 = 4;
    /**
     * 5
     */
    public static final int INT_5 = 5;
    /**
     * 6
     */
    public static final int INT_6 = 6;
    public static final long LONG_6 = 6;
    public static final long LONG_5 = 5;
    /**
     * 7
     */
    public static final int INT_7 = 7;
    public static final Integer INTEGER_7 = 7;
    /**
     * 8
     */
    public static final int INT_8 = 8;
    /**
     * 9
     */
    public static final int INT_9 = 9;
    /**
     * 10
     */
    public static final int INT_10 = 10;
    /**
     * 11
     */
    public static final int INT_11 = 11;
    /**
     * -1
     */
    public static final int INT_1_MIN = -1;
    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";
    /**
     * 下划线
     */
    public static final String UNDERLINE_DOUBLE = "__";
    /**
     * 逻辑符
     */
    public static final String QP_SEP_A = "&";
    /**
     * 等号
     */
    public static final String EQUALS_MARK = "=";
    /**
     * 问号
     */
    public static final String QUE_MARK = "?";
    /**
     * 分号
     */
    public static final String SEMICOLON_MARK = ";";
    /**
     * 逗号
     */
    public static final String COMMA_MARK = ",";
    /**
     * 空格
     */
    public static final String BLACK_MARK = " ";
    /**
     * 默认时间格式
     */
    public static final String DEFUALT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认时间格式
     */
    public static final String TIME_DATE_FORMAT = "yyyyMMddHHmmssSSS";
    /**
     * 简易时间格式
     */
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 系统异常
     */
    public static final int SYSTEM_ERROR = -2;
    /**
     * 登录超时
     */
    public static final int LOGIN_TIMEOUT = -1;
    /**
     * 成功
     */
    public static final int SUCCESS_CODE = 1;
    /**
     * 请求参数非法
     */
    public static final int PARAM_INVALIDATE = 2;
    /**
     * 业务异常
     */
    public static final int BUSINESS_ERROR = 3;
    /**
     * 无法创建拼团
     */
    public static final int CREATE_GROUP_ORDER_ERROR = -3;

    /**
     * 游船YC
     */
    public static final String YC = "YC";
    /**
     * 热气球 HQ
     */
    public static final String HQ = "HQ";
    /**
     *  门票PT
     */
    public static final String PT = "PT";
    /**
     * 车ZC
     */
    public static final String ZC = "ZC";
    /**
     *  餐CY
     */
    public static final String CY = "CY";

    public static final String TRUE = "true";

    public static final String FALSE = "false";

    public static final  String IS_CREATE_GROUP_ORDER= "isCreateGroupOrder";

    // 手机号验证不通过
    public static final int PHONE_NO_PADD = 1078;
    public static final String MESSAGE_SIGN_SHIP="【新绎游船】";

    /**
     * 退款消息模板
     */
    public static String REFUND_MESSAGE="尊敬的旅客，系统已取消您:(%s)%s开航的%s张船票；订单号：%s，退款总金额：%s元,手续费:%s元，退票票款按申请退票次日起,将在7个工作日内退还至第三方支付平台,第三方支付平台进行退款处理给银行。若有疑问，请拨打0779-3069988咨询，谢谢您的支持。温馨提示：为保护涠洲岛生态环境，涠洲岛旅游区实行上岛游客总量限制（夏季9000人/天，冬季11000人/天），请您提前安排好行程。";

    public static String REFUND_MESSAGES="尊敬的旅客，系统已取消您:(%s)%s开航的%s张船票；订单号：%s，退款总金额：%s元,手续费:%s元，退票票款按申请退票次日起,将在7个工作日内退还至第三方支付平台,第三方支付平台进行退款处理给银行。若有疑问，请拨打0779-3069988咨询，谢谢您的支持。";

}
