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
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Date;

// Clase "Fidness". Controla el flujo principal del programa. Contiene el menú principal y las opciones de submenú para acceder a cada uno de los módulos independientes. Maneja los vectores globales, flujos de ventanas 
// JOptionPane y persistencia simulada.
public class Fidness {

     // Variables globales estáticas. Garantizan acceso unificado desde cualquier punto lógico del hilo de ejecución.
    public static ArrayList<Usuario> baseDatosUsuarios = new ArrayList<>();
    public static ArrayList<Ejercicio> catalogoEjercicios = new ArrayList<>();
    public static ArrayList<Rutina> baseDatosRutinas = new ArrayList<>();
    public static Usuario usuarioLogueado = null;
    public static Sesion sesionActual = null;

    // Método main. Punto de inicio para la ejecución del programa.
    public static void main(String[] args) {
        
        // Inicialización obligatoria del entorno de datos por defecto para iniciar la ejecución del programa.
        cargarDatosPorDefecto();
       
        // Disparador del bucle principal del portal de bienvenida/ingreso.
        ejecutarMenuIngreso();
    }

    // Rellena las colecciones estaticas globales con datos precargados para facilitar la evaluación del programa.
    private static void cargarDatosPorDefecto() {
        
        Usuario defecto = new Usuario(
            1, 
            "Adrián Barquero Cordero", 
            "ABC001", 
            "ABC1234", 
            "abc001@gmail.com", 
            "8910-1234"
        );
        baseDatosUsuarios.add(defecto);

        // Inyección de los ejercicios preestablecidos en el sistema usando subclases polimórficas.
        catalogoEjercicios.add(new EjercicioEspecifico(1, "Sentadillas búlgaras", "8 a 10 repeticiones por pierna.", "Mantén el torso ligeramente al frente para enfocar los glúteos y cuádriceps.", "PIERNA", 90));
        catalogoEjercicios.add(new EjercicioEspecifico(2, "Dominadas o Jalón al pecho", "8 a 10 repeticiones.", "Lleva los codos hacia tus costados y contrae las escápulas al final del movimiento.", "ESPALDA", 90));
        catalogoEjercicios.add(new EjercicioEspecifico(3, "Curl de bíceps con mancuernas", "12 repeticiones.", "Mantén los codos pegados al torso sin balancear el cuerpo.", "BRAZO", 60));
        catalogoEjercicios.add(new EjercicioEspecifico(4, "Press de banca (con barra o mancuernas)", "8 a 10 repeticiones.", "Contrae los omóplatos contra el banco antes de bajar el peso para proteger los hombros.", "PECHO", 90));
        catalogoEjercicios.add(new EjercicioEspecifico(5, "Plancha abdominal (Plank)", "30 a 45 segundos.", "Contrae activamente los glúteos y el abdomen, evitando que la cadera se caiga.", "ABDOMEN", 45));
        catalogoEjercicios.add(new EjercicioEspecifico(6, "Protocolo HIIT (Alta intensidad)", "20 segundos de Burpees o Jumping Jacks a máxima intensidad seguidos de 40 segundos de descanso.", "Repite este ciclo por 5 a 10 minutos.", "CARDIO", 40));
    }
    
