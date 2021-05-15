import interfaces.repositorio.ServicioSrOperadorInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioSrOperadorImpl extends UnicastRemoteObject implements ServicioSrOperadorInterface {

    protected ServicioSrOperadorImpl() throws RemoteException {
        super();
    }
}
