package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.ShortLink;
import cn.enn.wise.platform.mall.bean.bo.WithdrawRecord;
import cn.enn.wise.platform.mall.bean.vo.AuditPassInfo;
import cn.enn.wise.platform.mall.constants.AuditConstants;
import cn.enn.wise.platform.mall.mapper.ShortLinkMapper;
import cn.enn.wise.platform.mall.mapper.WithdrawMapper;
import cn.enn.wise.platform.mall.service.WithdrawalService;
import cn.enn.wise.platform.mall.util.MessageSender;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 提现服务
 */
@Slf4j
@Service("withdrawalService")
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    MessageSender messageSender;
    @Autowired
    ShortLinkMapper ShortLinkMapper;

    @Value("${audit.h5url}")
    String auditH5URL;

    @Value("${audit.refundH5Url}")
    String auditRefundH5URL;

    @Value("${spring.profiles.active}")
    String profileActive;


    @Value("${audit.phone}")
    String phone;
    @Value("${audit.name}")
    String auditName;

    @Autowired
    WithdrawMapper withdrawMapper;

    /**
     * 发送手机验证码
     * @param phone　需要验证的手机号
     * @return
     */
    @Override
    public boolean sendAuditPhoneAuthCode(String phone){
        String redisKey = String.format(AuditConstants.AUDITSMS_CODE, phone);

        // 发送审核验证码　存redis
        int random = new Random().nextInt(999999);
        String smsCode = new DecimalFormat("000000").format(random);

        //发送短信
        HashMap<String, String> map = new HashMap<>();
        map.put("code", smsCode);

        map.put("companyId", String.valueOf(11));
        map.put("phone", phone);
        map.put("type", String.valueOf(1));
        try{
            messageSender.sendSmsV2(map);
            redisTemplate.opsForValue().set(redisKey,smsCode,60*3, TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            log.error("短信发送失败，参数：[{}]",map);
        }
        return false;
    }

    @Override
    public boolean checkPhoneAuthCode(String auditerPhone, String phoneAuthCode) {
        String redisKey = String.format(AuditConstants.AUDITSMS_CODE, auditerPhone);

        String realCode = redisTemplate.opsForValue().get(redisKey);
        if(phoneAuthCode.trim().equals(realCode)){
            redisTemplate.delete(redisKey);
            return true;
        }
        return false;
    }

    /**
     * 创建短链接并存储
     * @param realUrl 不包含域名的 真实链接 例： /mensz/user.html?id=1
     * @return
     */
    @Override
    public String createShortLink(String realUrl){
        //注明：　realUrl链接为域名后的path
//        String realUrl = auditH5URL + "?id=" + auditId;

        //分配短链接
        String shortUrl = getRandomString(8);

        String domain;
        //加上域名
        if(profileActive.equals("prod")){
            domain="http://travel.enn.cn/";
        }else if(profileActive.equals("test")){
            domain="http://genius.enn.cn/";
        }else{
            domain="http://tx.enn.cn/";
        }

        realUrl = domain+realUrl;
        shortUrl = domain+"/c/"+shortUrl;

        ShortLink shortLink = new ShortLink();
        shortLink.setRealUrl(realUrl);
        shortLink.setShortUrl(shortUrl);

        try {
            int insertCount = ShortLinkMapper.insert(shortLink);
            String redisKey = String.format(AuditConstants.AUDIT_SHORTLINK, shortUrl);

            redisTemplate.opsForValue().set(redisKey,realUrl);

            if(insertCount>0) {
                return shortUrl;
            }
        }catch (DuplicateKeyException e){
            createShortLink(realUrl);
        }
        return null;
    }

    /**
     * 获取正式链接
     * @param shortLink　短链接
     * @return
     */
    @Override
    public String getRealLink(String shortLink){
        String redisKey = String.format(AuditConstants.AUDIT_SHORTLINK, shortLink);

        String realLink = redisTemplate.opsForValue().get(redisKey);
        return realLink;
    }


    /**
     * 发送分销商 提现通知短信
     * @param ordersId
     * @return
     */
    @Override
    public boolean sendWithdrawNoticeSMS(String ordersId){
        String realUrl = auditH5URL + "?id=" + ordersId;
        String shortLink = createShortLink(realUrl);


        //发送短信
        HashMap<String, String> map = new HashMap<>();
        map.put("orderCode", ordersId);
        map.put("shortLink", shortLink);
        map.put("type", String.valueOf(8));
        map.put("phone",phone);
        map.put("companyId",String.valueOf(11));
        try{
            messageSender.sendSmsV2(map);
            return true;
        }catch (Exception e){
            log.error("短信发送失败，参数：[{}]",map);
        }
        return true;
    }

    /**
     * 发送分销商 退款通知短信
     * @param ordersId
     * @return
     */
    @Override
    public boolean sendRefundNoticeSMS(String ordersId){
        String realUrl = auditRefundH5URL + "?refundNum=" + ordersId;
        String shortLink = createShortLink(realUrl);

        //发送短信
        HashMap<String, String> map = new HashMap<>();
        map.put("orderCode", ordersId);
        map.put("shortLink", shortLink);
        map.put("phone",phone);
        map.put("type", String.valueOf(9));
        map.put("companyId",String.valueOf(11));
        try{
            messageSender.sendSmsV2(map);
            return true;
        }catch (Exception e){
            log.error("短信发送失败，参数：[{}]",map);
        }
        return true;
    }

    @Override
    public void initShortLineToRedis() {
        List<ShortLink> shortLinks = ShortLinkMapper.selectList(new LambdaQueryWrapper<>());
        for (ShortLink shortLinkEntity : shortLinks) {
            String shortUrl = shortLinkEntity.getShortUrl();
            String realUrl = shortLinkEntity.getRealUrl();
            String redisKey = String.format(AuditConstants.AUDIT_SHORTLINK, shortUrl);
            redisTemplate.opsForValue().set(redisKey,realUrl);
        }
    }

    @Override
    public boolean updateAuditResult(AuditPassInfo auditPassInfo) {
        QueryWrapper<WithdrawRecord> wrapper = new QueryWrapper();
        wrapper.eq("withdraw_serial",auditPassInfo.getId());
        WithdrawRecord withdrawRecord = withdrawMapper.selectOne(wrapper);

        Boolean ispass = auditPassInfo.getIspass();
        String auditDesc = auditPassInfo.getAuditDesc();
        withdrawRecord.setPermit(ispass?new Byte("1"):new Byte("-1"));
        withdrawRecord.setPermitUserId(0L);
        withdrawRecord.setPermitDateTime(new Date());
        withdrawRecord.setPermitUserName(auditName);
        withdrawRecord.setPermitComment(auditDesc);

        return withdrawMapper.updateById(withdrawRecord) > 0;
    }


    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random=new Random();
        StringBuffer sb=new StringBuffer();

        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }







}
