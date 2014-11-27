package lecture3.example2;

import org.springframework.aop.framework.ProxyFactory;

public class SecurityExample {

    public static void main(String[] args) {
        SecurityManager manager = new SecurityManager();

        MessageService service = getService(manager);

        try {
            manager.login("john", "P@ssw0rd");
            service.send("mary", "Spring AOP works!");
        } catch (SecurityException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            manager.logout();
        }

        try {
            manager.login("mary", "1q2w3e4r");
            service.send("john", "Spring AOP works!");
        } catch (SecurityException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            manager.logout();
        }

        try {
            service.send("john", "Spring AOP works!");
        } catch (SecurityException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static MessageService getService(
            SecurityManager manager) {
        MessageService target = new MessageService();

        SecurityAdvice advice = new SecurityAdvice(manager);

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvice(advice);

        return (MessageService)proxyFactory.getProxy();
    }
}
