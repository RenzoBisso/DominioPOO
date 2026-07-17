package Vista;

import Controlador.IControlador;
import Modelo.Ficha;
import Modelo.JugadorXPartida;
import Modelo.Mano;
import Observer.IObservador;

import java.util.ArrayList;
import java.util.Map;

public interface IVista extends IObservador {
    void setControlador(IControlador controlador);

    void setVentanasJugadores(Map<String, IVentanaJugador> ventanasJugadores);

    void imprimirMensaje(String mensaje);

    void mensajeGlobal(String mensaje);

    int obtenerOpcion();

    int obtenerOpcion(String nombreDelDato);

    String obtenerDato(String nombreDelDato);

    void mostrarMenuPrincipal();

    void mostrarMenuJuego();

    void mostrarMensaje(String mensaje);

    void mostrarFicha(Ficha ficha);

    void mostrarMesa(ArrayList<Ficha> mesa);

    void mostrarReglasDomino();

    void mostrarInfoJugadores(ArrayList<JugadorXPartida> jugadores);

    void mostrarMano(ArrayList<Ficha> mano);

    void mostrarPozo(ArrayList<Ficha> pozo);

    @Override
    void actualizar();

    void crearVentanasParaJugadores(ArrayList<Mano> manos);
}
