import commons.ConstantesRMI;
import commons.interfaces.servidor.ServicioDatosInterface;
import commons.interfaces.servidor.ServicioGestorInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {

    private final ServicioDatosInterface servicioDatos;

    protected ServicioGestorImpl() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        this.servicioDatos = (ServicioDatosInterface) Naming.lookup(ConstantesRMI.DIRECCION_DATOS);
    }

    @Override
    public String subirFichero(int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("##Servicio gestor: subirFichero");
        return this.servicioDatos.obtenerIdRepositorioDeCliente(idSesionCliente);
    }

    @Override
    public String bajarFichero(String URLdiscoCliente, int idFichero, int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException {
        return null;
    }

    @Override
    public List<String> listarFicherosCliente(int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException {
        return null;
    }

    @Override
    public String listarClientes() throws MalformedURLException, RemoteException, NotBoundException {
        return null;
    }

    @Override
    public String borrarFichero(int idFichero, int idSesionCliente) throws MalformedURLException, RemoteException, NotBoundException {
        return null;
    }
}

