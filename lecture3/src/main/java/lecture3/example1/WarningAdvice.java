package lecture3.example1;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

public class WarningAdvice implements MethodBeforeAdvice {

    public static void main(String[] args) {
        Kettle target = new Kettle();

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvice(new WarningAdvice());
        proxyFactory.setTarget(target);

        Kettle proxy = (Kettle)proxyFactory.getProxy();

        proxy.boil();
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("WARNING! High temperature!");
    }
}
