package springlectures.example2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConfigWithAnnotatedBeansExample {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "springlectures/example2/message-service.xml");

        MessageService service =
                context.getBean(MessageService.class);

        service.message("john@gmail.com", "Spring Framework is Great!");
    }
}
