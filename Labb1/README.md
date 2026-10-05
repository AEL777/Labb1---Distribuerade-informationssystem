# HI1031 Webbshop

En enkel webbshop för Java-laborationen i HI1031 Distribuerade informationssystem. Projektet implementerar endast funktionalitet för betyg 3: login, produktlista, möjlighet att lägga produkter i kundvagn och möjlighet att visa kundvagnen.

## Teknik

- Java 21
- JSP
- Servlets med `javax.servlet.*`
- JDBC
- PostgreSQL
- `HttpSession`
- Maven
- Apache Tomcat 9

Projektet använder inte Spring, Hibernate, JPA, Jakarta EE eller andra större ramverk.

## 3-lagersarkitektur

Presentation layer:

- `src/main/java/presentation/LoginServlet.java`
- `src/main/java/presentation/LogoutServlet.java`
- `src/main/java/presentation/ProductServlet.java`
- `src/main/java/presentation/CartServlet.java`
- `src/main/webapp/index.jsp`
- `src/main/webapp/login.jsp`
- `src/main/webapp/products.jsp`
- `src/main/webapp/cart.jsp`

Business layer:

- `src/main/java/business/ShopService.java`

Data layer:

- `src/main/java/data/UserDAO.java`
- `src/main/java/data/ProductDAO.java`
- `src/main/java/data/DatabaseConnection.java`

Model-klasser:

- `src/main/java/model/User.java`
- `src/main/java/model/Product.java`
- `src/main/java/model/Cart.java`
- `src/main/java/model/CartItem.java`

Model-klasserna är dataobjekt som används mellan lagren, inte ett fjärde lager.

## Databas

Skapa databasen i PostgreSQL:

```sql
CREATE DATABASE webshop;
```

Kör sedan SQL-filen:

```bash
psql -d webshop -f database.sql
```

`database.sql` skapar tabellerna `users` och `products` och lägger in testdata.

Testkonto:

- användarnamn: `test`
- lösenord: `1234`

Exempelprodukter:

- T-shirt, 199 kr
- Hoodie, 499 kr
- Keps, 149 kr

## Databasinställningar

Databasanslutningen finns i:

```text
src/main/java/data/DatabaseConnection.java
```

Standardvärden:

- URL: `jdbc:postgresql://localhost:5432/webshop`
- användare: `postgres`
- lösenord: tom sträng

Ändra antingen värdena i `DatabaseConnection.java` eller sätt miljövariabler/system properties:

- `WEBSHOP_DB_URL`
- `WEBSHOP_DB_USER`
- `WEBSHOP_DB_PASSWORD`

Exempel:

```bash
export WEBSHOP_DB_URL="jdbc:postgresql://localhost:5432/webshop"
export WEBSHOP_DB_USER="postgres"
export WEBSHOP_DB_PASSWORD="ditt_losenord"
```

Inget riktigt PostgreSQL-lösenord är hårdkodat i projektet. Om din PostgreSQL kräver lösenord ska du sätta `WEBSHOP_DB_PASSWORD` innan Tomcat startas.

## Bygga projektet

Kör:

```bash
mvn clean package
```

Efter lyckad build skapas:

```text
target/webshop.war
```

## Deploy till Tomcat

Kopiera WAR-filen:

```bash
cp target/webshop.war ~/tomcat9/webapps/
```

Starta Tomcat:

```bash
cd ~/tomcat9/bin
./startup.sh
```

Stoppa Tomcat:

```bash
cd ~/tomcat9/bin
./shutdown.sh
```

Om omstart behövs:

```bash
cd ~/tomcat9/bin
./shutdown.sh
./startup.sh
```

Applikationen nås på:

```text
http://localhost:8080/webshop/
```

## Login och HttpSession

Loginflöde:

```text
login.jsp -> LoginServlet -> ShopService -> UserDAO -> PostgreSQL
```

Vid korrekt login sparas användaren i sessionen:

```java
session.setAttribute("user", user);
```

Logout görs i `LogoutServlet` med:

```java
session.invalidate();
```

## Produkter

Produktflöde:

```text
ProductServlet -> ShopService -> ProductDAO -> PostgreSQL
```

`ProductServlet` hämtar produkter från businesslagret och skickar dem till `products.jsp` med request-attributet `products`.

## Kundvagn

Kundvagnen sparas i `HttpSession`:

```java
session.setAttribute("cart", cart);
```

Varje browser-session får därför en egen kundvagn. Servlet-klasserna sparar inte användarspecifik data i instansvariabler.

När en produkt läggs till:

```text
CartServlet -> ShopService -> ProductDAO -> PostgreSQL
```

`CartServlet` hämtar produkten, hämtar eller skapar `Cart` i sessionen och lägger till produkten. Om samma produkt läggs till flera gånger ökar antal.

## Genomförda funktionstester

Följande funktionstester har genomförts lokalt mot PostgreSQL och Tomcat 9:

1. Öppna `http://localhost:8080/webshop/`.
2. Gå till login.
3. Logga in med `test` / `1234` och kontrollera att produktsidan visas.
4. Testa fel username/password och kontrollera att felmeddelande visas.
5. Kontrollera att produkter hämtas från PostgreSQL och visas i `products.jsp`.
6. Lägg T-shirt i kundvagnen.
7. Lägg flera produkter i kundvagnen.
8. Öppna kundvagnen och kontrollera produktnamn, pris och antal.
9. Ladda om produktsidan och kontrollera att login-sessionen finns kvar.
10. Ladda om kundvagnen och kontrollera att kundvagnen finns kvar.
11. Logga ut och kontrollera att skyddade sidor skickar till login.
12. Testa två olika browser-sessioner, till exempel vanlig och privat webbläsare, och kontrollera att kundvagnarna inte delas.

Vid senaste kontrollen byggdes projektet med `mvn clean package`, databasen skapades med `database.sql`, `webshop.war` kopierades till Tomcat och flödena ovan verifierades med HTTP-anrop och cookie-sessioner.

## Klassdiagram

Klassdiagram finns i:

```text
CLASS_DIAGRAM.md
```

Diagrammet visar presentation layer, business layer, data layer och model-klasserna.
