package lecture3.example3;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Pavel Karpukhin
 * @since 27.11.14.
 */
public class SimpleAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }
}
