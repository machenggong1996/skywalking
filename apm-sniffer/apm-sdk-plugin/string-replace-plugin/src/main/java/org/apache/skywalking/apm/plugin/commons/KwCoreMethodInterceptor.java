package org.apache.skywalking.apm.plugin.commons;

import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.tag.Tags;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.apache.skywalking.apm.agent.core.util.MethodUtil;

import java.lang.reflect.Method;

/**
 * @author machenggong
 * @since 2021/8/20
 */
public class KwCoreMethodInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                             MethodInterceptResult result) throws Throwable {

        String operationName = "";
        //取方法名路径
        operationName = MethodUtil.generateOperationName(method);
        //打span暴露到skywalking
        AbstractSpan span = ContextManager.createLocalSpan(operationName);
//            span.setComponent(new OfficialComponent(107, "custom-method"));
//            span.tag(new StringTag(1000, "params"), argumentsTypes[0].toString());
//            span.setLayer(SpanLayer.CACHE);
        Tags.DB_TYPE.set(span, "public method");

//            SpanLayer.asCache(span);

    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Object ret) throws Throwable {
        ContextManager.stopSpan();
        return ret;

    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes, Throwable t) {
        ContextManager.activeSpan().log(t);
    }

}
