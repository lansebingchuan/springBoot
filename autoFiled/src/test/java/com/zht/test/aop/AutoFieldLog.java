package com.zht.test.aop;

import cn.hutool.core.util.ObjectUtil;
import com.zht.test.annotation.SysLog;
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

import java.lang.reflect.Method;

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

    @Pointcut("@annotation(com.zht.test.annotation.SysLog)")
    private void sysLog() {
    }

    @Around(value = "sysLog()")
    public void around(ProceedingJoinPoint pjp){
        Object[] args = pjp.getArgs();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (ObjectUtil.isNotNull(sysLog)){
            String value = sysLog.value();
            String[] parameterNames = discoverer.getParameterNames(method);
            //将参数纳入Spring管理
            EvaluationContext context = new StandardEvaluationContext();
           for (int i = 0 ; i < parameterNames.length ; i++){
               context.setVariable(parameterNames[i], args[i]);
           }
            Expression expression = parser.parseExpression(value);
            String content = expression.getValue(context, String.class);
            System.out.println("打印内容："+content);
        }
    }
}
