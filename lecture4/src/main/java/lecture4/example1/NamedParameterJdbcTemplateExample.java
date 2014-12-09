package lecture4.example1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * @author Pavel Karpukhin
 * @since 10.12.14
 */
public class NamedParameterJdbcTemplateExample {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "lecture4/example1/jdbc-template-2.xml");

        ContactsDao contactsDao = context.getBean(ContactsDao.class);
        List<Contact> contacts = contactsDao.getContacts();
        printContacts(contacts);

        int id = 1;
        Contact contact = contactsDao.getContactById(id);
        System.out.println(contact);
        System.out.println();

        contact = new Contact();
        contact.setFirstName("Bill");
        contact.setLastName("Gates");
        contact.setEmail("bill.gates@microsoft.com");
        contact.setBirthDate(new Date());
        contactsDao.create(contact);
        System.out.println(contact);
        System.out.println();

        printContacts(contactsDao.getContacts());

        id = 0;
        contactsDao.removeById(id);

        printContacts(contactsDao.getContacts());
    }

    private static void printContacts(List<Contact> contacts) {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
        System.out.println();
    }
}
