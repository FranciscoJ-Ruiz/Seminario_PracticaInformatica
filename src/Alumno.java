public class Alumno {
    private String dni;
    private String nombreYApellido;
    private int curso;

    public Alumno(String dni, String nombreYApellido, int curso){
        this.dni = dni;
        this.nombreYApellido = nombreYApellido;
        this.curso = curso;
    }

    public String getDni(){
        return dni;
    }

    public String getNombreYApellido(){
        return nombreYApellido;
    }

    public int getCurso(){
        return curso;
    }

    public boolean verificarDatosCorrectos(){
        //  regexDni acepta Strings que solo contengan 8 números
        //  regexNYA acepta Strings que solo contengan letras y números y al menos 5 caracteres.
        String regexDni = "^[0-9]{8}$";
        String regexNYA = "^[a-zA-Z ]{5,}$";

        return (this.getDni().matches(regexDni) && this.getNombreYApellido().matches(regexNYA) &&
                1 <= this.getCurso() && this.getCurso() <= 6);
    }
}
