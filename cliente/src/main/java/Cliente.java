import interfaces.cliente.ServicioDiscoClienteInterface;
import interfaces.servidor.ServicioAutenticacionInterface;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

    private final static int puertoServicio = 8080;
    private final static String direccionServicio = "localhost";

    private static String discocliente;

    private String direccion = "localhost";
    private int puertoServidor = 8080;
    private String autenticador = "rmi://" + direccion + ":" + puertoServidor + "/autenticador";

    private ServicioAutenticacionInterface servicioAutenticacion = null;


    public Cliente() {

    }

    private void launch() throws MalformedURLException, NotBoundException, RemoteException {

        try {
        servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup(autenticador);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            System.out.println("Error en lookup servicioAutenticacion");
        }

        if(servicioAutenticacion != null) {
            System.out.println("Introduce un usuario para registar");
            Scanner s = new Scanner(System.in);
            String nombre = s.nextLine();

            System.out.println("Introduce una contraseña");
            String password = s.nextLine();

            servicioAutenticacion.registrarCliente(nombre, password);

            System.out.println("Introduce un usuario para registar");
            String nombre2 = s.nextLine();

            System.out.println("Introduce una contraseña");
            String password2 = s.nextLine();

            servicioAutenticacion.registrarCliente(nombre2, password2);


        }

    }

        public static void main (String[]args) throws RemoteException, MalformedURLException, NotBoundException {
            new Cliente().launch();

//        String URLdiscoCliente = "rmi://" + direccionServicio + ":" + puertoServicio + "/discocliente/";
//        arrancarRegistro(puertoServicio);
//        Utils.setCodeBase(ServicioDiscoClienteInterface.class);
//        ServicioDiscoClienteImpl servicioDiscoCliente = new ServicioDiscoClienteImpl();
//        Naming.rebind(URLdiscoCliente, servicioDiscoCliente);
//        System.out.println("Operacion: Servicio Disco Cliente preparado con exito");

//        servicioDiscoCliente.bajarFichero(1);

        }

        public static void arrancarRegistro ( int numPuertoRMI) throws RemoteException {
            Registry registryServicio;
            try {
                registryServicio = LocateRegistry.getRegistry(numPuertoRMI);
                registryServicio.list();
            } catch (RemoteException e) {
                System.out.println("El registro RMI no se puede localizar en el puerto " + numPuertoRMI);
                LocateRegistry.createRegistry(numPuertoRMI);
                System.out.println("Registro RMI creado en el puerto " + numPuertoRMI);
            }
        }

    }
