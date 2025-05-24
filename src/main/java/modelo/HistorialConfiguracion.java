package modelo;

public class HistorialConfiguracion {
    private int id;
    private int usuarioId;
    private int configuracionId;
    private String descripcion;
    private java.sql.Timestamp fechaModificacion;

    public HistorialConfiguracion() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getConfiguracionId() { return configuracionId; }
    public void setConfiguracionId(int configuracionId) { this.configuracionId = configuracionId; }


}
