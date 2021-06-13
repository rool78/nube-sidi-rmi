import commons.ConstantesRMI;
import commons.Gui;
import commons.Respuesta;
import commons.Utils;
import commons.interfaces.repositorio.ServicioSrOperadorInterface;
import commons.interfaces.servidor.ServicioAutenticacionInterface;
import commons.interfaces.servidor.ServicioGestorInterface;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Repositorio {

    private ServicioAutenticacionInterface servicioAutenticacion;
    private ServicioGestorInterface servicioGestor;
    private int idRepositorio;
    private String nombreRepositorio;

    public static void main(String[] args) throws MalformedURLException, RemoteException {
        new Repositorio().launch();
    }

    public Repositorio() throws MalformedURLException, RemoteException {
        levantarServicios();
    }

    public void launch() {

        try {
            this.servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup(ConstantesRMI.DIRECCION_AUTENTICADOR);
            this.servicioGestor = (ServicioGestorInterface) Naming.lookup(ConstantesRMI.DIRECCION_GESTOR);
            int opcion;
            do {
                opcion = Gui.menu("Acceso a Repositorio",
                        new String[]{"Registrar nuevo Repositorio", "Autenticar Repositorio"});
                switch (opcion) {
                    case 1:
                        registrarRepositorio();
                        break;
                    case 2:
                        autenticarRepositorio();
                        break;
                }
            } while (opcion != 3);
        } catch (ConnectException e) {
            System.out.println("El servidor no esta disponible " + e.getMessage());
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    private void registrarRepositorio() throws RemoteException, MalformedURLException, NotBoundException {
        String nombreRepositorio = Gui.entradaTexto("Introduzca nombre repositorio");
        int respuesta = this.servicioAutenticacion.registrarRepositorio(nombreRepositorio);
        if (respuesta >= Respuesta.OK.getCodigo()) {
            System.out.println("Repositorio registrado correctamente");
            this.nombreRepositorio = nombreRepositorio;
            menuRepositorio();
        }
    }

    private void autenticarRepositorio() throws RemoteException, MalformedURLException, NotBoundException {
        String nombreRepositorio = Gui.entradaTexto("Introduzca nombre repositorio");
        int respuesta = this.servicioAutenticacion.autenticarRepositorio(nombreRepositorio);
        if (respuesta >= Respuesta.OK.getCodigo()) {
            this.idRepositorio = respuesta;
            this.nombreRepositorio = nombreRepositorio;
            menuRepositorio();
        }
    }

    private void menuRepositorio() throws MalformedURLException, RemoteException, NotBoundException {
        levantarServicioRepositorio();
        int opcion = 0;
        do {
            opcion = Gui.menu("Operaciones de Repositorio", new String[]
                    {"Listar clientes", "Listar ficheros de clientes"});
            switch (opcion) {
                case 1:
                    listarClientes();
                    break;
                case 2:
                    listarFicherosClientes();
                    break;
            }
        } while (opcion != 3);
        desconectar();


    }

    private void listarClientes() throws MalformedURLException, NotBoundException, RemoteException {
        String listaClientes = obtenerClientesEnRepositorio();
        if (listaClientes.isEmpty()) {
            System.out.println("Todavía no hay clientes asociados al repositorio");
            return;
        }
        System.out.println(listaClientes);
    }

    private void listarFicherosClientes() throws RemoteException, MalformedURLException, NotBoundException {
        String listaClientes = obtenerClientesEnRepositorio();
        if (listaClientes.isEmpty()) {
            System.out.println("No se pueden listar ficheros ya que no hay clientes asociados al repositorio");
            return;
        }
        System.out.println(listaClientes);
        String carpeta = Gui.entradaTexto("Introduzca el ID del cliente para ver sus ficheros");
        File fichero = new File(carpeta);
        if (fichero.exists()){
            String[] ficheros = fichero.list();
            for (String s : ficheros) {
                System.out.println(s);
            }
        }else {
            System.out.println("No se han encontrado ficheros para ese ID");
        }
    }

    private String obtenerClientesEnRepositorio() throws RemoteException, MalformedURLException, NotBoundException {
        return this.servicioGestor.listarClientesRepositorio(this.idRepositorio);
    }

    private void desconectar() throws MalformedURLException, NotBoundException, RemoteException {
        this.servicioAutenticacion.desconectarRepositorio(this.nombreRepositorio);
    }

    private void levantarServicioRepositorio() throws RemoteException, MalformedURLException {
        Naming.rebind(ConstantesRMI.DIRECCION_SR_OPERADOR + "/" + idRepositorio,  new ServicioSrOperadorImpl());
        Naming.rebind(ConstantesRMI.DIRECCION_CL_OPERADOR + "/" + idRepositorio, new ServicioClOperadorImpl());
    }

    private void levantarServicios() throws RemoteException, MalformedURLException {
        Utils.arrancarRegistro(ConstantesRMI.PUERTO_REPOSITORIO);
        Utils.setCodeBase(ServicioSrOperadorInterface.class);

        ServicioSrOperadorImpl servicioSrOperador = new ServicioSrOperadorImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_SR_OPERADOR, servicioSrOperador);
        System.out.println("ServicioSrOperador preparado con exito");

        ServicioClOperadorImpl servicioClOperador = new ServicioClOperadorImpl();
        Naming.rebind(ConstantesRMI.DIRECCION_CL_OPERADOR, servicioClOperador);
        System.out.println("ServicioClOperador preparado con exito");
    }

}
