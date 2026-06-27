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


    public Partida() {
        contadorID++;
        this.id=contadorID;
        this.fechaInicio=LocalDateTime.now();
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
}
