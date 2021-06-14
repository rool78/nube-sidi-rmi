/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

package commons.interfaces.repositorio;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioSrOperadorInterface extends Remote {

    /**
     * Crea una carpeta asociada a un cliente
     * @param idCliente
     * @return Codigo respuesta
     * @throws RemoteException
     */
    public int crearCarpeta(int idCliente) throws RemoteException;

    /**
     * Intermediario servidor-disco cliente para bajar un fichero
     * @param URLdiscoCliente
     * @param nombreFichero
     * @param idCliente
     * @return Codigo respuesta
     * @throws MalformedURLException
     * @throws RemoteException
     * @throws NotBoundException
     */
    public int bajarFichero(String URLdiscoCliente,String nombreFichero,int idCliente) throws MalformedURLException, RemoteException, NotBoundException;

}
