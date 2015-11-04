package addressbook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static addressbook.Gender.FEMALE;
import static addressbook.Gender.MALE;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class AddressBookTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook(new AddressBookLoader("AddressBook.txt", AddressBookLoader.class));

    @Test
    public void returnsNumberOfMales() {
        int numberOfMales = addressBook.numberOf(MALE);

        assertThat(numberOfMales, equalTo(3));
    }

    @Test
    public void returnsNumberOfFemales() {
        int numberOfMales = addressBook.numberOf(FEMALE);

        assertThat(numberOfMales, equalTo(2));
    }

    @Test
    public void returnsOldestPerson() {
        Person person = addressBook.oldestPerson();

        assertThat(person.name(), equalTo("Wes Jackson"));
    }

    @Test
    public void throwsExceptionIfAddressBookIsEmpty() {
        AddressBook addressBook = new AddressBook(new AddressBookLoader("EmptyAddressBook.txt", getClass()));

        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Address book is empty");

        addressBook.oldestPerson();
    }

    @Test
    public void calculatesAgeDifferenceWithinSameYear() {
        AddressBook addressBook = new AddressBook(new AddressBookLoader("TestAddressBook.txt", getClass()));

        int difference = addressBook.ageDifference("Maciej Kowalski", "Jaroslaw Pawlak");

        assertThat(difference, equalTo(28 + 31));
    }

}
