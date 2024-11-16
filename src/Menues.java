import java.util.Scanner;

public class Menues{

    private Scanner scanner;
    private SistemaDeAlumnos sistemaDeAlumnos;
    private SistemaDePagosYDeudas sistemaDePagosYDeudas;

    public Menues(){
        scanner = new Scanner(System.in);
        sistemaDeAlumnos = new SistemaDeAlumnos();
        sistemaDePagosYDeudas = new SistemaDePagosYDeudas();
    }

    public void smoke(){
        // Prueba rápida de funcionalidades.
        // Puede ejecutarse en modo debug para navegar todos los métodos definidos
        System.out.println("----------------------------");
        System.out.println("Carga de alumnos");
        sistemaDeAlumnos.registrarAlumno("11111111", "AAAAAAA AAAAA", 1);
        sistemaDeAlumnos.registrarAlumno("22222222", "BBBBBB BBBBBB", 2);
        sistemaDeAlumnos.registrarAlumno("33333333", "CCCCC CCCCC", 3);
        System.out.println("----------------------------");
        System.out.println("Generar cupones.");
        sistemaDePagosYDeudas.generarCupones(1);
        sistemaDePagosYDeudas.generarCupones(2);
        System.out.println("----------------------");
        System.out.println("Deudas pre pago");
        sistemaDePagosYDeudas.consultarDeudasAlumno("11111111");
        System.out.println("----------------------");
        System.out.println("Pago");
        sistemaDePagosYDeudas.cargarPago("11111111", 1);
        sistemaDePagosYDeudas.cargarPago("11111111", 2);
        System.out.println("----------------------");
        System.out.println("Deudas post pago");
        sistemaDePagosYDeudas.consultarDeudasAlumno("11111111");
        System.out.println("----------------------");
        System.out.println("Deudas totales");
        sistemaDePagosYDeudas.consultarTotalDeudas();
        System.out.println("----------------------");
        System.out.println("Pagos restantes");
        sistemaDePagosYDeudas.cargarPago("22222222", 1);
        sistemaDePagosYDeudas.cargarPago("22222222", 2);
        sistemaDePagosYDeudas.cargarPago("33333333", 1);
        sistemaDePagosYDeudas.cargarPago("33333333", 2);
        System.out.println("----------------------");
        System.out.println("Sin deudas");
        sistemaDePagosYDeudas.consultarTotalDeudas();
        sistemaDeAlumnos.bajaAlumno("11111111");
        sistemaDeAlumnos.bajaAlumno("22222222");
        sistemaDeAlumnos.bajaAlumno("33333333");
    }

    public void menuPrincipal(){

        int opcionPrincipal;

        do {
            // Muestra el menú principal de la aplicación
            System.out.println("=== Menú Principal ===");
            System.out.println("1. Alumnos");
            System.out.println("2. Pagos");
            System.out.println("3. Gastos");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            // Leemos la opción seleccionada por el usuario
            opcionPrincipal = 0;
            opcionPrincipal = scanner.nextInt();

            // Procesamos la opción seleccionada
            switch (opcionPrincipal) {
                case 1:
                    System.out.println("Has seleccionado la Opción 1: Alumnos");
                    menuAlumnos();
                    break;
                case 2:
                    System.out.println("Has seleccionado la Opción 2: Pagos");
                    menuPagos();
                    break;
                case 3:
                    System.out.println("Has seleccionado la Opción 3: Gastos NO IMPLEMENTADO");
                    break;
                case 4:
                    System.out.println("Saliendo del programa");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }

            System.out.println(); // Espacio entre iteraciones del menú

        } while (opcionPrincipal != 4); // Repite hasta que el usuario elija la opción 4 (Salir)

        scanner.close();
    }

