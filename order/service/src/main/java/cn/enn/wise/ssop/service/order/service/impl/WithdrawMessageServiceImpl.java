
package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.service.order.config.constants.Consts;
import cn.enn.wise.ssop.service.order.handler.mq.sender.MessageSender;
import cn.enn.wise.ssop.service.order.model.ShortLink;
import cn.enn.wise.ssop.service.order.service.WithdrawMessageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * 提现服务
 */

@Slf4j
@Service
public class WithdrawMessageServiceImpl implements WithdrawMessageService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MessageSender messageSender;

    @Autowired
    cn.enn.wise.ssop.service.order.mapper.ShortLinkMapper shortLinkMapper;

    @Value("${withdraw-audit.h5url}")
    String auditH5URL;

    @Value("${withdraw-audit.refundH5Url}")
    String auditRefundH5URL;

    @Value("${withdraw-audit.phone}")
    String phone;

    @Value("${withdraw-audit.name}")
    String auditName;

    @Value("${spring.profiles.active}")
    String profileActive;

    /**
     * 发送手机验证码
     * @param phone　需要验证的手机号
     * @return
     */

    @Override
    public boolean sendAuthCode(String phone){
        String redisKey = String.format(Consts.AUDITSMS_CODE, phone);

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
            //messageSender.sendSmsV2(map);
            redisTemplate.opsForValue().set(redisKey,smsCode,60*3, TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            log.error("短信发送失败，参数：[{}]",map);
        }
        return false;
    }

    @Override
    public boolean checkAuthCode(String phone, String authCode) {
        String redisKey = String.format(Consts.AUDITSMS_CODE, phone);
        String realCode = redisTemplate.opsForValue().get(redisKey);
        if(authCode.trim().equals(realCode)){
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
            int insertCount = shortLinkMapper.insert(shortLink);
            String redisKey = String.format(Consts.AUDIT_SHORTLINK, shortUrl);
            redisTemplate.opsForValue().set(redisKey,realUrl);
            if(insertCount>0) {
                return shortUrl;
            }
        }catch (DuplicateKeyException e){
            return createShortLink(realUrl);
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
        String redisKey = String.format(Consts.AUDIT_SHORTLINK, shortLink);
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
            //messageSender.sendSmsV2(map);
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
            //messageSender.sendSmsV2(map);
            return true;
        }catch (Exception e){
            log.error("短信发送失败，参数：[{}]",map);
        }
        return true;
    }

    @Override
    public void initShortLinkToRedis() {
        List<ShortLink> shortLinks = shortLinkMapper.selectList(new LambdaQueryWrapper<>());
        for (ShortLink shortLinkEntity : shortLinks) {
            String shortUrl = shortLinkEntity.getShortUrl();
            String realUrl = shortLinkEntity.getRealUrl();
            String redisKey = String.format(Consts.AUDIT_SHORTLINK, shortUrl);
            redisTemplate.opsForValue().set(redisKey,realUrl);
        }
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

