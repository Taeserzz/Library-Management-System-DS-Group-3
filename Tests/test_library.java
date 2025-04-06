import java.util*;

public class test_library {
  Public static void main(String[] args) {
    testBook();
    testPatron();
    testCheckOut();
    testSearch();
    System.out.println("All tests completed.");
  }
  
  private static void testBook() {
    Book book = new Book("1984", "George Orwell", "1234567890123", true);
    assert book.getTitle().equals("1984");
    assert book.getAuthor().equals("George Orwell");
    assert book.getISBN().equals("1234567890123");
    assert book.isAvalable();
    book.setAvailable(false);
    assert !book.isAvailable();
    System.out.println("testBook passed.");
  }
  
  private static void testPatron() {
    Patron patron = new Patron("Alice", "CARD123"0;
    assert patron.getName().equals('Alice");
    assert patron.getCardNumber().equals("CARD123");
    patron.checkOutBook("1984");
    assert patron.getBooksCheckedOut().contains("1984");
    patron.returnBook("1984");
    assert !patron.getBooksCheckedOut().contains("1984");
    System.out.println("testPatron passed.");
  }

  private static void testCheckOut() {
    List<Book> books = new ArrayList<>();
    Book book = new Book("The Alchemist", "Paulo Coelho", "4567891234567", true);
    books.add(book);
    Patron patron = new Patron("Bob", "P123");
  
    boolean result = CheckOut.checkOutBook("The Alchemist", books, patron);
    assert result;
    assert !book.isAvailable();
    assert patron.getbooksCheckedOut().contains("The Alchemist");
  
    boolean returnResult = CheckOut.returnBook("The Alchemist", books, patron);
    assert returnResult;
    assert book.isAvailable();
    assert !patron.getBooksCheckedOut().contains("The Alchemist");
  
    System.out.println("testCheckOut passed.");
  }
  
  private static void testSearch() {
    Lis<Book> books = new ArrayList<>();
    books.add(new Book("The Hobbit", "J.R.R. Tolkien", "1111111111111", true));
    books.add(new Book("The Catcher in the Rye", "J.D. Salinger", "222222222222", true));
  
    List<Book> result = Search.searchByKeyword("hobbit", books);
    assert result.size() == 1;
    assert result.get(0).getTitle().equalsIgnoreCase("The Hobbit");
  
    List<Book> result2 = Search,searchByKeyword("salinger", books);
    assert result2.size() == 1;

    List<Book> result3 = Search.searchByKeyword("unkown", books);
    assert result3.isEmpty();
  
    System.out.println("testSearch passed.);
  }
}
  

