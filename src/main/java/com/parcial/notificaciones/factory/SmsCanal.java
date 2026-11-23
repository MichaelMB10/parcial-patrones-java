package com.parcial.notificaciones.factory;

import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;

public class SmsCanal implements CanalNotificacion {
    @Override
    public TipoCanal getTipo() { return TipoCanal.SMS; }

    @Override
    public String formatearMensaje(Usuario destino, String mensaje) {
        return "[SMS a " + destino.getContacto() + "] " + mensaje;
    }

    @Override
    public boolean enviar(Usuario destino, String mensaje) {
        return destino.aceptaCanal(TipoCanal.SMS) && mensaje != null && !mensaje.isBlank() && mensaje.length() <= 160;
    }
}