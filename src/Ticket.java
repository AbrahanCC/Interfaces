import javafx.beans.property.*;
import java.util.Stack;

public class Ticket {
    private IntegerProperty id;
    private StringProperty descripcion;
    private StringProperty estado; // se enlaza con la GUI
    private Stack<String> historialEstados; // historial de estados

    public Ticket(int id, String descripcion, String estadoInicial, String departamento) {
        this.id = new SimpleIntegerProperty(id);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.estado = new SimpleStringProperty(estadoInicial);
        this.historialEstados = new Stack<>();
        historialEstados.push(estadoInicial); // se guarda el estado inicial
    }

    // Getters para las propiedades
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    // Getters y setters
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

    // Método para cambiar el estado y guardar en historial
    public void cambiarEstado(String nuevoEstado) {
        historialEstados.push(nuevoEstado);
        this.estado.set(nuevoEstado);
    }

    // Método para deshacer el último cambio de estado
    public boolean deshacerUltimoCambio() {
        if (historialEstados.size() > 1) {
            historialEstados.pop(); // elimina el último

    void setDepartamento(String departamento) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
