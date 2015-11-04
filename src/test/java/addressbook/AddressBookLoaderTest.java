package addressbook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static addressbook.Gender.FEMALE;
import static addressbook.Gender.MALE;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressBookLoaderTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void loadsAddressBookFromFile() {
        AddressBookLoader addressBookLoader = new AddressBookLoader(new Clock(), "AddressBook.txt", AddressBookLoader.class);

        List<Person> persons = addressBookLoader.load();

        assertThat(persons, sameBeanAs(asList(
                new Person("Bill McKnight", MALE, LocalDate.of(1977, 3, 16)),
                new Person("Paul Robinson", MALE, LocalDate.of(1985, 1, 15)),
                new Person("Gemma Lane", FEMALE, LocalDate.of(1991, 11, 20)),
                new Person("Sarah Stone", FEMALE, LocalDate.of(1980, 9, 20)),
                new Person("Wes Jackson", MALE, LocalDate.of(1974, 8, 14))
        )));
    }

    @Test
    public void setsYear2000forPersonBornThisCentury() {
        Clock clock = mock(Clock.class);
        when(clock.now()).thenReturn(LocalDateTime.of(2015, 11, 4, 12, 34));
        AddressBookLoader addressBookLoader = new AddressBookLoader(clock, "TestAddressBookWithPersonBornThisCentury.txt", AddressBookLoader.class);

        List<Person> persons = addressBookLoader.load();

        assertThat(persons.get(0).dateOfBirth().toString(), equalTo("2000-01-03"));
    }

    @Test
    public void setsYear1900forVeryOldPersonBelowOneHundredYearsOld() {
        Clock clock = mock(Clock.class);
        when(clock.now()).thenReturn(LocalDateTime.of(2015, 1, 1, 0, 0));
        AddressBookLoader addressBookLoader = new AddressBookLoader(clock, "TestAddressBookWithVeryOldPerson.txt", AddressBookLoader.class);

        List<Person> persons = addressBookLoader.load();

        assertThat(persons.get(0).dateOfBirth().toString(), equalTo("1915-05-04"));
    }

    @Test
    public void throwsExceptionWhenOnlyNamePresent() {
        AddressBookLoader addressBookLoader = new AddressBookLoader(new Clock(), "InvalidAddressBook_onlyName.txt", getClass());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Not a valid address book file, invalid line: `blah`");

        addressBookLoader.load();
    }

    @Test
    public void throwsExceptionWhenGenderIsInvalid() {
        AddressBookLoader addressBookLoader = new AddressBookLoader(new Clock(), "InvalidAddressBook_invalidGender.txt", getClass());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Not a valid address book file, invalid line: `Hello, none, 01/01/01`");

        addressBookLoader.load();
    }

    @Test
    public void throwsExceptionWhenDateOfBirthIsInvalid() {
        AddressBookLoader addressBookLoader = new AddressBookLoader(new Clock(), "InvalidAddressBook_invalidDateOfBirth.txt", getClass());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Not a valid address book file, invalid line: `Hello, Male, 05/90`");

        addressBookLoader.load();
    }

    @Test
    public void throwsExceptionWhenThereAreMoreCommas() {
        AddressBookLoader addressBookLoader = new AddressBookLoader(new Clock(), "InvalidAddressBook_invalidNumberOfCommas.txt", getClass());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Not a valid address book file, invalid line: `Number Of commas, Male, 01/01/90,`");

        addressBookLoader.load();
    }

}
