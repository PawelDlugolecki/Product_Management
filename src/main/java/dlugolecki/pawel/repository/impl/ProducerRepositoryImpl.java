package dlugolecki.pawel.repository.impl;
import dlugolecki.pawel.connection.DBTables;
import dlugolecki.pawel.connection.DbConnection;
import dlugolecki.pawel.model.Producer;
import dlugolecki.pawel.repository.repos.ProducerRepository;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerRepositoryImpl implements ProducerRepository {
    private Connection connection = DbConnection.getInstance().getConnection();

    public void add(Producer producer) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlInsert = "insert into " + DBTables.Producer +
                    " (name, budget, countryId) values (?, ?, ?);";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, producer.getName());
            preparedStatement.setBigDecimal(2, producer.getBudget());
            preparedStatement.setInt(3, producer.getCountryId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/ADD " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/FINALLY/ADD " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void update(Producer producer) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlUpdate = "update " + DBTables.Producer +
                    " set name = ?, budget = ?, countryId = ? where id = ?;";
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, producer.getName());
            preparedStatement.setBigDecimal(2, producer.getBudget());
            preparedStatement.setInt(3, producer.getCountryId());
            preparedStatement.setInt(4, producer.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/UPDATE " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/FINALLY/UPDATE " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void delete(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlDelete = "delete from " + DBTables.Producer + " where id = ?;";
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/DELETE " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/FINALLY/DELETE " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void deleteAll() {
        Statement statement = null;
        try {
            final String sqlSelect = "delete from " + DBTables.Producer;
            statement = connection.createStatement();
            statement.execute(sqlSelect);
        } catch (SQLException e) {
            System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/DELETE ALL " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/FINALLY/DELETE ALL " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public Optional<Producer> findOneById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, budget, countryId from " + DBTables.Producer +
                            " where id = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Producer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .budget(resultSet.getBigDecimal(3))
                                .countryId(resultSet.getInt(4))
                                .build()
                );
            }
        } catch (SQLException e) {
            System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/FIND ONE BY ID " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/FINALLY/FIND ONE BY ID " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }

    public Optional<Producer> findOneByName(String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, budget, countryId from " + DBTables.Producer +
                            " where name = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Producer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .budget(resultSet.getBigDecimal(3))
                                .countryId(resultSet.getInt(4))
                                .build()
                );

            }
        } catch (SQLException e) {
            System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/FIND ONE BY NAME " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/FINALLY/FIND ONE BY NAME " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }

    public List<Producer> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, budget, countryId from " + DBTables.Producer;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlSelect);

            List<Producer> producers = new ArrayList<>();

            while (resultSet.next()) {
                producers.add(
                        Producer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .budget(resultSet.getBigDecimal(3))
                                .countryId(resultSet.getInt(4))
                                .build()
                );

            }
            return producers;
        } catch (SQLException e) {
            System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/FIND ALL " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("PRODUCERREPOSITORYIMPL/PRODUCER/FINALLY/FIND ALL " + DBTables.Producer + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return null;
    }

    public List<Producer> findAllProducer(String name, BigDecimal budget) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name, budget, countryId from " + DBTables.Producer + " where name = ? and budget = ?";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setString(1, name);
            preparedStatement.setBigDecimal(2, budget);
            resultSet = preparedStatement.executeQuery();

            List<Producer> producers = new ArrayList<>();

            while (resultSet.next()) {
                producers.add(
                        Producer
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .budget(resultSet.getBigDecimal(3))
                                .countryId(resultSet.getInt(4))
                                .build()
                );

            }
            return producers;
        } catch (SQLException e) {
            System.err.println("CUSTOMERREPOSITORYIMPL/PRODUCER/FINALLY/FIND ALL BY NAME AND BUDGET " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("CUSTOMERREPOSITORYIMPL/PRODUCER/FINALLY/FIND ALL BY NAME AND SURNAME " + DBTables.Customer + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return null;
    }
}
