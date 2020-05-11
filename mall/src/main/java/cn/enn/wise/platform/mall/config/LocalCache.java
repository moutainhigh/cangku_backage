package cn.enn.wise.platform.mall.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

public class LocalCache {

    private static LoadingCache<String,Object> dataCache = init();


    private static LoadingCache<String,Object> init(){
        return CacheBuilder.newBuilder()
                .initialCapacity(50)
                .maximumSize(500)
                .expireAfterWrite(120, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Object>() {
                    @Override
                    public Object load(String s) throws Exception {
                        return null;
                    }
                });
    }


    public static void set(String key,Object value){
        if(value == null || StringUtils.isEmpty(key)){
            return ;
        }
        dataCache.put(key,value);
    }

    public static Object get(String key){
        try{
            if(StringUtils.isEmpty(key)){
                return null;
            }
            return dataCache.get(key);
        }catch (Exception e){
            return null;
        }
    }


}
