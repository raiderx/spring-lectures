package lecture4.example1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JdbcTemplateExample {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "lecture4/example1/jdbc-template.xml");

        JdbcTemplate jdbcTemplate =
                context.getBean(JdbcTemplate.class);

        String lastName = "Jobs";
        String firstName = jdbcTemplate.queryForObject(
                "SELECT FIRST_NAME FROM CONTACTS WHERE LAST_NAME = ?",
                new Object[] { lastName }, String.class);
        System.out.println("First name: " + firstName + "\n");

        firstName = jdbcTemplate.queryForObject(
                "SELECT FIRST_NAME FROM CONTACTS WHERE EMAIL LIKE ?",
                String.class, "linus.torvalds@gmail.com");
        System.out.println("First name: " + firstName + "\n");

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

        Calendar calendar = Calendar.getInstance();
        calendar.set(1973, 8, 8);

        contact.setId(0);
        contact.setFirstName("Mark");
        contact.setLastName("Shuttleworth");
        contact.setEmail("mark.shuttleworth@ubuntu.com");
        contact.setBirthDate(calendar.getTime());
        contactsDao.update(contact);

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
