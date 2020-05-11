package cn.enn.wise.platform.mall.util.redlock;

/**
 * 不能获取锁的异常类
 *
 * @author baijie
 * @date 2019-09-12
 */
public class UnableToAquireLockException extends RuntimeException {

    public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
