package InterfazCajeroAutomatico;

public class Persona { // Clase base que representa a una persona en el sistema.

    // Atributos privados que definen el estado de la persona (Encapsulamiento).
    private String nombre;
    private String apellido;
    private String dni;

    // Constructor para inicializar los atributos de la persona.
    public Persona(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    // Métodos 'getter' para obtener el nombre (Selectores).
    public String getNombre() {
        return this.nombre;
    }

    // Métodos 'setter' para modificar el nombre (Mutadores).
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos 'getter' para obtener el apellido (Selectores).
    public String getApellido() {
        return this.apellido;
    }

    // Métodos 'setter' para modificar el apellido (Mutadores).
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    // Métodos 'getter' para obtener el DNI (Selectores).
    public String getDni() {
        return this.dni;
    }

    // Métodos 'setter' para modificar el DNI (Mutadores).
    public void setDni(String dni) {
        this.dni = dni;
    }

    // Sobreescribe el método para una representación de texto legible del objeto.
    @Override
    public String toString() {
        return this.nombre + " " + this.apellido + " (DNI: " + this.dni + ")";
    }
}