    public void menuAlumnos() {
        int opcion;
        String dni;
        String nYA;
        int curso;

        do {
            // Muestra el menú principal de la aplicación
            System.out.println("=== Menú Alumnos ===");
            System.out.println("1. Registrar Alumno.");
            System.out.println("2. Dar de baja Alumno.");
            System.out.println("3. Volver al Menu Principal.");
            System.out.print("Seleccione una opción: ");

            // Leemos la opción seleccionada por el usuario
            opcion = scanner.nextInt();

            // Procesamos la opción seleccionada
            switch (opcion) {
                case 1:
                    scanner.nextLine();
                    System.out.println("Has seleccionado la Opción 1: Registrar Alumno");
                    System.out.println("Ingrese el DNI del Alumno: ");
                    dni = scanner.nextLine();
                    System.out.println("Ingrese el Nombre y Apellido del alumno: ");
                    nYA = scanner.nextLine();
                    System.out.println("Ingrese el curso del alumno: ");
                    curso = scanner.nextInt();
                    try {
                        sistemaDeAlumnos.registrarAlumno(dni, nYA, curso);
                    } catch (DatosIncorrectosException e){
                        System.out.println("Los datos ingresados son incorrectos, recuerde estos parámetros: ");
                        System.out.println("El nombre del alumno debe contener solo letras y espacios.");
                        System.out.println("El DNI del alumno solo debe contener 8 dígitos numéricos.");
                        System.out.println("El curso del alumno debe ser un valor numérico. ");
                    }
                    break;
                case 2:
                    System.out.println("Has seleccionado la Opción 2: Dar de baja Alumno");
                    System.out.println("Ingrese el DNI del alumno a eliminar: ");
                    dni = scanner.next();
                    try{
                        sistemaDeAlumnos.bajaAlumno(dni);
                    } catch (AlumnoNoEncontradoException e){
                        System.out.println("El DNI ingresado no se corresponde con un alumno en el sistema.");
                        System.out.println("Verifique el dato ingresado.");
                    }
                    break;
                case 3:
                    System.out.println("Volviendo al Menu Principal.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }

            System.out.println(); // Espacio entre iteraciones del menú

        } while (opcion != 3); // Repite hasta que el usuario elija la opción 3 (Salir)

    }

    public void menuPagos() {
        int opcion;
        int mes;
        String dni;

        do {
            // Muestra el menú principal de la aplicación
            System.out.println("=== Menú Alumnos ===");
            System.out.println("1. Generar Cupones.");
            System.out.println("2. Cargar Pago.");
            System.out.println("3. Consultar deudas de un alumno.");
            System.out.println("4. Consultar deudas totales.");
            System.out.println("5. Volver al Menu Principal.");
            System.out.print("Seleccione una opción: ");

            // Leemos la opción seleccionada por el usuario
            opcion = scanner.nextInt();

            // Procesamos la opción seleccionada
            switch (opcion) {
                case 1:
                    System.out.println("Has seleccionado la Opción 1: Generar Cupones");
                    System.out.println("Ingrese el mes de los cupones que desea generar: ");
                    mes = scanner.nextInt();
                    sistemaDePagosYDeudas.generarCupones(mes);
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.println("Has seleccionado la Opción 2: Cargar Pago.");
                    System.out.println("Ingrese el dni del alumno que paga: ");
                    dni = scanner.nextLine();
                    System.out.println("Ingrese mes a pagar: ");
                    mes = scanner.nextInt();
                    try {
                        sistemaDePagosYDeudas.cargarPago(dni, mes);
                        System.out.println("El pago fue cargado correctamente");
                    } catch (DatosIncorrectosException e){
                        System.out.println("No se encontró ningíun cupón con los datos ingresados.");
                    } catch ( PagoYaRealizadoException e) {
                        System.out.println("Este pago ya fue realizado.");
                    }
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.println("Has seleccionado la Opción 3: Consultar deudas de un Alumno.");
                    System.out.println("Ingrese el DNI del alumno del que desea consultar deudas: ");
                    dni = scanner.nextLine();
                    sistemaDePagosYDeudas.consultarDeudasAlumno(dni);
                    break;
                case 4:
                    System.out.println("Has seleccionado la Opción 4: Consultar deudas totales.");
                    sistemaDePagosYDeudas.consultarTotalDeudas();
                    break;
                case 5:
                    System.out.println("Volviendo al Menu Principal.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }

            System.out.println(); // Espacio entre iteraciones del menú

        } while (opcion != 5); // Repite hasta que el usuario elija la opción 5 (Salir)

    }
}
