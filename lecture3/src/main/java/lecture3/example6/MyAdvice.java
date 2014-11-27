package lecture3.example6;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {

    @Pointcut("execution(* foo*(int)) && args(value)")
    public void fooExecution(int value) { }

    @Pointcut("bean(myDependency*)")
    public void inMyDependency() { }

    @Before("fooExecution(value) && inMyDependency()")
    public void simpleBeforeAdvice(JoinPoint joinPoint,
                                   int value) {
        if (value != 100) {
            System.out.println("Executing: " +
                    joinPoint.getSignature() +
                    " argument: " + value);
        }
    }

    @Around("fooExecution(value) && inMyDependency()")
    public Object simpleAroundAdvice(ProceedingJoinPoint pjp,
                                     int value) throws Throwable {
        System.out.println("Before execution: " +
                pjp.getSignature() +
                " argument: " + value);
        Object result = pjp.proceed();
        System.out.println("After execution: " +
                pjp.getSignature() +
                " argument: " + value);
        return result;
    }
}
