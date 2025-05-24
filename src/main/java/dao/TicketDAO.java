package dao;

import modelo.Ticket;
import java.util.List;

public interface TicketDAO {
    List<Ticket> obtenerTodos();
    Ticket obtenerPorId(int id);
    boolean insertar(Ticket ticket);
    boolean actualizar(Ticket ticket);
    boolean eliminar(int id);
}



