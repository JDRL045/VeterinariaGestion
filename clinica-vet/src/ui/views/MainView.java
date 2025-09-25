package ui.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import model.Rol;

public class MainView extends JFrame {
    private JMenuItem menuItemGestionUsuarios;
    private JMenuItem menuItemCerrarSesion;
    private JLabel lblBienvenida;

    public MainView() {
        setTitle("Sistema de Gestión - Clínica Veterinaria");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con BorderLayout
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Etiqueta de bienvenida GRANDE y CENTRADA
        lblBienvenida = new JLabel("Bienvenido", SwingConstants.CENTER); // CENTRADO
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 32)); // LETRA GRANDE
        // Añadimos un borde para darle espacio y que no quede pegado a los bordes
        lblBienvenida.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.add(lblBienvenida, BorderLayout.CENTER); // Posición central

        // --- Barra de Menú (sin cambios) ---
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuSistema = new JMenu("Sistema");
        menuItemCerrarSesion = new JMenuItem("Cerrar Sesión");
        menuItemCerrarSesion.setIcon(UIManager.getIcon("InternalFrame.closeIcon"));
        menuSistema.add(menuItemCerrarSesion);
        
        JMenu menuAdmin = new JMenu("Administración");
        menuItemGestionUsuarios = new JMenuItem("Gestionar Usuarios");
        menuItemGestionUsuarios.setIcon(UIManager.getIcon("FileView.computerIcon"));
        menuAdmin.add(menuItemGestionUsuarios);

        menuBar.add(menuSistema);
        menuBar.add(menuAdmin);
        setJMenuBar(menuBar);
    }

    public void setNombreUsuarioBienvenida(String nombre) {
        lblBienvenida.setText("Bienvenido, " + nombre);
    }

    public void configurarParaRol(Rol rol) {
        if (rol != Rol.ADMINISTRADOR) {
            getJMenuBar().getMenu(1).setVisible(false);
        }
    }

    public void addCerrarSesionListener(ActionListener listener) {
        menuItemCerrarSesion.addActionListener(listener);
    }
    
    public void addGestionUsuariosListener(ActionListener listener) {
        menuItemGestionUsuarios.addActionListener(listener);
    }
}