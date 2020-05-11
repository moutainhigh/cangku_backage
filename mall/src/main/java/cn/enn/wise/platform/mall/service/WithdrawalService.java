package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.vo.AuditPassInfo;

public interface WithdrawalService {
    boolean sendAuditPhoneAuthCode(String phone);

    boolean checkPhoneAuthCode(String auditerPhone, String phoneAuthCode);

    /**
     * 创建短链接并存储
     * @param realUrl 不包含域名的 真实链接 例： /mensz/user.html?id=1
     * @return
     */
    String createShortLink(String realUrl);

    String getRealLink(String shortLink);

    boolean sendWithdrawNoticeSMS(String ordersId);

    boolean sendRefundNoticeSMS(String ordersId);

    void initShortLineToRedis();

    boolean updateAuditResult(AuditPassInfo auditPassInfo);
}
