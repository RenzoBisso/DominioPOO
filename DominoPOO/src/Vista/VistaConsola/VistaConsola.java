package Vista.VistaConsola;

import Controlador.ControladorConsola;
import Modelo.Ficha;
import Modelo.JugadorXPartida;
import Modelo.Ronda;
import Observer.IObservador;

import java.util.ArrayList;
import java.util.Scanner;

public class VistaConsola implements IObservador {
    private Scanner sc = new Scanner(System.in);
    private ControladorConsola controlador;

    public void setControlador(Controlador.ControladorConsola controlador) {
        this.controlador = controlador;
    }
    public void mostrarMenuPrincipal(){
        System.out.println("\n--- Domino ---");
        System.out.println("1. Crear Partida");
        System.out.println("2. Entrar a Partida");
        System.out.println("3. Mostrar Reglas");
        System.out.println("0. Salir");
    }
    public void mostrarMenuJuego(){
        System.out.println("\n--- Menu Acciones ---");
        System.out.println("1. Colocar Ficha");
        System.out.println("2. Robar Ficha del pozo");
        System.out.println("3. Pasar turno");
        System.out.println("4. Mostrar Mano");
        System.out.println("0. Salir");
    }

    public int obtenerOpcion(){
        try {
            int opcion = sc.nextInt();
            sc.nextLine();
            return opcion;
        } catch (Exception e) {
            System.out.println("Error: Debe ingresar un número entero.");
            sc.nextLine();
            return obtenerOpcion();
        }
    }

    public int obtenerOpcion(String nombreDelDato){
        try {
            System.out.print("Ingrese " + nombreDelDato + ": ");
            int opcion = sc.nextInt();
            sc.nextLine();
            return opcion;
        } catch (Exception e) {
            System.out.println("Error: Debe ingresar un número entero.");
            sc.nextLine();
            return obtenerOpcion(nombreDelDato);
        }
    }



    public String obtenerDato(String nombreDelDato){
        System.out.print("Ingrese " + nombreDelDato + ": ");
        return sc.nextLine();
    }

    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje);
    }

    public void mostrarFicha(Ficha ficha) {
        if (ficha.getDoble()) {
            System.out.printf("""
                [ %d ]
                [---]
                [ %d ]%n""", ficha.getLadoA(), ficha.getLadoB());
        } else {
            System.out.printf("[ %d | %d ]%n", ficha.getLadoA(), ficha.getLadoB());
        }
    }

    public void mostrarMesa(ArrayList<Ficha> mesa){
        if(mesa.isEmpty()){
            mostrarMensaje("La mesa esta vacia");
        }else{
            for(Ficha ficha:mesa){
                mostrarFicha(ficha);
            }
        }
    }


    public void mostrarReglasDomino() {
        System.out.println("=========================================================");
        System.out.println("                 HISTORIA DEL DOMINÓ                     ");
        System.out.println("=========================================================");
        System.out.println("El dominó es un juego de mesa (extensión de los dados) de origen");
        System.out.println("oriental y antiquísimo. Fue introducido en Europa por los");
        System.out.println("italianos a mediados del siglo XVIII. Su popularidad es");
        System.out.println("inmensa en países latinoamericanos, especialmente en el Caribe.\n");

        System.out.println("---------------------------------------------------------");
        System.out.println("                    OBJETIVO DEL JUEGO                   ");
        System.out.println("---------------------------------------------------------");
        System.out.println("* Fichas: 28 rectangulares divididas en 2 espacios (cifras 0-6).");
        System.out.println("* Jugadores: 2, 3 o 4.");
        System.out.println("* Meta: Colocar todas tus fichas antes que los contrarios y sumar");
        System.out.println("  puntos de las fichas no colocadas por los oponentes.");
        System.out.println("* Fin de partida: Cuando se alcanzan los puntos fijados en mesa.\n");

        System.out.println("---------------------------------------------------------");
        System.out.println("                   DESARROLLO DEL JUEGO                  ");
        System.out.println("---------------------------------------------------------");
        System.out.println("1. Reparto: Cada jugador recibe 7 fichas. El resto va al 'pozo'.");
        System.out.println("2. Inicio: Empieza el jugador con el doble más alto (ej. 6 doble).");
        System.out.println("   Este jugador 'lleva la mano'.");
        System.out.println("3. Turnos: Sentido antihorario. Se debe coincidir el número de la");
        System.out.println("   ficha con uno de los extremos abiertos en la mesa.");
        System.out.println("4. Robar: Si no puedes jugar, robas del pozo hasta poder tirar.");
        System.out.println("   Si no quedan, pasas el turno.\n");

        System.out.println("---------------------------------------------------------");
        System.out.println("                   FINAL DE UNA RONDA                    ");
        System.out.println("---------------------------------------------------------");
        System.out.println("* Dominó: Un jugador coloca su última ficha. Gana la ronda y");
        System.out.println("  suma los puntos de sus contrincantes (y compañero si aplica).");
        System.out.println("* Cierre: Ningún jugador puede jugar (extremos jugados 7 veces).");
        System.out.println("  Se cuentan los puntos restantes; el que tenga menos, gana.\n");

        System.out.println("---------------------------------------------------------");
        System.out.println("                 CONTROLES E INTERFAZ                    ");
        System.out.println("---------------------------------------------------------");
        System.out.println("* Jugar/Mover: Pulsa o arrastra la ficha.");
        System.out.println("* Ordenar: Arrastra las fichas en tu mano. Pulsa abajo para voltear.");
        System.out.println("* Pozo: Aparece automáticamente cuando es necesario robar.");

        System.out.println("---------------------------------------------------------");
        System.out.println("              CLASIFICACIÓN Y PUNTUACIÓN                 ");
        System.out.println("---------------------------------------------------------");
        System.out.println("* Límite para ganar: Entre 50 y 300 puntos.");
        System.out.println("* XP (Partidas competitivas): 10 x número de jugadores.");
        System.out.println("  - 2 Jugadores = 20 XP");
        System.out.println("  - 3 Jugadores = 30 XP");
        System.out.println("  - 4 Jugadores = 40 XP (o 20 c/u por parejas)");
        System.out.println("* Los perdedores siempre suman 5 puntos de consolación.");
        System.out.println("=========================================================\n");
    }


    public void mostrarInfoJugadores(ArrayList<JugadorXPartida> jugadores){
        mostrarMensaje("-- INFORMACION DE JUGADORES DE LA PARTIDA --");
        for(JugadorXPartida jugador: jugadores){
            mostrarMensaje("Jugador "+jugador.getJugador().getNombre());
            mostrarMensaje("Puntos: "+jugador.getPuntosObtenidos());
        }
    }

    public void mostrarMano(ArrayList<Ficha> mano){
        if(mano.isEmpty()){
            mostrarMensaje("La mesa esta vacia");
        }else{
            int aux=0;
            for(Ficha ficha:mano){
                this.mostrarMensaje("Indice de ficha "+aux);
                aux++;
                mostrarFicha(ficha);
            }
        }
    }

    public void mostrarPozo(ArrayList<Ficha> pozo){
        if(pozo.isEmpty()){
            mostrarMensaje("El pozo esta vacio");
        }else{
            for(int k=0;k<pozo.size();k++){
                mostrarMensaje("["+k+"]");
            }
        }
    }



    @Override
    public void actualizar() {
        Ronda rondaActual = controlador.getRondaActual();

        if (rondaActual != null) {
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
}
