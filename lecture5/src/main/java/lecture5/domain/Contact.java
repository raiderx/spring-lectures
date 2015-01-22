package lecture5.domain;

import java.time.LocalDate;

/**
 * @author Pavel Karpukhin
 * @since 29.12.14
 */
public class Contact {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public Contact() {
    }

    public Contact(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
