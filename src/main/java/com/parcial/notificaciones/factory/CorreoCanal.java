package com.parcial.notificaciones.factory;

import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;

public class CorreoCanal implements CanalNotificacion {
    @Override
    public TipoCanal getTipo() { return TipoCanal.CORREO; }

    @Override
    public String formatearMensaje(Usuario destino, String mensaje) {
        return "[EMAIL a " + destino.getContacto() + "] Asunto: Notificación | Cuerpo: " + mensaje;
    }

    @Override
    public boolean enviar(Usuario destino, String mensaje) {
        return destino.aceptaCanal(TipoCanal.CORREO) && mensaje != null && !mensaje.isBlank();
    }
}