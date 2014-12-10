package lecture4.example2;

import java.util.List;

public interface PersonDao {

    List<Person> getPersons();

    Person findById(int id);

    void create(Person person);

    void update(Person person);

    void removeById(int id);
}
