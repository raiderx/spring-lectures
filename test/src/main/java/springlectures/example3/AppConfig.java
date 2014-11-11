package springlectures.example3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springlectures.example1.TransferService;
import springlectures.example1.CarTransferService;

@Configuration
public class AppConfig {

    @Bean
    public TransferService transferService() {
        return new CarTransferService();
    }
}
