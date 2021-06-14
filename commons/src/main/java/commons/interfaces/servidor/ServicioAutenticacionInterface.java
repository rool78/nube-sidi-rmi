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

    /**
     * Procesa la autenticacion de un cliente
     * @param nombre
     * @param password
     * @return Codigo respuesta
     * @throws RemoteException
     */
    public int autenticarCliente (String nombre, String password) throws RemoteException;

    /**
     * Procesa el registro de un cliente
     * @param nombre
     * @param password
     * @return Codigo respuesta
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws NotBoundException
     */
    public int registrarCliente (String nombre, String password) throws RemoteException, MalformedURLException, NotBoundException;

    /**
     * Procesa la autenticacion de un repositorio
     * @param nombre
     * @return Codigo respuesta
     * @throws RemoteException
     */
    public int autenticarRepositorio(String nombre) throws RemoteException;

    /**
     * Procesa el registro de un nuevo repositorio
     * @param nombre
     * @return
     * @throws RemoteException
     */
    public int registrarRepositorio(String nombre) throws RemoteException;

    /**
     * Desconexion de un cliente
     * @param nombre
     * @throws RemoteException
     */
    public void desconectarCliente(String nombre) throws RemoteException;

    /**
     * Desconexion de un repositorio
     * @param nombre
     * @throws RemoteException
     */
    public void desconectarRepositorio(String nombre) throws RemoteException;

}
