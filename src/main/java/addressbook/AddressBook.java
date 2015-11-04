package addressbook;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class AddressBook {

    private final List<Person> persons;

    public AddressBook(AddressBookLoader addressBookLoader) {
        this.persons = addressBookLoader.load();
    }

    /**
     * Counts number of people who meet given predicate
     */
    public int count(Predicate<Person> predicate) {
        return (int) persons.stream()
                .filter(predicate)
                .count();
    }

    /**
     * Returns person with lowest value for given comparator
     */
    public Person firstPerson(Comparator<Person> comparator) {
        return persons.stream()
                .min(comparator)
                .orElseThrow(() -> new IllegalStateException("Address book is empty"));
    }

    /**
     * Finds a first person who meets predicateOne and a first person who meets predicateTwo,
     * then returns their age difference in days.
     */
    public int ageDifferenceInDays(Predicate<Person> predicateOne, Predicate<Person> predicateTwo) {
        return Days.daysBetween(
                dateOfBirthFor("predicateOne", predicateOne),
                dateOfBirthFor("predicateTwo", predicateTwo)
        ).getDays();
    }


    private LocalDate dateOfBirthFor(String predicateName, Predicate<Person> predicateOne) {
        return persons.stream()
                .filter(predicateOne)
                .findFirst()
                .map(Person::dateOfBirth)
                .orElseThrow(() -> new IllegalArgumentException("No person found in address book for " + predicateName));
    }

}
