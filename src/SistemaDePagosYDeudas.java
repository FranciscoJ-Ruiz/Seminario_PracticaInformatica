import java.util.ArrayList;
import java.util.Iterator;

public class SistemaDePagosYDeudas {

    private ArrayList<Cupon> cupones;

    public SistemaDePagosYDeudas(int capacidad){
        cupones = new ArrayList<Cupon>();
    }

    // Getter
    public ArrayList<Cupon> getCupones() {
        return cupones;
    }

    // Funciones de la clase

    public void generarCupones(SistemaDeAlumnos alumnos, int mes){
        // Método que recibe un int y genera un listado de nuevos cupones para todos los alumnos.
        // Known issue: Con un uso inapropiado, podrían generarse cupones duplicados.
        ArrayList<Alumno> listado = alumnos.getAlumnos();

        for (Alumno alumno : listado){
            cupones.add(new Cupon(mes, alumno.getDni()));
        }
        System.out.println("Cupones para el mes " + mes + " generados exitosamente.");
    }

    public void cargarPago(String dni, int mes){
        // Recibe los datos de un alumno y un mes (Cupon) para efectuar un pago.
        // Verifica que el cupon exista, si no, arroja DatosIncorrectosException.
        // Luego verifica que el pago no haya sido efectuado previamente, en ese caso
        // arroja PagoYaRealizadoException.

        Iterator<Cupon> iterator = cupones.iterator();
        boolean pagoRealizado = false;

        while (iterator.hasNext() && !pagoRealizado){
            Cupon cupon = iterator.next();
            if (cupon.getDni().equals(dni) && cupon.getMes() == mes){
                if (cupon.pagar()){
                    System.out.println("Pago realizado " + mes);
                    pagoRealizado = true;
                } else {
                    throw new PagoYaRealizadoException(dni, mes);
                }
            }
        }
        if (!pagoRealizado) {
            throw new DatosIncorrectosException();
        }
    }


    public void consultarDeudasAlumno(String dni) {
        // Función que muestra por pantalla todos los cupones impagos de un alumno específico.
        // En caso que el alumno no posea deudas lo indica.
        boolean sinDeuda = true;

        System.out.println("El alumno con DNI: " + dni);
        for(Cupon cupon: cupones){
            if(cupon.getDni().equals(dni) && !cupon.getPago()){
                sinDeuda = false;
                System.out.println("Adeuda el mes " + cupon.getMes());
            }
        }

        if (sinDeuda) System.out.println("No posee deudas al día de la fecha.");
    }

    public void consultarTotalDeudas(){
        // Función que muestra por pantalla todos los cupones impagos.
        // En caso de que no existan deudas lo indica por pantalla.

        boolean sinDeudas = true;
        for(Cupon cupon: cupones){
            if(!cupon.getPago()){
                sinDeudas = false;
                System.out.println("El alumno " + cupon.getDni() + " adeuda el mes " + cupon.getMes());
            }
        }
        if (sinDeudas) {
            System.out.println("Al día de la fecha, ningun alumno adeuda sus pagos.");
        }
    }
}
