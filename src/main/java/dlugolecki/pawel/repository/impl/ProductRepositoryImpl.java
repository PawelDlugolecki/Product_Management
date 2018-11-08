package dlugolecki.pawel.repository.impl;
import dlugolecki.pawel.connection.DBTables;
import dlugolecki.pawel.connection.DbConnection;
import dlugolecki.pawel.model.Product;
import dlugolecki.pawel.repository.repos.ProductRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    private Connection connection = DbConnection.getInstance().getConnection();

    public void add(Product product) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlInsert = "insert into " + DBTables.Product +
                    " (name, price, categoryId, producerId, countryId) values (?, ?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setInt(3, product.getCategoryId());
            preparedStatement.setInt(4, product.getProducerId());
            preparedStatement.setInt(5, product.getCountryId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/ADD " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FINALLY/ADD " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void update(Product product) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlUpdate = "update " + DBTables.Product +
                    " set name = ?, price = ?, categoryId = ?, producerId = ?, countryId = ? where id = ?;";
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setInt(3, product.getCategoryId());
            preparedStatement.setInt(4, product.getProducerId());
            preparedStatement.setInt(5, product.getCountryId());
            preparedStatement.setInt(6, product.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/UPDATE " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FINALLY/UPDATE " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void delete(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlDelete = "delete from " + DBTables.Product + " where id = ?;";
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/DELETE " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FINALLY/DELETE " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void deleteAll() {
        Statement statement = null;
        try {
            final String sqlSelect = "delete from " + DBTables.Product;
            statement = connection.createStatement();
            statement.execute(sqlSelect);
        } catch (SQLException e) {
            System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/DELETE ALL " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FINALLY/DELETE ALL" + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public Optional<Product> findOneById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, price, categoryId, producerId, countryId from " + DBTables.Product +
                            " where id = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Product
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .price(resultSet.getBigDecimal(3))
                                .categoryId(resultSet.getInt(4))
                                .producerId(resultSet.getInt(5))
                                .countryId(resultSet.getInt(6))
                                .build()
                );
            }
        } catch (SQLException e) {
            System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FIND ONE BY ID " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FINALLY/FIND ONE BY ID " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }

    public Optional<Product> findOneByName(String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, price, categoryId, producerId, countryId from " + DBTables.Product +
                            " where name = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Product
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .price(resultSet.getBigDecimal(3))
                                .categoryId(resultSet.getInt(4))
                                .producerId(resultSet.getInt(5))
                                .countryId(resultSet.getInt(6))
                                .build()
                );
            }
        } catch (SQLException e) {
            System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FIND ONE BY NAME " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FINALLY/FIND ONE BY NAME " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }

    public Optional<Product> findGivenProduct(String name, int producerId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, price, categoryId, producerId, countryId from " + DBTables.Product +
                            " where name = ? and producerId = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, producerId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Product
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .price(resultSet.getBigDecimal(3))
                                .categoryId(resultSet.getInt(4))
                                .producerId(resultSet.getInt(5))
                                .countryId(resultSet.getInt(6))
                                .build()
                );
            }
        } catch (SQLException e) {
            System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FIND ONE BY NAME " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FINALLY/FIND ONE BY NAME " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }

    public List<Product> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, price, categoryId, producerId, countryId from " + DBTables.Product;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlSelect);

            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                products.add(
                        Product
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .price(resultSet.getBigDecimal(3))
                                .categoryId(resultSet.getInt(4))
                                .producerId(resultSet.getInt(5))
                                .countryId(resultSet.getInt(6))
                                .build()
                );

            }
            return products;
        } catch (SQLException e) {
            System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FIND ALL " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCTREPOSITORYIMPL/PRODUCT/FINALLY/FIND ALL " + DBTables.Product+ " [ERROR: " + e.getMessage() + "]");
            }
        }
        return null;
    }
}
