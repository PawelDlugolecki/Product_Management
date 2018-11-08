package dlugolecki.pawel.repository.impl;
import dlugolecki.pawel.connection.DBTables;
import dlugolecki.pawel.connection.DbConnection;
import dlugolecki.pawel.model.OrderTab;
import dlugolecki.pawel.repository.repos.OrderRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    private Connection connection = DbConnection.getInstance().getConnection();

    public void add(OrderTab order) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlInsert = "insert into " + DBTables.OrderTab +
                    " (customerId, productId, discount, quantity) values (?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setInt(1, order.getCustomerId());
            preparedStatement.setInt(2, order.getProductId());
            preparedStatement.setBigDecimal(3, order.getDiscount());
            preparedStatement.setInt(4, order.getQuantity());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/ADD " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/FINALLY/ADD " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void update(OrderTab orderTab) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlUpdate = "update " + DBTables.OrderTab +
                    " set customerId = ?, productId = ?, discount = ?, quantity = ? where id = ?;";
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setInt(1, orderTab.getCustomerId());
            System.out.println("1");
            preparedStatement.setInt(2, orderTab.getProductId());
            System.out.println("2");
            preparedStatement.setBigDecimal(3, orderTab.getDiscount());
            System.out.println("3");
            preparedStatement.setInt(4, orderTab.getQuantity());
            System.out.println("4");
            preparedStatement.setInt(5, orderTab.getId());
            System.out.println("5");
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/UPDATE " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/FINALLY/UPDATE " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void delete(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlDelete = "delete from " + DBTables.OrderTab + " where id = ?;";
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/DELETE " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/FINALLY/DELETE " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void deleteAll() {
        Statement statement = null;
        try {
            final String sqlSelect = "delete from " + DBTables.OrderTab;
            statement = connection.createStatement();
            statement.execute(sqlSelect);
        } catch (SQLException e) {
            System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/DELETE ALL " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/FINALLY/DELETE ALL " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public Optional<OrderTab> findOneById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, customerId, productId, discount, quantity from " + DBTables.OrderTab +
                            " where id = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        OrderTab
                                .builder()
                                .id(resultSet.getInt(1))
                                .customerId(resultSet.getInt(2))
                                .productId(resultSet.getInt(3))
                                .discount(resultSet.getBigDecimal(4))
                                .quantity(resultSet.getInt(5))
                                .build()
                );
            }
        } catch (SQLException e) {
            System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/FIND ONE BY ID " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/FINALLY/FIND ONE BY ID " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }

    public List<OrderTab> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, customerId, productId, discount, quantity from " + DBTables.OrderTab;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlSelect);

            List<OrderTab> orderTabs = new ArrayList<>();

            while (resultSet.next()) {
                orderTabs.add(
                        OrderTab
                                .builder()
                                .id(resultSet.getInt(1))
                                .customerId(resultSet.getInt(2))
                                .productId(resultSet.getInt(3))
                                .discount(resultSet.getBigDecimal(4))
                                .quantity(resultSet.getInt(5))
                                .build()
                );

            }
            return orderTabs;
        } catch (SQLException e) {
            System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/FIND ALL " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("ORDER-TAB-REPOSITORYIMPL/ORDER TAB/FINALLY/FIND ALL " + DBTables.OrderTab+ " [ERROR: " + e.getMessage() + "]");
            }
        }
        return null;
    }
}
