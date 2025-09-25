package services;

import java.util.List;
import model.Rol;
import model.Usuario;
import repositories.UsuarioRepository;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void crearUsuario(String id, String nombre, String contrasena, Rol rol) {
        if (usuarioRepository.buscarPorId(id) == null) {
            Usuario nuevoUsuario = new Usuario(id, nombre, contrasena, rol);
            usuarioRepository.agregar(nuevoUsuario);
        } else {
            System.out.println("Error: El usuario ya existe.");
        }
    }
    
    // --- NUEVOS MÉTODOS ---
    public Usuario buscarUsuarioPorId(String id) {
        return usuarioRepository.buscarPorId(id);
    }
    
    public void actualizarUsuario(Usuario usuario) {
        usuarioRepository.actualizar(usuario);
    }

    public void cambiarEstadoUsuario(String id) {
        Usuario usuario = usuarioRepository.buscarPorId(id);
        if (usuario != null) {
            usuario.setActivo(!usuario.isActivo()); // Invierte el estado actual
            usuarioRepository.actualizar(usuario);
        }
    }
    
    // --- MÉTODOS EXISTENTES ---
    public void restablecerContrasena(String id, String nuevaContrasena) {
        Usuario usuario = usuarioRepository.buscarPorId(id);
        if (usuario != null) {
            usuario.setContrasena(nuevaContrasena);
            usuarioRepository.actualizar(usuario);
        }
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listarTodos();
    }
}