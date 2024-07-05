# Manipulation des données

### Ressources

- `src/main/resources/database.properties` : Ressources de connexion à la Base de Données
- `src/main/java/learn/DBConnection.java` : Connexion à la Base de Données
- `src/main/java/learn/DataManager.java` : Manipulation par requête de la Base de Données
- `src/main/java/learn/App.java` : Appel des méthodes génératrice de requêtes

## Projet

- Création d'un projet Java/Maven
- Ajout de la dépendance MySQL Connector/J à votre pom.xml
- Création d'un fichier de configuration pour la base de données. Ce fichier contiendra les informations de connexion, telles que l'adresse du serveur, le nom d'utilisateur et le mot de passe. Ce fichier pourra être créé dans le répertoire `src/main/resouces`.
- Création d'une classe de connexion à la base de données. Cette classe lira les informations de connexion à partir du fichier `database.properties` et créera une connexion à la base de données à l'aide du pilote JDBC MySQL.
- Création d'une classe de gestion des données. Cette classe contiendra des méthodes qui permettront de récupérer des données à partir de la base de données, de les modifier et de les supprimer. Ces méthodes devront être générées à partir de requêtes SQL. Les requêtes devront être stockées dans un fichier de configuration, de manière à pouvoir
les modifier facilement sans avoir à modifier le code source.

## Dépendance MySQL Connector/J

MySQL Connector/J est un pilote JDBC(Java Database Connectivity) pour MySQL. Il permet aux applications Java de se connecter à une base de données MySQL et d'effectuer des opérations de lecture et d'écriture sur cette base de données.

Pour utiliser cette dépendance, vous devez ajouter la dépendance suivante à votre fichier `pom.xml` :

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

Cela indique à Maven de télécharger la bibliothèque correspendante et de l'inclure dans le classpath de votre projet. La version actuelle de MySQL Connector/J est 8.0.33.

## Fichier de configuration **`database.properties`**

Le fichier de configuration `database.properties` contient les information de connexion à votre base de données MySQL telles que l'adresse du serveur, le nom d'utilisateur et le mot de passe. En utilisant un fichier de configuration séparé, vous pouvez facilement modifier les paramètres de connexion à la base de données sans avoir à modifier le code source de votre programme.

Voici un expemple de contenu pour le fichier `database.properties` :

```properties
jdbc.driverClassName=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mydatabase
jdbc.username=myusername
jdbc.password=mypassword
```
- jdbc.driverClassName : le nom de la classe du pilote JDBC pour MySQL. Dans ce cas, nous utilisons le pilote MySQL Connector/J.
- jdbc.url : l'URL de connexion à la base de données. Elle contient l'adresse du serveur, le numéro de port et le nom de la base de données.
- jdbc.username : le nom d'utilisateur pour se connecter à la base de données.
- jdbc.password : le mot de passe pour se connecter à la base de données.

## Classe de connexion à la base de données

La classe de connexion à la base de données est responsable de la création et de la gestion de la connexion à la base de données MySQL. Elle lit les informations de connexion à partir du fichier `database.properties` et utilise le pilote LDBC MySQL pour créer une connexion à la base de données.

Voici un exemple de code pour la classe `DBConnection` :

```java
package learn;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
                Properties prop = new Properties();
                prop.load(input);

                String driver = prop.getProperty("jdbc.driverClassName");
                String url = prop.getProperty("jdbc.url");
                String username = prop.getProperty("jdbc.username");
                String password = prop.getProperty("jdbc.password");

                Class.forName(driver);

                connection = DriverManager.getConnection(url, username, password);
            } catch (IOException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
```

Dans cet exemple, la méthode `getConnection()` vérifie si une connexion à la base de données existe déjà. Si ce n'est pas le cas, elle lit les informations de connexion à partir du fichier `database.properties`, charge le pilote JDBC MySQL à l'aide de la méthode `Class.forName()`, et crée une nouvelle connexion à l'aide de la méthode `DriverManager.getConnection()`.

## Classe de gestion des données

La classe de gestion des données contient des méthodes pour effectuer des opérations sur la base de données, telles que la récupération, la modification et la suppression de données. Ces méthodes utilisent des requêtes SQL pour interagir avec la base de données.

Voici un exemple de code pour la classe `DataManager` :

```java
package learn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public List<List<String>> returnAllDataUsers() throws SQLException {
        List<List<String>> results = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                List<String> row = new ArrayList<>();
                row.add(resultSet.getString("username"));
                row.add(resultSet.getString("email"));
                row.add(resultSet.getString("password"));
                results.add(row);
            }
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

    ...

}
```

Dans cet exemple, la classe `DataManager` contient 2 méthodes : `returnAllDataUsers()` et `insertDataUsers()`.

La méthode `returnAllDataUsers()` utilise une requête SQL pour récupérer toutes les lignes de la table `user` et les stocker dans une liste de listes de chaînes de caractères. La méthode renvoie ensuite cette liste.

La méthode `insertDataUsers()` utilise une requête SQL pour insérer une nouvelle ligne dans la table `user` avec les valeurs spécifiées pour le nom de l'utilisateur, l'adresse e-mail, et le mot de passe."# Connexion-manipulation-BDD_JAVA" 
