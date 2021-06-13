/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

import commons.ConstantesRMI;
import commons.Gui;
import commons.Utils;
import commons.interfaces.servidor.ServicioAutenticacionInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Servidor {

    private ServicioDatosImpl servicioDatos;

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        new Servidor().launch();
    }

    public Servidor() {

    }

    private void launch() throws RemoteException, MalformedURLException, NotBoundException {
        Utils.arrancarRegistro(ConstantesRMI.PUERTO_SERVIDOR);
        Utils.setCodeBase(ServicioAutenticacionInterface.class);

        this.servicioDatos = new ServicioDatosImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_DATOS, servicioDatos);
        System.out.println("[INFO] Servicio Datos preparado con exito");

        ServicioAutenticacionImpl servicioAutenticacion = new ServicioAutenticacionImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_AUTENTICADOR, servicioAutenticacion);
        System.out.println("[INFO] Servicio Autenticador preparado con exito");

        ServicioGestorImpl servicioGestor = new ServicioGestorImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_GESTOR, servicioGestor);
        System.out.println("[INFO] Servicio gestor preparado con exito");

        menuServidor();
    }

    private void menuServidor() throws RemoteException, MalformedURLException, NotBoundException {
        int opcion = 0;
        do {
            opcion = Gui.menu("Servidor", new String[]{
                    "Listar Clientes", "Listar Repositorios", "Listar Parejas Repositorio-Cliente"});
            switch (opcion) {
                case 1:
                    System.out.println(servicioDatos.listarClientes());
                    break;
                case 2:
                    System.out.println(servicioDatos.listarRepositorios());
                    break;
                case 3:
                    System.out.println(servicioDatos.listarParejasRepositorioCliente());
                    break;
            }
        } while (opcion != 4);

        Naming.unbind(ConstantesRMI.DIRECCION_AUTENTICADOR);
        Naming.unbind(ConstantesRMI.DIRECCION_DATOS);
        Naming.unbind(ConstantesRMI.DIRECCION_GESTOR);
        System.exit(0);

    }
}