# Fruit API - Spring Boot & Persistència Políglota

## Descripció - Enunciat de l'exercici

Aquest projecte consisteix en el desenvolupament d'una API REST completa utilitzant **Spring Boot**, evolucionant a través de tres nivells de complexitat per gestionar 
diferents entitats i sistemes de persistència.

El projecte implementa una arquitectura que permet la coexistència de bases de dades relacionals i no relacionals.

* **Nivell 1 (H2):** Creació d'una API bàsica per a la gestió d'una entitat `Fruita` (CRUD: Crear, Llegir, Actualitzar, Esborrar) utilitzant una base de dades en memòria H2
  per a desenvolupament ràpid.
* **Nivell 2 (MySQL):** Migració de la persistència de l'entitat `Fruita` a una base de dades relacional **MySQL**, configurant el projecte per a un entorn més robust.
* **Nivell 3 (MongoDB - Políglota):** Implementació d'un sistema de persistència híbrid. S'afegeix l'entitat `Comanda` (Orders), la qual es gestiona mitjançant una base de
  dades NoSQL **MongoDB**.
    
* El resultat final és una aplicació que emmagatzema fruites a MySQL i comandes a MongoDB simultàniament.

## Tecnologies Utilitzades

Aquest projecte s'ha desenvolupat amb les següents tecnologies i eines:

* **Llenguatge:** Java 21
* **Framework:** Spring Boot 3.x (Web, Data JPA, Data MongoDB)
* **Bases de Dades:**
    * MySQL 8.0 (Gestió de Fruites)
    * MongoDB (Gestió de Comandes)
* **Containerització:** Docker & Docker Compose
* **Eines de Construcció:** Maven
* **Altres:** Postman (per a proves d'API), Git.

## Requisits

Per poder executar aquest projecte en el teu entorn local, necessites tenir instal·lat:

1.  **Docker Desktop:** Imprescindible per aixecar els contenidors de la base de dades i de l'aplicació sense necessitat d'instal·lar MySQL o Mongo localment.
2.  **Git:** Per clonar el repositori.
3.  **(Opcional) Java 21 JDK & Maven:** Només si vols executar l'aplicació fora de Docker.

## Instal·lació

Segueix aquests passos per obtenir el codi font:

1.  Obre la terminal.
2.  Clona el repositori:
    ```bash
    git clone <URL_DEL_TEU_REPOSITORI>
    ```
3.  Accedeix a la carpeta del projecte:
    ```bash
    cd fruit-api-h2
    ```

## Execució

La forma més senzilla i neta d'executar el projecte (Nivell 3 complet) és mitjançant Docker Compose, ja que configura automàticament MySQL, MongoDB i l'aplicació Java 
connectant-los entre si.

### 1. Aixecar l'entorn
Executa la següent comanda a l'arrel del projecte:

```bash
docker compose up -d --build
(Això construirà la imatge de l'aplicació, baixarà les imatges de MySQL i Mongo, i iniciarà els tres serveis).

2. Verificar l'estat
Assegura't que els tres contenidors estan actius (running):

Bash
docker ps
Hauries de veure: fruit-api-compose, mysql-compose-db i mongo-db-fruit.

3. Provar l'API (Endpoints)
L'API estarà disponible a http://localhost:8080. Pots utilitzar Postman o cURL.

Gestió de Fruites (MySQL):

POST /fruits/add: Afegir fruita.

PUT /fruits/update: Actualitzar fruita.

DELETE /fruits/delete/{id}: Esborrar fruita.

GET /fruits/getOne/{id}: Obtenir una fruita.

GET /fruits/getAll: Llistar totes.

Gestió de Comandes (MongoDB):

POST /orders: Crear una nova comanda.

GET /orders: Llistar totes les comandes.

GET /orders/{id}: Veure detall d'una comanda.

PUT /orders/{id}: Modificar una comanda existent.

DELETE /orders/{id}: Eliminar una comanda.

## Desplegament
Per desplegar aquest projecte en un entorn de producció o servidor remot:

Generació de l'Artefacte: Crea el fitxer .jar executable optimitzat:

Bash
./mvnw clean package -DskipTests
Containerització per a Producció: El projecte ja està "dockeritzat". En un servidor amb Docker instal·lat, només cal copiar el fitxer docker-compose.yml i la carpeta del
projecte.

Per assegurar que les dades persisteixen en producció, el docker-compose.yml ja inclou volums:
YAML
volumes:
  - mysql_data:/var/lib/mysql
  - mongo_data:/data/db
Simplement executa docker compose up -d al servidor i l'API estarà operativa i accessible pel port 8080.
