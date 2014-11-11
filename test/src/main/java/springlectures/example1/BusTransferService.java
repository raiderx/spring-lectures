package springlectures.example1;

public class BusTransferService implements TransferService {

    private BusDriver driver;

    public void setDriver(BusDriver driver) {
        this.driver = driver;
    }

    @Override
    public void transfer(String from, String to) {
    }
}
