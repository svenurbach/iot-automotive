# IOT_Automotive_PaF2023_2

## Spring Framework
### Getting Started
#### Datenbank starten
MacOS:  
PostgreSQL installieren: ```brew install postgresql```
PostgreSQL starten: ```/opt/homebrew/opt/postgresql@14/bin/postgres -D /opt/homebrew/var/postgresql@14```

#### Erster Start
1. In der `backend/src/main/resources/application.yml` sicherstellen das spring.jpa.hibernate.ddl-auto auf `create` steht.
1. Start in IntelliJ IDEA über den Run-Button.
1. Anschließend nach dem Starten der Applikation von `create` auf `update` setzen, damit bei einem erneuten Start die Datenbank nicht überschrieben wird bzw. es zu Fehlern kommt.

## Backend-API
Endpunkt: http://localhost:8080/api/

#### Frontend
http://localhost:4200/


