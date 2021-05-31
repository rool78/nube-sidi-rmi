package commons.interfaces.servidor;

import java.rmi.RemoteException;

public interface ServicioDatosInterface extends ServicioAutenticacionInterface {

    public String listarClientes() throws RemoteException;

    public String obtenerIdRepositorioDeCliente(int clienteId) throws RemoteException;
}
