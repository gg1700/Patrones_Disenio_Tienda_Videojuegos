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