package springlectures.example3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springlectures.example2.Gateway;
import springlectures.example2.MailMessageService;
import springlectures.example2.MessageService;

@Configuration
public class AppConfig {

    @Bean
    public Gateway gateway() {
        return new Gateway();
    }

    @Bean
    public MessageService transferService() {
        return new MailMessageService();
    }
}
