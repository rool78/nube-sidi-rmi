import commons.ConstantesRMI;
import commons.Metadatos;
import commons.Respuesta;
import commons.interfaces.repositorio.ServicioSrOperadorInterface;
import commons.interfaces.servidor.ServicioDatosInterface;
import commons.modelo.Repo;
import commons.modelo.Usuario;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {

    private List<Usuario> clientesRegistrados = new ArrayList<>();
    private List<Usuario> clientesEnLinea = new ArrayList<>();

    private List<Repo> repositoriosRegistrados = new ArrayList<>();
    private List<Repo> repositoriosEnLinea = new ArrayList<>();

    private Map<Integer, List<Metadatos>> ficherosCliente = new HashMap<>();

    private int idUsuarios = 0;
    private int idRepositorios = 0;

    protected ServicioDatosImpl() throws RemoteException {
        super();
    }

    @Override
    public int autenticarCliente(String nombre, String password) throws RemoteException {
        for (Usuario u : clientesRegistrados) {
            if (u.getNombre().equals(nombre) && u.getPassword().equals(password)) {
                for (Repo r : repositoriosEnLinea) {
                    if (r.equals(u.getRepositorio())) {
                        System.out.println("Autenticación correcta");
                        return u.getId();
                    }
                }
            }
        }
        return Respuesta.ERROR_AUTENTICACION.getCodigo();
    }

    @Override
    public int registrarCliente(String nombre, String password) throws RemoteException, MalformedURLException, NotBoundException {
        for (Usuario u : clientesRegistrados) {
            if (u.getNombre().equals(nombre)) {
                System.out.println("Ya existe un Usuario con ese nombre");
                return Respuesta.NOMBRE_YA_EN_USO.getCodigo();
            }
        }
        if (repositoriosEnLinea.isEmpty()) {
            System.out.println("No hay repositorios en linea");
            return Respuesta.NO_HAY_REPOSITORIOS_EN_LINEA.getCodigo();
        }
        int asignacionAleatoria = (int) (Math.random() * repositoriosEnLinea.size());
        Usuario nuevoUsuario = new Usuario(nombre, password, generarIdUsuario());

        //Añadimos el repo al usuario
        nuevoUsuario.setRepositorio(repositoriosEnLinea.get(asignacionAleatoria));

        clientesRegistrados.add(nuevoUsuario);
        clientesEnLinea.add(nuevoUsuario);

        //Añadimos el nuevo usuario al repo asignado
        repositoriosEnLinea.get(asignacionAleatoria).getUsuarios().add(nuevoUsuario);

        //Creamos carpeta
        ServicioSrOperadorInterface servidorSrOperador = (ServicioSrOperadorInterface) Naming.lookup(ConstantesRMI.DIRECCION_SR_OPERADOR);
        int respuesta = servidorSrOperador.crearCarpeta(nuevoUsuario.getId());
        if (respuesta == Respuesta.ERROR_AL_CREAR_CARPETA.getCodigo()) {
            return respuesta;
        }
        return nuevoUsuario.getId();
    }

    public String listarClientes() {
        if (this.clientesRegistrados.isEmpty()) {
            return "No hay ningun cliente";
        }
        StringBuilder stringBuilder = new StringBuilder("Lista clientes registrados: ");
        for (Usuario u: this.clientesRegistrados) {
            stringBuilder.append(" [")
                    .append(u.getNombre())
                    .append("] ");
        }
        return stringBuilder.toString();
    }

    @Override
    public String listarClientesRepositorio(int id) throws RemoteException {
        for (Repo r : this.repositoriosEnLinea) {
            if (r.getId() == id) {
                return listarClientesRepositorio(r);
            }
        }
        return "";
    }

    private String listarClientesRepositorio(Repo repositorio) {
        if (repositorio.getUsuarios().isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("Lista clientes en repositorio: ");
        for (Usuario u: repositorio.getUsuarios()) {
            stringBuilder.append(" [")
                    .append("id: ")
                    .append(u.getId())
                    .append(" Nombre: ")
                    .append(u.getNombre())
                    .append("] ");
        }
        return stringBuilder.toString();
    }

    @Override
    public String obtenerUrlRepositorioDeCliente(int clienteId) {
        for (Usuario u: clientesEnLinea) {
            if (u.getId() == clienteId) {
                return  ConstantesRMI.DIRECCION_CL_OPERADOR + "/" + u.getRepositorio().getId();
            }
        }
        return "";
    }

    @Override
    public String obtenerUrlRepositorio(int clienteId) throws RemoteException {
        for (Repo r: this.repositoriosEnLinea) {
            for (Usuario u: r.getUsuarios()) {
                if (u.getId() == clienteId) {
                    return ConstantesRMI.DIRECCION_SR_OPERADOR + "/" + r.getId();
                }
            }
        }
        return "";
    }

    @Override
    public String listarFicherosCliente(int idCliente) throws RemoteException {
        if (this.ficherosCliente.isEmpty() || this.ficherosCliente.get(idCliente) == null) {
            return "";
        }
        List<Metadatos> ficheros = this.ficherosCliente.get(idCliente);
        StringBuilder stringBuilder = new StringBuilder("Lista ficheros: ");
        for (Metadatos m: ficheros) {
            stringBuilder.append(" [")
                         .append(m.getNombreFichero())
                         .append("] ");
        }
        return stringBuilder.toString();
    }

    @Override
    public int ficheroSubido(Metadatos ficheroSubido) throws RemoteException {
        if (this.ficherosCliente.containsKey(ficheroSubido.getIdCliente())) {
            this.ficherosCliente.get(ficheroSubido.getIdCliente()).add(ficheroSubido);
        } else {
            List<Metadatos> list = new ArrayList<Metadatos>();
            list.add(ficheroSubido);
            this.ficherosCliente.put(ficheroSubido.getIdCliente(), list);
        }
        return Respuesta.OK.getCodigo();
    }

    @Override
    public int ficheroBorrado(Metadatos ficheroBorrado) throws RemoteException {
        List<Metadatos> ficheros = this.ficherosCliente.get(ficheroBorrado.getIdCliente());
        Iterator<Metadatos> it = ficheros.iterator();
        while (it.hasNext()) {
            Metadatos m = it.next();
            if (m.getNombreFichero().equals(ficheroBorrado.getNombreFichero())) {
                it.remove();
                break;
            }
        }
        return 0;
    }

    @Override
    public int autenticarRepositorio(String nombre) throws RemoteException {
        Repo repositorioParaAutenticar = null;
        boolean repositorioRegistrado = false;
        for (Repo r: repositoriosRegistrados) {
            if (r.getNombre().equals(nombre)) {
                repositorioParaAutenticar = r;
                repositorioRegistrado = true;
            }
        }
        if (!repositorioRegistrado) {
            System.out.println("El repositorio no esta registrado");
            return Respuesta.ERROR_AUTENTICACION.getCodigo();
        }
        for (Repo r: repositoriosEnLinea) {
            if (r.getNombre().equals(repositorioParaAutenticar.getNombre())) {
                System.out.println("El repositorio ya estaba en linea");
                return r.getId();
            }
        }
        //Lo añadimos a la lista de repositorios en linea
        repositoriosEnLinea.add(repositorioParaAutenticar);

        return repositorioParaAutenticar.getId();
    }

    @Override
    public int registrarRepositorio(String nombre) throws RemoteException {
        for (Repo r : repositoriosRegistrados) {
            if (r.getNombre().equals(nombre)) {
                System.out.println("Nombre del repositorio ya en uso");
                return Respuesta.NOMBRE_YA_EN_USO.getCodigo();
            }
        }
        Repo nuevoRepositorio = new Repo(nombre, generarIdRepositorio());
        repositoriosRegistrados.add(nuevoRepositorio);
        repositoriosEnLinea.add(nuevoRepositorio);
        System.out.println("Repositorio creado correctamente");
        return nuevoRepositorio.getId();
    }

    @Override
    public void desconectarCliente(String nombre) throws RemoteException {
        Usuario usuarioDesconectar  = null;
        for (Usuario u : clientesEnLinea) {
            if (u.getNombre().equals(nombre)) {
                usuarioDesconectar = u;
            }
        }
        if (usuarioDesconectar != null) {
            clientesEnLinea.remove(usuarioDesconectar);
            System.out.println("El usuario se ha desconectado correctamnente");
        }
    }

    @Override
    public void desconectarRepositorio(String nombre) throws RemoteException {
        Repo repositorioDesconectar = null;
        for (Repo r : repositoriosEnLinea) {
            if (r.getNombre().equals(nombre)) {
                repositorioDesconectar = r;
            }
        }
        if (repositorioDesconectar != null) {
            repositoriosEnLinea.remove(repositorioDesconectar);
            System.out.println("Repositorio desconectado correctamente");
        }
    }

    private int generarIdUsuario() {
        return idUsuarios++;
    }

    private int generarIdRepositorio() {
        return idRepositorios++;
    }
}
