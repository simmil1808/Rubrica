package model;

import java.util.Vector;

public class GestoreRubrica {

    private Vector<Persona> listaPersone = new Vector<>();

    public GestoreRubrica() {
        this.listaPersone = new Vector<>();
    }

    // Restituisce la lista completa
    public Vector<Persona> getListaPersone() {
        return listaPersone;
    }

    // Aggiunge una nuova persona
    public void aggiungiPersona(Persona persona) {
        listaPersone.add(persona);
    }

    // Ottiene una persona singola (usato per la modifica)
    public Persona getPersona(int index) {
        if (index >= 0 && index < listaPersone.size()) {
            return listaPersone.get(index);
        }
        return null;
    }

    // Modifica una persona esistente (sostituisce quella all'indice)
    public void modificaPersona(int index, Persona personaModificata) {
        if (index >= 0 && index < listaPersone.size()) {
            listaPersone.set(index, personaModificata);
        }
    }

    // Elimina una persona all'indice specificato
    public void eliminaPersona(int index) {
        if (index >= 0 && index < listaPersone.size()) {
            listaPersone.remove(index);
        }
    }

    // Sovrascrive l'intera lista (utile dopo lettura da file)
    public void setListaPersone(Vector<Persona> personaVector) {
        this.listaPersone = personaVector;
    }
}
