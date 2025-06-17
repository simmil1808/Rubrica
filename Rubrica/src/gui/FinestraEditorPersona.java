package gui;

import model.Persona;

import javax.swing.*;
import java.awt.*;

public class FinestraEditorPersona extends JDialog {

    private JTextField jTextFieldNome;
    private JTextField jTextFieldCognome;
    private JTextField jTextFieldIndirizzo;
    private JTextField jTextFieldTelefono;
    private JTextField jTextFieldEta;

    private Persona persona;
    private boolean salvato = false;

    public FinestraEditorPersona(Frame owner, Persona persona) {
        super(owner, true); // Finestra modale
        this.persona = persona;

        setTitle(persona == null ? "Nuova Persona" : "Modifica Persona");
        setSize(350, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Pannello campi
        JPanel jPanelCampi = new JPanel(new GridLayout(5, 2, 5, 5));
        jPanelCampi.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jPanelCampi.add(new JLabel("Nome:"));
        jTextFieldNome = new JTextField();
        jPanelCampi.add(jTextFieldNome);

        jPanelCampi.add(new JLabel("Cognome:"));
        jTextFieldCognome = new JTextField();
        jPanelCampi.add(jTextFieldCognome);

        jPanelCampi.add(new JLabel("Indirizzo:"));
        jTextFieldIndirizzo = new JTextField();
        jPanelCampi.add(jTextFieldIndirizzo);

        jPanelCampi.add(new JLabel("Telefono:"));
        jTextFieldTelefono = new JTextField();
        jPanelCampi.add(jTextFieldTelefono);

        jPanelCampi.add(new JLabel("Età:"));
        jTextFieldEta = new JTextField();
        jPanelCampi.add(jTextFieldEta);

        add(jPanelCampi, BorderLayout.CENTER);

        // Pannello bottoni
        JPanel pannelloBottoni = new JPanel();
        JButton jButtonSalva = new JButton("Salva");
        JButton jButtonAnnulla = new JButton("Annulla");

        pannelloBottoni.add(jButtonSalva);
        pannelloBottoni.add(jButtonAnnulla);
        add(pannelloBottoni, BorderLayout.SOUTH);

        // Precompila i campi se in modifica
        if (persona != null) {
            jTextFieldNome.setText(persona.getNome());
            jTextFieldCognome.setText(persona.getCognome());
            jTextFieldIndirizzo.setText(persona.getIndirizzo());
            jTextFieldTelefono.setText(persona.getTelefono());
            jTextFieldEta.setText(String.valueOf(persona.getEta()));
        }

        // SALVA
        jButtonSalva.addActionListener(e -> {
            if (validaCampi()) {
                salvato = true;
                dispose(); // Chiude il dialogo
            }
        });

        // ANNULLA
        jButtonAnnulla.addActionListener(e -> {
            salvato = false;
            dispose();
        });

        getRootPane().setDefaultButton(jButtonSalva); // Enter su "Salva"
        //setVisible(true); // Mostra la finestra modale
    }

    public boolean isSalvato() {
        return salvato;
    }

    public Persona getPersona() {
        return new Persona(
                jTextFieldNome.getText().trim(),
                jTextFieldCognome.getText().trim(),
                jTextFieldIndirizzo.getText().trim(),
                jTextFieldTelefono.getText().trim(),
                Integer.parseInt(jTextFieldEta.getText().trim())
        );
    }

    private boolean validaCampi() {
        if (jTextFieldNome.getText().isEmpty() ||
                jTextFieldCognome.getText().isEmpty() ||
                jTextFieldIndirizzo.getText().isEmpty() ||
                jTextFieldTelefono.getText().isEmpty() ||
                jTextFieldEta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori.");
            return false;
        }

        try {
            Integer.parseInt(jTextFieldEta.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Età deve essere un numero intero.");
            return false;
        }

        return true;
    }
}
