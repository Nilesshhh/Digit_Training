package dao;

import model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao {
    private static final String TABLE_NAME = "dbeaber_books";
    
    public BookDao() {
        createTableIfNotExists();
    }
    
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "name VARCHAR(100) NOT NULL, " +
                     "price DECIMAL(10,2) NOT NULL, " +
                     "author VARCHAR(100) NOT NULL)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public int addBook(Book book) {
        String sql = "INSERT INTO " + TABLE_NAME + " (name, price, author) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, book.getName());
            pstmt.setDouble(2, book.getPrice());
            pstmt.setString(3, book.getAuthor());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public boolean updateBook(Book book) {
        String sql = "UPDATE " + TABLE_NAME + " SET name = ?, price = ?, author = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, book.getName());
            pstmt.setDouble(2, book.getPrice());
            pstmt.setString(3, book.getAuthor());
            pstmt.setInt(4, book.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteBook(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Optional<Book> getBookById(int id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(new Book(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("author")
                ));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    public List<Book> getAllBooks(String orderBy) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + orderBy;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("author")
                ));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}
