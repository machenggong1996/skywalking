package org.apache.skywalking.apm.plugin.commons.define;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.DeclaredInstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.apache.skywalking.apm.agent.core.plugin.match.ClassMatch;
import org.apache.skywalking.apm.agent.core.plugin.match.PrefixMatch;

import static net.bytebuddy.matcher.ElementMatchers.isPublic;

/**
 * @author machenggong
 * @since 2021/8/20
 */
public class KwCoreActivation extends ClassInstanceMethodsEnhancePluginDefine {

    public static final String KW_CORE_METHOD_INTERCEPTOR = "org.apache.skywalking.apm.plugin.commons.KwCoreMethodInterceptor";

    @Override
    public ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return new ConstructorInterceptPoint[0];
    }

    @Override
    public InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[]{
                new DeclaredInstanceMethodsInterceptPoint() {
                    @Override
                    public ElementMatcher<MethodDescription> getMethodsMatcher() {
                        //匹配要增强的方法  我选择的public类型的方法
                        //net.bytebuddy.matcher.ElementMatchers  类有很多选择方法，自取
                        return isPublic();
                    }

                    @Override
                    public String getMethodsInterceptor() {
                        return KW_CORE_METHOD_INTERCEPTOR;
                    }

                    @Override
                    public boolean isOverrideArgs() {
                        return false;
                    }
                }
        };
    }

    @Override
    protected ClassMatch enhanceClass() {
        //匹配到所有com.开头的class并做增强准备，改成你想要增强的class
        //org.apache.skywalking.apm.agent.core.plugin.match  此包下有各种匹配器，各取所需
        return PrefixMatch.nameStartsWith("com.");
    }

}
