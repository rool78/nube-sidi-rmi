import interfaces.servidor.ServicioAutenticacionInterface;
import interfaces.servidor.ServicioDatosInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {

    private ServicioDatosInterface servicioDatos;
    private int puerto = 8080;

    protected ServicioAutenticacionImpl() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        //Buscamos el servicio de datos
        String URLRegistro = "rmi://localhost:" + puerto + "/datos";
        servicioDatos = (ServicioDatosInterface) Naming.lookup(URLRegistro);
    }

    @Override
    public int autenticarCliente(String nombre, String password) throws RemoteException {
        return 0;
    }

    @Override
    public int registrarCliente(String nombre, String password) throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("RegistroCliente Auth");
        int respuesta = servicioDatos.registrarCliente(nombre, password);

        System.out.println("Procesamos respuesta: " + respuesta);

        System.out.println("Prueba listar clientes, borrar...");
        System.out.println(servicioDatos.listarClientes());

        return respuesta;
    }

    @Override
    public int autenticarRepositorio(String nombre) throws RemoteException {
        return 0;
    }

    @Override
    public int registrarRepositorio(String nombre) throws RemoteException {
        return 0;
    }

    @Override
    public void desconectarCliente(int sesion) throws RemoteException {

    }

    @Override
    public void desconectarRepositorio(int sesion) throws RemoteException {

    }
}
