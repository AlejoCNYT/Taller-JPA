# Accessing Data with JPA — Spring Boot (International README)

> **ES / Español** y **EN / English** en el mismo archivo.  
> Proyecto mínimo con **Spring Data JPA** + **H2 (en memoria)** siguiendo el tutorial oficial de Spring (“Accessing Data with JPA”), con ajustes para pruebas limpias.

---

## 🧭 Overview / Resumen

**EN** — This project is a minimal Spring Boot app that persists `Customer` entities using **Spring Data JPA** and an in‑memory **H2** database. On startup (non‑test profiles), a `CommandLineRunner` seeds five sample customers and logs basic queries (`findAll`, `findById`, `findByLastName`). Tests run with a clean schema and no seed data.

**ES** — Este proyecto es una app mínima de Spring Boot que persiste entidades `Customer` con **Spring Data JPA** y **H2** en memoria. Al iniciar (perfiles que no sean de test), un `CommandLineRunner` inserta cinco clientes de ejemplo y registra consultas básicas (`findAll`, `findById`, `findByLastName`). Las pruebas se ejecutan con un esquema limpio y sin datos sembrados.

---

## ⚙️ Requirements / Requisitos

- **Java 17+**
- **Maven Wrapper** (incluido: `mvnw` / `mvnw.cmd`)
- (Opcional) **Gradle Wrapper** si inicializaste con Gradle
- IDE recomendado: IntelliJ IDEA / VS Code / STS (o tu favorito)

> Verifica tu versión de Java:
> ```bash
> java -version
> ```

---
<img width="802" height="736" alt="Screenshot 2025-09-17 105405" src="https://github.com/user-attachments/assets/1fcc5beb-eb9d-4c2a-a3c9-46e2965c1d7c" />
<img width="755" height="231" alt="Screenshot 2025-09-17 105409" src="https://github.com/user-attachments/assets/a3f3d95a-3d73-4bac-9408-06399d4882ee" />

## 🧩 Tech Stack

- **Spring Boot 3.x**
- **Spring Data JPA (Hibernate 6)**
- **H2** in‑memory database
- **JUnit 5** + **Spring Test**

---

## 📁 Project Structure / Estructura

```
accessing-data-jpa/
├─ pom.xml
├─ src/
│  ├─ main/
│  │  ├─ java/com/example/accessing_data_jpa/
│  │  │  ├─ AccessingDataJpaApplication.java
│  │  │  ├─ Customer.java
│  │  │  └─ CustomerRepository.java
│  │  └─ resources/
│  │     └─ application.properties
│  └─ test/
│     ├─ java/com/example/accessing_data_jpa/
│     │  └─ CustomerRepositoryTest.java
│     └─ resources/
│        └─ application-test.properties   (opcional recomendado)
```

> Asegúrate de que **TODAS** las clases Java usan el **mismo paquete** (`com.example.accessing_data_jpa` o el que elegiste).

---

## 🚀 Run (Dev) / Ejecutar (Desarrollo)

### Maven — Spring Boot Run
**Windows (PowerShell)**
```powershell
.\mvnw.cmd spring-boot:run
```
**macOS / Linux**
```bash
./mvnw spring-boot:run
```

### Package & Run JAR / Empaquetar y correr JAR
**Windows**
```powershell
.\mvnw.cmd -q clean package
java -jar target\accessing-data-jpa-0.0.1-SNAPSHOT.jar
```
**macOS / Linux**
```bash
./mvnw -q clean package
java -jar target/accessing-data-jpa-0.0.1-SNAPSHOT.jar
```
<img width="1768" height="853" alt="Screenshot 2025-09-17 105747" src="https://github.com/user-attachments/assets/3b530cf7-0f3c-4c9c-8911-7471667f3464" />

> Al iniciar, verás en consola los `Customer` sembrados y el resultado de las consultas.

---

## 🗄️ H2 Console (Opcional)

Con la aplicación corriendo, abre:  
**http://localhost:8080/h2-console**

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User:** `sa`
- **Password:** *(vacío)*

> Si no abre, verifica en `src/main/resources/application.properties` que `spring.h2.console.enabled=true` y `spring.h2.console.path=/h2-console`.

