package addressbook;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.List;

import static java.util.Comparator.comparing;

public class AddressBook {

    private final List<Person> persons;

    public AddressBook(AddressBookLoader addressBookLoader) {
        persons = addressBookLoader.load();
    }

    public int numberOf(Gender gender) {
        return (int) persons.stream()
                .filter((Person person) -> person.gender() == gender)
                .count();
    }

    public Person oldestPerson() {
        return persons.stream()
                .min(comparing(Person::dateOfBirth))
                .orElseThrow(() -> new IllegalStateException("Address book is empty"));
    }

    public int ageDifference(String nameOne, String nameTwo) {
        return Days.daysBetween(dateOfBirthOf(nameOne), dateOfBirthOf(nameTwo)).getDays();
    }

    private LocalDate dateOfBirthOf(String name) {
        return persons.stream()
                .filter((Person person) -> person.name().equals(name))
                .findFirst()
                .map(Person::dateOfBirth)
                .orElseThrow(() -> new IllegalArgumentException(name + " not found in address book"));
    }

}
