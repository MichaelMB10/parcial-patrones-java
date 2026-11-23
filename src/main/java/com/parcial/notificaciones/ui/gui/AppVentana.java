package com.parcial.notificaciones.ui.gui;

import com.parcial.notificaciones.factory.CanalFactory;
import com.parcial.notificaciones.model.TipoCanal;
import com.parcial.notificaciones.model.Usuario;
import com.parcial.notificaciones.observer.HistorialEnvioObserver;
import com.parcial.notificaciones.service.GestorNotificaciones;
import com.parcial.notificaciones.strategy.EnvioConReintentoStrategy;
import com.parcial.notificaciones.strategy.EnvioInformativoStrategy;
import com.parcial.notificaciones.strategy.EnvioUrgenteStrategy;
import com.parcial.notificaciones.strategy.EstrategiaEnvio;

import javax.swing.*;
import java.awt.*;
import java.util.EnumSet;

public class AppVentana extends JFrame {
    private final GestorNotificaciones gestor;
    private final HistorialEnvioObserver historial;
    private final JComboBox<Usuario> comboUsuarios;
    private final JComboBox<TipoCanal> comboCanal;
    private final JComboBox<String> comboEstrategia;
    private final JTextField txtMensaje;
    private final JTextArea areaHistorial;

    public AppVentana() {
        super("Sistema de Notificaciones Multicanal");
        this.gestor = new GestorNotificaciones(new CanalFactory());
        this.historial = new HistorialEnvioObserver();
        this.gestor.agregarObservador(historial);

        gestor.registrarUsuario(new Usuario("Ana", "ana@correo.com", EnumSet.of(TipoCanal.CORREO, TipoCanal.SMS)));
        gestor.registrarUsuario(new Usuario("Carlos", "3001234567", EnumSet.of(TipoCanal.MENSAJERIA)));
        gestor.registrarUsuario(new Usuario("Laura", "laura@correo.com", EnumSet.allOf(TipoCanal.class)));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(680, 440);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 8, 8));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        comboUsuarios = new JComboBox<>(gestor.getUsuarios().toArray(new Usuario[0]));
        comboCanal = new JComboBox<>(TipoCanal.values());
        comboEstrategia = new JComboBox<>(new String[]{"Informativo", "Urgente", "Con Reintentos"});
        txtMensaje = new JTextField();
        JButton btnEnviar = new JButton("Enviar Notificación");

        panelForm.add(new JLabel("Usuario destino:"));
        panelForm.add(comboUsuarios);
        panelForm.add(new JLabel("Canal preferido:"));
        panelForm.add(comboCanal);
        panelForm.add(new JLabel("Regla de envío:"));
        panelForm.add(comboEstrategia);
        panelForm.add(new JLabel("Mensaje:"));
        panelForm.add(txtMensaje);
        panelForm.add(new JLabel(""));
        panelForm.add(btnEnviar);

        areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);

        btnEnviar.addActionListener(e -> enviar());

        add(panelForm, BorderLayout.NORTH);
        add(new JScrollPane(areaHistorial), BorderLayout.CENTER);
    }

    private void enviar() {
        Usuario u = (Usuario) comboUsuarios.getSelectedItem();
        TipoCanal canal = (TipoCanal) comboCanal.getSelectedItem();
        String tipoEst = (String) comboEstrategia.getSelectedItem();
        String msg = txtMensaje.getText();

        EstrategiaEnvio estrategia = switch (tipoEst) {
            case "Urgente" -> new EnvioUrgenteStrategy();
            case "Con Reintentos" -> new EnvioConReintentoStrategy(3);
            default -> new EnvioInformativoStrategy();
        };

        boolean ok = gestor.enviarNotificacion(u, canal, msg, estrategia);
        JOptionPane.showMessageDialog(this, ok ? "Notificación enviada" : "Falló el envío");
        actualizarHistorial();
    }

    private void actualizarHistorial() {
        StringBuilder sb = new StringBuilder("=== ENVIADOS ===\n");
        historial.getEnviados().forEach(s -> sb.append(s).append("\n"));
        sb.append("\n=== FALLIDOS ===\n");
        historial.getFallidos().forEach(s -> sb.append(s).append("\n"));
        areaHistorial.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppVentana().setVisible(true));
    }
}