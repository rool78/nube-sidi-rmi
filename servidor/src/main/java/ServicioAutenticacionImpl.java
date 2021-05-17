import interfaces.servidor.ServicioAutenticacionInterface;
import interfaces.servidor.ServicioDatosInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {

    private final ServicioDatosInterface servicioDatos;

    protected ServicioAutenticacionImpl() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        servicioDatos = (ServicioDatosInterface) Naming.lookup(ConstantesRMI.DIRECCION_DATOS);
    }

    @Override
    public int autenticarCliente(String nombre, String password) throws RemoteException {
        System.out.println("##autenticarCliente");
        return servicioDatos.autenticarCliente(nombre, password);
    }

    @Override
    public int registrarCliente(String nombre, String password) throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("##registrarCliente");
        return servicioDatos.registrarCliente(nombre, password);
    }

    @Override
    public int autenticarRepositorio(String nombre) throws RemoteException {
        return servicioDatos.autenticarRepositorio(nombre);
    }

    @Override
    public int registrarRepositorio(String nombre) throws RemoteException {
        return servicioDatos.registrarRepositorio(nombre);
    }

    @Override
    public void desconectarCliente(String nombre) throws RemoteException {
        servicioDatos.desconectarCliente(nombre);
//        for (Usuario u: clien
//             ) {
//
//        }
    }

    @Override
    public void desconectarRepositorio(String nombre) throws RemoteException {
        servicioDatos.desconectarRepositorio(nombre);
    }
}
