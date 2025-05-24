package modelo;

import java.time.LocalDate;

public class Ticket {
    private int id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaCreacion;
    private String estado; // nombre del estado actual
    private Usuario creador;
    private Usuario tecnico;

    public Ticket() {}

    public Ticket(int id, String titulo, String descripcion, LocalDate fechaCreacion,
                  String estado, Usuario creador, Usuario tecnico) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.creador = creador;
        this.tecnico = tecnico;
    }

    // Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public String getEstado() { return estado; }
    public Usuario getCreador() { return creador; }
    public Usuario getTecnico() { return tecnico; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCreador(Usuario creador) { this.creador = creador; }
    public void setTecnico(Usuario tecnico) { this.tecnico = tecnico; }

    // Auxiliares para mostrar en tabla
    public String getNombreCreador() {
        return creador != null ? creador.getNombre() : "Sin creador";
    }

    public String getNombreTecnico() {
        return tecnico != null ? tecnico.getNombre() : "Sin asignar";
    }
}
