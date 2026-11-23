package com.parcial.notificaciones.service;

import com.parcial.notificaciones.factory.CanalFactory;
import com.parcial.notificaciones.factory.CanalNotificacion;
import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;
import com.parcial.notificaciones.observer.ObservadorEnvio;
import com.parcial.notificaciones.strategy.EstrategiaEnvio;

import java.util.ArrayList;
import java.util.List;

public class GestorNotificaciones {
    private final CanalFactory factory;
    private final List<ObservadorEnvio> observadores = new ArrayList<>();
    private final List<Usuario> usuarios = new ArrayList<>();

    public GestorNotificaciones(CanalFactory factory) {
        this.factory = factory;
    }

    public void agregarObservador(ObservadorEnvio observador) { observadores.add(observador); }
    public void registrarUsuario(Usuario usuario) { usuarios.add(usuario); }
    public List<Usuario> getUsuarios() { return List.copyOf(usuarios); }

    public boolean enviarNotificacion(Usuario usuario, TipoCanal canal, String mensaje, EstrategiaEnvio estrategia) {
        boolean exito = estrategia.ejecutarEnvio(usuario, canal, mensaje, factory);
        CanalNotificacion canalObj = factory.crearCanal(canal);
        String vistaPrevia = canalObj.formatearMensaje(usuario, mensaje);
        String estado = exito ? "ENVIADO" : "FALLIDO";
        String detalle = estado + " | Estrategia: " + estrategia.getNombreEstrategia() + " | " + vistaPrevia;

        for (ObservadorEnvio obs : observadores) {
            obs.alFinalizarEnvio(exito, detalle);
        }
        return exito;
    }
}