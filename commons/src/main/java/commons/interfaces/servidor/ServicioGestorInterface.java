/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

package commons.interfaces.servidor;

import commons.Metadatos;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioGestorInterface extends Remote {

    public String subirFichero(int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException;

    public int bajarFichero(String URLdiscoCliente, String nombreFichero, int idCliente) throws RemoteException, MalformedURLException, NotBoundException;

    public String listarFicherosCliente(int idSesionCliente) throws RemoteException, MalformedURLException, NotBoundException;

    /**
     * Lista clientes registrados en el sistema
     * @return
     * @throws RemoteException
     */
    public String listarClientes() throws RemoteException, NotBoundException, MalformedURLException;

    public String listarClientesRepositorio(int id) throws RemoteException;

    public String borrarFichero(int idSesionCliente) throws MalformedURLException, RemoteException, NotBoundException;

    public int ficheroSubido(Metadatos ficheroSubido) throws RemoteException;

    public int ficheroBorrado(Metadatos ficheroBorrado) throws RemoteException;

}
