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

    public String getNombre() { // Devuelve el nombre de la persona
        return this.nombre;
    }

    public void setNombre(String nombre) { // Modifica el nombre de la persona
        this.nombre = nombre;
    }

    public String getApellido() { // Devuelve el apellido de la persona
        return this.apellido;
    }

    public void setApellido(String apellido) { // Modifica el apellido de la persona
        this.apellido = apellido;
    }

    public String getDni() { // Devuelve el DNI de la persona
        return this.dni;
    }

    public void setDni(String dni) { // Modifica el DNI de la persona
        this.dni = dni;
    }

    // Sobreescribe el método para una representación de texto legible del objeto.
    @Override 
    public String toString() { 
        return this.nombre + " " + this.apellido + " (DNI: " + this.dni + ")"; 
    }
}
