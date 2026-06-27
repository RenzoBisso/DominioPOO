package Modelo;


public class Mano extends GrupoFichas{
    private Integer cantidadFichas;
    private Integer puntajeMano;
    private JugadorXPartida jugador;


    public Mano(JugadorXPartida jugador) {
        this.jugador = jugador;
    }

    public Integer getCantidadFichas() {
        setCantidadFichas(getFichas().size());
        return this.cantidadFichas;
    }

    public void setCantidadFichas(Integer cantidadFichas) {
        this.cantidadFichas = cantidadFichas;
    }

    public Integer getPuntajeMano() {
        return puntajeMano;
    }

    public void setPuntajeMano(Integer puntajeMano) {
        this.puntajeMano = puntajeMano;
    }

    public JugadorXPartida getJugador() {
        return jugador;
    }

    public void setJugador(JugadorXPartida jugador) {
        this.jugador = jugador;
    }






}
