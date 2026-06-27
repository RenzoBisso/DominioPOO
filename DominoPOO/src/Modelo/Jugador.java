package Modelo;

public class Jugador {
    private static int idContador=0;
    private Integer idJugador;
    private String nombre;
    private EstadoJugador estado;

    public Jugador(String nombre, EstadoJugador estado) {
        idContador++;
        this.idJugador = idContador;
        this.nombre = nombre;
        this.estado = estado;
    }

    public EstadoJugador getEstado() {
        return estado;
    }

    public void setEstado(EstadoJugador estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Integer idJugador) {
        this.idJugador = idJugador;
    }
}
