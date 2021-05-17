import interfaces.cliente.ServicioDiscoClienteInterface;
import interfaces.servidor.ServicioAutenticacionInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {

    private ServicioAutenticacionInterface servicioAutenticacion = null;

    public Cliente() throws MalformedURLException, RemoteException {
        levantarServicios();
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        new Cliente().launch();

    }

    private void levantarServicios() throws MalformedURLException, RemoteException {
        Utils.arrancarRegistro(ConstantesRMI.PUERTO_CLIENTE);
        Utils.setCodeBase(ServicioDiscoClienteInterface.class);
        ServicioDiscoClienteImpl servicioDiscoCliente = new ServicioDiscoClienteImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_DISCO_CLIENTE, servicioDiscoCliente);
    }

    private void launch() throws MalformedURLException, NotBoundException, RemoteException {
        int opcion = 0;
        servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup(ConstantesRMI.DIRECCION_AUTENTICADOR);
        //todo borrar, registro un cliente
        servicioAutenticacion.registrarCliente("rol", "1234");
        do {
            opcion = Gui.menu("Acceso de Cliente",new String[]
                    {"Registrar un nuevo usuario","Autenticarse en el sistema(hacer login)"});
            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    autenticarUsuario();
                    break;
            }
        } while (opcion != 3);
    }

    private void registrarUsuario() throws MalformedURLException, NotBoundException, RemoteException {
        try {
            servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup(ConstantesRMI.DIRECCION_AUTENTICADOR);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            System.out.println("Error en lookup servicioAutenticacion");
        }
        if (servicioAutenticacion != null) {
            System.out.println("Menu registro nuevo usuario.");
            String nombre = Gui.entradaTexto("Introduce el nuevo nombre de ususario:");
            String password = Gui.entradaTexto("Introduce una contraseña:");

            servicioAutenticacion.registrarCliente(nombre, password);
        }
    }

    private void autenticarUsuario() throws RemoteException {
        try {
            servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup(ConstantesRMI.DIRECCION_AUTENTICADOR);
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            System.out.println("Error en lookup servicioAutenticacion");
        }
        if (servicioAutenticacion != null) {
            System.out.println("Menu autenticacion usuario");
            String nombre = Gui.entradaTexto("Introduce tu nombre de usuario:");
            System.out.println("Introduce tu contraseña:");
            String password = Gui.entradaTexto("Introduce tu contraseña:");

            int respuesta = servicioAutenticacion.autenticarCliente(nombre, password);
            if (respuesta >= 0) {
                System.out.println("Usuario autenticado satisfactoriamente");
            } else {
                System.out.println("Usuario o contareña incorrectos");
            }
        }

    }

    public static void arrancarRegistro(int numPuertoRMI) throws RemoteException {
        try {
            Registry registryServicio = LocateRegistry.getRegistry(numPuertoRMI);
            registryServicio.list();
        } catch (RemoteException e) {
            System.out.println("El registro RMI no se puede localizar en el puerto " + numPuertoRMI);
            LocateRegistry.createRegistry(numPuertoRMI);
            System.out.println("Registro RMI creado en el puerto " + numPuertoRMI);
        }
    }
}
