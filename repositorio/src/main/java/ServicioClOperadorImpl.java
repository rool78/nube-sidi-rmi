import interfaces.repositorio.ServicioClOperadorInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioClOperadorImpl extends UnicastRemoteObject implements ServicioClOperadorInterface {

    protected ServicioClOperadorImpl() throws RemoteException {
        super();
    }
}
