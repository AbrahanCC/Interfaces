package modelo;

import javafx.beans.property.*;

public class Departamento {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty descripcion = new SimpleStringProperty();

    public Departamento() {}

    public Departamento(String nombre, String descripcion) {
        this.nombre.set(nombre);
        this.descripcion.set(descripcion);
    }

    public Departamento(int id, String nombre, String descripcion) {
        this.id.set(id);
        this.nombre.set(nombre);
        this.descripcion.set(descripcion);
    }

    // Getters y setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    @Override
    public String toString() {
        return nombre.get();
    }
}