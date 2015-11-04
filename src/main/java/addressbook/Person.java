package addressbook;

import java.util.Date;

public class Person {

    private final String name;
    private final String surname;
    private final Gender gender;
    private final Date dateOfBirth;

    public Person(String name, String surname, Gender gender, Date dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Gender gender() {
        return gender;
    }

}
