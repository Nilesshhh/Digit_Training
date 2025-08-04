import service.BookService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookService bookService = new BookService();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        
        while (!exit) {
            System.out.println("---X---X---X---X---");
            System.out.println("Select an option:");
            System.out.println("1. Add a Book");
            System.out.println("2. Update a Book");
            System.out.println("3. Delete a Book");
            System.out.println("4. View a book");
            System.out.println("5. View all Books");
            System.out.println("6. Exit Menu");
            System.out.print("Your option: ");
            
            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }
            
            try {
                switch (option) {
                    case 1:
                        bookService.addBook();
                        break;
                    case 2:
                        bookService.updateBook();
                        break;
                    case 3:
                        bookService.deleteBook();
                        break;
                    case 4:
                        bookService.viewBook();
                        break;
                    case 5:
                        bookService.viewAllBooks();
                        break;
                    case 6:
                        System.out.print("Are you sure you want to exit (y/n): ");
                        String confirmation = scanner.nextLine();
                        if (confirmation.equalsIgnoreCase("y")) {
                            exit = true;
                            System.out.println("Thanks for using our portal.");
                            System.out.println("Visit again soon !!");
                            System.out.println("---X---X---X---X---");
                        }
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
