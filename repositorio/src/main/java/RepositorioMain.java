import interfaces.servidor.ServicioAutenticacionInterface;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RepositorioMain {

    private ServicioAutenticacionInterface servicioAutenticacion;

    public static void main(String[] args) {

    }

    public RepositorioMain() {

    }

    public void launch() {
// si el servidor no esta disponible, cerramos informando de ello
        try {
            servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup(ConstantesRMI.DIRECCION_AUTENTICADOR);

            int opcion;
            do {
                opcion = Gui.menu("Acceso a Repositorio", new String[]{"Registrar nuevo Repositorio", "Autenticar Repositorio (login)"});
                switch (opcion) {
                    case 1:
                        registrarRepositorio();
                        break;
                    case 2:
                        autenticarRepositorio();
                        break;
                }
            } while (opcion != 3);
        } catch (ConnectException e) {
            System.out.println("Error de conexion, el servidor no esta disponible, vuelva a intentarlo mas tarde");
//           Gui.entradaTexto("Pulse enter para finalizar...");
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            e.printStackTrace();
        }
    }

    private void registrarRepositorio() throws RemoteException {
        String nombreRepositorio = Gui.entradaTexto("Introduzca nombre repositorio");
        int respuesta = servicioAutenticacion.registrarRepositorio(nombreRepositorio);
    }

    private void autenticarRepositorio() throws RemoteException {
        String nombreRepositorio = Gui.entradaTexto("Introduzca nombre repositorio");
        int respuesta = servicioAutenticacion.autenticarRepositorio(nombreRepositorio);
    }

}
