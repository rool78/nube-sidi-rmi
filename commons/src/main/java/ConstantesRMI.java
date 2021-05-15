public class ConstantesRMI {

    public static final int PUERTO_SERVIDOR = 8080;
    public static final String IP = "localhost";

    public static final String DIRECCION_AUTENTICADOR = "rmi://" + IP + ":" + PUERTO_SERVIDOR + "/autenticador";
    public static final String DIRECCION_DATOS = "rmi://" + IP + ":" + PUERTO_SERVIDOR + "/datos";

    public static final String DIRECCION_DISCO_CLIENTE = "rmi://" + IP + ":" + "8081" + "/discocliente/";


}
