package commons.interfaces.repositorio;

import commons.Fichero;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioClOperadorInterface extends Remote {

    int subirFichero(Fichero fichero, int idCliente) throws RemoteException;

    int borrarFichero(int idCliente, String fichero) throws RemoteException;

}
