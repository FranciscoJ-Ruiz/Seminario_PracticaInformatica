import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SistemaDePagosYDeudas {

    // Funciones de la clase
    public void generarCupones(int mes){

        String selectSQL = "SELECT * FROM alumno";
        String insertSQL = "INSERT INTO cupon (dniAlumno, mes, pagoRealizado) VALUES (?, ?, ?)";

        try (var connection = MySQLConnection.connect();
             var selectStmt = connection.prepareStatement(selectSQL, Statement.RETURN_GENERATED_KEYS);
             var insertStmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)
        ) {

            // Ejecuto la query para obtener todos los alumnos y los guardo en un set de datos.
            ResultSet alumnos = selectStmt.executeQuery();

            // Para cada alumno en el set, generamos un cupón con su DNI y el mes indicado.
            // Luego lo insertamos en la tabla cupon.
            while (alumnos.next()) {
                String dni = alumnos.getString("dni");

                insertStmt.setString(1, dni);
                insertStmt.setInt(2, mes);
                insertStmt.setBoolean(3, false);
                insertStmt.executeUpdate();
            }

            // Si se llegó a este punto sin excepciones los cupones fueron cargados correctamente.
            System.out.println("Cupones para el mes " + mes + " generados exitosamente.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void cargarPago(String dni, int mes){
        // Recibe los datos de un alumno y un mes (Cupon) para efectuar un pago.
        // Luego verifica que el pago no haya sido efectuado previamente, invocando
        // el método verificar estado del pago.
        // En caso de no haber excepción se continua a cargar el pago.

        var sql = "UPDATE cupon SET pagoRealizado = ? WHERE (dniAlumno = ? AND mes = ?)";

        // Valida que el pago no se encuentra ya cargado
        verificarEstadoDelPago(dni, mes);

        // Si el pago no se encuentra previamente cargado, carga el pago.
        try (var connection = MySQLConnection.connect();
             var stmt = connection.prepareStatement(sql)) {
            // prepare data for update
            stmt.setBoolean(1, true);
            stmt.setString(2, dni);
            stmt.setInt(3, mes);

            // execute the update
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new PagoYaRealizadoException(dni, mes);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void verificarEstadoDelPago(String dniAlumno, int mes) {
        var sql = "SELECT pagoRealizado FROM cupon WHERE (dniAlumno = ? AND mes = ?)";

        try (var connection = MySQLConnection.connect();
             var stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, dniAlumno);
            stmt.setInt(2, mes);

            ResultSet pagos = stmt.executeQuery();
            if (pagos.next()) {
                boolean pagoRealizado = pagos.getBoolean("pagoRealizado");

                if (pagoRealizado) {
                    throw new PagoYaRealizadoException(dniAlumno, mes);
                }
            } else {
                throw new DatosIncorrectosException();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void consultarDeudasAlumno(String dni) {
        // Función que muestra por pantalla todos los cupones impagos de un alumno específico.
        // En caso que el alumno no posea deudas lo indica.

        var sql = "SELECT mes FROM cupon WHERE (dniAlumno = ? AND pagoRealizado = false)";

        try (var connection = MySQLConnection.connect();
             var stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, dni);

            ResultSet deudas = stmt.executeQuery();
            if (deudas.next()) {
                System.out.println("El alumno con DNI: " + dni + " Adeuda los siguientes meses: ");
                System.out.println(deudas.getInt("mes"));
                while (deudas.next()) {
                    System.out.println(deudas.getInt("mes"));
                }
            } else {
                System.out.println("El alumno con DNI: " + dni + " No posee deudas al día de la fecha.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void consultarTotalDeudas(){
        // Función que muestra por pantalla todos los cupones impagos.

        var sql = "SELECT dniAlumno, mes FROM cupon WHERE pagoRealizado = false ORDER BY dniAlumno";

        try (var connection = MySQLConnection.connect();
             var stmt = connection.prepareStatement(sql)) {

            ResultSet deudas = stmt.executeQuery();

            while (deudas.next()) {
                System.out.println("El alumno con DNI: " + deudas.getString("dniAlumno") + " Adeuda el mes: " + deudas.getInt("mes"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


}
