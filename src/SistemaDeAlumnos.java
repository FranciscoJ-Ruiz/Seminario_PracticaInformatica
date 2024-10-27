import java.util.ArrayList;

public class SistemaDeAlumnos {

    private ArrayList<Alumno> alumnos;

    public SistemaDeAlumnos(){
        alumnos = new ArrayList<>();
    }

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    // Función de registro de un nuevo alumno.
    // Se valida que los datos ingresados sean correctos, en caso de que lo sean carga el nuevo alumno al sistema.
    // Para claridad se imprime por pantalla un mensaje de éxito mostrando los datos ingresados.
    // Arroja una excepcion si los datos no respetan el formato adecuado.

    public void registrarAlumno(String dni, String nYA, int curso){
        Alumno nuevoAlumno =  new Alumno(dni, nYA, curso);
        if (validarNuevoAlumno(nuevoAlumno)){
            alumnos.add(nuevoAlumno);
            System.out.println("Alumno registrado exitosamente con los datos:");
            System.out.println("DNI: " + dni + ", Nombre completo: " + nYA + ", Curso: " + curso);
        } else{
            throw new DatosIncorrectosException();
        }
    }

    public void bajaAlumno(String dni) {
        // Se valida si el alumno existe, en caso de que exista lo elimina
        // En caso de que no exista arroja una excepción.

        if (validarAlumnoExistente(dni)) {
            for (Alumno alumno : alumnos) {
                if (alumno.getDni().equals(dni)) {
                    alumnos.remove(alumno);
                    System.out.println("Alumno eliminado exitosamente.");
                    break;
                }
            }
        } else {
            throw new AlumnoNoEncontradoException(dni);
        }
    }

    public boolean validarNuevoAlumno(Alumno alumno){
        // Valida que los datos del alumno sean correctos y que el alumno no exista previamente

        return (alumno.verificarDatosCorrectos() && !validarAlumnoExistente(alumno.getDni()));
    }

    public boolean validarAlumnoExistente(String dni){
        // Recorre la linked list verigicando si el alumno existe o no
        // retorna true si el alumno existe y false si no.

        for (Alumno alumno : alumnos) {
            if (alumno.getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }

}
