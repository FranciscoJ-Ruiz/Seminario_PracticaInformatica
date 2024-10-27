public class AlumnoNoEncontradoException extends RuntimeException {

    public AlumnoNoEncontradoException(String dni){
        super("Alumno con DNI " + dni + " no encontrado.");
    }


}
