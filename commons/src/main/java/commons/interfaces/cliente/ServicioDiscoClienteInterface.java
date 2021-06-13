package commons.interfaces.cliente;

import commons.Fichero;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioDiscoClienteInterface extends Remote {

    int bajarFichero(Fichero fichero) throws RemoteException;

}