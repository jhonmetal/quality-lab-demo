# quality-lab-demo

Plantilla para laboratorios de calidad de software (Spring Boot) — **Versión para curso de Maestría**

## Resumen
Este proyecto contiene una aplicación web simple en Java + Spring Boot con ejemplos y ejercicios listos para practicar herramientas de calidad: JUnit/JaCoCo, SonarQube, GitHub Actions, Selenium, Postman/Newman, JMeter y OWASP ZAP (opcional).

La configuración está preparada para que funcione **sin Docker** (recomendado para estudiantes que no lo tengan), y también incluye archivos `docker/` opcionales para quienes sí usan Docker.

---

## Estructura importante
- `backend/` — aplicación Spring Boot (código fuente y tests)
- `tests/postman/` — colección Postman (JSON) para Newman
- `tests/jmeter/` — archivos JMeter: `load-10.jmx`, `load-100.jmx`, `load-500.jmx`
- `docker/` — *opcional*: `docker-compose.yml` para SonarQube y ZAP
- `solutions/` — versión con ejercicios resueltos para el docente
- `.github/workflows/ci.yml` — workflow de CI (build, tests, Newman)

---

## Requisitos mínimos (sin Docker)
- Java 17 (JDK)
- Maven 3.8+
- Chrome y chromedriver (opcional para Selenium UI tests)
- Node.js + npm (opcional para Newman)
- JMeter (opcional para pruebas de carga)

## Ejecución local (sin Docker) — paso a paso

### 1) Ejecutar la aplicación
```bash
cd backend
mvn spring-boot:run
# Abre http://localhost:8080
```

### 2) Ejecutar tests unitarios y generar reporte JaCoCo
```bash
cd backend
mvn clean verify
# Reporte JaCoCo: backend/target/site/jacoco/index.html
```
> Nota: el build fallará si la cobertura de instrucciones es menor al 80% (JaCoCo está configurado para esto).

### 3) Ejecutar la colección Postman con Newman (opcional)
```bash
npm install -g newman
newman run ../tests/postman/QualityLab.postman_collection.json
```

### 4) Ejecutar tests Selenium (opcional)
Asegúrate de que Chromedriver esté en el PATH y que la app esté corriendo:
```bash
cd backend
mvn -Dtest=com.example.qualitydemo.SeleniumRegistrationTest test
```

### 5) Ejecutar JMeter (opcional)
Abre JMeter GUI o corre en modo no gráfico:
```bash
jmeter -n -t tests/jmeter/load-10.jmx -l results-10.jtl
jmeter -n -t tests/jmeter/load-100.jmx -l results-100.jtl
jmeter -n -t tests/jmeter/load-500.jmx -l results-500.jtl
```

### 6) Escanear con OWASP ZAP (opcional, con Docker recomendado)
Si tienes Docker, puedes levantar ZAP fácilmente (ver docker/docker-compose.yml) o usar el contenedor directo:
```bash
docker run -t owasp/zap2docker-stable zap-baseline.py -t http://host.docker.internal:8080 -r zap-report.html
```

---

## Uso opcional con Docker (si el estudiante sí tiene Docker)
En la carpeta `docker/` hay un `docker-compose.yml` para levantar SonarQube y ZAP (no incluye la app). Para usar Sonar con la app local:
```bash
cd docker
docker-compose up -d
# SonarQube disponible en http://localhost:9000
```

---

## Guía para crear el repositorio en GitHub (profesor o líder de curso)
```bash
# desde la carpeta raíz (donde está README.md)
git init
git add .
git commit -m "Initial Quality Lab Demo"
# crea un repo público 'quality-lab-demo' en GitHub y agrega remote
git remote add origin git@github.com:<tu-usuario>/quality-lab-demo.git
git branch -M main
git push -u origin main
```

## Cómo pedir a los estudiantes que suban sus prácticas (Forks y PRs)
1. El estudiante hace **Fork** del repo `quality-lab-demo` a su cuenta de GitHub.  
2. Clona su fork:
```bash
git clone git@github.com:<estudiante>/quality-lab-demo.git
cd quality-lab-demo
```
3. Crea una rama para su trabajo:
```bash
git checkout -b practica/<nombre-grupo>-ejercicio1
```
4. Hacen cambios, añaden resultados (screenshots, reports) y commitean:
```bash
git add .
git commit -m "Practica 1: tests unitarios y reporte JaCoCo"
git push origin practica/<nombre-grupo>-ejercicio1
```
5. En la página del fork, hacen **Compare & pull request** hacia `origin/main` del repo original.
6. Revisión: el docente revisa el PR, ejecuta CI (Actions) y deja comentarios. Se evalúa según reproducibilidad, evidencia y calidad del reporte.

Buenas prácticas que recomiendes a tus estudiantes:
- Nombre de rama: `practica/<grupo>-<tema>` (ej. `practica/grupo3-junit`)
- Mensajes de commit claros y cortos.
- Añadir un `lab-report.md` con pasos para reproducir y evidencias (screenshots, links a reports).

---

## Carpeta `solutions/` (solo para docentes)
Incluye alternativas resueltas: tests adicionales, casos edge, y un `teacher-notes.md` con respuestas y criterios de evaluación.

---

## Soporte y personalización
Si quieres, puedo:
- Añadir más endpoints vulnerables para prácticas de seguridad (XSS, CSRF)
- Preparar un script que genere automáticamente issues/PR templates para evaluación
- Crear plantillas de `lab-report.md` para que tus estudiantes las completen

