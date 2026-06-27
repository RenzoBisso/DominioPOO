package Modelo;

import java.util.ArrayList;

public class Ronda {
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


        for (JugadorXPartida jxp : jugadoresDeLaMesa) {
            Mano nuevaMano = new Mano(jxp);
            this.manos.add(nuevaMano);
        }
    }

    public void robarFicha(int posicion){
        try{
            Ficha fichaNueva=this.getPozo().extraerFicha(posicion);
            for(Mano mano:this.getManos()){
                if(mano.getJugador().equals(getTurnoActual())){
                    mano.agregarFicha(fichaNueva);
                }
                break;
            }
        } catch (Exception e) {
            System.out.println("Error al intentar agregar una ficha al jugador "+e.getMessage());
        }
    }

    //true=derecho || false=izquierdo
    public boolean colocarFicha(Ficha ficha, Boolean extremo){
        try {
            if(getMesa().colocarFicha(ficha,extremo)){
                for(Mano mano:this.getManos()){
                    if(mano.getJugador().equals(getTurnoActual())){
                        mano.getFichas().remove(ficha);
                        break;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al colocar la ficha en la mesa "+e.getMessage());
        }
        return false;
    }


    public void repartirFichas(){
        try{
            for(Mano mano:getManos()){
                for(int k=0;k<7;k++){
                    mano.agregarFicha(getPozo().extraerFicha(0));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las fichas "+e.getMessage());
        }
    }
}
