package edu.pucmm.isc.servicios;

import edu.pucmm.isc.objetos.CarroCompra;
import edu.pucmm.isc.objetos.Producto;
import edu.pucmm.isc.objetos.Usuario;
import edu.pucmm.isc.objetos.VentasProducto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StoreServices {

    private static StoreServices tienda;
    private List<Producto> listaProductos = new ArrayList<Producto>();
    private List<VentasProducto> listaVentas = new ArrayList<VentasProducto>();
    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
    private CarroCompra carrito;
    private boolean usr = false;
    private boolean adm = false;

    private StoreServices(){
        //Se añaden los objetos que estarán preestablecidos
        //Productos
        listaProductos.add(new Producto(1, "Motherboard", new BigDecimal("9500")));
        listaProductos.add(new Producto(2, "CPU AMD Ryzen 5 3500", new BigDecimal("14350")));
        //Usuarios
        listaUsuarios.add(new Usuario("admin", "Administrador", "admin"));
        listaUsuarios.add(new Usuario("rafael", "Rafael Felipe", "0712"));
        //Ventas
        List<Producto> listaTemporalVenta = new ArrayList<Producto>();
        listaTemporalVenta.add(new Producto(1, "Motherboard", new BigDecimal("9500"), 2));
        listaTemporalVenta.add(new Producto(2, "CPU AMD Ryzen 5 3500", new BigDecimal("14350"), 1));
        listaVentas.add(new VentasProducto(1, new Date(), "Rafael Felipe", listaTemporalVenta));
    }

    //Instancia singleton
    public static StoreServices getInstance(){
        if(tienda == null){
            tienda = new StoreServices();
        }
        return tienda;
    }

    public List<Producto> getListaProductos(){
        return listaProductos;
    }

    public List<VentasProducto> getListaVentas(){
        return listaVentas;
    }

    public List<Usuario> getListaUsuarios(){
        return listaUsuarios;
    }

    public CarroCompra getCarrito() { return carrito; }

    public void setCarrito(CarroCompra cart) { this.carrito = cart; }

    public boolean getUsr() { return usr; }

    public void setUsr(boolean loggeado) { usr = loggeado; }

    public boolean getAdm() { return adm; }

    public void setAdm(boolean admin) { adm = admin; }

    // Productos y lista de productos

    public Producto getProductoPorID(int id){
        return listaProductos.stream().filter(producto -> producto.getId() == id).findFirst().orElse(null);
    }

    public Producto crearProducto(Producto producto){
        listaProductos.add(producto);
        return producto;
    }

    public boolean eliminarProducto(Producto producto){
        return listaProductos.remove(producto);
    }

    // Carrito

    public Producto getProductoEnCarrito(int id){
        return carrito.getListaProductos().stream().filter(producto -> producto.getId() == id).findFirst().orElse(null);
    }

    public void limpiarCarrito(){
        List<Producto> tmp = new ArrayList<Producto>();
        carrito.setListaProductos(tmp);
    }

    public void procesarVenta(VentasProducto venta){
        listaVentas.add(venta);
    }

    public Usuario getUsuarioPorNombreUsuario(String usr){
        return listaUsuarios.stream().filter(usuario -> usuario.getUsuario().equals(usr)).findFirst().orElse(null);
    }

    public Usuario loginUsuario(String usuario, String passw){
        Usuario tmp = getUsuarioPorNombreUsuario(usuario);
        if(tmp == null) {
            throw new RuntimeException("Usuario no existente!");
        } else if(tmp.getUsuario().equals("admin") && tmp.getPassword().equals("admin")) {
            adm = true;
            usr = false;
            return tmp;
        } else if(tmp.getUsuario().equals(usuario) && tmp.getPassword().equals(passw)) {
            usr = true;
            adm = false;
            return tmp;
        } else throw new RuntimeException("Password incorrecto!");
    }

    public void logoutUsuario() {
        usr = false;
        adm = false;
    }
}
