package springlectures.example2;

import org.springframework.stereotype.Service;

@Service
public class SmsMessageService implements MessageService {

    @Override
    public void message(String to, String message) {
        System.out.println("Sending " + message + " to " + to);
    }
}
