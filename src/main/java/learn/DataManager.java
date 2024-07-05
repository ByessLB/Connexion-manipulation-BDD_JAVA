package learn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    public List<List<String>> returnAllDataUsers() throws SQLException {
        List<List<String>> results = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            // Parcours des résultats de la requête et ajout des valeurs à la liste "results"
            while (resultSet.next()) {
                List<String> row = new ArrayList<>();
                row.add(resultSet.getString("username"));
                row.add(resultSet.getString("email"));
                row.add(resultSet.getString("password"));
                results.add(row);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return results;
    }

    public void insertDataUsers(String user, String email, String pwd) {
        String sql = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user);
            statement.setString(2, email);
            statement.setString(3, pwd);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error: méthode insertData :" + e.getMessage());
        }
    }

    public List<String> returnAllDataUser(int id) throws SQLException {
        List<String> result = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE id = ?"; // id est la clé primaire de la table user
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result.add(resultSet.getString("username"));
                    result.add(resultSet.getString("email"));
                    result.add(resultSet.getString("password"));
                } else {
                    throw new SQLException("Aucun utilisateur trouvé avec l'ID : " + id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: méthode returnAllDataUser :" + e.getMessage());
        }
        return result;
    }

    public void updateDataUser(int id, String newUsername, String newPassword) {
        String sql = "UPDATE user SET username = ?, password = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newUsername);
            statement.setString(2, newPassword);
            statement.setInt(3, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Aucun utilisateur trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error: méthode updateDataUser :" + e.getMessage());
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Aucun utilisateur trouvé avec l'ID : " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error: méthode deleteUser :" + e.getMessage());
        }
    }
}
