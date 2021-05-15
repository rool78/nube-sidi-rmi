import interfaces.cliente.ServicioDiscoClienteInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioDiscoClienteImpl extends UnicastRemoteObject implements ServicioDiscoClienteInterface {

    protected ServicioDiscoClienteImpl() throws RemoteException {
        super();
    }

    @Override
    public int bajarFichero(int fichero) throws RemoteException {
        System.out.println("Has conseguido llegar aquí, enohorabuena... " + fichero);

        return 200;
    }
}
