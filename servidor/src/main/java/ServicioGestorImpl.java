import interfaces.servidor.ServicioGestorInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {


    protected ServicioGestorImpl() throws RemoteException {
        super();
    }
}

