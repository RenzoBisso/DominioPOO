package Vista.VistaConsola;

import Controlador.ControladorConsola;
import Modelo.Ficha;
import Modelo.JugadorXPartida;
import Modelo.Mano;
import Modelo.Ronda;
import Observer.IObservador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VistaConsola implements IObservador {
    private ControladorConsola controlador;

    // Las ventanas reales
    private VentanaJugador ventanaLobby;
    private Map<String, VentanaJugador> ventanasJugadores;
    private VentanaJugador ventanaActiva;

    public VistaConsola() {
        ventanaLobby = new VentanaJugador("DOMINÓ - LOBBY PRINCIPAL");
        ventanaActiva = ventanaLobby;
        ventanasJugadores = new HashMap<>();
    }

    public void setControlador(ControladorConsola controlador) {
        this.controlador = controlador;
    }


    private void imprimirMensaje(String mensaje) {
        ventanaActiva.imprimirMensaje(mensaje);
    }

    public void mensajeGlobal(String mensaje) {
        ventanaLobby.imprimirMensaje(mensaje);
        for (VentanaJugador v : ventanasJugadores.values()) {
            v.imprimirMensaje(mensaje);
        }
    }


    public int obtenerOpcion() {
        try {
            String input = ventanaActiva.obtenerInput();
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            ventanaActiva.imprimirMensaje("Error: Debe ingresar un número entero.");
            return obtenerOpcion();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        }
    }

    public int obtenerOpcion(String nombreDelDato) {
        imprimirMensaje("Ingrese " + nombreDelDato + ": ");
        try {
            String input = ventanaActiva.obtenerInput();
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            imprimirMensaje("Error: Debe ingresar un número entero.");
            return obtenerOpcion(nombreDelDato);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        }
    }

    public String obtenerDato(String nombreDelDato) {
        imprimirMensaje("Ingrese " + nombreDelDato + ": ");
        try {
            return ventanaActiva.obtenerInput();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "";
        }
    }


    public void mostrarMenuPrincipal() {
        imprimirMensaje("\n--- Domino ---");
        imprimirMensaje("1. Crear Partida");
        imprimirMensaje("2. Entrar a Partida");
        imprimirMensaje("3. Mostrar Reglas");
        imprimirMensaje("0. Salir");
    }

    public void mostrarMenuJuego() {
        imprimirMensaje("\n--- Menu Acciones ---");
        imprimirMensaje("1. Colocar Ficha");
        imprimirMensaje("2. Robar Ficha del pozo");
        imprimirMensaje("3. Pasar turno");
        imprimirMensaje("4. Mostrar Mano");
        imprimirMensaje("0. Salir");
    }

    public void mostrarMensaje(String mensaje) {
        imprimirMensaje(mensaje);
    }

    public void mostrarFicha(Ficha ficha) {
        if (ficha.getDoble()) {
            imprimirMensaje(String.format("""
                [ %d ]
                [---]
                [ %d ]""", ficha.getLadoA(), ficha.getLadoB()));
        } else {
            imprimirMensaje(String.format("[ %d | %d ]", ficha.getLadoA(), ficha.getLadoB()));
        }
    }

    public void mostrarMesa(ArrayList<Ficha> mesa) {
        if (mesa.isEmpty()) {
            mostrarMensaje("La mesa esta vacia");
        } else {
            for (Ficha ficha : mesa) {
                mostrarFicha(ficha);
            }
        }
    }

    public void mostrarReglasDomino() {
        imprimirMensaje("=========================================================");
        imprimirMensaje("                 HISTORIA DEL DOMINÓ                     ");
        imprimirMensaje("=========================================================");
        imprimirMensaje("El dominó es un juego de mesa (extensión de los dados) de origen");
        imprimirMensaje("oriental y antiquísimo. Fue introducido en Europa por los");
        imprimirMensaje("italianos a mediados del siglo XVIII. Su popularidad es");
        imprimirMensaje("inmensa en países latinoamericanos, especialmente en el Caribe.\n");

        imprimirMensaje("---------------------------------------------------------");
        imprimirMensaje("                    OBJETIVO DEL JUEGO                   ");
        imprimirMensaje("---------------------------------------------------------");
        imprimirMensaje("* Fichas: 28 rectangulares divididas en 2 espacios (cifras 0-6).");
        imprimirMensaje("* Jugadores: 2, 3 o 4.");
        imprimirMensaje("* Meta: Colocar todas tus fichas antes que los contrarios y sumar");
        imprimirMensaje("  puntos de las fichas no colocadas por los oponentes.");
        imprimirMensaje("* Fin de partida: Cuando se alcanzan los puntos fijados en mesa.\n");

        imprimirMensaje("---------------------------------------------------------");
        imprimirMensaje("                   DESARROLLO DEL JUEGO                  ");
        imprimirMensaje("---------------------------------------------------------");
        imprimirMensaje("1. Reparto: Cada jugador recibe 7 fichas. El resto va al 'pozo'.");
        imprimirMensaje("2. Inicio: Empieza el jugador con el doble más alto (ej. 6 doble).");
        imprimirMensaje("   Este jugador 'lleva la mano'.");
        imprimirMensaje("3. Turnos: Sentido antihorario. Se debe coincidir el número de la");
        imprimirMensaje("   ficha con uno de los extremos abiertos en la mesa.");
        imprimirMensaje("4. Robar: Si no puedes jugar, robas del pozo hasta poder tirar.");
        imprimirMensaje("   Si no quedan, pasas el turno.\n");

        imprimirMensaje("---------------------------------------------------------");
        imprimirMensaje("                   FINAL DE UNA RONDA                    ");
        imprimirMensaje("---------------------------------------------------------");
        imprimirMensaje("* Dominó: Un jugador coloca su última ficha. Gana la ronda y");
        imprimirMensaje("  suma los puntos de sus contrincantes (y compañero si aplica).");
        imprimirMensaje("* Cierre: Ningún jugador puede jugar (extremos jugados 7 veces).");
        imprimirMensaje("  Se cuentan los puntos restantes; el que tenga menos, gana.\n");

        imprimirMensaje("---------------------------------------------------------");
        imprimirMensaje("                 CONTROLES E INTERFAZ                    ");
        imprimirMensaje("---------------------------------------------------------");
        imprimirMensaje("* Jugar/Mover: Pulsa o arrastra la ficha.");
        imprimirMensaje("* Ordenar: Arrastra las fichas en tu mano. Pulsa abajo para voltear.");
        imprimirMensaje("* Pozo: Aparece automáticamente cuando es necesario robar.");

        imprimirMensaje("---------------------------------------------------------");
        imprimirMensaje("              CLASIFICACIÓN Y PUNTUACIÓN                 ");
        imprimirMensaje("---------------------------------------------------------");
        imprimirMensaje("* Límite para ganar: Entre 50 y 300 puntos.");
        imprimirMensaje("* XP (Partidas competitivas): 10 x número de jugadores.");
        imprimirMensaje("  - 2 Jugadores = 20 XP");
        imprimirMensaje("  - 3 Jugadores = 30 XP");
        imprimirMensaje("  - 4 Jugadores = 40 XP (o 20 c/u por parejas)");
        imprimirMensaje("* Los perdedores siempre suman 5 puntos de consolación.");
        imprimirMensaje("=========================================================\n");
    }
    public void mostrarInfoJugadores(ArrayList<JugadorXPartida> jugadores) {
        mostrarMensaje("-- INFORMACION DE JUGADORES DE LA PARTIDA --");
        for (JugadorXPartida jugador : jugadores) {
            mostrarMensaje("Jugador " + jugador.getJugador().getNombre());
            mostrarMensaje("Puntos: " + jugador.getPuntosObtenidos());
        }
    }

    public void mostrarMano(ArrayList<Ficha> mano) {
        if (mano.isEmpty()) {
            mostrarMensaje("La mano esta vacia");
        } else {
            int aux = 0;
            for (Ficha ficha : mano) {
                this.mostrarMensaje("Indice de ficha " + aux);
                aux++;
                mostrarFicha(ficha);
            }
        }
    }

    public void mostrarPozo(ArrayList<Ficha> pozo) {
        if (pozo.isEmpty()) {
            mostrarMensaje("El pozo esta vacio");
        } else {
            for (int k = 0; k < pozo.size(); k++) {
                mostrarMensaje("[" + k + "]");
            }
        }
    }


    @Override
    public void actualizar() {
        if (controlador == null) return;
        Ronda rondaActual = controlador.getRondaActual();

        if (rondaActual != null) {
            String nombreTurno = rondaActual.getTurnoActual().getJugador().getNombre();

            if (!ventanasJugadores.containsKey(nombreTurno)) {

                for (VentanaJugador v : ventanasJugadores.values()) {
                    v.cerrarVentana();
                }
                ventanasJugadores.clear();

                crearVentanasParaJugadores(controlador.getRondaActual().getManos());
            }

            ventanaActiva = ventanasJugadores.get(nombreTurno);
            ventanaActiva.setInputHabilitado(true);

            mostrarMensaje("\n=====================================");
            mostrarMensaje("ESTADO ACTUALIZADO DEL JUEGO");
            mostrarMensaje("=====================================");
            mostrarMensaje("Turno de: " + rondaActual.getTurnoActual().getJugador().getNombre().toUpperCase());
            mostrarMensaje("\n--- MESA ---");
            mostrarMesa(rondaActual.getMesa().getFichasJugadas());
            mostrarMensaje("\nFichas en el pozo: " + rondaActual.getPozo().getFichasPozo().size());
            mostrarMensaje("=====================================\n");
        }
    }

    private void crearVentanasParaJugadores(ArrayList<Mano> manos) {
        for (Mano mano : manos) {
            JugadorXPartida jxp = mano.getJugador();
            String nombreJugador = jxp.getJugador().getNombre();

            VentanaJugador nuevaVentana = new VentanaJugador("DOMINÓ - Pantalla de: " + nombreJugador);

            ventanasJugadores.put(nombreJugador, nuevaVentana);

            nuevaVentana.imprimirMensaje("¡Bienvenido a la partida, " + nombreJugador + "!");
            nuevaVentana.imprimirMensaje("Esperando tu turno...\n");
        }

        ventanaLobby.imprimirMensaje("Partida iniciada. Las ventanas de los jugadores han sido creadas.");
    }
}