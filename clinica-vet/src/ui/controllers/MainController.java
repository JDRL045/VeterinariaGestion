package ui.controllers;

import model.Usuario;
import services.AutenticacionService;
import services.UsuarioService;
import ui.views.GestionUsuariosView;
import ui.views.MainView;
import ui.views.LoginView; // Necesario para volver al login

public class MainController {
    private final MainView mainView;
    private final UsuarioService usuarioService;
    private final AutenticacionService autenticacionService;

    public MainController(MainView mainView, UsuarioService usuarioService, AutenticacionService autenticacionService) {
        this.mainView = mainView;
        this.usuarioService = usuarioService;
        this.autenticacionService = autenticacionService;

        // Asignar listeners a los items del menú
        this.mainView.addGestionUsuariosListener(e -> abrirGestionUsuarios());
        this.mainView.addCerrarSesionListener(e -> cerrarSesion());

        establecerMensajeBienvenida();
    }
    
    private void establecerMensajeBienvenida() {
        Usuario usuarioAutenticado = autenticacionService.getUsuarioAutenticado();
        if (usuarioAutenticado != null) {
            mainView.setNombreUsuarioBienvenida(usuarioAutenticado.getNombre());
        }
    }

    public void iniciar() {
        mainView.setVisible(true);
    }

    // --- MÉTODO COMPLETO PARA ABRIR LA VENTANA DE GESTIÓN ---
    private void abrirGestionUsuarios() {
        GestionUsuariosView gestionView = new GestionUsuariosView();
        GestionUsuariosController gestionController = new GestionUsuariosController(gestionView, usuarioService);
        gestionController.iniciar();
    }

    // --- MÉTODO COMPLETO PARA CERRAR SESIÓN ---
    private void cerrarSesion() {
        // Cierra la ventana principal
        mainView.dispose();
        
        // Vuelve a abrir la ventana de Login
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView, autenticacionService, usuarioService);
        loginController.iniciar();
    }
}