/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

package commons.interfaces.servidor;

import commons.Metadatos;

import java.rmi.RemoteException;

public interface ServicioDatosInterface extends ServicioAutenticacionInterface {

    /**
     * Lista clientes registrados en el sistema
     * @return Texto con los clientes
     * @throws RemoteException
     */
    public String listarClientes() throws RemoteException;

    /**
     * Lista repositorios registrados en el sistema
     * @return Texto con los repositorios
     * @throws RemoteException
     */
    public String listarRepositorios() throws RemoteException;

    /**
     * Lista los repositorios con los clientes que tiene asociados
     * @return Texto con la inforamcion
     * @throws RemoteException
     */
    public String listarParejasRepositorioCliente() throws RemoteException;

    /**
     * Lista los clientes asociados a un repositorio
     * @param idRepositorio
     * @return Texto con la informacion
     * @throws RemoteException
     */
    public String listarClientesRepositorio(int idRepositorio) throws RemoteException;

    /**
     * Obtiene la url del repositorio de un cliente
     * @param clienteId
     * @return url
     * @throws RemoteException
     */
    public String obtenerUrlRepositorioDeCliente(int clienteId) throws RemoteException;

    /**
     * Obtiene la url servicio operador
     * @param clienteId
     * @return url
     * @throws RemoteException
     */
    public String obtenerUrlRepositorio(int clienteId) throws RemoteException;

    /**
     * Lista los ficheros de un cliente en el repositorio
     * @param idCliente
     * @return Texto con los ficheros disponibles
     * @throws RemoteException
     */
    public String listarFicherosCliente(int idCliente) throws RemoteException;

    /**
     * Callback cuando un fichero se ha subido
     * @param ficheroSubido
     * @return Codigo respuesta
     * @throws RemoteException
     */
    public int ficheroSubido(Metadatos ficheroSubido) throws RemoteException;

    /**
     * Callback cuando un fichero se ha borrado
     * @param ficheroBorrado
     * @return Codigo respuesta
     * @throws RemoteException
     */
    public int ficheroBorrado(Metadatos ficheroBorrado) throws RemoteException;
}
