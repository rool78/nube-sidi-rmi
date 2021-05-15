public enum Respuesta {

    OK(0),
    USUARIO_YA_REGISTRADO(-1),
    USUARIO_O_PASSWORD_INCORRECTO(-2)
    ;

    private final int codigo;

    private Respuesta(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
