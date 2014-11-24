package lecture3.example2;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class SecurityAdvice implements MethodBeforeAdvice {

    private SecurityManager securityManager;

    public SecurityAdvice(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        String username = securityManager.getUsername();
        if (username == null) {
            throw new SecurityException("You must login before attempting to invoke method " + method.getName());
        }
        if ("john".equals(username)) {
            System.out.println("Logged in user is " + username + ": OK!");
        } else {
            System.out.println("Logged in user is " + username + ": BAD!");
            throw new SecurityException("User " + username + " is not allowed to invoke method " + method.getName());
        }
    }
}
