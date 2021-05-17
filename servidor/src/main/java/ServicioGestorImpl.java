import commons.interfaces.servidor.ServicioGestorInterface;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {


    protected ServicioGestorImpl() throws RemoteException {
        super();
    }

    @Override
    public int subirFichero(String nombreFichero, int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException {

        return 0;
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

