package dao;

import modelo.EstadoTicket;
import java.util.List;

public interface EstadoTicketDAO {

    // Obtener todos los estados
    List<EstadoTicket> obtenerTodos();

    // Obtener un estado por ID
    EstadoTicket obtenerPorId(int id);

    // Guardar un nuevo estado o actualizar uno existente
    boolean guardarEstado(EstadoTicket estado);

    // Eliminar un estado por ID
    boolean eliminarEstado(int id);

    // Obtener los IDs de los estados a los que se puede transicionar desde uno dado
    List<Integer> obtenerEstadosSiguientes(int estadoId);

    // Guardar transiciones posibles desde un estado (elimina las existentes y guarda nuevas)
    boolean guardarTransiciones(int estadoOrigenId, List<Integer> estadosDestino);
}
