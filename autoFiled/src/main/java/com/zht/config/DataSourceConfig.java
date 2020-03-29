package com.zht.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 数据源及事务配置
 * </p>
 *
 * @author zht
 * @since 2020-03-26
 */
@Aspect
@Configuration
public class DataSourceConfig {

    private static final String AOP_POINTCUT_EXPRESSION = "bean(*ServiceImpl) && execution(* com.zht..*.*(..))";

    @Autowired
    PlatformTransactionManager txManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        //支持当前事务，如果当前没有事务，就新建一个事务
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        //只读事务，支持当前事务，如果当前没有事务，就以非事务方式执行
        RuleBasedTransactionAttribute readonlyTx = new RuleBasedTransactionAttribute();
        readonlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        readonlyTx.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        Map<String, TransactionAttribute> txMap = new HashMap<>(15);
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("init*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("modify*", requiredTx);
        txMap.put("edit*", requiredTx);
        txMap.put("del*", requiredTx);
        txMap.put("remove*", requiredTx);
        txMap.put("*", readonlyTx);
        source.setNameMap(txMap);
        return new TransactionInterceptor(txManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
