/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

import commons.ConstantesRMI;
import commons.interfaces.servidor.ServicioAutenticacionInterface;
import commons.interfaces.servidor.ServicioDatosInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {

    private final ServicioDatosInterface servicioDatos;

    protected ServicioAutenticacionImpl() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        this.servicioDatos = (ServicioDatosInterface) Naming.lookup(ConstantesRMI.DIRECCION_DATOS);
    }

    @Override
    public int autenticarCliente(String nombre, String password) throws RemoteException {
        return this.servicioDatos.autenticarCliente(nombre, password);
    }

    @Override
    public int registrarCliente(String nombre, String password) throws RemoteException, MalformedURLException, NotBoundException {
        return this.servicioDatos.registrarCliente(nombre, password);
    }

    @Override
    public int autenticarRepositorio(String nombre) throws RemoteException {
        return this.servicioDatos.autenticarRepositorio(nombre);
    }

    @Override
    public int registrarRepositorio(String nombre) throws RemoteException {
        return this.servicioDatos.registrarRepositorio(nombre);
    }

    @Override
    public void desconectarCliente(String nombre) throws RemoteException {
        this.servicioDatos.desconectarCliente(nombre);
    }

    @Override
    public void desconectarRepositorio(String nombre) throws RemoteException {
        this.servicioDatos.desconectarRepositorio(nombre);
    }
}
