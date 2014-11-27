package lecture3.example4;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProxyFactoryBeanExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "lecture3/example4/pfb-example.xml");
        MyBean bean1 = context.getBean("myBean1", MyBean.class);
        MyBean bean2 = context.getBean("myBean2", MyBean.class);

        System.out.println("Bean 1");
        bean1.execute();

        System.out.println("\nBean 2");
        bean2.execute();
    }
}
