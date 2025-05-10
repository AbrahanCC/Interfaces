import java.util.LinkedList;
import java.util.Queue;

public class Departamento {
    private String nombre;
    private Queue<Ticket> colaTickets;

    public Departamento(String nombre) {
        this.nombre = nombre;
        this.colaTickets = new LinkedList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarTicket(Ticket ticket) {
        colaTickets.add(ticket);
    }

    public Ticket obtenerSiguienteTicket() {
        return colaTickets.poll(); // obtiene y elimina el primero
    }

    public Ticket verSiguienteTicket() {
        return colaTickets.peek(); // solo lo ve
    }

    public Queue<Ticket> getColaTickets() {
        return colaTickets;
    }
}

