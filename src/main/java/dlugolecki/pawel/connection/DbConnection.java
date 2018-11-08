package dlugolecki.pawel.connection;
import dlugolecki.pawel.exception.MyException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class DbConnection {
    private static DbConnection ourInstance = new DbConnection();
    public static DbConnection getInstance() {
        return ourInstance;
    }

    private static final String DB_NAME = "jdbc:sqlite:projectJDBC_db";
    private static final String DRIVER = "org.sqlite.JDBC";
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private DbConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_NAME);
            createTables();
        } catch (ClassNotFoundException e) {
            throw new MyException("DbConnection - ERROR DURING LOADING DB DRIVERS", LocalDateTime.now());
        } catch (SQLException e) {
            throw new MyException("DbConnection - ERROR DURING LOADING DB DRIVERS", LocalDateTime.now());
        }
    }

    private void createTables() {
        try {
            Statement statement = connection.createStatement();

            String categoryTableSql = SqlTableCommand.builder()
                    .table("Category")
                    .primaryKey("id")
                    .stringColumn("name", 50, "not null")
                    .build().compact();

            String countryTableSql = SqlTableCommand.builder()
                    .table("Country")
                    .primaryKey("id")
                    .stringColumn("name", 50, "not null")
                    .build().compact();

            String producerTableSql = SqlTableCommand.builder()
                    .table("Producer")
                    .primaryKey("id")
                    .stringColumn("name", 50, "not null")
                    .decimalColumn("budget", 5, 2, "default 0")
                    .intColumn("countryId", "default 0")
                    .foreignKey("countryId", "Country", "id", "on delete cascade")
                    .build().compact();

            String productTableSql = SqlTableCommand.builder()
                    .table("Product")
                    .primaryKey("id")
                    .stringColumn("name", 50, "not null")
                    .decimalColumn("price", 4, 2, "default 0")
                    .intColumn("categoryId", "default 0")
                    .intColumn("producerId", "default 0")
                    .intColumn("countryId", "default 0")
                    .foreignKey("categoryId", "Category", "id", "on delete cascade")
                    .foreignKey("producerId", "Producer", "id", "on delete cascade")
                    .foreignKey("countryId", "Country", "id", "on delete cascade")
                    .build().compact();

            String customerTableSql = SqlTableCommand.builder()
                    .table("Customer")
                    .primaryKey("id")
                    .stringColumn("name", 50, "not null")
                    .stringColumn("surname", 50, "not null")
                    .intColumn("age", "default 0")
                    .intColumn("countryId", "default 0")
                    .foreignKey("countryId", "Country", "id", "on delete cascade")
                    .build().compact();

            String orderTableSql = SqlTableCommand.builder()
                    .table("OrderTab")
                    .primaryKey("id")
                    .intColumn("customerId", "default 0")
                    .intColumn("productId", "default 0")
                    .decimalColumn("discount", 2, 1, "default 0")
                    .intColumn("quantity", "default 0")
                    .foreignKey("customerId", "Customer", "id", "on delete cascade")
                    .foreignKey("productId", "Product", "id", "on delete cascade")
                    .build().compact();

            statement.execute(categoryTableSql);
            statement.execute(countryTableSql);
            statement.execute(producerTableSql);
            statement.execute(productTableSql);
            statement.execute(customerTableSql);
            statement.execute(orderTableSql);
            statement.close();

        } catch (SQLException e) {
            throw new MyException("DbConnection - ERROR DURING CLOSING THE DB CONNECTION", LocalDateTime.now());
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new MyException("DbConnection - ERROR DURING CLOSING THE DB CONNECTION", LocalDateTime.now());
            }
        }
    }
}