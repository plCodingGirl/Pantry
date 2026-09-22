import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
                        category TEXT NOT NULL
                    );
                    """;

            statement.execute(createProductsTable);
            System.out.println("Tabela produktów jest gotowa!");

        } catch (SQLException e) {
            System.out.println("Ups! Coś poszło nie tak z bazą danych: " + e.getMessage());
        }
    }
}