---

## 🧪 Testing

Este proyecto está configurado para que **los tests no siembren datos**. Hay dos mecanismos:

1) **`CommandLineRunner` desactivado en tests**  
   En `AccessingDataJpaApplication.java` el bean está anotado con `@Profile("!test")`.
2) **Perfil `test` activo** en los tests (p. ej., con `@ActiveProfiles("test")`) y/o limpieza explícita:

`src/test/resources/application-test.properties` (recomendado):
```properties
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
logging.level.org.hibernate.SQL=OFF
```

Ejecutar pruebas manualmente:
```bash
# Windows
.\mvnw.cmd -q test
# macOS/Linux
./mvnw -q test
```

> Si tus tests insertan datos y validan tamaños, evita que el runner de producción los afecte.

---

## 🧾 Configuration / Configuración

`src/main/resources/application.properties` (ejemplo):
```properties
# Mostrar SQL en logs (opcional)
spring.jpa.show-sql=true

# Crear/actualizar esquema en memoria
spring.jpa.hibernate.ddl-auto=update

# H2 en memoria
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Consola H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

`src/test/resources/application-test.properties` (opcional recomendado):
```properties
# DB limpia en cada run de pruebas
spring.jpa.hibernate.ddl-auto=create-drop

# Reducir ruido en logs de pruebas
spring.jpa.show-sql=false
logging.level.org.hibernate.SQL=OFF
```

---

## 🔁 Profiles (Perfiles)

- **default**: comportamiento normal; el `CommandLineRunner` **sí** siembra 5 `Customer`.
- **test**: el `CommandLineRunner` **NO** se ejecuta por `@Profile("!test")`. DB limpia con `create-drop` si configuras `application-test.properties` como arriba.

---

## 🧪 Sample Test / Test de ejemplo

`CustomerRepositoryTest` verifica operaciones básicas (`save`, `findByLastName`, `findById`), manejando el `Optional` que devuelve `CrudRepository#findById`:

```java
@DataJpaTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository repository;

  @BeforeEach
  void clean() {
    repository.deleteAll();
  }

  @Test
  void saveAndQuery() {
    repository.save(new Customer("Jack", "Bauer"));
    repository.save(new Customer("Kim", "Bauer"));

    List<Customer> bauers = repository.findByLastName("Bauer");
    assertThat(bauers).hasSize(2);

    Optional<Customer> one = repository.findById(bauers.get(0).getId());
    assertThat(one).isPresent();
    assertThat(one.get().getLastName()).isEqualTo("Bauer");
  }
}
```

---

## 🧰 Common Issues / Problemas comunes

- **`java.lang.UnsupportedClassVersionError`**: actualiza a **Java 17+**.
- **Puerto 8080 en uso**: cambia `server.port` en `application.properties` o cierra el proceso que lo usa.
- **Maven no reconocido**: usa el **wrapper** (`./mvnw` o `.\mvnw.cmd`), no necesitas instalar Maven global.
- **Aserciones fallan por “más registros de los esperados”**: confirma que el test usa `@ActiveProfiles("test")` y/o borra con `repository.deleteAll()` al inicio.
- **Consola H2 no abre**: habilita `spring.h2.console.enabled=true` y revisa la URL `/h2-console`.

---

## 🪄 Gradle (si iniciaste con Gradle)

**Run:**
```bash
# Windows
.\gradlew.bat bootRun
# macOS/Linux
./gradlew bootRun
```

**Package & Run JAR:**
```bash
# Windows
.\gradlew.bat build
java -jar build\libs\accessing-data-jpa-0.0.1-SNAPSHOT.jar

# macOS/Linux
./gradlew build
java -jar build/libs/accessing-data-jpa-0.0.1-SNAPSHOT.jar
```

---

## ⬆️ Publish to GitHub / Subir a GitHub

```bash
git init
git add .
git commit -m "JPA lab: Customer + repository + tests"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo>.git
git push -u origin main
```

---

## 📖 Credits / Créditos

Basado en el tutorial oficial **“Accessing Data with JPA”** de Spring.  
Código bajo licencia **ASLv2**.
