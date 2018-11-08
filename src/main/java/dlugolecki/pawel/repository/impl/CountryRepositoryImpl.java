package dlugolecki.pawel.repository.impl;
import dlugolecki.pawel.connection.DBTables;
import dlugolecki.pawel.connection.DbConnection;
import dlugolecki.pawel.model.Country;
import dlugolecki.pawel.repository.repos.CountryRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryRepositoryImpl implements CountryRepository {
    private Connection connection = DbConnection.getInstance().getConnection();

    public void add(Country country) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlInsert = "insert into " + DBTables.Country +
                    " (name) values (?);";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, country.getName());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/ADD " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/FINALLY/ADD " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void update(Country country) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlUpdate = "update " + DBTables.Country +
                    " set name = ? where id = ?;";
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, country.getName());
            preparedStatement.setInt(2, country.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/UPDATE " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/FINALLY/UPDATE " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void delete(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlDelete = "delete from " + DBTables.Country + " where id = ?;";
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/DELETE " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/FINALLY/DELETE " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void deleteAll() {
        Statement statement = null;
        try {
            final String sqlSelect = "delete from " + DBTables.Country;
            statement = connection.createStatement();
            statement.execute(sqlSelect);
        } catch (SQLException e) {
            System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/DELETE ALL " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/FINALLY/DELETE ALL " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public Optional<Country> findOneById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name from " + DBTables.Country +
                            " where id = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Country
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .build()
                );
            }
        } catch (SQLException e) {
            System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/FIND ONE BY ID " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/FINALLY/FIND ONE BY ID " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }

    public List<Country> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name from " + DBTables.Country;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlSelect);

            List<Country> countries = new ArrayList<>();

            while (resultSet.next()) {
                countries.add(
                        Country
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .build()
                );
            }
            return countries;
        } catch (SQLException e) {
            System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/FIND ALL " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/FINALLY/FIND ALL " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return null;
    }

    public Optional<Country> findOneByName(String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name from " + DBTables.Country +
                            " where name = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Country
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .build()
                );

            }
        } catch (SQLException e) {
            System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/FIND ONE BY NAME " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("COUNTRYREPOSITORYIMPL/COUNTRY/FINALLY/FIND ONE BY NAME " + DBTables.Country + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }
}
