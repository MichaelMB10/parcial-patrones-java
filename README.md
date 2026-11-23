# parcial-patrones-java
Parcial 3 - Ingeniería de Software II: Patrones de Diseño en Java

# Sistema de Notificaciones Multicanal

Aplicación desarrollada en Java que simula el envío de notificaciones a través de múltiples canales (Correo electrónico, SMS y Mensajería instantánea), aplicando patrones de diseño clásicos y principios SOLID para lograr un núcleo de negocio desacoplado, extensible y reutilizable desde distintas interfaces de usuario.

---

## Funcionalidades Implementadas

- **Gestión de Usuarios y Preferencias:** Registro de usuarios junto con la configuración de los canales de comunicación que tienen habilitados.
- **Canales de Notificación Simulados:**
  - **Correo (`CORREO`):** Envío estándar de mensajes por correo electrónico.
  - **SMS (`SMS`):** Envío de mensajes de texto con validación estricta de longitud máxima (160 caracteres).
  - **Mensajería (`MENSAJERIA`):** Envío mediante plataforma de mensajería instantánea.
- **Estrategias de Envío Configurables:**
  - **Informativo:** Envío estándar a través del canal seleccionado.
  - **Urgente:** Difusión prioritaria a través de todos los canales permitidos del usuario.
  - **Con Reintentos:** Tolerancia a fallos mediante múltiples intentos de despacho ante errores simulados.
- **Historial y Auditoría de Envíos:** Registro automático de notificaciones exitosas y fallidas con marca de tiempo.
- **Ejecución Multi-interfaz:** Soporte para operar tanto por línea de comandos (`AppConsola`) como mediante interfaz gráfica (`AppVentana`) compartiendo el mismo gestor central.

---

## Arquitectura y Patrones de Diseño

El proyecto separa estrictamente la lógica de negocio de las capas de presentación mediante los siguientes paquetes y patrones:

- **Factory Method (`factory`):** `CanalFactory` centraliza la creación de instancias concretas (`CorreoCanal`, `SmsCanal`, `MensajeriaCanal`) bajo la interfaz común `CanalNotificacion`.
- **Strategy (`strategy`):** `GestorNotificaciones` delega la política de entrega a la interfaz `EstrategiaEnvio`, permitiendo intercambiar dinámicamente entre `EnvioInformativoStrategy`, `EnvioUrgenteStrategy` y `EnvioConReintentoStrategy`.
- **Observer (`observer`):** `GestorNotificaciones` actúa como sujeto publicador y notifica el resultado de cada envío a los suscriptores de `ObservadorEnvio` (implementado por `HistorialEnvioObserver`).

### Estructura de Paquetes

```text
src/
├── main/java/com/parcial/notificaciones/
│   ├── model/       # Usuario, TipoCanal
│   ├── factory/     # CanalNotificacion, CorreoCanal, SmsCanal, MensajeriaCanal, CanalFactory
│   ├── strategy/    # EstrategiaEnvio, EnvioInformativoStrategy, EnvioUrgenteStrategy, EnvioConReintentoStrategy
│   ├── observer/    # ObservadorEnvio, HistorialEnvioObserver
│   ├── service/     # GestorNotificaciones
│   └── ui/
│       ├── console/ # AppConsola
│       └── gui/     # AppVentana
└── test/java/com/parcial/notificaciones/
    └── PatronesNotificacionesTest.java  # Pruebas unitarias con JUnit 5
