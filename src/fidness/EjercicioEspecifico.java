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

// Clase hija "EjercicioEspecifico" que se extiende de la clase "Ejercicio" con el fin de implementar Herencia. Permite añadir personalización de tiempos de descanso y, además, demuestra Polimorfismo.
public class EjercicioEspecifico extends Ejercicio {
    private int tiempoRest; // Tiempo de descanso en segundos específico de esta subclase

    // Constructor de la clase "EjercicioEspecifico". Invoca al constructor de la clase padre mediante super().
    public EjercicioEspecifico(int id, String nombre, String descripcion, String instruccionesEjecucion, String recursoMultimedia, int tiempoRest) {
        super(id, nombre, descripcion, instruccionesEjecucion, recursoMultimedia);
        this.tiempoRest = tiempoRest;
    }

    // Getter y Setter de la clase "EjercicioEspecifico".
    public int getTiempoRest() { return tiempoRest; }
    public void setTiempoRest(int tiempoRest) { this.tiempoRest = tiempoRest; }

    // Sobreescritura del método del padre agregando la información del tiempo de descanso al reporte textual con el fin de implementar polimorfismo.
    @Override
    public String obtenerDetalleCompleto() {
        return super.obtenerDetalleCompleto() + "\nDescanso: " + tiempoRest + " segundos.";
    }
}