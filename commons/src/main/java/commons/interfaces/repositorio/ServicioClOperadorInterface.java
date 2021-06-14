/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

package commons.interfaces.repositorio;

import commons.Fichero;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioClOperadorInterface extends Remote {

    /**
     * Subida de un fichero por el cliente
     * @param fichero
     * @param idCliente
     * @return Codigo respuesta
     * @throws RemoteException
     */
    int subirFichero(Fichero fichero, int idCliente) throws RemoteException;

    /**
     * Borrado de un fichero por el cliente
     * @param idCliente
     * @param fichero
     * @return Codigo respuesta
     * @throws RemoteException
     */
    int borrarFichero(int idCliente, String fichero) throws RemoteException;

}
