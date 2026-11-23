package com.parcial.notificaciones.strategy;

import com.parcial.notificaciones.factory.CanalFactory;
import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;

public interface EstrategiaEnvio {
    String getNombreEstrategia();
    boolean ejecutarEnvio(Usuario usuario, TipoCanal canalPreferido, String mensaje, CanalFactory factory);
}