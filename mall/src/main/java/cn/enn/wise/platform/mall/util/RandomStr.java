package cn.enn.wise.platform.mall.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomStr {

    private static Random random = new SecureRandom();

    /**
     * 获取数字随机数
     * @param length 字符长度：1 —— 9
     * @return
     */
    public static String digits(int length){
        if(length < 1){
            return "0";
        }
        int min;
        int max;
        if(length>9){
            length = 9;
        }
        if(length ==  1){
            min = 0;
            max = 9;
        }else{
            StringBuffer strMin = new StringBuffer();
            StringBuffer strMax = new StringBuffer();
            strMin.append("1");
            strMax.append("9");
            for(int i = 1;i<length;i++){
                strMin.append("0");
                strMax.append("9");
            }
            min = Integer.valueOf(strMin.toString());
            max = Integer.valueOf(strMax.toString());
        }
        int r = random.ints(min,(max + 1)).limit(1).findFirst().getAsInt();
        return String.valueOf(r);
    }



    /**
     * 混合大小写字母与数字
     *
     * @param length
     * @return
     */
    public static String mixed(int length){
        String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        StringBuffer sb=new StringBuffer();
        for(int i=0; i<length; ++i){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机字母字符串
     *
     * @param length
     * @return
     */
    public static String character(int length){
        String str="QWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuffer sb=new StringBuffer();
        for(int i=0; i<length; ++i){
            int number=random.nextInt(26);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
