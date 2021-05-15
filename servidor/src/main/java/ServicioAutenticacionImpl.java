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
        //Buscamos el servicio de datos
        servicioDatos = (ServicioDatosInterface) Naming.lookup(ConstantesRMI.DIRECCION_DATOS);
    }

    @Override
    public int autenticarCliente(String nombre, String password) throws RemoteException {
        System.out.println("##autenticarCliente");
        int respuesta = servicioDatos.autenticarCliente(nombre, password);
        if (respuesta >= 0) {
            System.out.println("Usuario autenticado satisfactoriamente");
        } else {
            System.out.println("Usuario o contareña incorrectos");
        }
        return respuesta;
    }

    @Override
    public int registrarCliente(String nombre, String password) throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("##registrarCliente");
        return servicioDatos.registrarCliente(nombre, password);
    }

    @Override
    public int autenticarRepositorio(String nombre) throws RemoteException {
        int respuesta = servicioDatos.autenticarRepositorio(nombre);
        return respuesta;
    }

    @Override
    public int registrarRepositorio(String nombre) throws RemoteException {
        int respuesta = servicioDatos.registrarRepositorio(nombre);
        return respuesta;
    }

    @Override
    public void desconectarCliente(int sesion) throws RemoteException {

    }

    @Override
    public void desconectarRepositorio(int sesion) throws RemoteException {

    }
}
