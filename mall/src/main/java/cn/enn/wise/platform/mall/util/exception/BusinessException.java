package cn.enn.wise.platform.mall.util.exception;

import java.io.Serializable;

/**
 * 业务异常封装
 *
 * @author caiyt
 */
public class BusinessException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 3787730660315875183L;

    private String message;

    private int code;

    private Object data;

    public BusinessException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BusinessException(int code, String message,Object data) {
        super(message);
        this.message = message;
        this.code = code;
        this.data = data;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return this.code;
    }
    public Object getData() {
        return this.data;
    }
}
