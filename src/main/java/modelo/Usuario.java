package modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String clave;
    private String tipoUsuario;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor completo
    public Usuario(int id, String nombre, String correo, String clave, String tipoUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipoUsuario + ")";
    }
}
