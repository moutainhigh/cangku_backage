package cn.enn.wise.platform.mall.util.order;

import cn.enn.wise.platform.mall.service.OrderStateHistoryService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * 订单流转记录
 * @program: mall
 * @author: zsj
 * @create: 2020-01-15 10:17
 **/
@Aspect
@Component
public class OrderStateAspect {

    private static final Logger logger = LoggerFactory.getLogger(OrderStateAspect.class);

    @Autowired
    private OrderStateHistoryService orderStateHistoryService;

    @Pointcut("@annotation(cn.enn.wise.platform.mall.util.order.OrderState)")
    public void annotationPointcut() {
    }

    @After("annotationPointcut() && @annotation(orderState)")
    public void afterAnnotationPointcut(JoinPoint joinPoint, OrderState orderState) throws IllegalAccessException, NoSuchMethodException {
        logger.info("======触发订单流转记录=======");
        String value = orderState.value();
        String operate = orderState.operate();
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] argValues = joinPoint.getArgs();
        if(value.equals("1")){
            for (int i = 0; i < argNames.length; i++) {
                if(argNames[i].equals("id")){
                    Long val = (Long)argValues[i];
                    if(val != null){
                        orderStateHistoryService.saveOrderStateHistory(val, operate);
                    }
                }
            }
        }else if(value.equals("2")){
            Class<?> clazz = orderState.clazz();
            if(clazz != null){
                for (Object argValue : argValues) {
                    if(clazz.isInstance(argValue)){
                        Field[] fields = clazz.getDeclaredFields();
                        if(fields != null){
                            for (Field field : fields) {
                                field.setAccessible(true);
                                if(field.getName().equals("id")){
                                    Long obj = (Long) field.get(argValue);
                                    if(obj != null){
                                        orderStateHistoryService.saveOrderStateHistory(obj, operate);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
