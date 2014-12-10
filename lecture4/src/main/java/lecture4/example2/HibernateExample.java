package lecture4.example2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;
import java.util.List;

public class HibernateExample {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "lecture4/example2/hibernate-context.xml");

        PersonDao personDao = context.getBean(PersonDao.class);

        List<Person> persons = personDao.getPersons();
        printPersons(persons);

        Person person = personDao.findById(1);
        System.out.println(person + "\n");

        Calendar calendar = Calendar.getInstance();
        calendar.set(1961, 5, 14);

        person = new Person();
        person.setFirstName("Samuel");
        person.setLastName("Perkins");
        person.setHeight(206);
        person.setWeight(107);
        person.setBirthDate(calendar.getTime());
        personDao.create(person);
        System.out.println(person + "\n");

        printPersons(personDao.getPersons());

    }

    private static void printPersons(List<Person> persons) {
        for (Person person : persons) {
            System.out.println(person);
        }
        System.out.println();
    }
}
