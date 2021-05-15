public enum Respuesta {

    USUARIO_YA_REGISTRADO(-1),
    OK(0)
    ;

    private final int codigo;

    private Respuesta(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
