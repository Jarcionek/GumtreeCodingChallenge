package addressbook;

import java.time.temporal.ChronoUnit;

import static addressbook.Gender.MALE;
import static java.util.Comparator.comparing;

public class Main {

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook(new AddressBookLoader(new Clock(), "AddressBook.txt", Main.class));

        System.out.println("How many males are in the address book?");
        System.out.println("\t" + addressBook.count(person -> person.gender() == MALE));
        System.out.println();

        System.out.println("Who is the oldest person in the address book?");
        System.out.println("\t" + addressBook.firstPerson(comparing(Person::dateOfBirth)).name());
        System.out.println();

        System.out.println("How many days older is Bill than Paul?");
        System.out.println("\t" + addressBook.ageDifference(person -> person.name().startsWith("Bill"),
                                                            person -> person.name().startsWith("Paul"),
                                                            ChronoUnit.DAYS));
    }

}
