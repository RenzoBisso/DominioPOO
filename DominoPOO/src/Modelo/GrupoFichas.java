package Modelo;

import java.util.ArrayList;

public abstract class GrupoFichas {

    protected ArrayList<Ficha> fichas;

    public GrupoFichas(){
        this.fichas=new ArrayList<>();
    }

    public Ficha extraerFicha(int posicion) {
        try {
            if (posicion >= 0 && posicion < this.fichas.size()) {
                return this.fichas.remove(posicion);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al sacar una ficha: " + e.getMessage());
            return null;
        }
    }

    public void agregarFicha(Ficha nuevaFicha) {
        this.fichas.add(nuevaFicha);
    }

    public void mostrarFichas(){
        for(Ficha ficha:getFichas()){
            String fichaString=ficha.toString();
            System.out.println(fichaString);
        }
    }



    public ArrayList<Ficha> getFichas() {
        return this.fichas;
    }
}
