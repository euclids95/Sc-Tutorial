import java.util.Scanner;
import java.io.*;

// ---------------- BOOK NODE ----------------
class BookNode {
    String bookID;
    String bookTitle;
    BookNode next;

    public BookNode(String bookID, String bookTitle) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.next = null;
    }
}

// ---------------- MEMBER CLASS ----------------
class Member {
    String memberID;
    String memberName;
    BookNode borrowedBooksHead;

    public Member(String memberID, String memberName) {
        this.memberID = memberID;
        this.memberName = memberName;
        this.borrowedBooksHead = null;
    }
    
    // LINKED LIST ADDITION: Adds a node to the front of the list
    public void borrowBook(String bookID, String bookTitle) {
        BookNode newBook = new BookNode(bookID, bookTitle);
        newBook.next = borrowedBooksHead;
        borrowedBooksHead = newBook;
    }
    // LINKED LIST DELETION: Removes a node from the chain
    public boolean returnBook(String bookID) {
        BookNode current = borrowedBooksHead;
        BookNode previous = null;

        while (current != null) {
            if (current.bookID.equals(bookID)) {
                if (previous == null)
                    borrowedBooksHead = current.next;
                else
                    previous.next = current.next;

                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }
    // LINKED LIST TRAVERSAL: Walking through the chain
    public void displayBorrowedBooks() {
        BookNode current = borrowedBooksHead;
        if (current == null) {
            System.out.println("No borrowed books.");
            return;
        }
        System.out.println("Borrowed Books:");
        while (current != null) {
            System.out.println("Book ID: " + current.bookID + " | Title: " + current.bookTitle);
            current = current.next;
        }
    }
}

// ---------------- LIBRARY CLASS ----------------
public class Library {
    private Member[] members;
    private int memberCount;
    private static final int MAX_MEMBERS = 100;

    public Library() {
        members = new Member[MAX_MEMBERS];
        memberCount = 0;
    }

    // 1. Add Member
    public void addMember(String memberID, String memberName) {
        if (memberCount < MAX_MEMBERS) {
            members[memberCount++] = new Member(memberID, memberName);
            System.out.println("Member added successfully.");
        } else {
            System.out.println("Member limit reached.");
        }
    }

    // 2. Search by ID
    public Member searchMemberByID(String memberID) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].memberID.equals(memberID)) {
                return members[i];
            }
        }
        return null;
    }

    // 3. Search by Name
    public Member searchMemberByName(String memberName) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].memberName.equalsIgnoreCase(memberName)) {
                return members[i];
            }
        }
        return null;
    }

    // 4. Sort by ID
    public void sortMembersByID() {
        for (int i = 0; i < memberCount - 1; i++) {
            for (int j = 0; j < memberCount - i - 1; j++) {
                if (members[j].memberID.compareTo(members[j + 1].memberID) > 0) {
                    Member temp = members[j];
                    members[j] = members[j + 1];
                    members[j + 1] = temp;
                }
            }
        }
        System.out.println("Members sorted by ID.");
    }

    // 5. Delete Member + Books
    public boolean deleteMember(String memberID) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].memberID.equals(memberID)) {

                for (int j = i; j < memberCount - 1; j++) {
                    members[j] = members[j + 1];
                }

                memberCount--;
                System.out.println("Member deleted.");
                return true;
            }
        }
        return false;
    }

    // 6. Load from file
    public void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("members.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().equals("")) continue;

                String[] parts = line.split(",");
                Member m = new Member(parts[0], parts[1]);

                while (!(line = br.readLine()).equals("#")) {
                    String[] bookParts = line.split(",");
                    m.borrowBook(bookParts[0], bookParts[1]);
                }

                members[memberCount++] = m;
            }
            System.out.println("Data loaded successfully.");
        } catch (Exception e) {
            System.out.println("No existing data found.");
        }
    }

    // 7. Save to file
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("members.txt"))) {

            for (int i = 0; i < memberCount; i++) {
                Member m = members[i];
                pw.println(m.memberID + "," + m.memberName);

                BookNode current = m.borrowedBooksHead;
                while (current != null) {
                    pw.println(current.bookID + "," + current.bookTitle);
                    current = current.next;
                }

                pw.println("#");
            }

            System.out.println("Data saved successfully.");

        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    // ---------------- MENU APP ----------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library lib = new Library();

        lib.loadFromFile();

        int choice;
        do {
            System.out.println("\n===== LIBRARY MENU =====");
            System.out.println("1. Add Member");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Display Borrowed Books");
            System.out.println("5. Search Member");
            System.out.println("6. Sort Members");
            System.out.println("7. Delete Member");
            System.out.println("8. Exit");
            System.out.print("Choose: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Member ID: ");
                    String id = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    lib.addMember(id, name);
                    break;

                case 2:
                    System.out.print("Member ID: ");
                    Member m1 = lib.searchMemberByID(sc.nextLine());
                    if (m1 == null) System.out.println("Not found.");
                    else {
                        System.out.print("Book ID: ");
                        String bID = sc.nextLine();
                        System.out.print("Book Title: ");
                        String bTitle = sc.nextLine();
                        m1.borrowBook(bID, bTitle);
                        System.out.println("Book borrowed.");
                    }
                    break;

                case 3:
                    System.out.print("Member ID: ");
                    Member m2 = lib.searchMemberByID(sc.nextLine());
                    if (m2 == null) System.out.println("Not found.");
                    else {
                        System.out.print("Book ID to return: ");
                        if (m2.returnBook(sc.nextLine()))
                            System.out.println("Returned successfully.");
                        else
                            System.out.println("Book not found.");
                    }
                    break;

                case 4:
                    System.out.print("Member ID: ");
                    Member m3 = lib.searchMemberByID(sc.nextLine());
                    if (m3 != null) m3.displayBorrowedBooks();
                    else System.out.println("Not found.");
                    break;

                case 5:
                    System.out.print("Enter name or ID: ");
                    String key = sc.nextLine();
                    Member m4 = lib.searchMemberByID(key);
                    if (m4 == null) m4 = lib.searchMemberByName(key);
                    if (m4 == null) System.out.println("Not found.");
                    else System.out.println("Found: " + m4.memberID + " " + m4.memberName);
                    break;

                case 6:
                    lib.sortMembersByID();
                    break;

                case 7:
                    System.out.print("Member ID to delete: ");
                    if (!lib.deleteMember(sc.nextLine()))
                        System.out.println("Not found.");
                    break;

                case 8:
                    lib.saveToFile();
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 8);
    }
}
