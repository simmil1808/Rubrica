package gui;

import model.GestoreRubrica;
import model.Persona;
import persistenza.GestoreDatabase;
import persistenza.GestoreFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class FinestraPrincipale extends JFrame {
    private GestoreRubrica gestoreRubrica;
    private JTable tabella;
    private DefaultTableModel tabellaModel;

    public FinestraPrincipale(GestoreRubrica gestoreRubrica) {
        this.gestoreRubrica = gestoreRubrica;

        setTitle("Rubrica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        inizializzaComponenti();
        aggiornaTabella();

        setVisible(true);
    }

    private void inizializzaComponenti() {
        // Layout principale
        setLayout(new BorderLayout());

        // Tabella
        tabellaModel = new DefaultTableModel(new Object[]{"Nome", "Cognome", "Telefono"}, 0);
        tabella = new JTable(tabellaModel);
        JScrollPane jScrollPane = new JScrollPane(tabella);
        add(jScrollPane, BorderLayout.CENTER);

        // Pannello bottoni
        JPanel pannelloBottoni = new JPanel();
        JButton jButtonNuovo = new JButton("Nuovo");
        JButton jButtonModifica = new JButton("Modifica");
        JButton jButtonElimina = new JButton("Elimina");

        pannelloBottoni.add(jButtonNuovo);
        pannelloBottoni.add(jButtonModifica);
        pannelloBottoni.add(jButtonElimina);

        add(pannelloBottoni, BorderLayout.SOUTH);

        // AZIONE: NUOVO
        jButtonNuovo.addActionListener(e -> {
            FinestraEditorPersona editor = new FinestraEditorPersona(this, null);
            editor.setVisible(true);
            if (editor.isSalvato()) {
                Persona nuova = editor.getPersona();
                gestoreRubrica.aggiungiPersona(nuova);
                aggiornaTabella();
                //GestoreFile.salvaPersoneSuFile(gestoreRubrica.getListaPersone());
                GestoreDatabase.salvaPersoneNelDatabase(gestoreRubrica.getListaPersone());
            }
        });

        // AZIONE: MODIFICA
        jButtonModifica.addActionListener(e -> {
            int index = tabella.getSelectedRow();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona prima una persona da modificare.");
                return;
            }

            Persona selezionata = gestoreRubrica.getPersona(index);
            FinestraEditorPersona editor = new FinestraEditorPersona(this, selezionata);
            editor.setVisible(true);
            if (editor.isSalvato()) {
                Persona modificata = editor.getPersona();
                gestoreRubrica.modificaPersona(index, modificata);
                aggiornaTabella();
                //GestoreFile.salvaPersoneSuFile(gestoreRubrica.getListaPersone());
                GestoreDatabase.salvaPersoneNelDatabase(gestoreRubrica.getListaPersone());
            }
        });

        // AZIONE: ELIMINA
        jButtonElimina.addActionListener(e -> {
            int index = tabella.getSelectedRow();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona prima una persona da eliminare.");
                return;
            }

            Persona daEliminare = gestoreRubrica.getPersona(index);
            int conferma = JOptionPane.showConfirmDialog(this,
                    "Eliminare la persona " + daEliminare.getNome() + " " + daEliminare.getCognome() + "?",
                    "Conferma eliminazione", JOptionPane.YES_NO_OPTION);

            if (conferma == JOptionPane.YES_OPTION) {
                gestoreRubrica.eliminaPersona(index);
                aggiornaTabella();
                //GestoreFile.salvaPersoneSuFile(gestoreRubrica.getListaPersone());
                GestoreDatabase.salvaPersoneNelDatabase(gestoreRubrica.getListaPersone());
            }
        });
    }

    public void aggiornaTabella() {
        tabellaModel.setRowCount(0); // Pulisce la tabella
        Vector<Persona> personaVector = gestoreRubrica.getListaPersone();

        for (Persona persona : personaVector) {
            tabellaModel.addRow(new Object[]{
                    persona.getNome(),
                    persona.getCognome(),
                    persona.getTelefono()
            });
        }
    }

}
