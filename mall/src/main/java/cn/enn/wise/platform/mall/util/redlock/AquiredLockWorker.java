package cn.enn.wise.platform.mall.util.redlock;

/**
 * 获取锁后需要处理的逻辑
 *
 * @author baijie
 * @date 2019-09-12
 */
public interface AquiredLockWorker<T> {
    /**
     * 获取锁后需要处理的逻辑
     * @return
     * @throws Exception
     */
    T invokeAfterLockAquire() throws Exception;

    T falseResult();
}
