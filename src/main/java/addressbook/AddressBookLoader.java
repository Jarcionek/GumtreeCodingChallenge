package addressbook;

import com.google.common.io.Resources;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.google.common.io.Resources.getResource;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.stream.Collectors.toList;

public class AddressBookLoader {

    private String resourceName;
    private Class<?> contextClass;

    public AddressBookLoader(String resourceName, Class<?> contextClass) {
        this.resourceName = resourceName;
        this.contextClass = contextClass;
    }

    public List<Person> load() {
        try {
            String addressBook = Resources.toString(getResource(contextClass, resourceName), defaultCharset());

            return Pattern.compile("\r?\n").splitAsStream(addressBook)
                    .map((String line) -> line.split(", "))
                    .map((String[] array) -> newPerson(array[0], array[1], array[2]))
                    .collect(toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Person newPerson(String name, String gender, String dateOfBirth) {
        return new Person(
                name,
                Gender.valueOf(gender.toUpperCase()),
                newDate(dateOfBirth.split("/"))
        );
    }

    private static Date newDate(String[] date) {
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1900 + year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

}
