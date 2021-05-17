import commons.Fichero;
import commons.interfaces.cliente.ServicioDiscoClienteInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioDiscoClienteImpl extends UnicastRemoteObject implements ServicioDiscoClienteInterface {

    protected ServicioDiscoClienteImpl() throws RemoteException {
        super();
    }

    @Override
    public int bajarFichero(Fichero fichero, int idCliente) throws RemoteException {

        return 0;
    }
}
