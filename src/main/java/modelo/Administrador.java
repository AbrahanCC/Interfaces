package modelo;
/**
 * Clase que representa a un administrador del sistema.
 * Hereda de Usuario.
 */

public class Administrador extends Usuario {

    // Constructor vacío
    public Administrador() {
        // Opcional: puedes establecer el tipo de usuario por defecto
        this.setTipoUsuario("administrador");
    }

    // Constructor con parámetros
    public Administrador(int id, String nombre, String correo, String clave) {
        super(id, nombre, correo, clave, "Administrador");
    }

    // Puedes agregar métodos específicos del administrador más adelante
}
