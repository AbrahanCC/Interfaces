package dao;

import modelo.Departamento;
import java.util.List;

public interface DepartamentoDAO {
    List<Departamento> obtenerTodos();
    Departamento obtenerPorId(int id);
    boolean insertar(Departamento departamento);
    boolean actualizar(Departamento departamento);
    boolean eliminar(int id);
}
