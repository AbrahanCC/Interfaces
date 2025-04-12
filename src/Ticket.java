import javafx.beans.property.*;

public class Ticket {
    private final IntegerProperty id;
    private final StringProperty descripcion;
    private final StringProperty estado;

    public Ticket(int id, String descripcion, String estado) {
        this.id = new SimpleIntegerProperty(id);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.estado = new SimpleStringProperty(estado);
    }

    // Getters para las propiedades (necesarios para el TableView)
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    // Getters y setters tradicionales
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }
}


