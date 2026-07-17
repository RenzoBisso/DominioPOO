package Vista;



public interface IVentanaJugador  {


    void imprimirMensaje(String mensaje);

    String obtenerInput() throws InterruptedException;

    void setInputHabilitado(boolean habilitado);

    void cerrarVentana();
}
