package com.parcial.notificaciones.factory;

import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;

public class MensajeriaCanal implements CanalNotificacion {
    @Override
    public TipoCanal getTipo() { return TipoCanal.MENSAJERIA; }

    @Override
    public String formatearMensaje(Usuario destino, String mensaje) {
        return "[CHAT/APP @" + destino.getNombre() + "] " + mensaje;
    }

    @Override
    public boolean enviar(Usuario destino, String mensaje) {
        return destino.aceptaCanal(TipoCanal.MENSAJERIA) && mensaje != null && !mensaje.isBlank();
    }
}