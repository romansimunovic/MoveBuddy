### Kako dodati datoteku u projekt:

U mapi `backend/backend` kreiraj novu datoteku pod nazivom `README.md` i u nju kopiraj sav sadržaj iz koda ispod.

```markdown
# MoveBuddy Backend 🚀

Ovo je backend dio **MoveBuddy** aplikacije, izgrađen pomoću **Spring Boot** (v3.5.15) i **Java 17** tehnologija. Sustav koristi **PostgreSQL** bazu podataka i osiguran je pomoću **Spring Security** i **JWT (JSON Web Token)** mehanizama autorizacije.

---

## 🛠️ Preduvjeti

Prije nego što pokreneš aplikaciju, provjeri imaš li instalirano sljedeće na svom računalu:
* **Java Development Kit (JDK) 17**
* **PostgreSQL** baza podataka (i pokrenut lokalni server)
* **Maven** (ugrađen kroz `./mvnw` wrapper unutar projekta)

---

## 🔒 Lokalna Konfiguracija (Sigurnost na prvom mjestu)

Zbog sigurnosnih razloga, tajni ključevi, lozinke baze i osjetljivi podaci **nisu** dio repozitorija i nalaze se u `.gitignore`. 

Za lokalni razvoj koristi se profil `local`. Unutar mape `backend/backend/src/main/resources/` kreiraj datoteku:
📄 `application-local.properties`

Unutar nje definiraj svoje lokalne varijable (prilagodi podatke za bazu i JWT ključ):

```properties
# Konfiguracija PostgreSQL baze podataka
spring.datasource.url=jdbc:postgresql://localhost:5123/movebuddy
spring.datasource.username=tvoj_username
spring.datasource.password=tvoja_lozinka
spring.jpa.hibernate.ddl-auto=update

# JWT Sigurnost (Ključ mora imati barem 256 bita / 32+ znakova)
JWT_SECRET_KEY=mojasupertajnaisigurnasifrazamovebuddyaplikaciju1234567890abcdef

```

> ⚠️ **Važno:** Nikada nemoj dodavati `application-local.properties` ili `.env` datoteke na GitHub repozitorij!

---

## 🏃‍♂️ Pokretanje Aplikacije

Aplikaciju možeš pokrenuti na dva načina, ovisno o tome što ti više odgovara u tom trenutku:

### Opcija 1: Putem PowerShell terminala (Preporučeno)

Otvori terminal, pozicioniraj se u mapu `backend/backend` i pokreni aplikaciju uz eksplicitno aktiviranje lokalnog profila pod navodnicima:

```powershell
./mvnw spring-boot:run "-Dspring-boot.run.profiles=local"

```

### Opcija 2: Putem VS Code sučelja (Run and Debug)

Ako želiš koristiti debugiranje unutar VS Code-a:

1. U VS Code-u idi na `File -> Open Folder` i otvori isključivo mapu `backend/backend`.
2. Pritisni tipku **`F5`** ili klikni na ikonu bube s trokutićem (*Run and Debug*) s lijeve strane.
3. VS Code će automatski učitati konfiguraciju iz `.vscode/launch.json` i podići aplikaciju s ispravnim varijablama.

Kada u terminalu vidiš poruku `Tomcat initialized with port 8080 (http)`, aplikacija uspješno radi u pozadini.

---

## 📡 Integracija s Android Emulatorom

Kada pokrećeš React Native frontend na Android emulatoru, emulator tvoje računalo ne vidi kao `localhost`, već kao vanjski server.

U frontend konfiguraciji (`axiosConfig.js` ili slično), osnovni URL za slanje zahtjeva prema ovom backendu mora biti postavljen na ugrađeni Android IP trik:

```javascript
baseURL: '[http://10.0.2.2:8080/api](http://10.0.2.2:8080/api)'