package lecture3.example5;

import org.aspectj.lang.JoinPoint;

public class MyAdvice {

    public void simpleBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Executing: " +
                joinPoint.getSignature());
    }
}
