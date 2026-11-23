package com.parcial.notificaciones.observer;

public interface ObservadorEnvio {
    void alFinalizarEnvio(boolean exito, String detalle);
}