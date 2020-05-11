package cn.enn.wise.platform.mall.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/5/22 19:21
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:返回结果
 ******************************************/
@ApiModel(value = "响应结果类")
public class ResponseEntity<T> implements Serializable {
    private static final long serialVersionUID = 7097939240250833859L;

    @ApiModelProperty(value = "返回状态：1:成功 2:请求参数非法 3:业务异常 -1:登录超时 -2:系统内部异常")
    protected int result;

    @ApiModelProperty(value = "返回结果")
    protected String message;

    @ApiModelProperty(value = "返回值")
    protected T value;

    public ResponseEntity() {
        super();
        this.result = GeneConstant.SUCCESS_CODE;
        this.message = GeneConstant.SUCCESS;
    }

    public ResponseEntity(int result) {
        super();
        this.result = result;
        if (GeneConstant.SUCCESS_CODE == result) {
            message = GeneConstant.SUCCESS;
        } else {
            message = GeneConstant.ERROR;
        }
    }

    /**
     * 返回结果初始化 Creates a new instance of ResponseEntity.
     *
     * @param
     */
    public ResponseEntity(String message) {
        super();
        this.message = message;
    }

    /**
     * 返回结果初始化 Creates a new instance of ResponseEntity.
     *
     * @param
     */
    public ResponseEntity(T value) {
        super();
        this.result = GeneConstant.SUCCESS_CODE;
        this.message = GeneConstant.SUCCESS;
        this.value = value;

    }

    /**
     * Creates a new instance of ResponseEntity.
     *
     * @param result
     * @param message
     */
    public ResponseEntity(int result, String message) {
        super();
        this.result = result;
        this.message = message;
    }

    /**
     * Creates a new instance of ResponseEntity.
     *
     * @param message
     * @param value
     */
    public ResponseEntity(String message, T value) {
        this(message);
        this.value = value;
    }

    /**
     * Creates a new instance of ResponseEntity.
     *
     * @param result
     * @param message
     * @param value
     */
    public ResponseEntity(int result, String message, T value) {
        super();
        this.result = result;
        this.message = message;
        this.value = value;
    }

    /**
     * Creates a new instance of ResponseEntity.
     *
     * @param responseEntity
     */
    public ResponseEntity(ResponseEntity<T> responseEntity) {
        super();
        this.result = responseEntity.result;
        this.message = responseEntity.message;
        this.value = responseEntity.value;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ResponseEntity [result=" + result + ", message=" + message
                + ", value=" + value + "]";
    }

    public static <T> ResponseEntity<T> ok(){
        return ok(null);
    }

    public static <T> ResponseEntity<T> ok(T value){
        ResponseEntity<T> entity = new ResponseEntity<>();
        entity.result = GeneConstant.SUCCESS_CODE;
        entity.message = GeneConstant.SUCCESS;
        entity.value = value;
        return entity;
    }

    public static ResponseEntity error(){
        return error(null);
    }

    public static ResponseEntity error(String message){
        ResponseEntity entity = new ResponseEntity();
        entity.result = GeneConstant.BUSINESS_ERROR;
        entity.message = message;
        return entity;
    }



    public static ResponseEntity success( Object t){

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,t);
    }
    public static ResponseEntity success(){

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE);
    }
}