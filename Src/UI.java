import java.util.*; 

public class UI {
    private static List<Book> books = new ArrayList<>();
    private static Map<String, Patron> patrons = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load data
        books = FileHandler.loadBooks();
        patrons = FileHandler.loadPatrons();
        
        while (true) {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. Check Out Book");
            System.out.println("3. Return Book");
            System.out.println("4. Search Book");
            System.out.println("5. View Patron Info");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addBook(); break;
                case "2": checkOutBook(); break;
                case "3": returnBook(); break;
                case "4": searchBook(); break;
                case "5": viewPatron(); break;
                case "6": exitProgram(); return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void addBook() {
        String title = promptNonEmpty("Enter title: ");
        String author = promptNonEmpty("Enter author: ");
        String isbn = promptNonEmpty("Enter ISBN: ");

        books.add(new Book(title, author, isbn));
        System.out.println("Book added.");
    }

    private static void checkOutBook() {
        String name = promptNonEmpty("Enter patron name: ");
        String card = promptNonEmpty("Enter card number: ");
        String title = promptNonEmpty("Enter book title to check out: ");

        Patron patron = getOrcreatePatron(name, card);
        boolean success = CheckOut.CheckOutBook(title, patron);

        if (success) {
            System.out.println("Book checked out.");
        } else {
            System.out.println("Book not found or unavailable.");
        }
    }
    
    private static void returnBook() {
        String card = promptNonEmpty("Enter card number: ");
        String title = promptNonEmpty("Enter book title to return: ");
        
        Patron patron = patrons.get(card);
        if (patron != null) {
            boolean success = CheckOut.returnBook(title, patron);
            if (success) {
                System.out.println("Book returned.");
            } else {
                System.out.println("Book not found in patron's list.");
            }
        } else {
            System.out.println("No patron found with that card number.");
        }
    }

    private static void searchBook() {
        String keyword = promptNonEmpty("Enter search keyword: ");
        List<Book> results = Search.searchByKeyword(keyword, books);

        if (results.isEmpty()) {
            System.out.println("No books matched your search.");
        } else {
            for (Book b : results) {
                System.out.println(b);
            }
        }
    }

    private static void viewPatron() {
        String card = promptNonEmpty("Enter card number: ");
        Patron patron = patrons.get(card);
        
        if (patron != null) {
            System.out.println(patron);
        } else {
            System.out.println("No patron found with that card number.");
        }
    }

    private static void exitProgram() {
        FileHandler.saveBooks(books);
        FileHandler.savePatrons(patrons);
        System.out.println("Goodbye!");
    }

    private static String promptNonEmpty(String message) {
        String input;
        do {
            System.out.print(message);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    private static Patron getOrCreatePatron(String name, String card) {
        if (patrons.containsKey(card)) {
            return patrons.get(card);
        }
        Patron newPatron = new Patron(name, card);
        patrons.put(card, newPatron);
        return newPatron;
    }

        private static Patron findPatronByCard(String card) {
            for (Patron p : patrons) {
                if (p.getCarNumber().equals(card)) {
                   return p;
                }
            }
            return null;
        }
}
