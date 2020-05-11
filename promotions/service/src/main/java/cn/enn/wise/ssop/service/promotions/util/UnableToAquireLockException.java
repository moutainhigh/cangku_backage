package cn.enn.wise.ssop.service.promotions.util;

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
