package addressbook;

import com.google.common.io.Resources;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import static com.google.common.io.Resources.getResource;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.stream.Collectors.toList;

public class AddressBookLoader {

    private static final Pattern pattern = Pattern.compile("[a-zA-Z ]+, (Male|Female), \\d{2}/\\d{2}/\\d{2}");

    private final String resourceName;
    private final Class<?> contextClass;

    public AddressBookLoader(String resourceName, Class<?> contextClass) {
        this.resourceName = resourceName;
        this.contextClass = contextClass;
    }

    @SuppressWarnings("Convert2MethodRef")
    public List<Person> load() {
        try {
            String addressBook = Resources.toString(getResource(contextClass, resourceName), defaultCharset());

            return Pattern.compile("\r?\n").splitAsStream(addressBook)
                    .peek((String line) -> validate(line))
                    .map((String line) -> line.split(", "))
                    .map((String[] array) -> newPerson(array[0], array[1], array[2]))
                    .collect(toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validate(String line) {
        if (!pattern.matcher(line).matches()) {
            throw new IllegalArgumentException("Not a valid address book file, invalid line: `" + line + "`");
        }
    }

    private static Person newPerson(String name, String gender, String dateOfBirth) {
        return new Person(
                name,
                Gender.valueOf(gender.toUpperCase()),
                newDate(dateOfBirth.split("/"))
        );
    }

    private static LocalDate newDate(String[] date) {
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);

        return new LocalDate(1900 + year, month, day);
    }

}
