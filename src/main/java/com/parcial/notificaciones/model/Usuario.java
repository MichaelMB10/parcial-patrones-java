package com.parcial.notificaciones.model;

import java.util.EnumSet;
import java.util.Set;

public class Usuario {
    private final String nombre;
    private final String contacto;
    private final Set<TipoCanal> canalesPermitidos;

    public Usuario(String nombre, String contacto, Set<TipoCanal> canalesPermitidos) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.canalesPermitidos = (canalesPermitidos == null || canalesPermitidos.isEmpty())
                ? EnumSet.noneOf(TipoCanal.class)
                : EnumSet.copyOf(canalesPermitidos);
    }

    public String getNombre() { return nombre; }
    public String getContacto() { return contacto; }
    public boolean aceptaCanal(TipoCanal canal) { return canalesPermitidos.contains(canal); }
    public Set<TipoCanal> getCanalesPermitidos() { return EnumSet.copyOf(canalesPermitidos); }

    @Override
    public String toString() {
        return nombre + " (" + contacto + ") - Canales: " + canalesPermitidos;
    }
}