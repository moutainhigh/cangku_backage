package cn.enn.wise.ssop.service.promotions.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author baijie
 * @date 2019-09-12
 */
@Component
public class RedisLocker implements DistributedLocker {


    private final static String LOCKER_PREFIX = "lock:";

    @Autowired
    RedissonConnector redissonConnector;
    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws InterruptedException, UnableToAquireLockException, Exception {

        return lock(resourceName, worker, 100,0);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime,int num) throws UnableToAquireLockException, Exception {
        RedissonClient redisson= redissonConnector.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);
        // Wait for 100 seconds seconds and automatically unlock it after lockTime seconds
        boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
        if (success) {
            try {
                T t = worker.invokeAfterLockAquire();

                return t;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }else{
            if(num==4){
                return worker.falseResult();
            }else{
                num++;
                return lock(resourceName, worker, 100,num);
            }

        }
        throw new UnableToAquireLockException();
    }
}
