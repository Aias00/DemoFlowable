package com.aias.demo.framework.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *  事务配置
 *  
 */
@Aspect
@Configuration
public class TransactionConfig {

	
	@Value("${aop.pointcut.expression}")
	private String aop_pointcut_expression;
	
	
    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute rollbackTx = new RuleBasedTransactionAttribute();
        rollbackTx.setRollbackRules(
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        rollbackTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("query*", readOnlyTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("load*", readOnlyTx);
        txMap.put("is*", readOnlyTx);
        txMap.put("find*", readOnlyTx);

        txMap.put("add*", rollbackTx);
        txMap.put("del*", rollbackTx);
        txMap.put("save*", rollbackTx);
        txMap.put("modify*", rollbackTx);
        txMap.put("update*", rollbackTx);
        txMap.put("edit*", rollbackTx);
        txMap.put("bulkUpdate*", rollbackTx);
        txMap.put("merge*", rollbackTx);
        txMap.put("persist*", rollbackTx);
        txMap.put("complete*", rollbackTx);
        txMap.put("execute*", rollbackTx);
        txMap.put("create*", rollbackTx);
        txMap.put("commit*", rollbackTx);
        txMap.put("insert*", rollbackTx);
        txMap.put("to*", rollbackTx);
        txMap.put("review*", rollbackTx);
        txMap.put("batch*", rollbackTx);
        txMap.put("generate*", rollbackTx);
        txMap.put("apply*", rollbackTx);
        txMap.put("import*", rollbackTx);
        txMap.put("start*", rollbackTx);
        txMap.put("send*", rollbackTx);
        txMap.put("submit*", rollbackTx);

        source.setNameMap(txMap);
        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(aop_pointcut_expression);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }


}