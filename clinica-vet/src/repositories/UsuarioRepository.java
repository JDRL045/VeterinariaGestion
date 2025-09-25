package repositories;

import java.util.List;
import model.Usuario;

public interface UsuarioRepository {
    void agregar(Usuario usuario);
    void actualizar(Usuario usuario);
    Usuario buscarPorId(String id);
    List<Usuario> listarTodos();
}