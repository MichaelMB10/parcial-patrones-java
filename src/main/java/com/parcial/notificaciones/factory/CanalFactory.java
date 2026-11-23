package com.parcial.notificaciones.factory;

import com.parcial.notificaciones.model.TipoCanal;

public class CanalFactory {
    public CanalNotificacion crearCanal(TipoCanal tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de canal no puede ser nulo");
        }
        return switch (tipo) {
            case CORREO -> new CorreoCanal();
            case SMS -> new SmsCanal();
            case MENSAJERIA -> new MensajeriaCanal();
        };
    }
}