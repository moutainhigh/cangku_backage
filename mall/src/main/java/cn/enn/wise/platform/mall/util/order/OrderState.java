package cn.enn.wise.platform.mall.util.order;

/***
 * 订单流转记录
 * @author zhangshaojie
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OrderState {

    /**
     * 本次操作说明
     * @return
     */
    public String operate() default "";

    /**
     * 参数对象，
     * 如果使用的自定的订单对象，请传入
     */
    public Class<?> clazz();

    /**
     * 默认为基本语句修改如  update orders set state = ? where id = ?
     * 基本操作参数默认为  1
     * 自定义参数对象请输入  2 如order对象、orders对象、以及自定义的订单表对象。
     * @return
     */
    public String value() default "1";
}
