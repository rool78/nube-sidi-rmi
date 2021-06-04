package commons.interfaces.servidor;

import commons.Metadatos;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioGestorInterface extends Remote {

    //todo objeto datos?
    public String subirFichero(int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException;

    public String bajarFichero(String URLdiscoCliente,int idFichero,int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException;

    public String listarFicherosCliente(int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException;

    public String listarClientes() throws MalformedURLException, RemoteException, NotBoundException;

    //todo objeto datos?
    public String borrarFichero(int idFichero,int idSesionCliente) throws MalformedURLException, RemoteException, NotBoundException;

    public int ficheroSubido(Metadatos ficheroSubido) throws RemoteException;

    public int ficheroBorrado(Metadatos ficheroBorrado) throws RemoteException;

}
