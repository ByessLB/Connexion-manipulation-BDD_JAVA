package learn;

import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws SQLException {
        DataManager dataManager = new DataManager();

        // Insérer un utilisateur
        try {
            dataManager.insertDataUsers("JohnDoe", "john.doe@exemple.com", "123456");
        } catch (Exception e) {
            System.err.println("Error: insertion data: " + e.getMessage());
            e.printStackTrace();
        }

        // Récupérer tous les utilisateurs
        try {
            List<List<String>> users = dataManager.returnAllDataUsers();
            // Afficher les résultats dans la console
            System.out.println("Utilisateurs enregistrés :");
            for (List<String> user : users) {
                System.out.println("Nom d'utilisateur : " + user.get(0));
                System.out.println("E-mail : " + user.get(1));
                System.out.println("Mot de passe : " + user.get(2));
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Error: récupération data: " + e.getMessage());
        }

        // Récupérer un utilisateur par son ID
        try {
            List<String> user = dataManager.returnAllDataUser(1);
            // Afficher les résultats dans la console
            System.out.println("Utilisateur enregistré :");
            System.out.println("Nom d'utilisateur : " + user.get(0));
            System.out.println("E-mail : " + user.get(1));
            System.out.println("Mot de passe : " + user.get(2));
        } catch (SQLException e) {
            System.err.println("Error: récupération data: " + e.getMessage());
        }

        // Mettre à jour un utilisateur par son ID
        try {
            dataManager.updateDataUser(1,"JaneDoe", "654987");
            System.out.println("Mise à jour de l'utilisateur réussi !");
        } catch (Exception e) {
            System.err.println("Error: mise à jour data: " + e.getMessage());
        }

        // Supprimer un utilisateur par son ID
        try {
            dataManager.deleteUser(2);
            System.out.println("Suppression de l'utilisateur réussi !");
        } catch (Exception e) {
            System.err.println("Error: suppression data: " + e.getMessage());
        }
    }
}
