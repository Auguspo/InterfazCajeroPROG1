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

     
    public String getNombre() { // Métodos 'getter' para obtener el nombre (Selectores).
        return this.nombre;
    }

    public void setNombre(String nombre) { // Métodos 'setter' para modificar el nombre (Mutadores).
        this.nombre = nombre;
    }

    public String getApellido() { // Métodos 'getter' para obtener el apellido (Selectores).
        return this.apellido;
    }

    public void setApellido(String apellido) { // Métodos 'setter' para modificar el apellido (Mutadores).
        this.apellido = apellido;
    }

    public String getDni() { // Métodos 'getter' para obtener el DNI (Selectores).
        return this.dni;
    }

    public void setDni(String dni) { // Métodos 'setter' para modificar el DNI (Mutadores).
        this.dni = dni;
    }

    @Override // Sobreescribe el método para una representación de texto legible del objeto.
    public String toString() {
        return this.nombre + " " + this.apellido + " (DNI: " + this.dni + ")";
    }
}
