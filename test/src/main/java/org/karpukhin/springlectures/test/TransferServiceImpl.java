package org.karpukhin.springlectures.test;

/**
 * @author Pavel Karpukhin
 * @since 09.11.14.
 */
public class TransferServiceImpl implements TransferService {

    @Override
    public void transfer(String from, String to) {
        System.out.println("Transfer from " + from + " to " + to);
    }
}
