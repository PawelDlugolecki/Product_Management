package dlugolecki.pawel.repository.impl;

import dlugolecki.pawel.connection.DBTables;
import dlugolecki.pawel.connection.DbConnection;
import dlugolecki.pawel.model.Category;
import dlugolecki.pawel.repository.repos.CategoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {
    private Connection connection = DbConnection.getInstance().getConnection();

    public void add(Category category) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlInsert = "insert into " + DBTables.Category +
                    " (name) values (?);";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, category.getName());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/ADD " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/FINALLY/ADD " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void update(Category category) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlUpdate = "update " + DBTables.Category +
                    " set name = ? where id = ?;";
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/UPDATE " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/FINALLY/UPDATE " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void delete(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            final String sqlDelete = "delete from " + DBTables.Category + " where id = ?;";
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/DELETE " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {

                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/FINALLY/DELETE " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public void deleteAll() {
        Statement statement = null;
        try {
            final String sqlSelect = "delete from " + DBTables.Category;
            statement = connection.createStatement();
            statement.execute(sqlSelect);
        } catch (SQLException e) {
            System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/DELETE ALL " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/FINALLY/DELETE ALL " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
            }
        }
    }

    public Optional<Category> findOneById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name from " + DBTables.Category +
                            " where id = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Category
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .build()
                );

            }
        } catch (SQLException e) {
            System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/FIND ONE BY ID " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/FINALLY/FIND ONE BY ID " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }

    public Optional<Category> findOneByName(String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name from " + DBTables.Category +
                            " where name = ? ";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(
                        Category
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .build()
                );

            }
        } catch (SQLException e) {
            System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/FIND ONE BY NAME " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/FINALLY/FIND ONE BY NAME " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return Optional.empty();
    }

    public List<Category> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            final String sqlSelect =
                    "select id, name from " + DBTables.Category;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlSelect);

            List<Category> categories = new ArrayList<>();

            while (resultSet.next()) {
                categories.add(
                        Category
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .build()
                );
            }
            return categories;
        } catch (SQLException e) {
            System.err.println("REPOSITORY/CATEGORY/FIND ALL " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.err.println("CATEGORYREPOSITORYIMPL/CATEGORY/FINALLY/FIND ALL " + DBTables.Category + " [ERROR: " + e.getMessage() + "]");
            }
        }
        return null;
    }
}
