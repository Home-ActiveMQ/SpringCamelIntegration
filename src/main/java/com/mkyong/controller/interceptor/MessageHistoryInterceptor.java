package com.mkyong.controller.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

@Component
public class MessageHistoryInterceptor implements MethodInterceptor {

    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

    private static final Logger LOGGER = LogManager.getLogger(MessageHistoryInterceptor.class);

    @Override
    public Object invoke(MethodInvocation i) throws Throwable {
        Class<?> c = AopProxyUtils.ultimateTargetClass(i.getThis());
        if (c == null && i.getThis() != null) {
            c = i.getThis().getClass();
        }

        Method m = ClassUtils.getMostSpecificMethod(i.getMethod(), c);
        MessageHistoryOperation a = AnnotationUtils.getAnnotation(m, MessageHistoryOperation.class);

        String messageHistory = createMessageHistory(a, m, i);
        LOGGER.debug(messageHistory);

        // proceed
        Object r = i.proceed();
        return r;
    }


    private String createMessageHistory(MessageHistoryOperation a, Method m, MethodInvocation i) {
        StandardEvaluationContext elContext = new StandardEvaluationContext(i.getArguments());

        String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(m);
        if (parameterNames != null) {
            for (int j = 0; j < parameterNames.length; j++) {
                elContext.setVariable(parameterNames[j], i.getArguments()[j]);
            }
        }

        String messageHistory = "";
        messageHistory = messageHistory + "connectorClient = " + a.connectorClient() + "; ";
        messageHistory = messageHistory + "operatorID = " + a.operatorID() + "; ";
        messageHistory = messageHistory + "operationType = " + a.operationType() + "; ";

        return messageHistory;
    }
}
