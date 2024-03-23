# IoT_Automotive_PaF2023_2

## Spring Framework
### Datenbank starten
MacOS:  
PostgreSQL installieren: ```brew install postgresql```
PostgreSQL starten: ```/opt/homebrew/opt/postgresql@14/bin/postgres -D /opt/homebrew/var/postgresql@14```

### Erster Start / Starten
1. Im Terminal in das `backend` Verzeichnis wechseln.
1. `mvn clean install` ausführen. 
1. In der `backend/src/main/resources/application.yml` sicherstellen das spring.jpa.hibernate.ddl-auto auf `create` steht.
1. Start in IntelliJ IDEA über den Run-Button.
1. Anschließend nach dem Starten der Applikation von `create` auf `update` setzen, damit bei einem erneuten Start die Datenbank nicht überschrieben wird bzw. es zu Fehlern kommt.

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

