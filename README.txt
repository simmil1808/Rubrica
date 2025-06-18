RUBRICA – CONFIGURAZIONE DATABASE

Autore: Simone Milone

Questo software richiede l'accesso a un database MySQL per il salvataggio e il caricamento dei dati (persone e utenti).




	CONTENUTO

- Rubrica.jar                      → applicazione eseguibile
- credenziali_database.properties → file esterno con configurazione DB
- schema_database.sql             → script SQL per creare il database



	1. REQUISITI

✔ Java installato (versione 11 o superiore)
✔ MySQL installato sul sistema
✔ MySQL Connector/J incluso nel .jar (già fatto)
✔ Un utente MySQL abilitato all'accesso e creazione tabelle



	2. CREAZIONE DEL DATABASE

1. Apri un client MySQL (es. DBeaver, MySQL Workbench, terminale)
2. Lancia lo script SQL fornito nel file:
   → `schema_database.sql`

Questo creerà:

- Il database (es. rubrica_db)
- La tabella persone
- La tabella utenti (con utente 'admin' predefinito)



	3. CONFIGURAZIONE CREDENZIALI

Apri il file `credenziali_database.properties` e modifica i valori,
dovrebbe bastare solo la password:

