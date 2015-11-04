package addressbook;

import org.joda.time.Days;

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
        Person personOne = persons.stream().filter((Person person) -> person.name().equals(nameOne)).findFirst().get();
        Person personTwo = persons.stream().filter((Person person) -> person.name().equals(nameTwo)).findFirst().get();

        return Days.daysBetween(personTwo.dateOfBirth(), personOne.dateOfBirth()).getDays();
    }

}
