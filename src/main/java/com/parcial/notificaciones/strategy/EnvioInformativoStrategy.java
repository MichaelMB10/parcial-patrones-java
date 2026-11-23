package com.parcial.notificaciones.strategy;

import com.parcial.notificaciones.factory.CanalFactory;
import com.parcial.notificaciones.factory.CanalNotificacion;
import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;

public class EnvioInformativoStrategy implements EstrategiaEnvio {
    @Override
    public String getNombreEstrategia() { return "Informativo (1 canal)"; }

    @Override
    public boolean ejecutarEnvio(Usuario usuario, TipoCanal canalPreferido, String mensaje, CanalFactory factory) {
        CanalNotificacion canal = factory.crearCanal(canalPreferido);
        return canal.enviar(usuario, mensaje);
    }
}