package addressbook;

import org.junit.Test;

import static addressbook.Gender.MALE;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void returnsNumberOfMales() {
        int numberOfMales = addressBook.numberOf(MALE);

        assertThat(numberOfMales, equalTo(3));
    }

}
