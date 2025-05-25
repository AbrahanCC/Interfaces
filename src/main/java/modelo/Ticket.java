package modelo;

import java.time.LocalDate;

public class Ticket {
    private int id;
    private String descripcion;
    private LocalDate fechaCreacion;
    private LocalDate fechaCierre;
    private EstadoTicket estado;
    private Usuario creador;
    private Usuario tecnico;

    public Ticket() {}

    public Ticket(int id, String descripcion, LocalDate fechaCreacion, LocalDate fechaCierre,
                  EstadoTicket estado, Usuario creador, Usuario tecnico) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaCierre = fechaCierre;
        this.estado = estado;
        this.creador = creador;
        this.tecnico = tecnico;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public Usuario getCreador() {
        return creador;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }

    // Métodos auxiliares para mostrar en la vista (TableView)
    public String getNombreCreador() {
        return creador != null ? creador.getNombre() : "Sin creador";
    }

    public String getNombreTecnico() {
        return tecnico != null ? tecnico.getNombre() : "Sin asignar";
    }

    public String getNombreEstado() {
        return estado != null ? estado.getNombreEstado() : "Sin estado";
    }
}