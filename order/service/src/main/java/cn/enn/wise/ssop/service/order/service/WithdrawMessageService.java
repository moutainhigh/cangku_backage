package cn.enn.wise.ssop.service.order.service;


public interface WithdrawMessageService {

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    boolean sendAuthCode(String phone);

    /**
     * 验证短信验证码
     * @param auditerPhone
     * @param phoneAuthCode
     * @return
     */
    boolean checkAuthCode(String auditerPhone, String phoneAuthCode);

    /**
     * 创建短链接并存储
     * @param realUrl 不包含域名的 真实链接 例： /mensz/user.html?id=1
     * @return
     */
    String createShortLink(String realUrl);

    /**
     * 获取跳转目标链接
     * @param shortLink
     * @return
     */
    String getRealLink(String shortLink);

    /**
     * 发送提现提示短信
     * @param ordersId
     * @return
     */
    boolean sendWithdrawNoticeSMS(String ordersId);

    /**
     * 发送退款提示短信
     * @param ordersId
     * @return
     */
    boolean sendRefundNoticeSMS(String ordersId);

    /**
     * 将数据库中的短链接加载至Redis
     */
    void initShortLinkToRedis();

}
