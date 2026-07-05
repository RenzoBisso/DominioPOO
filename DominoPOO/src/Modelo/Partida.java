package Modelo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Partida {

    private static int contadorID=0;
    private int id;
    private LocalTime tiempoPorTurno;
    private Integer limiteDePuntos;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private EstadoPartida estadoPartida;
    private ArrayList<Ronda> rondasJugadas=new ArrayList<>();
    private ArrayList<JugadorXPartida> jugadores;
    private Jugador ganador;

    public Partida(ArrayList<JugadorXPartida> jugadores, Integer limiteDePuntos) {
        contadorID++;
        this.id = contadorID;
        this.fechaInicio = LocalDateTime.now();

        this.jugadores = jugadores;
        this.limiteDePuntos = limiteDePuntos;
        this.estadoPartida = EstadoPartida.EN_CURSO;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public void setGanador(Jugador ganador) {
        this.ganador = ganador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getTiempoPorTurno() {
        return tiempoPorTurno;
    }

    public void setTiempoPorTurno(LocalTime tiempoPorTurno) {
        this.tiempoPorTurno = tiempoPorTurno;
    }

    public Integer getLimiteDePuntos() {
        return limiteDePuntos;
    }

    public void setLimiteDePuntos(Integer limiteDePuntos) {
        this.limiteDePuntos = limiteDePuntos;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoPartida getEstadoPartida() {
        return estadoPartida;
    }

    public void setEstadoPartida(EstadoPartida estadoPartida) {
        this.estadoPartida = estadoPartida;
    }

    public ArrayList<Ronda> getRondasJugadas() {
        return rondasJugadas;
    }

    public void setRondasJugadas(ArrayList<Ronda> rondasJugadas) {
        this.rondasJugadas = rondasJugadas;
    }
    public ArrayList<JugadorXPartida> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<JugadorXPartida> jugadores) {
        this.jugadores = jugadores;
    }

    public boolean verificarFinDePartida() {
        for (JugadorXPartida jxp : this.jugadores) {

            if (jxp.getPuntosObtenidos() >= this.limiteDePuntos) {
                this.estadoPartida = EstadoPartida.FINALIZADA;
                this.fechaFin = LocalDateTime.now();
                jxp.setGanador(true);
                this.setGanador(jxp.getJugador());


                return true;
            }
        }
        return false;
    }
    public Ronda crearNuevaRonda() {
        Ronda nuevaRonda = new Ronda(this.jugadores);
        this.rondasJugadas.add(nuevaRonda);
        return nuevaRonda;
    }
}
