package addressbook;

import org.junit.Test;

import static addressbook.Gender.FEMALE;
import static addressbook.Gender.MALE;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook(new AddressBookLoader());

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

}
