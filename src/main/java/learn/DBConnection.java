package learn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection; // La connexion à la base de données

    public static Connection getConnection() {
        if (connection == null) { // Si la connexion n'a pas encore été établie
            try (InputStream input = new FileInputStream("src/main/resources/database.properties")) {
                Properties prop = new Properties(); // Création d'un objet Properties pour lire les propriétés du fichier de configuration
                prop.load(input); // Chargement des propriétés à partir du fichier de configuration

                String driverClassName = prop.getProperty("jdbc.driverClassName"); // Récupération du nom de la classe du pilote JDBC
                String url = prop.getProperty("jdbc.url"); // Récupération de l'URL de connexion à la base de données
                String username = prop.getProperty("jdbc.username"); // Récupération du nom d'utilisateur
                String password = prop.getProperty("jdbc.password"); // Récupération du mot de passe

                Class.forName(driverClassName); // Chargement du pilote JDBC
                connection = DriverManager.getConnection(url, username, password); // Etablissement de la connexion à la BDD
            } catch (IOException | ClassNotFoundException | SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return connection; // Retour de la connexion à la BDD
    }
}
