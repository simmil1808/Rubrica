package persistenza;

import model.Persona;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;

public class GestoreDatabase {

    private static Connection connessione;

    private static Connection getConnessione() throws SQLException {
        if (connessione == null || connessione.isClosed()) {
            try {
                Properties properties = new Properties();

                //properties.load(new FileInputStream("credenziali_database.properties"));

                // Ottieni il percorso del .jar eseguibile
                File jarDirectory = new File(GestoreDatabase.class.getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI())
                        .getParentFile();

                // Crea riferimento al file nella stessa cartella
                File properitesFile = new File(jarDirectory, "credenziali_database.properties");

                // Carica le proprietà
                properties.load(new FileInputStream(properitesFile));

                String host = properties.getProperty("host");
                String port = properties.getProperty("port");
                String database = properties.getProperty("database");
                String user = properties.getProperty("user");
                String password = properties.getProperty("password");

                String url = "jdbc:mysql://" + host + ":" + port + "/" + database +
                        "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

                connessione = DriverManager.getConnection(url, user, password);

            } catch (IOException | URISyntaxException e) {
                JOptionPane.showMessageDialog(null, "Errore nel file credenziali_database:\n" + e.getMessage());
                e.printStackTrace();
            }
        }
        return connessione;
    }

    public static void salvaPersoneNelDatabase(Vector<Persona> persone) {

        try (Connection connection = getConnessione()) {
            connection.createStatement().executeUpdate("DELETE FROM persone");

            String sql = "INSERT INTO persone (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";

            //PreparedStatement è una classe che serve per inviare comandi SQL al database,
            //ma in modo più sicuro, veloce e flessibile rispetto a Statement.
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                //I metodi addBatch() ed executeBatch() servono a eseguire più operazioni SQL insieme, in un’unica “spedizione” al database.
                //Dopo aver fatto .addBatch() per ogni riga, chiami .executeBatch() una sola volta per eseguire tutte insieme.
                for (Persona persona : persone) {
                    preparedStatement.setString(1, persona.getNome());
                    preparedStatement.setString(2, persona.getCognome());
                    preparedStatement.setString(3, persona.getIndirizzo());
                    preparedStatement.setString(4, persona.getTelefono());
                    preparedStatement.setInt(5, persona.getEta());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore nel salvataggio su database:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
    public static Vector<Persona> caricaPersoneDalDatabase() {

        Vector<Persona> persone = new Vector<>();

        try (Connection connection = getConnessione()) {

            String sql = "SELECT * FROM persone ORDER BY id";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);

                 //ResultSet rappresenta il risultato di una query SQL SELECT.
                 //È come una tabella temporanea che puoi scorrere riga per riga, colonna per colonna.
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Persona persona = new Persona(
                            resultSet.getString("nome"),
                            resultSet.getString("cognome"),
                            resultSet.getString("indirizzo"),
                            resultSet.getString("telefono"),
                            resultSet.getInt("eta")
                    );
                    persone.add(persona);
                }
            }

        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "Errore nel caricamento dal database.");
            JOptionPane.showMessageDialog(null, "Errore nel caricamento dal database:\n" + e.getMessage());
            e.printStackTrace();
        }

        return persone;
    }

    public static boolean verificaCredenziali(String username, String password) throws SQLException {

        try (Connection connection = getConnessione()) {

            String sql = "SELECT * FROM utenti WHERE username = ? AND password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // true se trova un record

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante il login:\n" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
