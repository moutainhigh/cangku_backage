package cn.enn.wise.platform.mall.constants;

/**
 * 登录常量类
 *
 * @author caiyt
 */
public class LoginConstants {
    /**
     * 后台用户登录后token保存在缓存中，key值前缀为 MS_TOKEN_
     */
    public final static String MANAGEMENT_SYSTEM_TOKEN = "MS_TOKEN_%s";

    /**
     * token有效期
     */
    // TODO 上线前更改为 30分钟
    public final static int TOKEN_VALIDITY_TIME =86400;

    /**
     * 登录结果枚举类
     *
     * @author caiyt
     */
    public enum LoginEnum {
        /**
         * 用户不存在或密码错误
         */
        USER_NOT_EXIT_OR_PWD_ERROR(100, "用户不存在或密码错误!"),
        /**
         * 登录已失效，请重新登录
         */
        USER_LOGIN_EXPIRED(101, "登录已失效，请重新登录");

        LoginEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        private int code;
        private String message;

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }
}