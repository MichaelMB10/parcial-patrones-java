package com.parcial.notificaciones.strategy;

import com.parcial.notificaciones.factory.CanalFactory;
import com.parcial.notificaciones.factory.CanalNotificacion;
import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;

public class EnvioConReintentoStrategy implements EstrategiaEnvio {
    private final int maxIntentos;

    public EnvioConReintentoStrategy(int maxIntentos) {
        this.maxIntentos = Math.max(1, maxIntentos);
    }

    @Override
    public String getNombreEstrategia() { return "Con Reintentos (máx. " + maxIntentos + ")"; }

    @Override
    public boolean ejecutarEnvio(Usuario usuario, TipoCanal canalPreferido, String mensaje, CanalFactory factory) {
        CanalNotificacion canal = factory.crearCanal(canalPreferido);
        for (int i = 1; i <= maxIntentos; i++) {
            if (canal.enviar(usuario, mensaje)) {
                return true;
            }
        }
        return false;
    }
}