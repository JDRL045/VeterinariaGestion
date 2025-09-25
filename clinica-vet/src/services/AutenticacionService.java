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

        // 1. Primero, verifica si el usuario existe. Si no, las credenciales son inválidas.
        if (usuario == null) {
            return EstadoLogin.CREDENCIALES_INVALIDAS;
        }

        // 2. AHORA, la nueva lógica: si el usuario existe, verifica PRIMERO si está inactivo.
        // Esto tiene prioridad sobre la contraseña.
        if (!usuario.isActivo()) {
            return EstadoLogin.USUARIO_INACTIVO;
        }

        // 3. Si el usuario existe y está activo, finalmente verifica la contraseña.
        if (!usuario.getContrasena().equals(contrasena)) {
            return EstadoLogin.CREDENCIALES_INVALIDAS;
        }

        // 4. Si todo está bien, el inicio de sesión es exitoso.
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