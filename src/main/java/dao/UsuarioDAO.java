package dao;

import modelo.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void guardar(Usuario usuario);
    void actualizar(Usuario usuario);
    void eliminar(int id);
    Usuario obtenerPorId(int id);
    List<Usuario> listarTodos();
    List<Usuario> obtenerPorRol(String rol);
}

