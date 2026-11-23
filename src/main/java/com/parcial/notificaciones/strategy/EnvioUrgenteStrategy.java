package com.parcial.notificaciones.strategy;

import com.parcial.notificaciones.factory.CanalFactory;
import com.parcial.notificaciones.factory.CanalNotificacion;
import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;

public class EnvioUrgenteStrategy implements EstrategiaEnvio {
    @Override
    public String getNombreEstrategia() { return "Urgente (todos los canales aceptados)"; }

    @Override
    public boolean ejecutarEnvio(Usuario usuario, TipoCanal canalPreferido, String mensaje, CanalFactory factory) {
        boolean algunExito = false;
        for (TipoCanal tipo : TipoCanal.values()) {
            CanalNotificacion canal = factory.crearCanal(tipo);
            if (canal.enviar(usuario, "[URGENTE] " + mensaje)) {
                algunExito = true;
            }
        }
        return algunExito;
    }
}