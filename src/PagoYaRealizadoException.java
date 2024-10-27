public class PagoYaRealizadoException extends RuntimeException{

    public PagoYaRealizadoException(String dni, int mes){
        super("El cupon para dni: " + dni + "y mes: " + mes + " ya se encuentra pago." );
    }

}
