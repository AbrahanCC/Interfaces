package modelo;

/**
 * Clase que representa a un técnico del sistema.
 * Hereda de Usuario.
 */
public class Tecnico extends Usuario {

    // Constructor vacío
    public Tecnico() {
        this.setTipoUsuario("tecnico");
    }

    // Constructor con parámetros
    public Tecnico(int id, String nombre, String correo, String clave) {
        super(id, nombre, correo, clave, "tecnico");
    }

    // Puedes agregar métodos específicos del técnico más adelante
}
