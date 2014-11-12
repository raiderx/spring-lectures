package springlectures.example2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailMessageService implements MessageService {

    @Autowired
    private Gateway gateway;

    @Override
    public void message(String to, String message) {
        System.out.println("Sending '" + message + "' to " +
                to + " through " + gateway.getAddress());
    }
}
