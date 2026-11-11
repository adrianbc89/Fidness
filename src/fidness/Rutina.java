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
import java.util.ArrayList;
import java.util.Date;

// Clase "Rutina". Gestiona las colecciones de ejercicios asignadas o creadas por un usuario.
public class Rutina {
    
    // Atributos privados de la clase "Rutina".
    private int id;
    private String nombre;
    private Date fechaCreacion;
    
    // Uso de colecciones dinámicas (ArrayList) para el almacenamiento de objetos de tipo Ejercicio
    private ArrayList<Ejercicio> listaEjercicios;

    // Constructores de la clase "Rutina".Inicializan la lista interna de ejercicios para evitar errores de puntero nulo.
    public Rutina(int id, String nombre, Date fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.listaEjercicios = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public ArrayList<Ejercicio> getListaEjercicios() { return listaEjercicios; }

    // Agrega un objeto de tipo "Ejercicio" a la colección dinámica interna.
    public void agregarEjercicio(Ejercicio ej) {
        this.listaEjercicios.add(ej);
    }

    // Remueve un objeto de tipo "Ejercicio" de la colección interna.
    public void quitarEjercicio(Ejercicio ej) {
        this.listaEjercicios.remove(ej);
    }
}