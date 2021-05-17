package commons.interfaces.servidor;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServicioGestorInterface extends Remote {

    //todo objeto datos?
    public int subirFichero(String nombreFichero,int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException;

    public String bajarFichero(String URLdiscoCliente,int idFichero,int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException;

    public List<String> listarFicherosCliente(int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException;

    public String listarClientes() throws MalformedURLException, RemoteException, NotBoundException;

    //todo objeto datos?
    public String borrarFichero(int idFichero,int idSesionCliente) throws MalformedURLException, RemoteException, NotBoundException;

}
