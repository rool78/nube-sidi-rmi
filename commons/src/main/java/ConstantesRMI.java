public class ConstantesRMI {

    public static final int PUERTO_SERVIDOR = 8080;
    public static final int PUERTO_CLIENTE = 8081;
    public static final int PUERTO_REPOSITORIO = 8082;

    public static final String IP = "localhost";

    public static final String DIRECCION_AUTENTICADOR = "rmi://" + IP + ":" + PUERTO_SERVIDOR + "/autenticador";
    public static final String DIRECCION_DATOS = "rmi://" + IP + ":" + PUERTO_SERVIDOR + "/datos";
    public static final String DIRECCION_GESTOR = "rmi://" + IP + ":" + PUERTO_SERVIDOR + "/gestor";

    public static final String DIRECCION_DISCO_CLIENTE = "rmi://" + IP + ":" + PUERTO_CLIENTE + "/discocliente";

    public static final String DIRECCION_CL_OPERADOR = "rmi://" + IP + ":" + PUERTO_REPOSITORIO + "/cloperador";
    public static final String DIRECCION_SR_OPERADOR = "rmi://" + IP + ":" + PUERTO_REPOSITORIO + "/sroperador";


}
