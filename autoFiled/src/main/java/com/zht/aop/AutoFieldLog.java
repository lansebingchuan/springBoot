package com.zht.aop;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.zht.annotation.FormatDateTime;
import com.zht.annotation.SysLog;
import com.zht.utils.DateTimeUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhanghaitao
 * @date 2020/3/20 0020
 */
@Component
@Aspect
public class AutoFieldLog {

    /**
     * spel解析表达式
     */
    private ExpressionParser parser = new SpelExpressionParser();

    /**
     * 将方法参数纳入Spring管理
     */
    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Pointcut("@annotation(com.zht.annotation.SysLog)")
    private void sysLog() {
    }

    @Around(value = "sysLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (ObjectUtil.isNotNull(sysLog)){
            String value = sysLog.value();
            String[] parameterNames = discoverer.getParameterNames(method);
            //将参数纳入Spring管理
            EvaluationContext context = new StandardEvaluationContext();
            if (parameterNames.length > 0){
                //设置对应的参数值
                for (int i = 0 ; i < parameterNames.length ; i++){
                    context.setVariable(parameterNames[i], args[i]);
                }
                //解析表达式或者值
                Expression expression = parser.parseExpression(value);
                //通过表达式把值带入
                String content = expression.getValue(context, String.class);
                System.out.println("打印内容："+content);
            }
        }
        //方法获取注解
        FormatDateTime formatDateTime = method.getAnnotation(FormatDateTime.class);
        //执行目标方法->返回值
        Object obj = pjp.proceed();
        if (ObjectUtil.isNotNull(formatDateTime)){
            Object value = obj;
            if (!formatDateTime.ref().equals("")){
                //获取具体的属性
                String[] fieldSources = formatDateTime.ref().split(".");
                //循环得到值
                for (int i = 0; i < fieldSources.length; i++){
                    if (i == 0){
                        value = ReflectUtil.getFieldValue(obj, fieldSources[i]);
                    }else {
                        value = ReflectUtil.getFieldValue(value, fieldSources[i]);
                    }
                }
            }
            //最终的值
            Object source = value;
            //获取需要解释对象的所有字段
            Field[] fields = source.getClass().getDeclaredFields();
            Arrays.stream(fields).forEach(field -> {
                //获取字段注解上的->formatDateTimeField
                FormatDateTime formatDateTimeField = field.getDeclaredAnnotation(FormatDateTime.class);
                if (ObjectUtil.isNotNull(formatDateTimeField)){
                    //如果有，获取格式化方式
                    String format = formatDateTimeField.format();
                    //获取格式化的原对象
                    String ref = formatDateTimeField.ref();
                    //原对象值
                    Object fieldValue = ReflectUtil.getFieldValue(source, ref);
                    //通过反射注入格式化后的值
                    ReflectUtil.setFieldValue(source,  field, DateTimeUtil.formatLocalDateTime((LocalDateTime)fieldValue, format));
                }
            });
        }
        return obj;
    }
}
