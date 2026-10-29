/* PATRON SINGLETON (Creacional). Rol: clase singleton. Su constructor es
privado y la unica instancia se obtiene con getInstancia(), asi toda la tienda
comparte un mismo catalogo de videojuegos. */
class Catalogo{
   private static Catalogo instancia;
   private ArrayList<Videojuego> juegos = new ArrayList<>();
   private Catalogo(){
   }
   public static Catalogo getInstancia(){
       if (instancia == null){
           instancia = new Catalogo();
       }
       return instancia;
   }
   public void agregar(Videojuego juego){
      juegos.add(juego);
   }
   public ArrayList<Videojuego> getJuegos(){
      return juegos;
   }
   public Videojuego buscar(String titulo){
       for (Videojuego juego : juegos){
           if (juego.getTitulo().equals(titulo)){
              return juego;
           }
       }
       return null;
   }
}

/* Rol: usuario del singleton. No crea el catalogo, lo pide con getInstancia(),
por eso trabaja con la misma instancia que usa el resto del programa. */
class ServicioCompras{
   public void comprar(String titulo, MetodoPago metodo, Cliente cliente){
       Videojuego juego = Catalogo.getInstancia().buscar(titulo);
       if (juego == null){
           cliente.notificar("El juego no existe: " + titulo);
       }else if (metodo.pagar(juego.getPrecio())){
           cliente.notificar("Compra exitosa: " + titulo);
       }else{
           cliente.notificar("Compra rechazada: " + titulo);
       }
   }
}