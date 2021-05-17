import commons.Fichero;
import commons.interfaces.repositorio.ServicioClOperadorInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioClOperadorImpl extends UnicastRemoteObject implements ServicioClOperadorInterface {

    protected ServicioClOperadorImpl() throws RemoteException {
        super();
    }

    @Override
    public int subirFichero(Fichero fichero) throws RemoteException {
        return 0;
    }

    @Override
    public int borrarFichero(String fichero, String carpeta) throws RemoteException {
        return 0;
    }
}
