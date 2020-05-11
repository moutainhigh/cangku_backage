package cn.enn.wise.platform.mall.constants;

/**
 * 登录常量类
 *
 * @author caiyt
 */
public class AuditConstants {
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


}