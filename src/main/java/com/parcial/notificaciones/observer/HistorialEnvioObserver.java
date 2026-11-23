package com.parcial.notificaciones.observer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistorialEnvioObserver implements ObservadorEnvio {
    private final List<String> enviados = new ArrayList<>();
    private final List<String> fallidos = new ArrayList<>();
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void alFinalizarEnvio(boolean exito, String detalle) {
        String marcaTiempo = "[" + LocalTime.now().format(fmt) + "] " + detalle;
        if (exito) {
            enviados.add(marcaTiempo);
        } else {
            fallidos.add(marcaTiempo);
        }
    }

    public List<String> getEnviados() { return List.copyOf(enviados); }
    public List<String> getFallidos() { return List.copyOf(fallidos); }
}