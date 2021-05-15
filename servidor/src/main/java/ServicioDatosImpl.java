import interfaces.servidor.ServicioDatosInterface;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {

    private Map<String, Usuario> clientesRegistrados = new HashMap<>(); //Nombre-Usurario

    private int IdUsuarios = 0;

    protected ServicioDatosImpl() throws RemoteException {
        super();
    }

    @Override
    public int autenticarCliente(String nombre, String password) throws RemoteException {
        return 0;
    }

    @Override
    public int registrarCliente(String nombre, String password) throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("RegistroCliente Datos");
        if (clientesRegistrados.containsKey(nombre)) {
            System.out.println("Ya existe un Usuario con ese nombre");
            return Respuesta.USUARIO_YA_REGISTRADO.getCodigo();
        }

        Usuario nuevoUsuario = new Usuario(nombre, password, generateNewId());
        clientesRegistrados.put(nombre, nuevoUsuario);
        System.out.println("Usuario registrado con exito");

        System.out.println("Usuario en persistencia:::" + clientesRegistrados.get(nombre).toString());

        return Respuesta.OK.getCodigo();
    }

    public String listarClientes() {
        return clientesRegistrados.toString();
    }

    private int generateNewId() {
        return IdUsuarios++;
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
