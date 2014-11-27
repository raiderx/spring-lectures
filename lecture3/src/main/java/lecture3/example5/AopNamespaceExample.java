package lecture3.example5;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopNamespaceExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "lecture3/example5/aopns-example.xml");

        MyBean bean = context.getBean("myBean", MyBean.class);

        bean.execute();
    }
}
