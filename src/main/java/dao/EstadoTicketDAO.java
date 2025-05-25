package dao;

import modelo.EstadoTicket;
import java.util.List;

public interface EstadoTicketDAO {

     //Obtiene todos los estados de ticket disponibles.

    List<EstadoTicket> obtenerTodos();

     //Obtiene un estado de ticket por su ID.
    EstadoTicket obtenerPorId(int id);

    //Guarda un nuevo estado o actualiza uno existente.
    boolean guardarEstado(EstadoTicket estado);

    //Elimina un estado por su ID.
    boolean eliminarEstado(int id);

     //Obtiene los IDs de los estados a los que se puede transicionar desde un estado dado.
    List<Integer> obtenerEstadosSiguientes(int estadoId);

     // Primero elimina las transiciones anteriores y luego guarda las nuevas.
    boolean guardarTransiciones(int estadoOrigenId, List<Integer> estadosDestino);
}
