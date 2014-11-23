package lecture3.example1;

import org.springframework.aop.framework.ProxyFactory;

public class NotificationExample {

    public static void main(String[] args) {
        Kettle target = new Kettle();

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvice(new NotifyingInterceptor());
        proxyFactory.setTarget(target);

        Kettle proxy = (Kettle)proxyFactory.getProxy();

        proxy.boil();
    }
}
