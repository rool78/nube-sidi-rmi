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
     * @return
     * @throws RemoteException
     */
    public String listarClientes() throws RemoteException;

    public String listarRepositorios() throws RemoteException;

    public String listarParejasRepositorioCliente() throws RemoteException;

    public String listarClientesRepositorio(int id) throws RemoteException;

    public String obtenerUrlRepositorioDeCliente(int clienteId) throws RemoteException;

    public String obtenerUrlRepositorio(int clienteId) throws RemoteException;

    public String listarFicherosCliente(int idCliente) throws RemoteException;

    public int ficheroSubido(Metadatos ficheroSubido) throws RemoteException;

    public int ficheroBorrado(Metadatos ficheroBorrado) throws RemoteException;
}
