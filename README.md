# Buhos-App

Buhos-App es una API REST modular diseñada para la gestión, control y reserva de asientos en salas, auditorios o 
eventos dentro del entorno universitario. El sistema permite administrar de forma eficiente la disponibilidad de 
espacios físicos, la creación de salas dinámicas y el procesamiento seguro de reservas simultáneas.

El backend está construido bajo una arquitectura limpia **N-Capas** (Controladores, Servicios, Repositorios) 
garantizando un desacoplamiento óptimo, facilidad de testing y alta escalabilidad en la nube.

## 🛠️ Stack Tecnológico

- **Core Runtime:** Java 21 & Spring Boot 3.x
- **Persistencia de Datos:** Spring Data JPA & Hibernate
- **Motor de Base de Datos:** PostgreSQL 16 (Producción/Contenedores)
- **Base de Datos en Memoria:** H2 Database (Exclusivo para entornos de Pruebas/Testing)
- **Gestor de Dependencias:** Maven
- **Contenedorización y Orquestación:** Docker & Docker Compose
- **Integración y Despliegue Continuo (CI/CD):** GitHub Actions

---

## 🧱 Arquitectura y Estructura del Proyecto

El diseño del código sigue el patrón estándar de Spring Boot para el aislamiento de responsabilidades:

```text
src/main/java/lat/buhoseats/api/
│
├── config/
├── controllers/
├── domain/
├      ├── dtos/
├      ├── entities/
├── exceptions/
├── repositories/
└── services/
├      ├── Impl/