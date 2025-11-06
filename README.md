 Biblioteca — Gestione prestiti e utenti

Progetto web realizzato in Java (JSP + Servlet) per la gestione di una biblioteca digitale.  
L’applicazione consente la registrazione e gestione degli utenti, la visualizzazione del catalogo libri, e la gestione dei prestiti.  
Include inoltre un sistema di amministrazione con ruoli differenziati (admin master e admin slave) e invio automatico di avvisi tramite n8n e Mailtrap.

---

 Funzionalità principali
-  Registrazione e login utenti
-  Catalogo libri con possibilità di ricerca e filtro
-  Gestione prestiti: richiesta e restituzione libri
- Ruoli amministrativi:
	° Admin master: gestione completa e creazione nuovi admin
    ° Admin slave: gestione utenti e libri
- Avvisi automatici di ritardo tramite n8n e Mailtrap
- Database MySQL con gestione DAO e JDBC
- Interfaccia responsive realizzata con Bootstrap
- Password criptate e connessione protetta

Configurazione locale

 1. Database
Crea un database MySQL chiamato libreria e importa le relative tabelle (utenti, libri, autori, prestiti, crypt).

Da configurare il file db.properties (che non è incluso nel repository).

Esempio di contenuto:
properties
db.url=jdbc:mysql://localhost:3306/libreria
db.user=tuo_username
db.password=la_tua_password


---

 2. Variabili d’ambiente (obbligatorie)

Configura le seguenti variabili d’ambiente sul tuo sistema (Windows, Mac o cloud):

| Nome variabile | Valore esempio | Descrizione |
|----------------|----------------|--------------|
| CONFIG_PATH : C:\Users\andre\Desktop\sviluppo\workspaceJava\connessione\db.properties -  Percorso del file di configurazione DB 
| N8N_WEBHOOK_URL : https://tuo_username.app.n8n.cloud/webhook-test/avviso-biblioteca - Webhook per l’invio automatizzato di email tramite n8n 

---

 3. Avvio del progetto
1. Importa il progetto in Eclipse
2. Configura un server Apache Tomcat 11
3. Avvia l’applicazione  
  
---

  Tecnologie utilizzate
| Componente | Tecnologia |
|-------------|-------------|
| Backend | Java 17, JSP, Servlet |
| Frontend | HTML, CSS, Bootstrap |
| Database | MySQL 8 + JDBC |
| Automazioni | n8n (cloud) |
| Email testing | Mailtrap |
| IDE 		| Eclipse |
| Server | Apache Tomcat 11 |

---

  Sicurezza
- Credenziali e URL sensibili non inclusi nel repository
- File `db.properties` e variabili n8n gestiti esternamente
- `.gitignore` configurato per escludere build, configurazioni IDE e dati sensibili

---

  Autori
Andrea Vendetti e Giulio Vittorio Ferrari
Progetto realizzato per stage scolastico in collaborazione con CEFI Informatica.

---

