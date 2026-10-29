import java.util.ArrayList;

/* PATRON STRATEGY (Comportamiento). Rol: estrategia (strategy). Interfaz comun
de la familia de algoritmos para calcular el precio de un juego. */
interface EstrategiaPrecio{
   double calcular(double precioBase);
}

// Rol: estrategia concreta, el juego se vende al precio base.
class PrecioNormal implements EstrategiaPrecio{
   public double calcular(double precioBase){
      return precioBase;
   }
}

// Rol: estrategia concreta, aplica un descuento porcentual.
class PrecioOferta implements EstrategiaPrecio{
   private int descuento;
   public PrecioOferta(int descuento){
      this.descuento = descuento;
   }
   public double calcular(double precioBase){
       return precioBase - (precioBase * descuento / 100);
   }
}

// Rol: estrategia concreta, el juego no tiene costo.
class PrecioGratuito implements EstrategiaPrecio{
   public double calcular(double precioBase){
      return 0;
   }
}

/* Rol: contexto (context). Guarda una estrategia y le delega el calculo del
precio, se puede cambiar en cualquier momento con setEstrategia(). */
class Videojuego{
   private String titulo;
   private double precioBase;
   private EstrategiaPrecio estrategia;
   public Videojuego(String titulo, double precioBase,
                     EstrategiaPrecio estrategia){
       this.titulo = titulo;
       this.precioBase = precioBase;
       this.estrategia = estrategia;
   }
   public String getTitulo(){
      return titulo;
   }
   public void setEstrategia(EstrategiaPrecio estrategia){
       this.estrategia = estrategia;
   }
   public double obtenerPrecioFinal(){
      return estrategia.calcular(precioBase);
   }
}

interface MetodoPago{
   boolean pagar(double monto);
}

class PagoTarjetaRegalo implements MetodoPago{
   private double saldo;
   public PagoTarjetaRegalo(double saldo){
      this.saldo = saldo;
   }
   public boolean pagar(double monto){
       if (monto > saldo){
           System.out.println("Saldo insuficiente (saldo: Bs. " + saldo + ")");
           return false;
       }
       saldo -= monto;
       System.out.println("Cobrando Bs. " + monto + " (saldo: " + saldo + ")");
       return true;
   }
}

class Cliente{
   private String nombre;
   public Cliente(String nombre){
      this.nombre = nombre;
   }
   public void notificar(String mensaje){
       System.out.println("[Mensaje para " + nombre + "] " + mensaje);
   }
}

// Rol: cliente del patron, pide el precio final sin saber que estrategia se usa.
class ServicioCompras{
   public void comprar(Videojuego juego, MetodoPago metodo, Cliente cliente){
       if (metodo.pagar(juego.obtenerPrecioFinal())){
           cliente.notificar("Compra exitosa: " + juego.getTitulo());
       }else{
           cliente.notificar("Compra rechazada: " + juego.getTitulo());
       }
   }
}

public class TiendaVideojuegos{
   public static void main(String[] args){
      ArrayList<Videojuego> catalogo = new ArrayList<>();
      catalogo.add(new Videojuego("Pixel Quest", 70.0, new PrecioNormal()));
      catalogo.add(new Videojuego("Space Runner", 80.0, new PrecioNormal()));
      catalogo.add(new Videojuego("Block Puzzle", 40.0, new PrecioGratuito()));
      for (Videojuego j : catalogo){
         System.out.println(j.getTitulo() + ": Bs. " + j.obtenerPrecioFinal());
      }

      Cliente cliente = new Cliente("Gabriel");
      PagoTarjetaRegalo regalo = new PagoTarjetaRegalo(70.0);
      ServicioCompras servicio = new ServicioCompras();
      servicio.comprar(catalogo.get(1), regalo, cliente);

      catalogo.get(1).setEstrategia(new PrecioOferta(25));
      System.out.println("Space Runner en oferta: Bs. "
               + catalogo.get(1).obtenerPrecioFinal());
      servicio.comprar(catalogo.get(1), regalo, cliente);
      servicio.comprar(catalogo.get(2), regalo, cliente);
   }
}