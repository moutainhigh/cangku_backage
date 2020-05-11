package cn.enn.wise.ssop.service.promotions.util;

/**
 * 获取锁管理类
 *
 * @author baijie
 * @date 2019-09-12
 */
public interface DistributedLocker {
    /**
     * 获取锁
     * @param resourceName  锁的名称
     * @param worker 获取锁后的处理类
     * @param <T>
     * @return 处理完具体的业务逻辑要返回的数据
     * @throws UnableToAquireLockException
     * @throws Exception
     */
    <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception;

    <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime, int t) throws UnableToAquireLockException, Exception;
}
