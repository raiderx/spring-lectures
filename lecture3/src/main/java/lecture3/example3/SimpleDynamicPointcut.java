package lecture3.example3;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

public class SimpleDynamicPointcut
        extends DynamicMethodMatcherPointcut {

    @Override
    public ClassFilter getClassFilter() {
        return new ClassFilter() {
            @Override
            public boolean matches(Class<?> clazz) {
                return clazz == BeanTwo.class;
            }
        };
    }

    @Override
    public boolean matches(Method method, Class<?> clazz) {
        return "foo".equals(method.getName());
    }

    @Override
    public boolean matches(Method method, Class<?> clazz,
                           Object[] args) {
        int x = ((Integer)args[0]).intValue();
        return x != 100;
    }
}
