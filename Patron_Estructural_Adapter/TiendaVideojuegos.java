class Videojuego{
   private String titulo;
   private double precio;
   public Videojuego(String titulo, double precio){
       this.titulo = titulo;
       this.precio = precio;
   }
   public String getTitulo(){
      return titulo;
   }
   public double getPrecio(){
      return precio;
   }
}

/* PATRON ADAPTER (Estructural). Rol: interfaz objetivo (target). Es lo que
espera el servicio de compras, un metodo pagar(monto) en bolivianos. */
interface MetodoPago{
   boolean pagar(double monto);
}

// Medio de pago propio de la tienda, ya cumple con la interfaz objetivo.
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

/* Rol: clase adaptada (adaptee). Es una pasarela de pagos externa que no se
puede modificar y su metodo es incompatible: cobra en centavos y otro nombre. */
class PasarelaExterna{
   public boolean cobrarEnCentavos(long centavos){
       System.out.println("Pasarela externa: " + centavos + " centavos");
       return centavos <= 10000;
   }
}

/* Rol: adaptador (adapter). Implementa la interfaz objetivo y por dentro
traduce pagar(monto) al metodo cobrarEnCentavos de la pasarela externa. */
class AdaptadorPasarela implements MetodoPago{
   private PasarelaExterna pasarela;
   public AdaptadorPasarela(PasarelaExterna pasarela){
       this.pasarela = pasarela;
   }
   public boolean pagar(double monto){
       return pasarela.cobrarEnCentavos(Math.round(monto * 100));
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

/* Rol: cliente del patron. Solo conoce MetodoPago, no sabe si detras hay una
tarjeta de regalo o una pasarela externa adaptada. */
class ServicioCompras{
   public void comprar(Videojuego juego, MetodoPago metodo, Cliente cliente){
       if (metodo.pagar(juego.getPrecio())){
           cliente.notificar("Compra exitosa: " + juego.getTitulo());
       }else{
           cliente.notificar("Compra rechazada: " + juego.getTitulo());
       }
   }
}