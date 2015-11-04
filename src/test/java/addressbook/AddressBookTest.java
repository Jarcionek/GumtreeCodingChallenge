package addressbook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static addressbook.Gender.FEMALE;
import static addressbook.Gender.MALE;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static java.util.Comparator.comparing;
import static org.hamcrest.CoreMatchers.equalTo;

public class AddressBookTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook(new AddressBookLoader(new Clock(), "AddressBook.txt", AddressBookLoader.class));

    @Test
    public void returnsNumberOfMales() {
        int numberOfMales = addressBook.count(person -> person.gender() == MALE);

        assertThat(numberOfMales, equalTo(3));
    }

    @Test
    public void returnsNumberOfFemales() {
        int numberOfMales = addressBook.count(person -> person.gender() == FEMALE);

        assertThat(numberOfMales, equalTo(2));
    }

    @Test
    public void returnsOldestPerson() {
        Person person = addressBook.firstPerson(comparing(Person::dateOfBirth));

        assertThat(person.name(), equalTo("Wes Jackson"));
    }

    @Test
    public void throwsExceptionIfAddressBookIsEmpty() {
        AddressBook addressBook = new AddressBook(new AddressBookLoader(new Clock(), "EmptyAddressBook.txt", getClass()));

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Address book is empty");

        addressBook.firstPerson(comparing(Person::dateOfBirth));
    }

    @Test
    public void calculatesAgeDifferenceWithinSameYear() {
        AddressBook addressBook = new AddressBook(new AddressBookLoader(new Clock(), "TestAddressBook.txt", getClass()));

        long difference = addressBook.ageDifferenceInDays(person -> person.name().equals("Jaroslaw Pawlak"),
                                                         person -> person.name().equals("Maciej Kowalski"));

        assertThat(difference, equalTo(28L + 31L));
    }

    @Test
    public void calculatesAgeDifferenceForPeopleBornInDifferentYears() {
        AddressBook addressBook = new AddressBook(new AddressBookLoader(new Clock(), "TestAddressBook.txt", getClass()));

        long difference = addressBook.ageDifferenceInDays(person -> person.name().equals("Sarah Stone"),
                                                         person -> person.name().equals("Paul Robinson"));

        // days between 20/09/80 and 15/01/85
        long daysIn1980 = 10 + 31 + 30 + 31;
        long daysIn1981_1984 = 365 + 365 + 365 + 366;
        long daysIn1985 = 15;
        assertThat(difference, equalTo(daysIn1980 + daysIn1981_1984 + daysIn1985));
    }

    @Test
    public void throwsExceptionWhenFirstPersonNotFound() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("No person found in address book for predicateOne");

        addressBook.ageDifferenceInDays(person -> false, person -> true);
    }

    @Test
    public void throwsExceptionWhenSecondPersonNotFound() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("No person found in address book for predicateTwo");

        addressBook.ageDifferenceInDays(person -> true, person -> false);
    }

}
