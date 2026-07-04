package Modelo;

import java.util.ArrayList;

public class Mesa {
    private Integer extremoIzquierdo;
    private Integer extremoDerecho;
    private ArrayList<Ficha> fichasJugadas=new ArrayList<>();


    public Integer getExtremoIzquierdo() {
        return extremoIzquierdo;
    }

    public void setExtremoIzquierdo(Integer extremoIzquierdo) {
        this.extremoIzquierdo = extremoIzquierdo;
    }

    public Integer getExtremoDerecho() {
        return extremoDerecho;
    }

    public void setExtremoDerecho(Integer extremoDerecho) {
        this.extremoDerecho = extremoDerecho;
    }

    public ArrayList<Ficha> getFichasJugadas() {
        return fichasJugadas;
    }

    public void setFichasJugadas(ArrayList<Ficha> fichasJugadas) {
        this.fichasJugadas = fichasJugadas;
    }


    public boolean colocarFicha(Ficha ficha,Boolean extremo){
        Integer ladoA=ficha.getLadoA();
        Integer ladoB=ficha.getLadoB();

        if (getFichasJugadas().isEmpty()) {
            getFichasJugadas().add(ficha);
            setExtremoIzquierdo(ficha.getLadoA());
            setExtremoDerecho(ficha.getLadoB());
            return true;
        }

        if(extremo){
           if(ladoA.equals(getExtremoDerecho())){
               getFichasJugadas().add(ficha);
               setExtremoDerecho(ladoB);
               return true;
           } else if (ladoB.equals(getExtremoDerecho())) {
               ficha.rotar();
               getFichasJugadas().add(ficha);
               setExtremoDerecho(ladoA);
               return true;
           }

        }else{
            if(ladoB.equals(getExtremoIzquierdo())){
                getFichasJugadas().addFirst(ficha);
                setExtremoIzquierdo(ladoA);
                return true;
            }else if(ladoA.equals(getExtremoIzquierdo())){
                ficha.rotar();
                getFichasJugadas().addFirst(ficha);
                setExtremoIzquierdo(ladoB);
                return true;
            }
        }
        return false;
    }

    public boolean fichaValida(Ficha ficha){
        try{
            if (getFichasJugadas().isEmpty()) {
                return true;
            }
            return ficha.getLadoA().equals(getExtremoIzquierdo()) || ficha.getLadoA().equals(getExtremoDerecho()) || ficha.getLadoB().equals(getExtremoIzquierdo()) || ficha.getLadoB().equals(getExtremoDerecho());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
