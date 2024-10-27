public class Cupon {
    private int mes;
    private String dni;
    private boolean pago;

    public Cupon(int mes, String dni){
        this.mes = mes;
        this.dni = dni;
    }

    public int getMes(){
        return mes;
    }

    public String getDni(){
        return dni;
    }

    public boolean getPago(){
        return pago;
    }

    public boolean pagar(){
        // En caso de que un cupon se encuentre realizado retorna falso.
        // Si el pago aun no está cargado, lo carga y retorna true.
        if (pago){ return false; }
        pago = true;
        return true;
    }
}
