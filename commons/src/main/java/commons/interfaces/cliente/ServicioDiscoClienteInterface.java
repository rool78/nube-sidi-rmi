/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

package commons.interfaces.cliente;

import commons.Fichero;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioDiscoClienteInterface extends Remote {

    /**
     * Permite realizar la descarga de un fichero en el disco del cliente
     * @param fichero
     * @return Codigo respuesta
     * @throws RemoteException
     */
    int bajarFichero(Fichero fichero) throws RemoteException;

}