package lecture5.service;

import lecture5.domain.Contact;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Karpukhin
 * @since 29.12.14
 */
@Service
public class ContactService {

    private List<Contact> contacts = new ArrayList<>();

    public ContactService() {
        contacts.add(new Contact("Michael", "Jordan", LocalDate.of(1963, 2, 17)));
        contacts.add(new Contact("Patrick", "Ewing", LocalDate.of(1962, 8, 5)));
    }

    public List<Contact> findAll() {
        return contacts;
    }
}
