package dao;

import modelo.Ticket;
import java.util.List;

public interface TicketDAO {
    boolean insertar(Ticket ticket);
    void actualizar(Ticket ticket);
    void eliminar(int id);
    Ticket obtenerPorId(int id);
    List<Ticket> obtenerTodos();
}





