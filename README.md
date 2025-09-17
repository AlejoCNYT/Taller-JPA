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

<img width="802" height="736" alt="Screenshot 2025-09-17 105405" src="https://github.com/user-attachments/assets/c71f3dd1-b98b-4011-a226-82b374d247cb" />
<img width="755" height="231" alt="Screenshot 2025-09-17 105409" src="https://github.com/user-attachments/assets/9156a52f-e1bd-4a74-abc1-61ebcbce0d38" />

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

## 📖 Credits / Créditos

Basado en el tutorial oficial **“Accessing Data with JPA”** de Spring.  
Código bajo licencia **ASLv2**.
