import commons.*;
import commons.interfaces.cliente.ServicioDiscoClienteInterface;
import commons.interfaces.repositorio.ServicioClOperadorInterface;
import commons.interfaces.servidor.ServicioAutenticacionInterface;
import commons.interfaces.servidor.ServicioGestorInterface;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {

    private String nombre;
    private int idSesion;

    private ServicioAutenticacionInterface servicioAutenticacion;
    private ServicioGestorInterface servicioGestor;

    public Cliente() throws MalformedURLException, RemoteException {
//        levantarServicios();
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        new Cliente().launch();

    }

    private void levantarServicios() throws MalformedURLException, RemoteException {
        Utils.arrancarRegistro(ConstantesRMI.PUERTO_CLIENTE);
        Utils.setCodeBase(ServicioDiscoClienteInterface.class);
        ServicioDiscoClienteImpl servicioDiscoCliente = new ServicioDiscoClienteImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_DISCO_CLIENTE + this.idSesion, servicioDiscoCliente);
        System.out.println("Servicio cliente lavantado con exito: " + ConstantesRMI.DIRECCION_DISCO_CLIENTE + this.idSesion);
    }

    private void launch() throws MalformedURLException, NotBoundException, RemoteException {
        try {
            this.servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup(ConstantesRMI.DIRECCION_AUTENTICADOR);
            this.servicioGestor = (ServicioGestorInterface) Naming.lookup(ConstantesRMI.DIRECCION_GESTOR);

        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            System.out.println("Error encontrando servicios");
        }
        int opcion = 0;
        do {
            opcion = Gui.menu("Acceso de Cliente", new String[]
                    {"Registrar un nuevo usuario", "Autenticarse en el sistema"});
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
        System.out.println("Menu registro nuevo usuario.");
        String nombre = Gui.entradaTexto("Introduce el nuevo nombre de ususario");
        String password = Gui.entradaTexto("Introduce una contraseña");

        int respuesta = this.servicioAutenticacion.registrarCliente(nombre, password);

        if (respuesta >= Respuesta.OK.getCodigo()) {
            System.out.println("Usuario registrado correctamente");
            this.idSesion = respuesta;
            this.nombre = nombre;
            menuCliente();
        }
    }

    private void desconectar() throws RemoteException {
        this.servicioAutenticacion.desconectarCliente(nombre);
    }

    private void autenticarUsuario() throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("Menu autenticacion usuario");
        String nombre = Gui.entradaTexto("Introduce tu nombre de usuario");
        String password = Gui.entradaTexto("Introduce tu contraseña");

        int respuesta = this.servicioAutenticacion.autenticarCliente(nombre, password);
        if (respuesta >= Respuesta.OK.getCodigo()) {
            levantarServicios();
            System.out.println("Usuario autenticado satisfactoriamente");
            this.nombre = nombre;
            menuCliente();
        } else {
            System.out.println("Usuario o contareña incorrectos");
        }
    }

    private void menuCliente() throws RemoteException, MalformedURLException, NotBoundException {
        int opcion = 0;
        do {
            opcion = Gui.menu("Operaciones de Cliente", new String[]
                    {"Subir fichero", "Bajar fichero", "Borrar fichero",
                            "Listar ficheros", "Listar clientes del sistema"});
            switch (opcion) {
                case 1:
                    subirFichero();
                    break;
                case 2:
                    bajarFichero();
                    break;
                case 3:
                    borrarFichero();
                    break;
                case 4:
                    listarFicheros();
                    break;
                case 5:
                    listarClientes();
                    break;
            }
        } while (opcion != 6);
        desconectar();
    }

    private void listarClientes() throws MalformedURLException, NotBoundException, RemoteException {
        System.out.println(this.servicioGestor.listarClientes());
    }

    private String obtenerFicherosDisponibles() throws MalformedURLException, NotBoundException, RemoteException {
        return this.servicioGestor.listarFicherosCliente(idSesion);
    }

    private void listarFicheros() throws MalformedURLException, NotBoundException, RemoteException {
        String listaFicheros = obtenerFicherosDisponibles();
        if (listaFicheros.isEmpty()) {
            System.out.println("Todavía no hay ficheros");
        } else {
            System.out.println(obtenerFicherosDisponibles());
        }
    }

    private void borrarFichero() throws MalformedURLException, NotBoundException, RemoteException {
        String ficheros = obtenerFicherosDisponibles();
        if (ficheros.isEmpty()) {
            System.out.println("No hay ficheros disponibles");
            return;
        }
        System.out.println(ficheros);

        String nombreFichero = Gui.entradaTexto("Introduzca el nombre del fichero que deseas borrar");
        String urlRepositorio = this.servicioGestor.borrarFichero(this.idSesion);

        ServicioClOperadorInterface servicioClOperador = (ServicioClOperadorInterface) Naming.lookup(urlRepositorio);

        int respuesta = servicioClOperador.borrarFichero(this.idSesion, nombreFichero);

        if (respuesta == Respuesta.OK.getCodigo()) {
            System.out.println("El fihcero se ha borrado correctamente");
        } else {
            System.out.println("A ocurrido un problema borrando el fichero");
        }

    }

    private void bajarFichero() throws MalformedURLException, NotBoundException, RemoteException {
        String ficheros = obtenerFicherosDisponibles();
        if (ficheros.isEmpty()) {
            System.out.println("No hay ficheros disponibles");
            return;
        }

        System.out.println(ficheros);
        String nombreFichero = Gui.entradaTexto("Introduzca el nombre del fichero que deseas descargar");
        int respuesta = this.servicioGestor.bajarFichero(ConstantesRMI.DIRECCION_DISCO_CLIENTE + this.idSesion
                , nombreFichero, this.idSesion);
    }

    private void subirFichero() throws MalformedURLException, NotBoundException, RemoteException {
        String nombreFichero = Gui.entradaTexto("Introduzca el nombre del fichero");
        File file = new File(nombreFichero);
        if (!file.exists()) {
            System.out.println("El fichero no existe");
            return;
        }
        String url = this.servicioGestor.subirFichero(idSesion);
        Fichero fichero = new Fichero(nombreFichero, String.valueOf(this.idSesion));
        ServicioClOperadorInterface servicioClOperador = (ServicioClOperadorInterface) Naming.lookup(url);
        int respuestaCl = servicioClOperador.subirFichero(fichero, idSesion);

        if (respuestaCl != Respuesta.OK.getCodigo()) {
            System.out.println("Error en la subida del fichero");
            return;
        }
        System.out.println("El fichero se ha subido correctamente");
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
