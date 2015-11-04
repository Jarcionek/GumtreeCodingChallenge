package addressbook;

import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.List;

import static addressbook.Gender.FEMALE;
import static addressbook.Gender.MALE;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.asList;

public class AddressBookLoaderTest {

    private final AddressBookLoader addressBookLoader = new AddressBookLoader("AddressBook.txt", AddressBookLoader.class);

    @Test
    public void loadsAddressBookFromFile() {
        List<Person> persons = addressBookLoader.load();

        assertThat(persons, sameBeanAs(asList(
                new Person("Bill McKnight", MALE,   date(16, 3,  77)),
                new Person("Paul Robinson", MALE,   date(15, 1,  85)),
                new Person("Gemma Lane",    FEMALE, date(20, 11, 91)),
                new Person("Sarah Stone",   FEMALE, date(20, 9,  80)),
                new Person("Wes Jackson",   MALE,   date(14, 8,  74))
        )));
    }

    private static LocalDate date(int day, int month, int year) {
        return new LocalDate(1900 + year, month, day);
    }

}
