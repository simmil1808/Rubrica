package gui;

import model.GestoreRubrica;
import model.Persona;
import persistenza.GestoreDatabase;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class FinestraLogin extends JFrame {

    private JTextField campoUsername;
    private JPasswordField campoPassword;

    public FinestraLogin() {
        setTitle("Login Rubrica");
        setSize(300, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pannello centrale per campi
        JPanel pannelloCampi = new JPanel(new GridLayout(2, 2, 5, 5));
        pannelloCampi.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pannelloCampi.add(new JLabel("Username:"));
        campoUsername = new JTextField();
        pannelloCampi.add(campoUsername);

        pannelloCampi.add(new JLabel("Password:"));
        campoPassword = new JPasswordField();
        pannelloCampi.add(campoPassword);

        add(pannelloCampi, BorderLayout.CENTER);

        // Bottone login
        JButton bottoneLogin = new JButton("LOGIN");
        bottoneLogin.addActionListener(e -> {
            try {
                verificaAccesso();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        JPanel pannelloBottoni = new JPanel();
        pannelloBottoni.add(bottoneLogin);

        add(pannelloBottoni, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void verificaAccesso() throws SQLException {

        //JTextField
        String username = campoUsername.getText().trim();
        //JPasswordField
        String password = new String(campoPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci username e password.");
            return;
        }

        boolean valido = GestoreDatabase.verificaCredenziali(username, password);

        if (valido) {
            dispose(); // Chiudi la finestra di login senza utilizzare memoria

            // Avvia GUI in modo thread-safe
            SwingUtilities.invokeLater(() ->  {

                // Carica dati dal file
                //Vector<Persona> personaVector = GestoreFile.caricaPersoneDaFile();

                // Carica dati dal DB mySQl
                Vector<Persona> personaVector = GestoreDatabase.caricaPersoneDalDatabase();

                // Inizializza logica
                GestoreRubrica gestoreRubrica = new GestoreRubrica();

                gestoreRubrica.setListaPersone(personaVector);
                new FinestraPrincipale(gestoreRubrica);

            });

        } else {
            JOptionPane.showMessageDialog(this, "Credenziali non valide.");
        }
    }
}
