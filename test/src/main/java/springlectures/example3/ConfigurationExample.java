package springlectures.example3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springlectures.example2.Gateway;
import springlectures.example2.MailMessageService;
import springlectures.example2.MessageService;

public class ConfigurationExample {

    public static void main(String[] args) {
        ApplicationContext context
                = new AnnotationConfigApplicationContext(
                        TestConfig.class, Gateway.class, MailMessageService.class);

        MessageService service =
                context.getBean(MessageService.class);

        service.message("john@gmail.com",
                        "Spring Framework is Great!");
    }
}
