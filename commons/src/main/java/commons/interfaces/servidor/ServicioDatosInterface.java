package commons.interfaces.servidor;

import commons.Metadatos;

import java.rmi.RemoteException;

public interface ServicioDatosInterface extends ServicioAutenticacionInterface {

    public String listarClientes() throws RemoteException;

    public String obtenerIdRepositorioDeCliente(int clienteId) throws RemoteException;

    public String listarFicherosCliente(int idCliente) throws RemoteException;

    public int ficheroSubido(Metadatos ficheroSubido) throws RemoteException;

    public int fihceroBorrado(Metadatos ficheroBorrado) throws RemoteException;
}
