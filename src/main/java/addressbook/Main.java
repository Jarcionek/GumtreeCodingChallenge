package addressbook;

import static addressbook.Gender.MALE;

public class Main {

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook(new AddressBookLoader(new Clock(), "AddressBook.txt", Main.class));

        System.out.println("How many males are in the address book?");
        System.out.println("\t" + addressBook.numberOf(MALE));
        System.out.println();

        System.out.println("Who is the oldest person in the address book?");
        System.out.println("\t" + addressBook.oldestPerson().name());
        System.out.println();

        System.out.println("How many days older is Bill than Paul?");
        System.out.println("\t" + addressBook.ageDifference("Bill McKnight", "Paul Robinson"));
    }

}
