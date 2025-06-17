package persistenza;

import model.Persona;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.Vector;
import java.io.*;

public class GestoreFile {

    private static final File CARTELLA_INFORMAZIONI;

    static {
        File cartellaInformazioni = null;
        String path;
        try {
            //percorso del jar
            path = new File(GestoreFile.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()).getParent();
            //File.separator per evitare incompatibilità
            cartellaInformazioni = new File(path + File.separator + "informazioni");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Percorso non valido!",
                    "Errore percorso",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        // Crea la cartella se non esiste
        CARTELLA_INFORMAZIONI = cartellaInformazioni;
        if (!CARTELLA_INFORMAZIONI.exists()) {
            // serve a creare la cartella
            CARTELLA_INFORMAZIONI.mkdir();
        }
    }

    public static void salvaPersoneSuFile(Vector<Persona> persone) {
        // Pulisce i file esistenti nella cartella
        File[] vecchiFile = CARTELLA_INFORMAZIONI.listFiles();
        if (vecchiFile != null) {
            for (File file : vecchiFile) {
                file.delete();
            }
        }

        int contatore = 1;
        for (Persona persona : persone) {
            File filePersona = new File(CARTELLA_INFORMAZIONI, "Persona" + contatore + ".txt");
            try (PrintWriter writer = new PrintWriter(filePersona)) {
                writer.println(persona.getNome());
                writer.println(persona.getCognome());
                writer.println(persona.getIndirizzo());
                writer.println(persona.getTelefono());
                writer.println(persona.getEta());
            } catch (IOException e) {
                e.printStackTrace();
            }
            contatore++;
        }
    }

    public static Vector<Persona> caricaPersoneDaFile() {
        Vector<Persona> persone = new Vector<>();

        if (!CARTELLA_INFORMAZIONI.exists()) return persone;

        File[] files = CARTELLA_INFORMAZIONI.listFiles((dir, name) -> name.startsWith("Persona") && name.endsWith(".txt"));
        if (files == null) return persone;

        // Ordina i file per nome (Persona1, Persona2, ...)
        java.util.Arrays.sort(files);

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String nome = reader.readLine();
                String cognome = reader.readLine();
                String indirizzo = reader.readLine();
                String telefono = reader.readLine();

                int eta = Integer.parseInt(reader.readLine());

                Persona persona = new Persona(nome, cognome, indirizzo, telefono, eta);
                persone.add(persona);
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return persone;
    }
}
