# SiteKeeper API

# **ES**

Una API para gestionar espacios, reportes y objetos diseñada utilizando SpringBoot y Spring security con una base de datos PostgreSQL. Esta aplicación permite a los usuarios crear, leer, actualizar y eliminar espacios, reportes, objetos asignados al espacio y objetos perdidos. 

## Características

### 1. **Gestión de Espacios**
La API permite a los usuarios (administradores) gestionar espacios comerciales de manera eficiente.

- **Crear Espacios:** Agregar nuevos espacios al sistema, especificando detalles como nombre, ubicación y estado.
- **Actualizar Espacios:** Modificar la información de espacios existentes, incluyendo nombre, ubicación o estado.
- **Eliminar Espacios:** Eliminar un espacio del sistema.
- **Listar Espacios:** Recuperar la lista de espacios gestionados por un usuario, filtrados por ubicación o estado.

### 2. **Gestión de Informes**
La API proporciona la capacidad de crear y gestionar informes para problemas o anomalías dentro de los espacios gestionados.

- **Crear Informes:** Los usuarios pueden enviar informes relacionados con problemas en espacios específicos, incluyendo detalles como tipo de problema y descripción.
- **Actualizar Informes:** Modificar el estado o detalles de un informe existente.
- **Eliminar Informes:** Eliminar informes del sistema una vez resueltos.
- **Listar Informes:** Recuperar todos los informes, filtrables por espacio, tipo de problema o prioridad.

### 3. **Gestión de Objetos**
Gestionar objetos dentro de los espacios, como equipos o recursos.

- **Agregar Objetos:** Crear nuevos objetos dentro de un espacio, con detalles como nombre, tipo y estado del objeto.
- **Actualizar Objetos:** Modificar los datos del objeto, como su condición o ubicación.
- **Eliminar Objetos:** Eliminar objetos del sistema si ya no forman parte del espacio.
- **Listar Objetos:** Recuperar todos los objetos asignados a un espacio específico.

### 4. **Gestión de Artículos Perdidos**
La plataforma permite a los usuarios rastrear y gestionar artículos perdidos dentro de un espacio.

- **Informar Artículos Perdidos:** Los clientes pueden informar artículos perdidos, proporcionando detalles como nombre, descripción y la fecha en que se perdió el objeto.
- **Buscar Artículos Perdidos:** Buscar artículos perdidos por nombre, tipo o la fecha en que fueron marcados como perdidos.
- **Gestionar Artículos Encontrados:** Los administradores pueden marcar artículos como encontrados y actualizar su estado en el sistema.

### Pasos para Ejecutar

1. Clona el repositorio:

   ```bash
   git clone https://github.com/Site-Keeper/Site-Keeper-SpringBoot.git
   ```

2. Navega al directorio del proyecto:

   ```bash
   cd Site-Keeper-SpringBoot
   ```

3. Actualiza la configuración de la base de datos PostgreSQL en `application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/sitekeeper_db
   spring.datasource.username=tu_usuario_db
   spring.datasource.password=tu_contraseña_db
   ```

4. Construye y ejecuta la aplicación:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. La API estará disponible en `http://localhost:8080`.



# **EN**

An API for managing spaces, reports, and objects designed using Spring Boot and Spring Security with a PostgreSQL database. This application allows users to create, read, update, and delete spaces, reports, objects assigned to spaces, and lost objects.

## Features

### **1. Spaces Management**

The API allows users (administrators) to manage business spaces efficiently.

- Create Spaces: Add new spaces to the system, specifying details such as name, location, and status.
- Update Spaces: Modify existing space information, including name, location, or status.
- Delete Spaces: Remove a space from the system.
- List Spaces: Retrieve the list of spaces managed by a user, filtered by location or status.

### 2. **Reports Management**
The API provides the ability to create and manage reports for problems or anomalies within managed spaces.

- **Create Reports:** Users can submit reports related to issues in specific spaces, including details like problem type and description.
- **Update Reports:** Modify the status or details of an existing report.
- **Delete Reports:** Remove reports from the system once resolved.
- **List Reports:** Retrieve all reports, filterable by space, issue type, or priority.

### 3. **Objects Management**
Manage objects within spaces, such as equipment or resources.

- **Add Objects:** Create new objects within a space, with details such as object name, type, and status.
- **Update Objects:** Modify object data, such as its condition or location.
- **Delete Objects:** Remove objects from the system if they are no longer part of the space.
- **List Objects:** Retrieve all objects assigned to a specific space.

### 4. **Lost Items Management**
The platform allows users to track and manage lost items within a space.

- **Report Lost Items:** Clients can report lost items, providing details like name, description, and the date the object was lost.
- **Search Lost Items:** Search for lost items by name, type, or the date they were marked as lost.
- **Manage Found Items:** Admins can mark items as found and update their status in the system.

## Technologies used

- **Backend Framework:** Spring Boot
- **Security Framework:** Spring Security
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **Java Version:** 17+

### Steps to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/Site-Keeper/Site-Keeper-SpringBoot.git
   ```

2. Navigate to the project directory:

   ```bash
   cd Site-Keeper-SpringBoot 
   ```

3. Update the PostgreSQL database configuration in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/sitekeeper_db
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   ```

4. Build and run the application:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. The API will be available at `http://localhost:8080`.

---
