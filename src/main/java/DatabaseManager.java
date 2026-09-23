import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:pantry.db";

    public void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {

            System.out.println("Połączono z bazą SQLite!");

            String createProductsTable = """
                                        CREATE TABLE IF NOT EXISTS products (
                                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                                        name TEXT NOT NULL,
                                        category TEXT NOT NULL,
                                        location TEXT NOT NULL,
                                        is_available BOOLEAN DEFAULT 1
                                        );
                                        """;

            statement.execute(createProductsTable);
            System.out.println("Tabela produktów jest gotowa!");

        } catch (SQLException e) {
            System.out.println("Ups! Coś poszło nie tak z bazą danych: " + e.getMessage());
        }
    }

    public void addProductToDatabase(String name, String category, String location) {
        String sql = "INSERT INTO products(name, category, location) VALUES(?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setString(3, location);

            pstmt.executeUpdate();
            System.out.println("Zapisano w bazie SQL: " + name + " (" + category + ")");

        } catch (SQLException e) {
            System.out.println("Błąd zapisu do bazy: " + e.getMessage());
        }
    }

    public void showProductsFromDatabase() {
        String sql = "SELECT * FROM products";

        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("\n--- Zawartość Twojej bazy danych ---");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                String location = resultSet.getString("location");
                boolean isAvailable = resultSet.getBoolean("is_available");

                System.out.println("ID: " + id + " | Produkt: " + name + " | Kategoria: " + category);
            }
            System.out.println("------------------------------------\n");

        } catch (SQLException e) {
            System.out.println("Błąd odczytu z bazy: " + e.getMessage());
        }
    }
}

