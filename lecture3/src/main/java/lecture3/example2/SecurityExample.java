package lecture3.example2;

import org.springframework.aop.framework.ProxyFactory;

public class SecurityExample {

    public static void main(String[] args) {
        SecurityManager securityManager = new SecurityManager();

        MessageService service = getMessageService(securityManager);

        try {
            securityManager.login("john", "P@ssw0rd");
            service.send("mary", "Spring AOP works!");
        } catch (SecurityException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            securityManager.logout();
        }

        try {
            securityManager.login("mary", "1q2w3e4r");
            service.send("john", "Spring AOP works!");
        } catch (SecurityException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            securityManager.logout();
        }

        try {
            service.send("john", "Spring AOP works!");
        } catch (SecurityException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static MessageService getMessageService(SecurityManager securityManager) {
        MessageService target = new MessageService();

        SecurityAdvice advice = new SecurityAdvice(securityManager);

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvice(advice);
        return (MessageService)proxyFactory.getProxy();
    }
}
