# IoT_Automotive_PaF2023_2

## Spring Backend
### Datenbank starten
MacOS:  
PostgreSQL installieren: ```brew install postgresql```
PostgreSQL starten: ```/opt/homebrew/opt/postgresql@14/bin/postgres -D /opt/homebrew/var/postgresql@14```

### Datenbank initialisieren
1. Datenbank "paf2023" anlegen
1. Role "paf2023_role" anlegen
1. Datenbank-User "paf2023" mit Passwort "paf2023" anlegen und Rolle zuweisen

### Starten
1. Im Terminal in das `backend` Verzeichnis wechseln.
1. `mvn clean install` ausführen. 
1. Start in IntelliJ IDEA über den Run-Button.

### Backend-API
Endpunkt: http://localhost:8080/api/

## Angular Frontend
### Starten
1. Im Terminal in das `frontend` Verzeichnis wechseln.
2. `npm install` ausführen.
3. `ng serve` ausführen.
4. Frontend ist erreichbar unter http://localhost:4200/

### e2e Tests
1. Im Terminal in das `frontend` Verzeichnis wechseln.
2. `ng e2e` ausführen oder `npx cypress open` für die GUI.

