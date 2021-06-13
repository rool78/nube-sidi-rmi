/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

package commons.interfaces.servidor;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioAutenticacionInterface extends Remote {

    public int autenticarCliente (String nombre, String password) throws RemoteException;

    public int registrarCliente (String nombre, String password) throws RemoteException, MalformedURLException, NotBoundException;

    public int autenticarRepositorio(String nombre) throws RemoteException;

    public int registrarRepositorio(String nombre) throws RemoteException;

    public void desconectarCliente(String nombre) throws RemoteException;

    public void desconectarRepositorio(String nombre) throws RemoteException;

}
