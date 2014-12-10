package lecture4.example2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class PersonDaoImpl implements PersonDao {

    private SessionFactory sessionFactory;

    public PersonDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getPersons() {
        return getSession().createQuery("from Person").list();
    }

    @Override
    @Transactional(readOnly = true)
    public Person findById(int id) {
        return (Person)getSession().get(Person.class, id);
    }

    @Override
    public void create(Person person) {
        getSession().save(person);
    }

    @Override
    public void update(Person person) {
        getSession().update(person);
    }

    @Override
    public void removeById(int id) {
        getSession().delete(findById(id));
    }
}
