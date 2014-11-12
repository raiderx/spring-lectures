package springlectures.example2;

import org.springframework.stereotype.Component;

@Component
public class Gateway {

    private String address;

    public Gateway(String address) {
        this.address = address;
    }
}
