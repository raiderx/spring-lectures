package springlectures.example2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Gateway {

    @Value("8.8.8.8")
    private String address;

    public String getAddress() {
        return address;
    }
}
