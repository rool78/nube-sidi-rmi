package interfaces.cliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioDiscoClienteInterface extends Remote {

    int bajarFichero(int fichero) throws RemoteException;
}