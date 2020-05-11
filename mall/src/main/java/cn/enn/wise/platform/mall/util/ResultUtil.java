package cn.enn.wise.platform.mall.util;

import lombok.Data;

import java.io.Serializable;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/4/23 14:28
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class ResultUtil<T> implements Serializable {


    private static final long serialVersionUID = 2549460992608552040L;


    /**返回结果的状态值**/
    private Integer result;
    /**返回结果的信息**/
    private String message;
    /**返回结果的数据为对象**/
    private T value;

    public ResultUtil() {

    }

    public ResultUtil(Integer result, String message, T value) {

        this.result=result;
        this.message=message;
        this.value=value;

    }
}
