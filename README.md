# parcial-patrones-java
Parcial 3 - Ingeniería de Software II: Patrones de Diseño en Java
# Sistema de Notificaciones Multicanal

Aplicación desarrollada en Java que simula el envío de notificaciones a través de múltiples canales (Correo electrónico, SMS y Mensajería instantánea), aplicando patrones de diseño clásicos y principios SOLID para lograr un núcleo de negocio desacoplado, extensible y reutilizable desde distintas interfaces de usuario.

---

## 📋 Funcionalidades Implementadas

* **Gestión de Usuarios y Preferencias:** Registro de usuarios junto con la configuración de los canales de comunicación que tienen habilitados.
* **Canales de Notificación Simulados:**
  * **Correo (`CORREO`):** Envío estándar de mensajes por correo electrónico.
  * **SMS (`SMS`):** Envío de mensajes de texto con validación estricta de longitud máxima (160 caracteres).
  * **Mensajería (`MENSAJERIA`):** Envío mediante plataforma de mensajería instantánea.
* **Estrategias de Envío Configurables:**
  * **Informativo:** Envío estándar a través del canal seleccionado.
  * **Urgente:** Difusión prioritaria a través de todos los canales permitidos del usuario.
  * **Con Reintentos:** Tolerancia a fallos mediante múltiples intentos de despacho ante errores simulados.
* **Historial y Auditoría de Envíos:** Registro automático de notificaciones exitosas y fallidas con marca de tiempo.
* **Ejecución Multi-interfaz:** Soporte para operar tanto por línea de comandos (`AppConsola`) como mediante interfaz gráfica (`AppVentana`) compartiendo el mismo gestor central.

---

## 🏗️ Arquitectura y Patrones de Diseño

El proyecto separa estrictamente la lógica de negocio de las capas de presentación mediante los siguientes paquetes y patrones:

* **Factory Method (`factory`):** `CanalFactory` centraliza la creación de instancias concretas (`CorreoCanal`, `SmsCanal`, `MensajeriaCanal`) bajo la interfaz común `CanalNotificacion`.
* **Strategy (`strategy`):** `GestorNotificaciones` delega la política de entrega a la interfaz `EstrategiaEnvio`, permitiendo intercambiar dinámicamente entre `EnvioInformativoStrategy`, `EnvioUrgenteStrategy` y `EnvioConReintentoStrategy`.
* **Observer (`observer`):** `GestorNotificaciones` actúa como sujeto publicador y notifica el resultado de cada envío a los suscriptores de `ObservadorEnvio` (implementado por `HistorialEnvioObserver`).

### Estructura de Paquetes

```text
src/
├── main/java/
│   ├── model/       # Usuario, TipoCanal
│   ├── factory/     # CanalNotificacion, CorreoCanal, SmsCanal, MensajeriaCanal, CanalFactory
│   ├── strategy/    # EstrategiaEnvio, EnvioInformativoStrategy, EnvioUrgenteStrategy, EnvioConReintentoStrategy
│   ├── observer/    # ObservadorEnvio, HistorialEnvioObserver
│   ├── service/     # GestorNotificaciones
│   └── ui/
│       ├── console/ # AppConsola
│       └── gui/     # AppVentana
└── test/java/       # Pruebas unitarias con JUnit
```

---

## 🚀 Compilación y Ejecución con Maven

Asegúrate de tener instalados **Java (JDK 11 o superior)** y **Apache Maven**.

### 1. Ejecutar pruebas unitarias
```bash
mvn test
```

### 2. Compilar el proyecto
```bash
mvn compile
```

### 3. Ejecutar la aplicación en modo Consola
```bash
mvn exec:java -Dexec.mainClass="ui.console.AppConsola"
```

### 4. Ejecutar la aplicación en modo Interfaz Gráfica (Ventana)
```bash
mvn exec:java -Dexec.mainClass="ui.gui.AppVentana"
```

*(Nota: Si tus paquetes tienen un prefijo como `com.proyecto.ui.console.AppConsola`, ajusta el parámetro `-Dexec.mainClass` según corresponda en tu `pom.xml`).*

---

## 🤖 Declaración de Uso de Herramientas

Durante el desarrollo de este proyecto se utilizaron herramientas de asistencia basadas en inteligencia artificial como apoyo para la estructuración de la documentación, revisión de redacción y validación de buenas prácticas de diseño. Toda la arquitectura, implementación del código fuente, diagramas UML y pruebas unitarias fueron diseñados, verificados y adaptados por los integrantes del equipo.
