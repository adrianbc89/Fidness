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
import java.util.Date;

// Clase "Sesion". Controla el ciclo de vida del acceso seguro del usuario al sistema.
public class Sesion {
    
    // Atributos privados de la clase "Sesion".
    private int id;
    private String token;
    private Date fechaInicio;
    private boolean activa;

    // Constructores de la clase "Sesion". Permite instanciar una sesión activa tras un login exitoso.
    public Sesion(int id, String token, Date fechaInicio, boolean activa) {
        this.id = id;
        this.token = token;
        this.fechaInicio = fechaInicio;
        this.activa = activa;
    }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    
    public void cerrar() {
        
        // Invalida la sesión actual de forma lógica
        this.activa = false; 
    }
}