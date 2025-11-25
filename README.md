# 💰 Proyecto TuBolsillo

Se trata de una aplicación web llamada **TuBolsillo**, la cual permite al usuario registrar sus movimientos bancarios y crear sus propias categorías personalizadas.

La idea de este proyecto surge de la necesidad de monitorear mis propios movimientos bancarios así como para practicar todo un stack tecnológico.

El proyecto consta principalmente de tres partes:

- **Base de datos:** Usando MySQL como SGBD.

- **Servicio Redis:** Servicio de Redis para una blacklist de tokens de acceso.

- **Backend:** Desarrollado con el framework Spring Boot 3.5.7 para Java 21.

- **Frontend:** Desarrollado con Angular 20.

Además, para el despliegue y portabilidad de la aplicación estoy usando Docker y Docker compose.

## 📁 Estructura del proyecto

La estructura general del proyecto es la siguiente:

```
/tu_bolsillo_project (Carpeta Principal)
├── /backend (Proyecto Spring Boot)
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile (Para el backend)
├── /frontend (Proyecto Angular)
│   ├── src/
│   ├── package.json
│   └── Dockerfile (Para el frontend)
├── /docker-config (Para docker)
│   └── /mysql
│       └── init.sql
├── docker-compose.yml (Archivo central de orquestación)
└── README.md (Documentación del proyecto)
```
