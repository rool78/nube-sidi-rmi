package commons.interfaces.repositorio;

import commons.Fichero;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioClOperadorInterface extends Remote {

    int subirFichero(Fichero fichero) throws RemoteException;

    int borrarFichero(String fichero, String carpeta) throws RemoteException;

}
