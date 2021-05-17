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
            opcion = Gui.menu("Acceso de Cliente", new String[]
                    {"Registrar un nuevo usuario", "Autenticarse en el sistema(hacer login)"});
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
        System.out.println("Menu registro nuevo usuario.");
        String nombre = Gui.entradaTexto("Introduce el nuevo nombre de ususario:");
        String password = Gui.entradaTexto("Introduce una contraseña:");

        int respuesta = servicioAutenticacion.registrarCliente(nombre, password);

        if (respuesta >= Respuesta.OK.getCodigo()) {
            //si la respuesta es correcta nos devuelven la id del usuario
            System.out.println("Usuario registrado correctamente");
            this.idSesion = respuesta;
            this.nombre = nombre;
            menuCliente();
        }
    }

    private void desconectar() throws RemoteException {
        servicioAutenticacion.desconectarCliente(nombre);
    }

    private void autenticarUsuario() throws RemoteException, MalformedURLException, NotBoundException {
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
            if (respuesta == Respuesta.OK.getCodigo()) {
                System.out.println("Usuario autenticado satisfactoriamente");
                this.nombre = nombre;
                menuCliente();
            } else {
                System.out.println("Usuario o contareña incorrectos");
            }
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

    private void listarClientes() {

    }

    private void listarFicheros() {

    }

    private void borrarFichero() {

    }

    private void bajarFichero() {

    }

    private void subirFichero() throws MalformedURLException, NotBoundException, RemoteException {
        System.out.println("¡¡OJO!! el fichero debe estar en la carpeta actual");
        String nombreFichero = Gui.entradaTexto("Introduzca el nombre del fichero");
        File f = new File(nombreFichero);
        if (!f.exists()) {
            System.out.println("El fichero no existe");
            return;
        } else {
            //Solicitamos la URL del ServicioClOperador
            ServicioGestorInterface servicioGestor = (ServicioGestorInterface) Naming.lookup(ConstantesRMI.DIRECCION_GESTOR);

            //Le pasamos el nombre del fichero y el id de sesion y Gestor ya se encargara de colocar los metadatos
            //Desde esta llamada podriamos pasar los metadatos como el peso o el ckecksum con los metodos de Fichero
            //De todas formas el gestor va a poder conseguir el idRepo el nombreRepo,idCliente,nombreCliente con
            //tan solo miSesion con ese dato que pida los datos al servicioDatos
            int respuestaGestor = servicioGestor.subirFichero(nombreFichero, idSesion);

            if (respuestaGestor != Respuesta.OK.getCodigo()) {
                System.out.println("Error en la subida del fichero");
                return;
            }
            Fichero fichero = new Fichero(nombreFichero, this.nombre);

            ServicioClOperadorInterface servicioClOperador = (ServicioClOperadorInterface) Naming.lookup(ConstantesRMI.DIRECCION_CL_OPERADOR);

            int respuestaCl = servicioClOperador.subirFichero(fichero);

            if (respuestaCl != Respuesta.OK.getCodigo()) {
                System.out.println("Error en la subida del fichero");
                return;
            }
            System.out.println("El fichero se ha subido correctametne");




            //extraemos la carpeta donde se va a guardar el fichero, que sera el id, del cliente
            //que sera al mismo tiempo el propietario del Fichero, segundo parametro

//            String URLservicioClOperador = datosURL.getUrl();
//            String propietario = ""+datosURL.getIdCliente();

            //HAY QUE COMPROBAR SI EXISTE EL FICHERO en la carpeta actual
//            Fichero fichero= new Fichero(nombreFichero,propietario);
//            ServicioClOperadorInterface servicioClOperador =(ServicioClOperadorInterface)Naming.lookup(URLservicioClOperador);

//            if (servicioClOperador.subirFichero(fichero)==false)
//            {
//                System.out.println("Error en el envío (Checksum failed), intenta de nuevo");
//            }
//            else{
//                System.out.println("Fichero: " + nombreFichero + " enviado");
//            }
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