    private static void ejecutarMenuIngreso() {
        
        // Acumulador local para validar bloqueos de seguridad. Se establece un máximo de 3 intentos fallidos por defecto.
        int intentosFallidos = 0; 
        boolean salir = false;

        while (!salir) {
            String menu = "                   Bienvenido a Fidness\n\n1. Iniciar Sesión\n2. Crear Cuenta\n\nIngrese un número de opción para continuar:";
            String entrada = JOptionPane.showInputDialog(null, menu, "Acceso", JOptionPane.QUESTION_MESSAGE);

            // Finaliza la ejecución del programa en caso de que el usuario haya presionado cancelar.
            if (entrada == null) {
                JOptionPane.showMessageDialog(null, "Gracias por usar Fidness. ¡Hasta pronto!", "Fidness", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            // Estructura de manejo de excepciones para "intentar" ejecutar un bloque de código que es propenso a fallar.
            try {
                int opcion = Integer.parseInt(entrada);
                switch (opcion) {
                    case 1:
                        intentosFallidos = procesarFlujoLogin(intentosFallidos);
                        break;
                    case 2:
                        procesarFlujoRegistro();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción fuera de rango. Intente nuevamente.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            
            // Red de seguridad para capturar y manejar errores en tiempo de ejecución. Si el código dentro de la estructura "try" falla el programa salta al bloque "catch" y brinda un mensaje de alerta en lugar de finalizar
            // la ejecución del programa de forma abrupta.
            } catch (NumberFormatException e) {
                
                // Captura de excepciones en caso de que la entrada contenga caracteres no numéricos
                JOptionPane.showMessageDialog(null, "Opción inválida. Debe digitar un número entero. Intente de nuevo.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Controla los flujos de validación de identidad del usuario.
    private static int procesarFlujoLogin(int intentos) {
        String user = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario: \n(Usuario por defecto: ABC001, o puede crear su propio usuario)", "Login", JOptionPane.QUESTION_MESSAGE);
        if (user == null) return intentos;
        
        String pass = JOptionPane.showInputDialog(null, "Ingrese su contraseña: \n(Contraseña: ABC1234)", "Login", JOptionPane.QUESTION_MESSAGE);
        if (pass == null) return intentos;

        // Búsqueda lineal secuencial dentro de la colección dinámica global.
        Usuario encontrado = null;
        for (Usuario u : baseDatosUsuarios) {
            if (u.getNombreUsuario().equals(user) && u.getContrasena().equals(pass)) {
                encontrado = u;
                break;
            }
        }

        if (encontrado != null) {
            
            // Inicialización de estados globales tras el inicio de sesión exitoso
            usuarioLogueado = encontrado;
            sesionActual = new Sesion(1, "TK-" + System.currentTimeMillis(), new Date(), true);
            JOptionPane.showMessageDialog(null, "¡Bienvenido a Fidness " + usuarioLogueado.getNombreCompleto() + "!", "Fidness", JOptionPane.INFORMATION_MESSAGE);
            
            // Transición del hilo de ejecución hacia la pantalla interna principal de la App
            ejecutarMenuPrincipal();
            
            // Reseteo preventivo de contador de intentos fallidos
            return 0; 
        } else {
            intentos++;
            if (intentos >= 3) {
                
                // Mensaje de alerta en caso de que el usuario realice tres intentos fallidos.
                JOptionPane.showMessageDialog(null, "Ha excedido el máximo de intentos permitidos. Comuníquese con un administrador", "Cuenta Bloqueada", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos. Intente de nuevo.\nIntentos fallidos: " + intentos + "/3", "Error de Credenciales", JOptionPane.WARNING_MESSAGE);
            }
        }
        return intentos;
    }
    
    // Gestiona el registro de nuevas cuentas garantizando la no duplicidad de campos clave.
    private static void procesarFlujoRegistro() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre completo:", "Registro", JOptionPane.QUESTION_MESSAGE);
        if (nombre == null || nombre.trim().isEmpty()) return;

        String correo = JOptionPane.showInputDialog(null, "Ingrese su correo electrónico:", "Registro", JOptionPane.QUESTION_MESSAGE);
        if (correo == null || correo.trim().isEmpty()) return;

        String telefono = JOptionPane.showInputDialog(null, "Ingrese su teléfono:", "Registro", JOptionPane.QUESTION_MESSAGE);
        if (telefono == null || telefono.trim().isEmpty()) return;

        String username = JOptionPane.showInputDialog(null, "Ingrese un nombre de usuario nuevo:", "Registro", JOptionPane.QUESTION_MESSAGE);
        if (username == null || username.trim().isEmpty()) return;

        String contrasena = JOptionPane.showInputDialog(null, "Establezca su contraseña:", "Registro", JOptionPane.QUESTION_MESSAGE);
        if (contrasena == null || contrasena.trim().isEmpty()) return;

        // Bloque de validación cruzada para evitar colisiones de datos en las colecciones
        for (Usuario u : baseDatosUsuarios) {
            if (u.getNombreCompleto().equalsIgnoreCase(nombre)) {
                JOptionPane.showMessageDialog(null, "El nombre completo ya se encuentra registrado en nuestro sistema.", "Registro Fallido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                JOptionPane.showMessageDialog(null, "El correo electrónico ya se encuentra registrado en nuestro sistema.", "Registro Fallido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (u.getTelefono().equals(telefono)) {
                JOptionPane.showMessageDialog(null, "El teléfono ya se encuentra registrado en nuestro sistema.", "Registro Fallido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (u.getNombreUsuario().equalsIgnoreCase(username)) {
                JOptionPane.showMessageDialog(null, "El nombre de usuario ya se encuentra registrado en nuestro sistema.", "Registro Fallido", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Añade un nuevo usuario a la base de datos tras un registro exitoso.
        int nuevoId = baseDatosUsuarios.size() + 1;
        Usuario nuevoUsuario = new Usuario(nuevoId, nombre, username, contrasena, correo, telefono);
        baseDatosUsuarios.add(nuevoUsuario);
        JOptionPane.showMessageDialog(null, "Se ha registrado con éxito en la plataforma Fidness.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }

    // Menú principal del programa Fidness.
    private static void ejecutarMenuPrincipal() {
        boolean enMenu = true;
        while (enMenu && sesionActual != null && sesionActual.isActiva()) {
            String menu = "                       Menú Principal"
                          + "\n\n1. Perfil de usuario"
                          + "\n2. Ejercicios"
                          + "\n3. Mis Rutinas"
                          + "\n4. Salir del programa"
                          + "\n\nIngrese un número de opción para continuar:";
            String entrada = JOptionPane.showInputDialog(null, menu, "Menu Principal", JOptionPane.QUESTION_MESSAGE);

            // Regresa a la pantalla de inicio de sesión en caso de que el usuario haya presionado cancelar.
            if (entrada == null) break;

            // Estructura de manejo de excepciones para "intentar" ejecutar un bloque de código que es propenso a fallar.
            try {
                int opcion = Integer.parseInt(entrada);
                
                // Estructura de control para el Menú Principal que ejecuta distintos bloques de código según el número de opción ingresado por el usuario.
                switch (opcion) {
                    
                    // Opción #1 del menú principal. Se hace un llamado al método "gestionarSubmenuPerfil()" para ingresar al submenú de Perfil de usuario.
                    case 1:
                        gestionarSubmenuPerfil();
                        break;
                    
                    // Opción #2 del menú principal. Se hace un llamado al método "gestionarSubmenuEjercicios()" para ingresar al submenú de Ejercicios.
                    case 2:
                        gestionarSubmenuEjercicios();
                        break;
                    
                    // Opción #3 del menú principal. Se hace un llamado al método "gestionarSubmenuRutinas()" para ingresar al submenú de Mis rutinas.
                    case 3:
                        gestionarSubmenuRutinas();
                        break;
                    
                    // Opción #4 del menú principal. Imprime un mensaje de despedida al usuario y finaliza la funcionalidad del programa.
                    case 4:
                        JOptionPane.showMessageDialog(null, "Gracias por utilizar nuestro sistema ¡Hasta pronto!", "Cierre de Sistema", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    
                    // Mensaje de alerta en caso de que el usuario ingrese una opción inválida.
                    default:
                        JOptionPane.showMessageDialog(null, "Opción inválida.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            
            // Red de seguridad para capturar y manejar errores en tiempo de ejecución. Si el código dentro de la estructura "try" falla el programa salta al bloque "catch" y brinda un mensaje de alerta en lugar de finalizar
            // la ejecución del programa de forma abrupta.
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, introduzca únicamente valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Método "gestionarSubmenuPerfil". Se dedica a la administración del perfil del usuario logueado.
    private static void gestionarSubmenuPerfil() {
        String menu = "                     Perfil de Usuario\n\n1. Ver perfil\n2. Editar Perfil\n3. Eliminar cuenta\n\nIngrese un número de opción para continuar:";
        String entrada = JOptionPane.showInputDialog(null, menu, "Gestión de Perfil", JOptionPane.QUESTION_MESSAGE);
        if (entrada == null) return;

        // Estructura de manejo de excepciones para "intentar" ejecutar un bloque de código que es propenso a fallar.
        try {
            int op = Integer.parseInt(entrada);
            if (op == 1) {
                String datos = "                          Mi Perfil"
                               + "\n\nNombre Completo: " + usuarioLogueado.getNombreCompleto()
                               + "\nCorreo: " + usuarioLogueado.getCorreo()
                               + "\nTeléfono: " + usuarioLogueado.getTelefono()
                               + "\nNombre de Usuario: " + usuarioLogueado.getNombreUsuario();
                JOptionPane.showMessageDialog(null, datos, "Perfil Actual", JOptionPane.INFORMATION_MESSAGE);
            } else if (op == 2) {
                String nom = JOptionPane.showInputDialog("Nuevo Nombre Completo:", usuarioLogueado.getNombreCompleto());
                String corr = JOptionPane.showInputDialog("Nuevo Correo:", usuarioLogueado.getCorreo());
                String tel = JOptionPane.showInputDialog("Nuevo Teléfono:", usuarioLogueado.getTelefono());
                String user = JOptionPane.showInputDialog("Nuevo Nombre de Usuario:", usuarioLogueado.getNombreUsuario());
                String pass = JOptionPane.showInputDialog("Nueva Contraseña:", usuarioLogueado.getContrasena());

                if (nom != null && corr != null && tel != null && user != null && pass != null) {
                    usuarioLogueado.setNombreCompleto(nom);
                    usuarioLogueado.setCorreo(corr);
                    usuarioLogueado.setTelefono(tel);
                    usuarioLogueado.setNombreUsuario(user);
                    usuarioLogueado.setContrasena(pass);
                    JOptionPane.showMessageDialog(null, "Perfil actualizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (op == 3) {
                String confirmacionPass = JOptionPane.showInputDialog(null, "Ingrese su contraseña para proceder a la eliminación:", "Confirmación", JOptionPane.WARNING_MESSAGE);
                if (confirmacionPass != null && confirmacionPass.equals(usuarioLogueado.getContrasena())) {
                    baseDatosUsuarios.remove(usuarioLogueado);
                    usuarioLogueado = null;
                    if (sesionActual != null) sesionActual.cerrar();
                    JOptionPane.showMessageDialog(null, "Su cuenta ha sido eliminada de nuestro sistema. ¡Hasta pronto!", "Cuenta eliminada", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta. Operación cancelada.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                }
            }
        
        // Red de seguridad para capturar y manejar errores en tiempo de ejecución. Si el código dentro de la estructura "try" falla el programa salta al bloque "catch" y brinda un mensaje de alerta en lugar de finalizar
        // la ejecución del programa de forma abrupta.
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al procesar la opción.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método "gestionarSubmenuEjercicios()". Permite consultar y modificar los ejercicios registrados en la base de datos.
    private static void gestionarSubmenuEjercicios() {
        String menu = "                           Ejercicios\n\n1. Consultar ejercicios\n2. Gestión de ejercicios\n\nIngrese un número de opción para continuar:";
        String entrada = JOptionPane.showInputDialog(null, menu, "Módulo de Ejercicios", JOptionPane.QUESTION_MESSAGE);
        if (entrada == null) return;

        // Estructura de manejo de excepciones para "intentar" ejecutar un bloque de código que es propenso a fallar.
        try {
            int op = Integer.parseInt(entrada);
            if (op == 1) {
                
                // Despliegue dinámico basado en la clase enum "TipoEjercicio".
                String enumMenu = "               Seleccione la categoría:\n\n1. Pierna\n2. Espalda\n3. Brazo\n4. Pecho\n5. Abdomen\n6. Cardio\n\nIngrese un número de opción para continuar:";
                String selected = JOptionPane.showInputDialog(null, enumMenu, "Músculo Objetivo", JOptionPane.QUESTION_MESSAGE);
                if (selected == null) return;
                
                int catId = Integer.parseInt(selected);
                String filtroStr = parsearEnumId(catId);
                
                StringBuilder sb = new StringBuilder("                                                                                Ejercicios de " + filtroStr + "\n");
                boolean hayEjercicios = false;
                for (Ejercicio e : catalogoEjercicios) {
                    if (e.getRecursoMultimedia().equalsIgnoreCase(filtroStr)) {
                        sb.append("\n").append(e.obtenerDetalleCompleto()).append("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        hayEjercicios = true;
                    }
                }
                if (!hayEjercicios) sb.append("No hay ejercicios registrados en esta categoría.");
                JOptionPane.showMessageDialog(null, sb.toString(), "Catálogo", JOptionPane.INFORMATION_MESSAGE);

            } else if (op == 2) {
                String confirm = JOptionPane.showInputDialog("Para gestionar ejercicios debe de contar con privilegios de administrador. ¿Desea continuar?\n1. Si\n2. No");
                if (confirm != null && confirm.equals("1")) {
                    String adminPass = JOptionPane.showInputDialog("Ingrese contraseña de Administrador: \n(Contraseña de admin por defecto: ABC002)");
                    if (adminPass != null && adminPass.equals("ABC002")) {
                        ejecutarCrudEjercicios();
                    } else {
                        JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        
        // Red de seguridad para capturar y manejar errores en tiempo de ejecución. Si el código dentro de la estructura "try" falla el programa salta al bloque "catch" y brinda un mensaje de alerta en lugar de finalizar
        // la ejecución del programa de forma abrupta.
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en flujo de ejercicios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String parsearEnumId(int id) {
        switch (id) {
            case 1: return TipoEjercicio.PIERNA.name();
            case 2: return TipoEjercicio.ESPALDA.name();
            case 3: return TipoEjercicio.BRAZO.name();
            case 4: return TipoEjercicio.PECHO.name();
            case 5: return TipoEjercicio.ABDOMEN.name();
            default: return TipoEjercicio.CARDIO.name();
        }
    }
    
    // Método "ejecutarCrudEjercicios()". Permite realizar actualizaciones y/o modificaciones a los ejercicios registrados en la base de datos.
    private static void ejecutarCrudEjercicios() {
        String menu = "              Gestión de Ejercicios\n\n1. Nuevo ejercicio\n2. Editar ejercicio\n3. Eliminar ejercicio\n\nIngrese un número de opción para continuar:";
        String entrada = JOptionPane.showInputDialog(null, menu, "Modo Root", JOptionPane.QUESTION_MESSAGE);
        if (entrada == null) return;

        // Estructura de manejo de excepciones para "intentar" ejecutar un bloque de código que es propenso a fallar.
        try {
            int op = Integer.parseInt(entrada);
            if (op == 1) {
                String enumMenu = "Categoría del nuevo ejercicio:\n\n1. Pierna\n2. Espalda\n3. Brazo\n4. Pecho\n5. Abdomen\n6. Cardio\n\nIngrese un número de opción para continuar:";
                int catId = Integer.parseInt(JOptionPane.showInputDialog(enumMenu));
                String catStr = parsearEnumId(catId);

                String nom = JOptionPane.showInputDialog("Nombre del Ejercicio:");
                String desc = JOptionPane.showInputDialog("Descripción:");
                String inst = JOptionPane.showInputDialog("Instrucciones de ejecución:");
                int descTime = Integer.parseInt(JOptionPane.showInputDialog("Segundos de descanso:"));

                int idN = catalogoEjercicios.size() + 1;
                Ejercicio nuevoE = new EjercicioEspecifico(idN, nom, desc, inst, catStr, descTime);
                catalogoEjercicios.add(nuevoE);
                JOptionPane.showMessageDialog(null, "Ejercicio creado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } else if (op == 2) {
                StringBuilder sb = new StringBuilder("Seleccione ID a editar:\n");
                for (Ejercicio e : catalogoEjercicios) {
                    sb.append(e.getId()).append(". ").append(e.getNombre()).append("\n");
                }
                int idEdit = Integer.parseInt(JOptionPane.showInputDialog(sb.toString()));
                Ejercicio target = null;
                for (Ejercicio e : catalogoEjercicios) {
                    if (e.getId() == idEdit) { target = e; break; }
                }
                if (target != null) {
                    String nuevoNom = JOptionPane.showInputDialog("Nuevo Nombre:", target.getNombre());
                    String nuevaDesc = JOptionPane.showInputDialog("Nueva Descripción:", target.getDescripcion());
                    target.setNombre(nuevoNom);
                    target.setDescripcion(nuevaDesc);
                    JOptionPane.showMessageDialog(null, "Ejercicio actualizado", "Modificación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (op == 3) {
                StringBuilder sb = new StringBuilder("Seleccione número de ejercicio a eliminar:\n");
                for (Ejercicio e : catalogoEjercicios) {
                    sb.append(e.getId()).append(". ").append(e.getNombre()).append("\n");
                }
                int idElim = Integer.parseInt(JOptionPane.showInputDialog(sb.toString()));
                Ejercicio target = null;
                for (Ejercicio e : catalogoEjercicios) {
                    if (e.getId() == idElim) { target = e; break; }
                }
                if (target != null) {
                    String conf = JOptionPane.showInputDialog("¿Desea eliminar " + target.getNombre() + "?\n\n1. Si\n2. No");
                    if (conf != null && conf.equals("1")) {
                        
                        // Remoción del catálogo maestro global.
                        catalogoEjercicios.remove(target);
                        
                        // Limpieza en cascada dentro de todas las rutinas de la plataforma.
                        for (Rutina r : baseDatosRutinas) {
                            r.getListaEjercicios().remove(target);
                        }
                        JOptionPane.showMessageDialog(null, "Ejercicio eliminado", "Fidness", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        
        // Red de seguridad para capturar y manejar errores en tiempo de ejecución. Si el código dentro de la estructura "try" falla el programa salta al bloque "catch" y brinda un mensaje de alerta en lugar de finalizar
        // la ejecución del programa de forma abrupta.
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error procesando el mantenimiento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Módulo del submenú para el armado y visualización de rutinas de entrenamiento.
     */
    private static void gestionarSubmenuRutinas() {
        String menu = "                           Mis Rutinas\n\n1. Ver rutinas\n2. Nueva rutina\n3. Editar rutina\n4. Eliminar rutina\n\nIngrese un número de opción para continuar:";
        String entrada = JOptionPane.showInputDialog(null, menu, "Entrenamiento Personal", JOptionPane.QUESTION_MESSAGE);
        if (entrada == null) return;

        // Estructura de manejo de excepciones para "intentar" ejecutar un bloque de código que es propenso a fallar.
        try {
            int op = Integer.parseInt(entrada);
            
            // Simulación de búsqueda de rutina ligada por índice/id al usuario activo
            Rutina miRutina = null;
            for (Rutina r : baseDatosRutinas) {
                if (r.getId() == usuarioLogueado.getId()) { miRutina = r; break; }
            }

            if (op == 1) {
                if (miRutina == null || miRutina.getListaEjercicios().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Aún no tienes rutinas creadas", "Rutina Vacía", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder sb = new StringBuilder("Rutina: " + miRutina.getNombre() + "\n\n");
                    for (Ejercicio e : miRutina.getListaEjercicios()) {
                        sb.append("\n- ").append(e.getNombre()).append("\n  ").append(e.getDescripcion());
                    }
                    JOptionPane.showMessageDialog(null, sb.toString(), "Visualizador de Rutinas", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (op == 2) {
                if (miRutina == null) {
                    miRutina = new Rutina(usuarioLogueado.getId(), "Plan de " + usuarioLogueado.getNombreCompleto(), new Date());
                    baseDatosRutinas.add(miRutina);
                }
                boolean agregando = true;
                while (agregando) {
                    StringBuilder sb = new StringBuilder("          Seleccione Ejercicio para agregar:\n\n");
                    for (Ejercicio e : catalogoEjercicios) {
                        sb.append(e.getId()).append(". [").append(e.getRecursoMultimedia()).append("] ").append(e.getNombre()).append("\n");
                    }
                    int ejId = Integer.parseInt(JOptionPane.showInputDialog(sb.toString()));
                    for (Ejercicio e : catalogoEjercicios) {
                        if (e.getId() == ejId) { miRutina.agregarEjercicio(e); break; }
                    }
                    String control = JOptionPane.showInputDialog("1. Agregar otro ejercicio\n2. Guardar rutina\n\nIngrese un número de opción para continuar:");
                    if (control != null && control.equals("2")) {
                        agregando = false;
                        JOptionPane.showMessageDialog(null, "Rutina creada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else if (op == 3) {
                if (miRutina == null || miRutina.getListaEjercicios().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay ejercicios para editar.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String controlEd = JOptionPane.showInputDialog("1. Agregar Ejercicio\n2. Eliminar Ejercicio");
                if (controlEd != null && controlEd.equals("1")) {
                    StringBuilder sb = new StringBuilder("Seleccione Ejercicio:\n");
                    for (Ejercicio e : catalogoEjercicios) sb.append(e.getId()).append(". ").append(e.getNombre()).append("\n");
                    int addId = Integer.parseInt(JOptionPane.showInputDialog(sb.toString()));
                    for (Ejercicio e : catalogoEjercicios) {
                        if (e.getId() == addId) { miRutina.agregarEjercicio(e); break; }
                    }
                } else if (controlEd != null && controlEd.equals("2")) {
                    if (miRutina.getListaEjercicios().size() <= 1) {
                        JOptionPane.showMessageDialog(null, "La rutina debe tener al menos un ejercicio", "Restricción de Negocio", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    StringBuilder sb = new StringBuilder("Seleccione Ejercicio a remover:\n");
                    for (int i = 0; i < miRutina.getListaEjercicios().size(); i++) {
                        sb.append(i).append(". ").append(miRutina.getListaEjercicios().get(i).getNombre()).append("\n");
                    }
                    int indexRem = Integer.parseInt(JOptionPane.showInputDialog(sb.toString()));
                    miRutina.getListaEjercicios().remove(indexRem);
                    JOptionPane.showMessageDialog(null, "Ejercicio quitado de la rutina.", "Fidness", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (op == 4) {
                if (miRutina != null) {
                    baseDatosRutinas.remove(miRutina);
                    JOptionPane.showMessageDialog(null, "Rutina eliminada", "Fidness", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        
        // Red de seguridad para capturar y manejar errores en tiempo de ejecución. Si el código dentro de la estructura "try" falla el programa salta al bloque "catch" y brinda un mensaje de alerta en lugar de finalizar
        // la ejecución del programa de forma abrupta.
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en gestión de rutinas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}