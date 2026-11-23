package com.parcial.notificaciones.ui.console;

import com.parcial.notificaciones.factory.CanalFactory;
import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;
import com.parcial.notificaciones.observer.HistorialEnvioObserver;
import com.parcial.notificaciones.service.GestorNotificaciones;
import com.parcial.notificaciones.strategy.EnvioConReintentoStrategy;
import com.parcial.notificaciones.strategy.EnvioInformativoStrategy;
import com.parcial.notificaciones.strategy.EnvioUrgenteStrategy;
import com.parcial.notificaciones.strategy.EstrategiaEnvio;

import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AppConsola {
    public static void main(String[] args) {
        GestorNotificaciones gestor = new GestorNotificaciones(new CanalFactory());
        HistorialEnvioObserver historial = new HistorialEnvioObserver();
        gestor.agregarObservador(historial);

        gestor.registrarUsuario(new Usuario("Ana", "ana@correo.com", EnumSet.of(TipoCanal.CORREO, TipoCanal.SMS)));
        gestor.registrarUsuario(new Usuario("Carlos", "3001234567", EnumSet.of(TipoCanal.MENSAJERIA)));

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== SISTEMA DE NOTIFICACIONES MULTICANAL (CONSOLA) ===");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Enviar notificación");
            System.out.println("3. Ver historial (enviados y fallidos)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> {
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Contacto: ");
                    String contacto = sc.nextLine();
                    Set<TipoCanal> canales = EnumSet.noneOf(TipoCanal.class);
                    System.out.print("¿Acepta CORREO? (s/n): ");
                    if (sc.nextLine().equalsIgnoreCase("s")) canales.add(TipoCanal.CORREO);
                    System.out.print("¿Acepta SMS? (s/n): ");
                    if (sc.nextLine().equalsIgnoreCase("s")) canales.add(TipoCanal.SMS);
                    System.out.print("¿Acepta MENSAJERIA? (s/n): ");
                    if (sc.nextLine().equalsIgnoreCase("s")) canales.add(TipoCanal.MENSAJERIA);
                    gestor.registrarUsuario(new Usuario(nombre, contacto, canales));
                    System.out.println("Usuario registrado.");
                }
                case 2 -> {
                    List<Usuario> usuarios = gestor.getUsuarios();
                    for (int i = 0; i < usuarios.size(); i++) {
                        System.out.println(i + ". " + usuarios.get(i));
                    }
                    System.out.print("Índice del usuario: ");
                    Usuario u = usuarios.get(Integer.parseInt(sc.nextLine()));

                    System.out.print("Canal (1=CORREO, 2=SMS, 3=MENSAJERIA): ");
                    TipoCanal canal = switch (Integer.parseInt(sc.nextLine())) {
                        case 1 -> TipoCanal.CORREO;
                        case 2 -> TipoCanal.SMS;
                        default -> TipoCanal.MENSAJERIA;
                    };

                    System.out.print("Estrategia (1=Informativo, 2=Urgente, 3=Con Reintentos): ");
                    EstrategiaEnvio estrategia = switch (Integer.parseInt(sc.nextLine())) {
                        case 2 -> new EnvioUrgenteStrategy();
                        case 3 -> new EnvioConReintentoStrategy(3);
                        default -> new EnvioInformativoStrategy();
                    };

                    System.out.print("Mensaje: ");
                    String msg = sc.nextLine();

                    boolean ok = gestor.enviarNotificacion(u, canal, msg, estrategia);
                    System.out.println(ok ? ">>> Notificación enviada con éxito." : ">>> Falló el envío.");
                }
                case 3 -> {
                    System.out.println("\n--- ENVIADOS ---");
                    historial.getEnviados().forEach(System.out::println);
                    System.out.println("\n--- FALLIDOS ---");
                    historial.getFallidos().forEach(System.out::println);
                }
            }
        } while (opcion != 0);
    }
}