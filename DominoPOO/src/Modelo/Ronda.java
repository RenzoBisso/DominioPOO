package Modelo;

import Observer.IObservable;
import Observer.IObservador;

import java.util.ArrayList;

public class Ronda implements IObservable {
    private static int contadorID=0;
    private Integer numeroRonda;
    private EstadoRonda estadoRonda;
    private JugadorXPartida jugadorMano;
    private JugadorXPartida turnoActual;
    private JugadorXPartida jugadorGanador;
    private Integer puntosGanador=0;
    private Mesa mesa;
    private ArrayList<Mano> manos=new ArrayList<>();
    private Pozo pozo;
    private ArrayList<IObservador> observadores = new ArrayList<>();

    public Ronda(ArrayList<JugadorXPartida> jugadores) {
        contadorID++;
        numeroRonda=contadorID;
        this.cargarRonda(jugadores);
        setEstadoRonda(EstadoRonda.EN_CURSO);
        repartirFichas();
    }

    public ArrayList<Mano> getManos() {
        return manos;
    }

    public void setManos(ArrayList<Mano> manos) {
        this.manos = manos;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Integer getPuntosGanador() {
        return puntosGanador;
    }

    public void setPuntosGanador(Integer puntosGanador) {
        this.puntosGanador = puntosGanador;
    }

    public JugadorXPartida getJugadorGanador() {
        return jugadorGanador;
    }

    public void setJugadorGanador(JugadorXPartida jugadorGanador) {
        this.jugadorGanador = jugadorGanador;
    }

    public JugadorXPartida getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(JugadorXPartida turnoActual) {
        this.turnoActual = turnoActual;
    }

    public JugadorXPartida getJugadorMano() {
        return jugadorMano;
    }

    public void setJugadorMano(JugadorXPartida jugadorMano) {
        this.jugadorMano = jugadorMano;
    }

    public EstadoRonda getEstadoRonda() {
        return estadoRonda;
    }

    public void setEstadoRonda(EstadoRonda estadoRonda) {
        this.estadoRonda = estadoRonda;
    }

    public Integer getNumeroRonda() {
        return numeroRonda;
    }

    public void setNumeroRonda(Integer numeroRonda) {
        this.numeroRonda = numeroRonda;
    }

    public Pozo getPozo() {
        return pozo;
    }

    public void setPozo(Pozo pozo) {
        this.pozo = pozo;
    }

    private void cargarRonda(ArrayList<JugadorXPartida> jugadoresDeLaMesa){
        this.pozo=new Pozo();
        this.manos = new ArrayList<>();
        this.mesa = new Mesa();

        for (JugadorXPartida jxp : jugadoresDeLaMesa) {
            Mano nuevaMano = new Mano(jxp);
            this.manos.add(nuevaMano);
        }
    }

    public boolean robarFicha(int posicion) {
        try {
            if (!getPozo().getFichasPozo().isEmpty()) {

                Ficha fichaNueva = this.getPozo().extraerFicha(posicion);

                if (fichaNueva != null) {

                    for (Mano mano : this.getManos()) {
                        if (mano.getJugador().equals(getTurnoActual())) {
                            mano.agregarFicha(fichaNueva);
                            this.notificarObservadores();
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al intentar robar una ficha del pozo: " + e.getMessage());
        }

        return false;
    }

    public Ficha buscarFicha(int indice){
        for(Mano mano:this.getManos()){
            if(mano.getJugador().equals(getTurnoActual())){
                return mano.getFichas().get(indice);
            }
        }
        return null;
    }


    //true=derecho || false=izquierdo
    public boolean colocarFicha(int indiceFicha, Boolean extremo){
        try {
            Ficha ficha=this.buscarFicha(indiceFicha);
            if(getMesa().colocarFicha(ficha,extremo)){
                for(Mano mano:this.getManos()){
                    if(mano.getJugador().equals(getTurnoActual())){
                        mano.getFichas().remove(ficha);
                        break;
                    }
                }
                this.notificarObservadores();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al colocar la ficha en la mesa "+e.getMessage());
        }
        return false;
    }


    public Mano obtenerMano(JugadorXPartida jugador){
        for(Mano mano : getManos()){
            if(mano.getJugador().equals(jugador)){
                return mano;
            }
        }
        return null;
    }



    public void repartirFichas(){
        try{
            for(Mano mano:getManos()){
                for(int k=0;k<1;k++){
                    mano.agregarFicha(getPozo().extraerFicha(0));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las fichas "+e.getMessage());
        }
    }

    public boolean detectarDomino() {
        try {
            JugadorXPartida jugadorAux = null;
            for (Mano mano : getManos()) {
                if (mano.getFichas().isEmpty()) {
                    jugadorAux = mano.getJugador();
                    break;
                }
            }
            if (jugadorAux != null) {
                int totalPuntosPerdedores = 0;
                for (Mano mano : getManos()) {
                    if (!mano.getJugador().equals(jugadorAux)) {
                        totalPuntosPerdedores += mano.sumarMano();
                        mano.getJugador().setPuntosObtenidos(mano.getJugador().getPuntosObtenidos()+5);

                    }
                }
                setJugadorGanador(jugadorAux);
                setPuntosGanador(totalPuntosPerdedores);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al detectar dominó: " + e.getMessage());
        }
        return false;
    }

    public boolean detectarCierre() {
        try {
            if (!getPozo().getFichas().isEmpty()) {
                return false;
            }
            for (Mano mano : getManos()) {
                for (Ficha ficha : mano.getFichas()) {
                    if (getMesa().fichaValida(ficha)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error al detectar cierre: " + e.getMessage());
            return false;
        }
    }

    public void avanzarTurno() {
        try {
            int indiceActual = -1;
            int totalJugadores = this.getManos().size();
            for (int i = 0; i < totalJugadores; i++) {
                if (this.getManos().get(i).getJugador().equals(this.getTurnoActual())) {
                    indiceActual = i;
                    break;
                }
            }
            if (indiceActual != -1) {
                int indiceSiguiente = (indiceActual + 1) % totalJugadores;
                JugadorXPartida siguienteJugador = this.getManos().get(indiceSiguiente).getJugador();
                this.setTurnoActual(siguienteJugador);
                this.notificarObservadores();
            }
        } catch (Exception e) {
            System.out.println("Error al avanzar el turno: " + e.getMessage());
        }
    }

    public String determinarTurnoInicial() {
        try {
            JugadorXPartida jugadorInicial = null;
            int valorDobleMasAlto = -1;

            for (Mano mano : this.getManos()) {

                for (Ficha ficha : mano.getFichas()) {

                    if (ficha.getLadoA().equals(ficha.getLadoB())) {

                        if (ficha.getLadoA() > valorDobleMasAlto) {
                            valorDobleMasAlto = ficha.getLadoA();
                            jugadorInicial = mano.getJugador();
                        }
                    }
                }
            }
            if (jugadorInicial == null) {
                jugadorInicial = this.getManos().getFirst().getJugador();
                this.setTurnoActual(jugadorInicial);
                return ("Nadie tiene dobles. El turno inicial es por defecto para: " + jugadorInicial.getJugador().getNombre());
            } else {
                this.setTurnoActual(jugadorInicial);
                return  ("El jugador inicial es " + jugadorInicial.getJugador().getNombre() + " con el doble " + valorDobleMasAlto + "-" + valorDobleMasAlto);
            }

        } catch (Exception e) {
            System.out.println("Error al determinar el turno inicial: " + e.getMessage());
        }
        return "Error";
    }

    public void resolverGanadorCierre() {
        try {
            JugadorXPartida jugadorGanador = null;

            int menorPuntaje = Integer.MAX_VALUE;

            int puntosTotalesMesa = 0;

            for (Mano mano : this.getManos()) {
                int puntosDeEstaMano = mano.sumarMano();

                puntosTotalesMesa += puntosDeEstaMano;

                if (puntosDeEstaMano < menorPuntaje) {
                    menorPuntaje = puntosDeEstaMano;
                    jugadorGanador = mano.getJugador();
                }
            }

            if (jugadorGanador != null) {
                this.setJugadorGanador(jugadorGanador);


                int puntosQueSeLleva = puntosTotalesMesa - menorPuntaje;
                this.setPuntosGanador(puntosQueSeLleva);

                System.out.println("¡El juego se ha cerrado!");
                System.out.println("El ganador por tener menos puntos es: " + jugadorGanador.getJugador().getNombre() + " (con " + menorPuntaje + " puntos en mano).");
                System.out.println("Puntos obtenidos de los rivales: " + puntosQueSeLleva);
            }

        } catch (Exception e) {
            System.out.println("Error al resolver el ganador por cierre: " + e.getMessage());
        }
    }

    public boolean intentarPasarTurno() {
        try {
            for (Mano mano : getManos()) {
                if (mano.getJugador().equals(getTurnoActual())) {

                    for (Ficha f : mano.getFichas()) {
                        if (getMesa().fichaValida(f)) {
                            System.out.println("No puedes pasar tienes fichas que puedes jugar en la mesa.");
                            return false;
                        }
                    }
                }
            }
            if (!getPozo().getFichas().isEmpty()) {
                System.out.println("No puedes pasar todavía quedan fichas en el pozo");
                return false;
            }

            this.avanzarTurno();
            return true;
        } catch (Exception e) {
            System.out.println("Error al intentar pasar turno: " + e.getMessage());
            return false;
        }
    }


    @Override
    public void agregarObservador(IObservador observador) {
        this.observadores.add(observador);
    }

    @Override
    public void quitarObservador(IObservador observador) {
        this.observadores.remove(observador);
    }

    @Override
    public void notificarObservadores() {
        for (IObservador obs : observadores) {
            obs.actualizar();
        }
    }
}
