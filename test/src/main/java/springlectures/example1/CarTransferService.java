package springlectures.example1;

public class CarTransferService implements TransferService {

    @Override
    public void transfer(String from, String to) {
        System.out.println("Transfer from " + from + " to " + to + " by car");
    }
}
