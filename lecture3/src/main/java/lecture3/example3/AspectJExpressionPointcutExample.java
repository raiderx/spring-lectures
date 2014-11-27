package lecture3.example3;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AspectJExpressionPointcutExample {

    public static void main(String[] args) {
        AspectJExpressionPointcut pointcut =
                new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo*(..))");

        Advice advice = new SimpleAdvice();

        Advisor advisor = new DefaultPointcutAdvisor(
                pointcut, advice);

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
    }
}
