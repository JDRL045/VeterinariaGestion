package ui.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnRestablecer; // Botón nuevo

    public LoginView() {
        setTitle("Iniciar Sesión - Clínica Veterinaria");
        setSize(450, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Bienvenido al Sistema", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelPrincipal.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        panelPrincipal.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        txtUsuario = new JTextField(15);
        panelPrincipal.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelPrincipal.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        txtContrasena = new JPasswordField(15);
        panelPrincipal.add(txtContrasena, gbc);
        
        // Panel para los dos botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnRestablecer = new JButton("Restablecer Contraseña");
        panelBotones.add(btnIniciarSesion);
        panelBotones.add(btnRestablecer);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panelPrincipal.add(panelBotones, gbc);
        
        add(panelPrincipal);
    }

    public String getUsuario() { return txtUsuario.getText(); }
    public String getContrasena() { return new String(txtContrasena.getPassword()); }
    public void addLoginListener(ActionListener listener) { btnIniciarSesion.addActionListener(listener); }
    public void addRestablecerListener(ActionListener listener) { btnRestablecer.addActionListener(listener); } // Listener nuevo
    public void mostrarMensaje(String mensaje) { JOptionPane.showMessageDialog(this, mensaje); }
}