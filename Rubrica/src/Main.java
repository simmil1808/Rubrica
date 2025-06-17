import gui.FinestraLogin;
import gui.FinestraPrincipale;
import model.GestoreRubrica;
import model.Persona;
import persistenza.GestoreDatabase;
import persistenza.GestoreFile;

import javax.swing.*;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new FinestraLogin());
    }
}