package services;

import model.Usuario;
import repositories.UsuarioRepository;

public class AutenticacionService {
    private final UsuarioRepository usuarioRepository;
    private Usuario usuarioAutenticado;

    public AutenticacionService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioAutenticado = null;
    }

    public EstadoLogin iniciarSesion(String id, String contrasena) {
        Usuario usuario = usuarioRepository.buscarPorId(id);

        
        if (usuario == null) {
            return EstadoLogin.CREDENCIALES_INVALIDAS;
        }

        
        if (!usuario.isActivo()) {
            return EstadoLogin.USUARIO_INACTIVO;
        }

        
        if (!usuario.getContrasena().equals(contrasena)) {
            return EstadoLogin.CREDENCIALES_INVALIDAS;
        }

        
        usuarioAutenticado = usuario;
        return EstadoLogin.EXITOSO;
    }

    public void cerrarSesion() {
        usuarioAutenticado = null;
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}