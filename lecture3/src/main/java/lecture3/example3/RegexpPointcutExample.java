package lecture3.example3;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

public class RegexpPointcutExample {

    public static void main(String[] args) {
        JdkRegexpMethodPointcut pointcut =
                new JdkRegexpMethodPointcut();
        pointcut.setPattern(".*foo.*");

        Advice advice = new SimpleAdvice();

        Advisor advisor = new DefaultPointcutAdvisor(
                pointcut, advice);

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);

    }
}
