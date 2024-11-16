import java.sql.SQLException;
import java.sql.Statement;

public class SistemaDeAlumnos {

    // Función de registro de un nuevo alumno.
    // Se valida que los datos ingresados sean correctos, en caso de que lo sean carga el nuevo alumno al sistema.
    // Para claridad se imprime por pantalla un mensaje de éxito.
    // Arroja una excepcion si los datos no respetan el formato adecuado.
    public void registrarAlumno(String dni, String nYA, int curso){
        Alumno nuevoAlumno =  new Alumno(dni, nYA, curso);
        if (validarNuevoAlumno(nuevoAlumno)){

            String sql = "INSERT INTO alumno(dni, nombreYApellido, curso) VALUES (?, ?, ?)";

            try (var connection = MySQLConnection.connect();
                 var stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, dni);
                stmt.setString(2, nYA);
                stmt.setInt(3, curso);

                stmt.executeUpdate();
                System.out.println("El alumno fue ingresado correctamente al sistema");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        } else{
            throw new DatosIncorrectosException();
        }
    }

    public void bajaAlumno(String dni) {
        // Se elimina un alumno de la base de datos.
        // En caso de que no exista arroja una excepción AlumnoNoEncontradoException.

        String sql = "DELETE FROM alumno WHERE dni = ?";

        try (var connection = MySQLConnection.connect();
             var stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, dni);

            int rowAffected = stmt.executeUpdate();

            if (rowAffected > 0){
                System.out.println("El alumno fue borrado exitosamente del sistema.");
            } else {
                throw new AlumnoNoEncontradoException(dni);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean validarNuevoAlumno(Alumno alumno){
        // Valida que los datos del alumno sean correctos
        return alumno.verificarDatosCorrectos();
    }

    // Función que quedo en desuso luego de la incorporación de la base de datos
    /*public boolean validarAlumnoExistente(String dni){
        // Recorre la linked list verigicando si el alumno existe o no
        // retorna true si el alumno existe y false si no.

        for (Alumno alumno : alumnos) {
            if (alumno.getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }*/

}
