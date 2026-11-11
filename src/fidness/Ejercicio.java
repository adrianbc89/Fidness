/*
 * Curso: Programación Cliente/Servidor Concurrente
 * Avance #2: Proyecto Final - Fidness
 * Realizado por: Adrián Barquero Cordero.
 * Fecha de entrega: 10-11-2026
 * Descripción general: Fidness es una plataforma integral de gestión de entrenamiento físico diseñada para entornos de gimnasios, desarrollada bajo el paradigma de programación orientada a objetos en Java. 
 *                      La aplicación articula una arquitectura modular mediante la interacción de clases core como Usuario, Sesion, Rutina y Ejercicio, vinculadas operativamente a través de la enumeración 
 *                      TipoEjercicio para clasificar de manera estricta las zonas musculares y actividades. La lógica del sistema implementa de forma nativa los pilares de la programación orientada a objetos: 
 *                      la herencia y el polimorfismo se demuestran mediante una estructura de persistencia y simulación de base de datos donde las entidades comparten un comportamiento base de inicialización y 
 *                      formateo de datos; el manejo riguroso de excepciones asegura la estabilidad del flujo operativo controlando las entradas erróneas del usuario en la interfaz gráfica interactiva construida 
 *                      con la librería Swing a través de componentes emergentes JOptionPane; y las colecciones dinámicas de la API de Java (específicamente ArrayList y HashMap) garantizan la manipulación en tiempo 
 *                      real, búsqueda, edición y consistencia de los datos del perfil, rutinas personalizadas y catálogo de ejercicios predeterminados y administrados.
 */

package fidness;

// Clase "Ejercicio". Representa un ejercicio físico genérico dentro de la plataforma. Se utiliza como base para demostrar conceptos de programación orientada a objetos.
public class Ejercicio {
    
    // Atributos privados para garantizar el encapsulamiento de datos.
    private int id;
    private String nombre;
    private String descripcion;
    private String instruccionesEjecucion;
    private String recursoMultimedia;
    private TipoEjercicio tipo;

    // Constructores de la clase "Ejercicio". Permiten inicializar todas las propiedades de un ejercicio nuevo.
    public Ejercicio(int id, String nombre, String descripcion, String instruccionesEjecucion, String recursoMultimedia) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.instruccionesEjecucion = instruccionesEjecucion;
        this.recursoMultimedia = recursoMultimedia;
    }

    // Métodos Getter y Setter de la clase "Ejercicio". Permiten el acceso controlado a atributos privados.
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getInstruccionesEjecucion() { return instruccionesEjecucion; }
    public void setInstruccionesEjecucion(String instruccionesEjecucion) { this.instruccionesEjecucion = instruccionesEjecucion; }
    
    public String getRecursoMultimedia() { return recursoMultimedia; }
    public void setRecursoMultimedia(String recursoMultimedia) { this.recursoMultimedia = recursoMultimedia; }

    // Método sobreescrito que será utilizado para demostrar polimorfismo dinámico devolviendo una ficha detallada formateada para las interfaces visuales.
    public String obtenerDetalleCompleto() {
        return "Ejercicio: " + nombre + "\n" +
               "Descripción: " + descripcion + "\n" +
               "Instrucciones: " + instruccionesEjecucion;
    }
}