package modelo;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class EstadoTicket {
    private final IntegerProperty id;
    private final StringProperty nombreEstado;
    private final StringProperty descripcion;
    private final BooleanProperty esFinal;
    private List<Integer> estadosSiguientes; // solo IDs de estados destino

    public EstadoTicket(int id, String nombre, String descripcion, boolean esFinal) {
        this.id = new SimpleIntegerProperty(id);
        this.nombreEstado = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.esFinal = new SimpleBooleanProperty(esFinal);
        this.estadosSiguientes = new ArrayList<>();
    }

    public EstadoTicket() {
        this.id = new SimpleIntegerProperty();
        this.nombreEstado = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.esFinal = new SimpleBooleanProperty();
        this.estadosSiguientes = new ArrayList<>();
    }

    // Getters
    public int getId() { return id.get(); }
    public String getNombreEstado() { return nombreEstado.get(); }
    public String getDescripcion() { return descripcion.get(); }
    public boolean isEsFinal() { return esFinal.get(); }
    public List<Integer> getEstadosSiguientes() { return estadosSiguientes; }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setNombreEstado(String nombre) { this.nombreEstado.set(nombre); }
    public void setDescripcion(String descripcion) { this.descripcion.set(descripcion); }
    public void setEsFinal(boolean esFinal) { this.esFinal.set(esFinal); }
    public void setEstadosSiguientes(List<Integer> lista) { this.estadosSiguientes = lista; }

    // Properties
    public IntegerProperty idProperty() { return id; }
    public StringProperty nombreEstadoProperty() { return nombreEstado; }
    public StringProperty descripcionProperty() { return descripcion; }
    public BooleanProperty esFinalProperty() { return esFinal; }

    @Override
    public String toString() {
        return nombreEstado.get();
    }
}
