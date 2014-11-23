package lecture3.example1;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class NotifyingInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("Boiling started");
        Object result = invocation.proceed();
        System.out.println("Boiling ended");
        return result;
    }
}
