package springlectures.example2;

import org.springframework.stereotype.Service;

@Service
public class MailMessageService implements MessageService {

    private Gateway gateway;

    @Override
    public void message(String to, String message) {
        System.out.println("Sending " + message + " to " + to);
    }
}
