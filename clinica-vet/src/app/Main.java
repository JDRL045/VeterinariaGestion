package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import repositories.JsonUsuarioRepository;
import repositories.UsuarioRepository;
import services.AutenticacionService;
import services.UsuarioService;
import ui.controllers.LoginController;
import ui.views.LoginView;

public class Main {
    public static void main(String[] args) {
        try {
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            UsuarioRepository usuarioRepository = new JsonUsuarioRepository();
            AutenticacionService autenticacionService = new AutenticacionService(usuarioRepository);
            UsuarioService usuarioService = new UsuarioService(usuarioRepository);
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView, autenticacionService, usuarioService);
            loginController.iniciar();
        });
    }
}