package addressbook;

import java.util.Date;

public class Person {

    private final String name;
    private final Gender gender;
    private final Date dateOfBirth;

    public Person(String name, Gender gender, Date dateOfBirth) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String name() {
        return name;
    }

    public Gender gender() {
        return gender;
    }

    public Date dateOfBirth() {
        return dateOfBirth;
    }

}
