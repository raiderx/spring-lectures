package lecture4.example1;

import java.util.List;

public interface ContactsDao {

    int getContactsCount();

    List<Contact> getContacts();

    Contact getContactById(int id);

    void create(Contact contact);

    void update(Contact contact);

    void removeById(int id);
}
