import commons.ConstantesRMI;
import commons.Gui;
import commons.Utils;
import commons.interfaces.repositorio.ServicioSrOperadorInterface;
import commons.interfaces.servidor.ServicioAutenticacionInterface;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RepositorioMain {

    private ServicioAutenticacionInterface servicioAutenticacion;
    private int idRepositorio;

    public static void main(String[] args) throws MalformedURLException, RemoteException {
        new RepositorioMain().launch();
    }

    public RepositorioMain() throws MalformedURLException, RemoteException {
        //levantarServicios();
    }

    public void launch() {

        try {
            servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup(ConstantesRMI.DIRECCION_AUTENTICADOR);
            //todo borrar creamos repo directamente
            servicioAutenticacion.registrarRepositorio("repo");

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
//           commons.Gui.entradaTexto("Pulse enter para finalizar...");
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            e.printStackTrace();
        }
    }

    private void registrarRepositorio() throws RemoteException {
        String nombreRepositorio = Gui.entradaTexto("Introduzca nombre repositorio");
        int respuesta = servicioAutenticacion.registrarRepositorio(nombreRepositorio);

        //TODO Nos faltaria levantarlo
        //Lo que hacen es registrar y luego autenticar. Despues de autenticar se levnata el servicio
    }

    private void autenticarRepositorio() throws RemoteException, MalformedURLException {
        String nombreRepositorio = Gui.entradaTexto("Introduzca nombre repositorio");
        int respuesta = servicioAutenticacion.autenticarRepositorio(nombreRepositorio);
        if (respuesta > -1) {
            idRepositorio = respuesta;
            levantarServicioRepositorio();
        }
    }

    private void levantarServicioRepositorio() throws RemoteException, MalformedURLException {
        //TODO lanzar servicio cliente operador y servidor operador con el id del repositorio que se ha autenticado
        ServicioSrOperadorImpl objetoSrOperador = new ServicioSrOperadorImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_SR_OPERADOR + "/" + idRepositorio, objetoSrOperador);
        System.out.println("Operacion: Servicio Servidor Operador preparado con exito");
        System.out.println("--> " + ConstantesRMI.DIRECCION_SR_OPERADOR + "/" + idRepositorio);

        ServicioClOperadorImpl objetoClOperador = new ServicioClOperadorImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_CL_OPERADOR + "/" + idRepositorio, objetoClOperador);
        System.out.println("Operacion: Servicio Cliente Operador preparado con exito");
        System.out.println("--> " + ConstantesRMI.DIRECCION_CL_OPERADOR + "/" + idRepositorio);

        //TODO De alguna forma en este punto tenemos que continuar el flujo del programa y lanzar el menu de repositorio autenticado

    }

    private void levantarServicios() throws RemoteException, MalformedURLException {
        Utils.arrancarRegistro(ConstantesRMI.PUERTO_REPOSITORIO);
        Utils.setCodeBase(ServicioSrOperadorInterface.class);

        //levantamos servicio SrOperador
        ServicioSrOperadorImpl servicioSrOperador = new ServicioSrOperadorImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_SR_OPERADOR, servicioSrOperador);
        System.out.println("ServicioSrOperador preparado con exito");

        //levantamos servicio ClOperador
        ServicioClOperadorImpl servicioClOperador = new ServicioClOperadorImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_CL_OPERADOR, servicioClOperador);
        System.out.println("ServicioClOperador preparado con exito");
    }

}
