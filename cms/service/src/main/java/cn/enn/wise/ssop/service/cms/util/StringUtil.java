/**
 * Copyright (C), 2018-2019
 * FileName: StringUtil
 * Author:   Administrator
 * Date:     2019-04-21 13:43
 * Description:
 */
package cn.enn.wise.ssop.service.cms.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具
 */
public class StringUtil {
    


    /**
     * 将list转换成string,元素中间以,分割
     * @param list
     * @return
     */
    public static String listToString(List<String> list){
        try {
            if(list == null || list.size() == 0){
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : list) {
                stringBuilder.append(",").append(s);
            }
            return stringBuilder.toString().substring(1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将String类型 中间以,分割转换成list
     * @param lists
     * @return
     */
      public static List<String> stringToList(String lists){
          List<String> result = new ArrayList<>();
          try {
              if(StringUtils.isEmpty(lists)){
                  return  result;
              }
              String[] split = lists.split(",");
              for (String s : split) {
                  result.add(s);
              }
              return result;
          } catch (Exception e) {
              e.printStackTrace();

              return result;
          }
      }
}