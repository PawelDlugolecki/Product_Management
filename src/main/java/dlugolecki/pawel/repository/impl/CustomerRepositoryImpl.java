package dlugolecki.pawel.repository.impl;
import dlugolecki.pawel.connection.DBTables;
import dlugolecki.pawel.connection.DbConnection;
import dlugolecki.pawel.model.Customer;
import dlugolecki.pawel.repository.repos.CustomerRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl implements CustomerRepository {
    private Connection connection = DbConnection.getInstance().getConnection();

    public void add(Customer customer) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlInsert = "insert into " + DBTables.Customer +
                    "(name, surname, age, countryId) values (?,?,?,?);";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setInt(3, customer.getAge());
            preparedStatement.setInt(4, customer.getCountryId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/ADD " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FINALLY/ADD " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void update(Customer customer) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlUpdate = "update " + DBTables.Customer +
                    " set name = ?, surname = ?, age = ?, countryId = ? where id = ?;";
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setInt(3, customer.getAge());
            preparedStatement.setInt(4, customer.getCountryId());
            preparedStatement.setInt(5, customer.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/UPDATE " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FINALLY/UPDATE " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void delete(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlDelete = "delete from " + DBTables.Customer + " where id = ?;";
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/DELETE " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FINALLY/DELETE " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void deleteAll() {
        Statement statement = null;
        try {
            final String sqlSelect = "delete from " + DBTables.Customer;
            statement = connection.createStatement();
            statement.execute(sqlSelect);
        } catch (SQLException e) {
            System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/DELETE ALL " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FINALLY/DELETE AL " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public Optional<Customer> findOneById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, surname, age, countryId from " + DBTables.Customer +
                            " where id = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Customer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .surname(resultSet.getString(3))
                                .age(resultSet.getInt(4))
                                .countryId(resultSet.getInt(5))
                                .build()
                );

            }
        } catch (SQLException e) {
            System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FIND ONE BY ID " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FINALLY/FIND ONE BY ID " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }

    public List<Customer> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, surname, age, countryId from " + DBTables.Customer;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlSelect);

            List<Customer> customers = new ArrayList<>();

            while (resultSet.next()) {
                customers.add(
                        Customer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .surname(resultSet.getString(3))
                                .age(resultSet.getInt(4))
                                .countryId(resultSet.getInt(5))
                                .build()
                );

            }
            return customers;
        } catch (SQLException e) {
            System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FIND ALL " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FINALLY/FIND ALL " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return null;
    }

    public Optional<Customer> findOneCustomerByNameAndSurname(String customerName, String customerSurname) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, surname, age, countryId from " + DBTables.Customer +
                            " where name = ? and surname = ?";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, customerSurname);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Customer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .surname(resultSet.getString(3))
                                .age(resultSet.getInt(4))
                                .countryId(resultSet.getInt(5))
                                .build()
                );

            }
        } catch (SQLException e) {
            System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FIND ONE CUSTOMER " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FINALLY/FIND ONE CUSTOMER " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }

    public Optional<Customer> findOneGivenCustomer(String name, String surname, int countryId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, surname, age, countryId from " + DBTables.Customer +
                            " where name = ? and surname = ? and countryId = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setInt(3, countryId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Customer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .surname(resultSet.getString(3))
                                .age(resultSet.getInt(4))
                                .countryId(resultSet.getInt(5))
                                .build()
                );

            }
        } catch (SQLException e) {
            System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FIND GIVEN CUSTOMER " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("CUSTOMERREPOSITORYIMPL/CUSTOMER/FINALLY/FIND GIVEN CUSTOMER " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }
}
