package com.parcial.notificaciones.factory;

import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;

public interface CanalNotificacion {
    TipoCanal getTipo();
    String formatearMensaje(Usuario destino, String mensaje);
    boolean enviar(Usuario destino, String mensaje);
}