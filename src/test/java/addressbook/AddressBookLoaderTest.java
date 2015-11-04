package addressbook;

import org.joda.time.LocalDate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static addressbook.Gender.FEMALE;
import static addressbook.Gender.MALE;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.asList;

public class AddressBookLoaderTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

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

    @Test
    public void throwsExceptionWhenOnlyNamePresent() {
        AddressBookLoader addressBookLoader = new AddressBookLoader("InvalidAddressBook_onlyName.txt", getClass());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Not a valid address book file, invalid line: `blah`");

        addressBookLoader.load();
    }

    @Test
    public void throwsExceptionWhenGenderIsInvalid() {
        AddressBookLoader addressBookLoader = new AddressBookLoader("InvalidAddressBook_invalidGender.txt", getClass());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Not a valid address book file, invalid line: `Hello, none, 01/01/01`");

        addressBookLoader.load();
    }

    @Test
    public void throwsExceptionWhenDateOfBirthIsInvalid() {
        AddressBookLoader addressBookLoader = new AddressBookLoader("InvalidAddressBook_invalidDateOfBirth.txt", getClass());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Not a valid address book file, invalid line: `Hello, Male, 05/90`");

        addressBookLoader.load();
    }

    @Test
    public void throwsExceptionWhenThereAreMoreCommas() {
        AddressBookLoader addressBookLoader = new AddressBookLoader("InvalidAddressBook_invalidNumberOfCommas.txt", getClass());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Not a valid address book file, invalid line: `Number Of commas, Male, 01/01/90,`");

        addressBookLoader.load();
    }

    private static LocalDate date(int day, int month, int year) {
        return new LocalDate(1900 + year, month, day);
    }

}
