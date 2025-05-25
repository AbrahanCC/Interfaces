package modelo;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class EstadoTicket {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombreEstado = new SimpleStringProperty();
    private final StringProperty descripcion = new SimpleStringProperty();
    private final BooleanProperty esFinal = new SimpleBooleanProperty();
    private List<Integer> estadosSiguientes = new ArrayList<>(); // IDs de estados destino

    // Constructor vacío
    public EstadoTicket() {
    }

    // Constructor con ID
    public EstadoTicket(int id, String nombre, String descripcion, boolean esFinal) {
        this.id.set(id);
        this.nombreEstado.set(nombre);
        this.descripcion.set(descripcion);
        this.esFinal.set(esFinal);
    }

    // Constructor sin ID (para nuevos registros)
    public EstadoTicket(String nombre, String descripcion, boolean esFinal) {
        this.nombreEstado.set(nombre);
        this.descripcion.set(descripcion);
        this.esFinal.set(esFinal);
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public String getNombreEstado() {
        return nombreEstado.get();
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public boolean isEsFinal() {
        return esFinal.get();
    }

    public List<Integer> getEstadosSiguientes() {
        return estadosSiguientes;
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setNombreEstado(String nombre) {
        this.nombreEstado.set(nombre);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public void setEsFinal(boolean esFinal) {
        this.esFinal.set(esFinal);
    }

    public void setEstadosSiguientes(List<Integer> lista) {
        this.estadosSiguientes = lista;
    }

    // Properties (para bindings en la interfaz)
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nombreEstadoProperty() {
        return nombreEstado;
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public BooleanProperty esFinalProperty() {
        return esFinal;
    }

    @Override
    public String toString() {
        return nombreEstado.get();
    }
}
