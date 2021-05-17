package commons.interfaces.repositorio;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioSrOperadorInterface extends Remote {

    public int crearCarpeta(int idCliente) throws RemoteException;

    public int bajarFichero(String URLdiscoCliente,String nombreFichero,int idCliente) throws MalformedURLException, RemoteException, NotBoundException;

}
