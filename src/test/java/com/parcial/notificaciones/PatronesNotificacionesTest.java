package com.parcial.notificaciones;

import com.parcial.notificaciones.factory.*;
import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;
import com.parcial.notificaciones.observer.HistorialEnvioObserver;
import com.parcial.notificaciones.service.GestorNotificaciones;
import com.parcial.notificaciones.strategy.EnvioConReintentoStrategy;
import com.parcial.notificaciones.strategy.EnvioInformativoStrategy;
import com.parcial.notificaciones.strategy.EnvioUrgenteStrategy;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class PatronesNotificacionesTest {

    @Test
    void factoryCreaCanalesCorrectos() {
        CanalFactory factory = new CanalFactory();
        assertInstanceOf(CorreoCanal.class, factory.crearCanal(TipoCanal.CORREO));
        assertInstanceOf(SmsCanal.class, factory.crearCanal(TipoCanal.SMS));
        assertInstanceOf(MensajeriaCanal.class, factory.crearCanal(TipoCanal.MENSAJERIA));
    }

    @Test
    void strategyInformativoRespetaPreferenciasUsuario() {
        CanalFactory factory = new CanalFactory();
        Usuario soloCorreo = new Usuario("Juan", "juan@mail.com", EnumSet.of(TipoCanal.CORREO));
        EnvioInformativoStrategy estrategia = new EnvioInformativoStrategy();

        assertTrue(estrategia.ejecutarEnvio(soloCorreo, TipoCanal.CORREO, "Hola", factory));
        assertFalse(estrategia.ejecutarEnvio(soloCorreo, TipoCanal.SMS, "Hola", factory));
    }

    @Test
    void strategyUrgenteEnviaSiTieneAlMenosUnCanalValido() {
        CanalFactory factory = new CanalFactory();
        Usuario soloChat = new Usuario("Sofía", "3110000000", EnumSet.of(TipoCanal.MENSAJERIA));
        EnvioUrgenteStrategy urgente = new EnvioUrgenteStrategy();

        assertTrue(urgente.ejecutarEnvio(soloChat, TipoCanal.CORREO, "Alerta crítica", factory));
    }

    @Test
    void strategyConReintentoFallaSiSmsExcede160Caracteres() {
        CanalFactory factory = new CanalFactory();
        Usuario usuarioSms = new Usuario("Pedro", "3009998877", EnumSet.of(TipoCanal.SMS));
        EnvioConReintentoStrategy reintento = new EnvioConReintentoStrategy(3);
        String mensajeLargo = "A".repeat(170);

        assertFalse(reintento.ejecutarEnvio(usuarioSms, TipoCanal.SMS, mensajeLargo, factory));
    }

    @Test
    void observerRegistraEnviadosYFallidosAutomaticamente() {
        GestorNotificaciones gestor = new GestorNotificaciones(new CanalFactory());
        HistorialEnvioObserver observer = new HistorialEnvioObserver();
        gestor.agregarObservador(observer);

        Usuario ana = new Usuario("Ana", "ana@mail.com", EnumSet.of(TipoCanal.CORREO));
        gestor.enviarNotificacion(ana, TipoCanal.CORREO, "Bienvenido", new EnvioInformativoStrategy());
        gestor.enviarNotificacion(ana, TipoCanal.SMS, "No permitido", new EnvioInformativoStrategy());

        assertEquals(1, observer.getEnviados().size());
        assertEquals(1, observer.getFallidos().size());
    }
}