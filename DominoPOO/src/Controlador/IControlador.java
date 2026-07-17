package Controlador;

import Modelo.Partida;
import Modelo.Ronda;


public interface IControlador {

    void iniciar();

    void mostrarReglas();

    void iniciarJuego();

    void salir();

    void mostrarMano();

    boolean colocarFicha();

    boolean robarFicha();

    boolean pasar();

    boolean gestionarFinDeRonda();

    void entrarPartida();

    void crearPartidaLocal();

    void crearPartidaLAN();
    Partida getPartida();
    Ronda getRondaActual();
}
