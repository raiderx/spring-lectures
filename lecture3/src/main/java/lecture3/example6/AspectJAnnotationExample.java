package lecture3.example6;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AspectJAnnotationExample {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        MyConfig.class);

        MyBean bean = context.getBean("myBean", MyBean.class);

        bean.execute();
    }
}
