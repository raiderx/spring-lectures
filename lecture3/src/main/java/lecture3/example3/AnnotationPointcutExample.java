package lecture3.example3;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

public class AnnotationPointcutExample {

    public static void main(String[] args) {
        Pointcut pointcut = AnnotationMatchingPointcut
                .forMethodAnnotation(AdviceRequired.class);

        Advice advice = new SimpleAdvice();

        Advisor advisor = new DefaultPointcutAdvisor(
                pointcut, advice);

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
    }
}
