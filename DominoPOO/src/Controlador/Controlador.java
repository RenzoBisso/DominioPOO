package Controlador;

import Modelo.*;
import Vista.IVista;

import java.util.ArrayList;


public class Controlador implements IControlador {
    IVista vista = null;
    Partida partida=null;
    Ronda rondaActual=null;


    public Controlador(IVista vista) {
        this.vista = vista;
        this.partida = partida;
        this.vista.setControlador(this);
    }

    @Override
    public void iniciar(){
        while(true){
            vista.mostrarMenuPrincipal();
            int opcion= vista.obtenerOpcion();
            switch (opcion){
                case 1:
                    crearPartidaLocal();
                    break;
                case 2:
                    entrarPartida();
                    break;
                case 3:
                    crearPartidaLAN();
                case 4:
                    mostrarReglas();
                    break;
                case 0:
                    vista.mostrarMensaje("Saliendo...");
                    salir();
                    return;
                default:
                    vista.mostrarMensaje("Opcion invalida");
            }
        }

    }



    @Override
    public void mostrarReglas() {

        this.vista.mostrarReglasDomino();

    }


    @Override
    public void iniciarJuego() {
        boolean juegoTerminado = false;
        vista.actualizar();
        vista.mostrarMensaje("--Mano del jugador--");
        vista.mostrarMano(rondaActual.obtenerMano(rondaActual.getTurnoActual()).getFichas());
        vista.mostrarInfoJugadores(this.partida.getJugadores());
        vista.mostrarMenuJuego();
        while (!juegoTerminado) {
            vista.mostrarMenuJuego();
            int accion = vista.obtenerOpcion();

            switch (accion) {
                case 1:
                    colocarFicha();
                    break;
                case 2:
                    robarFicha();
                    break;
                case 3:
                    pasar();
                    break;
                case 4:
                    mostrarMano();
                    break;
                case 0:
                    juegoTerminado = true;
                    break;
            }

            if (partida.verificarFinDePartida()) {
                juegoTerminado = true;
            }
        }
    }

    @Override
    public void salir() {
        try {
            Thread.sleep(1000);
            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void mostrarMano() {
        vista.mostrarMano(rondaActual.obtenerMano(rondaActual.getTurnoActual()).getFichas());
    }

    @Override
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
                }
            } else if (rondaActual.detectarCierre()) {
                rondaActual.resolverGanadorCierre();
                if(gestionarFinDeRonda()){
                    vista.mostrarInfoJugadores(this.partida.getJugadores());
                }
            } else {
                rondaActual.avanzarTurno();
            }
        }

        return jugadaExitosa;
    }

    @Override
    public boolean gestionarFinDeRonda() {
        JugadorXPartida ganadorRonda = rondaActual.getJugadorGanador();
        int puntosGanados = rondaActual.getPuntosGanador();

        if (ganadorRonda != null) {
            int puntosAcumulados = ganadorRonda.getPuntosObtenidos() + puntosGanados;
            ganadorRonda.setPuntosObtenidos(puntosAcumulados);
        }

        if (!partida.verificarFinDePartida()) {
            this.rondaActual = partida.crearNuevaRonda();
            this.rondaActual.agregarObservador(vista);
            this.rondaActual.determinarTurnoInicial();
            return false;
        }
        vista.mensajeGlobal("¡FIN DEL JUEGO! El ganador es: " + this.partida.getGanador().getNombre());

        return true;

    }

    @Override
    public void entrarPartida() {

    }


    @Override
    public Partida getPartida() {
        return this.partida;
    }

    @Override
    public void crearPartidaLocal() {
        vista.mostrarMensaje("\n--- INICIANDO PARTIDA LOCAL ---");
        ArrayList<JugadorXPartida> listaJugadores = new ArrayList<>();

        int cantidadJugadores=vista.obtenerOpcion("cantidad de jugadores");


        for(int k=0;k<cantidadJugadores;k++){
            String nombre = vista.obtenerDato("el nombre del Jugador "+k);
            Jugador j = new Jugador(nombre, null);
            JugadorXPartida jxp = new JugadorXPartida(j);
            jxp.setPuntosObtenidos(0);
            listaJugadores.add(jxp);

        }


        int puntos = vista.obtenerOpcion("Ingrese los puntos a conseguir");

        this.partida = new Partida(listaJugadores, puntos);

        this.rondaActual = this.partida.crearNuevaRonda();
        this.partida.agregarObservador(vista);
        this.rondaActual.agregarObservador(vista);

        vista.mostrarMensaje(this.rondaActual.determinarTurnoInicial());

        iniciarJuego();
    }

    public void crearPartidaLocal(ArrayList<JugadorXPartida>jugadores, Integer puntos) {

            this.partida = new Partida(jugadores, puntos);
            this.rondaActual = this.partida.crearNuevaRonda();
            this.partida.agregarObservador(vista);
            this.rondaActual.agregarObservador(vista);
            vista.mostrarMensaje(this.rondaActual.determinarTurnoInicial());
            iniciarJuego();
    }


    @Override
    public void crearPartidaLAN() {

    }


    @Override
    public boolean robarFicha() {
        vista.mostrarPozo(rondaActual.getPozo().getFichasPozo());
        int indice=vista.obtenerOpcion("Ingrese el indice de la ficha a robar o -1 para cancelar");
        if(indice>=0){
            boolean robo = rondaActual.robarFicha(indice);
        }
        return false;
    }

    @Override
    public boolean pasar() {
        return rondaActual.intentarPasarTurno();
    }

    @Override
    public Ronda getRondaActual() {
        return this.rondaActual;
    }

}
