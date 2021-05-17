public enum Respuesta {

    OK(0),
    NOMBRE_YA_EN_USO(-1),
    USUARIO_O_PASSWORD_INCORRECTO(-2),
    NO_HAY_REPOSITORIOS_EN_LINEA(-3),
    ERROR_AL_CREAR_CARPETA(-4),
    ERROR_AUTENTICACION(-5),
    ;

    private final int codigo;

    private Respuesta(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
