package addressbook;

import java.util.List;

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

}
