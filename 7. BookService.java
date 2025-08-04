package service;

import dao.BookDao;
import model.Book;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BookService {
    private BookDao bookDao;
    private Scanner scanner;
    
    public BookService() {
        this.bookDao = new BookDao();
        this.scanner = new Scanner(System.in);
    }
    
    public void addBook() {
        System.out.print("Enter book name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter book price: ");
        double price = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setAuthor(author);
        
        int id = bookDao.addBook(book);
        if (id > 0) {
            System.out.println("[The book \"" + name + "\" is added successfully]");
        } else {
            System.out.println("Failed to add book.");
        }
    }
    
    public void updateBook() {
        System.out.print("Enter the id of the book to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Optional<Book> optionalBook = bookDao.getBookById(id);
        if (optionalBook.isEmpty()) {
            System.out.println("Book with id " + id + " not found.");
            return;
        }
        
        Book book = optionalBook.get();
        System.out.println("Current book details:");
        System.out.println(book);
        
        System.out.print("Enter new book name (" + book.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            book.setName(name);
        }
        
        System.out.print("Enter new book price (" + book.getPrice() + "): ");
        String priceInput = scanner.nextLine();
        if (!priceInput.isEmpty()) {
            book.setPrice(Double.parseDouble(priceInput));
        }
        
        System.out.print("Enter new book author (" + book.getAuthor() + "): ");
        String author = scanner.nextLine();
        if (!author.isEmpty()) {
            book.setAuthor(author);
        }
        
        if (bookDao.updateBook(book)) {
            System.out.println("[The book: " + book.getName() + " with id " + id + " is updated successfully]");
        } else {
            System.out.println("Failed to update book.");
        }
    }
    
    public void deleteBook() {
        System.out.print("Enter the id of the book to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Optional<Book> optionalBook = bookDao.getBookById(id);
        if (optionalBook.isEmpty()) {
            System.out.println("Book with id " + id + " not found.");
            return;
        }
        
        Book book = optionalBook.get();
        System.out.println("Book to delete:");
        System.out.println(book);
        
        System.out.print("Are you sure you want to delete this book? (y/n): ");
        String confirmation = scanner.nextLine();
        
        if (confirmation.equalsIgnoreCase("y")) {
            if (bookDao.deleteBook(id)) {
                System.out.println("[The book: " + book.getName() + " with id " + id + " is deleted successfully]");
            } else {
                System.out.println("Failed to delete book.");
            }
        }
    }
    
    public void viewBook() {
        System.out.print("Enter the id of the book to view: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Optional<Book> optionalBook = bookDao.getBookById(id);
        if (optionalBook.isPresent()) {
            System.out.println(optionalBook.get());
        } else {
            System.out.println("Book with id " + id + " not found.");
        }
    }
    
    public void viewAllBooks() {
        boolean backToParent = false;
        
        while (!backToParent) {
            System.out.println("How do you want to display the list?");
            System.out.println("1. In Ascending order of id");
            System.out.println("2. In Descending order of id");
            System.out.println("3. In Ascending order of book name");
            System.out.println("4. In Descending order of book name");
            System.out.println("5. In Ascending order of price");
            System.out.println("6. In Descending order of price");
            System.out.println("7. Exit");
            System.out.print("Your option: ");
            
            int option = Integer.parseInt(scanner.nextLine());
            String orderBy = "";
            
            switch (option) {
                case 1:
                    orderBy = "id ASC";
                    break;
                case 2:
                    orderBy = "id DESC";
                    break;
                case 3:
                    orderBy = "name ASC";
                    break;
                case 4:
                    orderBy = "name DESC";
                    break;
                case 5:
                    orderBy = "price ASC";
                    break;
                case 6:
                    orderBy = "price DESC";
                    break;
                case 7:
                    backToParent = true;
                    continue;
                default:
                    System.out.println("Invalid option.");
                    continue;
            }
            
            List<Book> books = bookDao.getAllBooks(orderBy);
            if (books.isEmpty()) {
                System.out.println("No books found.");
            } else {
                System.out.println("id\tname\tprice\tauthor");
                books.forEach(book -> System.out.println(
                    book.getId() + "\t" + 
                    book.getName() + "\t" + 
                    book.getPrice() + "\t" + 
                    book.getAuthor()));
            }
        }
    }
}
