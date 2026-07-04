package Controlador;

import Modelo.*;
import Vista.VistaConsola.VistaConsola;

import java.util.ArrayList;

public class ControladorConsola {

    private VistaConsola vista;
    private Partida partida;
    private Ronda rondaActual;

    public ControladorConsola(VistaConsola vista, Partida partida) {
        this.vista = vista;
        this.partida = partida;
        this.vista.setControlador(this);
    }

    public void iniciar(){

        while(true){
            vista.mostrarMenuPrincipal();
            int opcion= vista.obtenerOpcion();
            switch (opcion){
                case 1:
                    crearPartida();
                    break;
                case 2:
                    entrarPartida();
                    break;
                case 3:
                    mostrarReglas();
                    break;
                case 0:
                    vista.mostrarMensaje("Saliendo...");
                    return;
                default:
                    vista.mostrarMensaje("Opcion invalida");
            }
        }

    }

    private void mostrarReglas() {
        vista.mostrarReglasDomino();
    }

    public void iniciarJuego(){

        while(true){

            vista.mostrarMensaje("--Mano del jugador--");
            vista.mostrarMano(rondaActual.obtenerMano(rondaActual.getTurnoActual()).getFichas());
            vista.mostrarInfoJugadores(this.partida.getJugadores());
            vista.mostrarMenuJuego();
            int opcion= vista.obtenerOpcion();
            switch (opcion){
                case 1:
                    colocarFicha();
                    break;
                case 2:
                    robarFicha();
                    break;
                case 3:
                    pasar();
                case 4:
                    mostrarMano();
                    break;
                case 0:
                    vista.mostrarMensaje("Saliendo...");
                    return;
                default:
                    vista.mostrarMensaje("Opcion invalida");
            }
        }

    }

    private void mostrarMano() {
        vista.mostrarMano(rondaActual.obtenerMano(rondaActual.getTurnoActual()).getFichas());
    }

    public boolean colocarFicha() {
        vista.mostrarMensaje("Mesa");
        vista.mostrarMesa(rondaActual.getMesa().getFichasJugadas());
        vista.mostrarMensaje("Mano del jugador");
        vista.mostrarMano(rondaActual.obtenerMano(rondaActual.getTurnoActual()).getFichas());
        int indiceFicha=vista.obtenerOpcion("Indice de la ficha o -1 para cancelar");
        if(indiceFicha<0){
            return false;
        }
        String ladoString=vista.obtenerDato("Lado(I-D)").toUpperCase();
        boolean lado;
        if(ladoString.equals("D")){
            lado=true;
        } else if (ladoString.equals("I")) {
            lado=false;
        }else{
            System.out.println("Dato invalido pruebe colocando de vuelta la ficha");
            return false;
        }

        boolean jugadaExitosa = rondaActual.colocarFicha(indiceFicha, lado);

        if (jugadaExitosa) {
            if (rondaActual.detectarDomino()) {
                if(gestionarFinDeRonda()){
                    vista.mostrarInfoJugadores(this.partida.getJugadores());
                    iniciar();
                }
            } else if (rondaActual.detectarCierre()) {
                rondaActual.resolverGanadorCierre();
                if(gestionarFinDeRonda()){
                    vista.mostrarInfoJugadores(this.partida.getJugadores());
                    iniciar();
                }
            } else {
                rondaActual.avanzarTurno();
            }
        }
        return jugadaExitosa;
    }

    public boolean robarFicha() {
        vista.mostrarPozo(rondaActual.getPozo().getFichasPozo());
        int indice=vista.obtenerOpcion("Ingrese el indice de la ficha a robar o -1 para cancelar");
        if(indice>0){
            boolean robo = rondaActual.robarFicha(indice);
        }
        return false;
    }

    public boolean pasar() {
        return rondaActual.intentarPasarTurno();
    }

    private boolean gestionarFinDeRonda() {
        JugadorXPartida ganadorRonda = rondaActual.getJugadorGanador();
        int puntosGanados = rondaActual.getPuntosGanador();

        if (ganadorRonda != null) {
            int puntosAcumulados = ganadorRonda.getPuntosObtenidos() + puntosGanados;
            ganadorRonda.setPuntosObtenidos(puntosAcumulados);
        }

        if (!partida.verificarFinDePartida()) {
            this.rondaActual = partida.crearNuevaRonda();
            this.rondaActual.determinarTurnoInicial();
        }
        return true;

    }

    private void entrarPartida() {
    }

    private void crearPartida() {
        vista.mostrarMensaje("\n--- INICIANDO PARTIDA LOCAL ---");

        String nombre1 = vista.obtenerDato("el nombre del Jugador 1");
        String nombre2 = vista.obtenerDato("el nombre del Jugador 2");

        Jugador j1 = new Jugador(nombre1, null);
        Jugador j2 = new Jugador(nombre2, null);

        JugadorXPartida jxp1 = new JugadorXPartida(j1);
        jxp1.setPuntosObtenidos(0);
        JugadorXPartida jxp2 = new JugadorXPartida(j2);
        jxp2.setPuntosObtenidos(0);

        int puntos = vista.obtenerOpcion("Ingrese los puntos a conseguir");


        ArrayList<JugadorXPartida> listaJugadores = new ArrayList<>();
        listaJugadores.add(jxp1);
        listaJugadores.add(jxp2);

        this.partida = new Partida(listaJugadores, puntos);

        this.rondaActual = this.partida.crearNuevaRonda();

        this.rondaActual.agregarObservador(vista);

        this.rondaActual.determinarTurnoInicial();

        iniciarJuego();
    }

    public Ronda getRondaActual() {
        return this.rondaActual;
    }

}
