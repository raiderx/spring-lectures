package springlectures.example3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springlectures.example1.TransferService;

/**
 * @author Pavel Karpukhin
 * @since 12.11.14.
 */
public class ConfigurationExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        TransferService service = context.getBean(TransferService.class);
        service.transfer("A", "B");
    }

}
