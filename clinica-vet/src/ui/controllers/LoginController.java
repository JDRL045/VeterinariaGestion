package ui.controllers;

import model.Usuario;
import services.AutenticacionService;
import services.EstadoLogin; // <-- Importar el nuevo enum
import services.UsuarioService;
import ui.views.LoginView;
import ui.views.MainView;

import javax.swing.JOptionPane;

public class LoginController {
    private final LoginView loginView;
    private final AutenticacionService autenticacionService;
    private final UsuarioService usuarioService;

    public LoginController(LoginView loginView, AutenticacionService autenticacionService, UsuarioService usuarioService) {
        this.loginView = loginView;
        this.autenticacionService = autenticacionService;
        this.usuarioService = usuarioService;
        
        this.loginView.addLoginListener(e -> autenticarUsuario());
        this.loginView.addRestablecerListener(e -> restablecerContrasena());
    }

    private void autenticarUsuario() {
        String usuario = loginView.getUsuario();
        String contrasena = loginView.getContrasena();

        // El método ahora devuelve nuestro enum
        EstadoLogin resultado = autenticacionService.iniciarSesion(usuario, contrasena);

        // Usamos un switch para manejar cada posible resultado
        switch (resultado) {
            case EXITOSO:
                loginView.dispose();
                MainView mainView = new MainView();
                mainView.configurarParaRol(autenticacionService.getUsuarioAutenticado().getRol());
                MainController mainController = new MainController(mainView, usuarioService, autenticacionService);
                mainController.iniciar();
                break;
            
            case USUARIO_INACTIVO:
                loginView.mostrarMensaje("Este usuario se encuentra inactivo. Contacte al administrador.");
                break;

            case CREDENCIALES_INVALIDAS:
                loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
                break;
        }
    }

    private void restablecerContrasena() {
        String idUsuario = JOptionPane.showInputDialog(loginView, "Ingrese el ID del usuario para restablecer su contraseña:", "Restablecer Contraseña", JOptionPane.PLAIN_MESSAGE);
        
        if (idUsuario == null || idUsuario.trim().isEmpty()) {
            return;
        }
        
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        
        if (usuario == null) {
            loginView.mostrarMensaje("El usuario '" + idUsuario + "' no fue encontrado.");
            return;
        }

        if (idUsuario.equals("admin")) {
            loginView.mostrarMensaje("La contraseña del administrador no se puede restablecer desde aquí.");
            return;
        }

        String nuevaContrasena = JOptionPane.showInputDialog(loginView, "Ingrese la nueva contraseña para el usuario '" + idUsuario + "':", "Nueva Contraseña", JOptionPane.PLAIN_MESSAGE);

        if (nuevaContrasena != null && !nuevaContrasena.trim().isEmpty()) {
            usuarioService.restablecerContrasena(idUsuario, nuevaContrasena);
            loginView.mostrarMensaje("Contraseña actualizada exitosamente.");
        }
    }

    public void iniciar() {
        loginView.setVisible(true);
    }
}