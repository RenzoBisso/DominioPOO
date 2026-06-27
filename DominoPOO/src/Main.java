import Modelo.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<JugadorXPartida> listaJugadores = new ArrayList<>();
        Jugador jugador=new Jugador("Renzo",EstadoJugador.ACTIVO);
        JugadorXPartida j1=new JugadorXPartida(jugador);


        Jugador jugador2=new Jugador("Juan",EstadoJugador.ACTIVO);
        JugadorXPartida j2=new JugadorXPartida(jugador2);


        Jugador jugador3=new Jugador("Pedor",EstadoJugador.ACTIVO);
        JugadorXPartida j3=new JugadorXPartida(jugador3);

        listaJugadores.add(j1);
        listaJugadores.add(j2);
        listaJugadores.add(j3);


        Ronda ronda = new Ronda(listaJugadores);



        for (Mano mano : ronda.getManos()) {
            System.out.println("Fichas del jugador " + mano.getJugador().getJugador().getNombre() + ": " + mano.getCantidadFichas());
            mano.mostrarFichas();
        }


    }

}
