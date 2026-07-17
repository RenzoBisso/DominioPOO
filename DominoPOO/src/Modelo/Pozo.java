package Modelo;

import java.util.ArrayList;
import java.util.Collections;

public class Pozo  extends GrupoFichas{



    public ArrayList<Ficha> getFichasPozo() {
        return this.fichas;
    }

    public void setFichasPozo(ArrayList<Ficha> fichasPozo) {
        this.fichas = fichasPozo;
    }


    public Pozo() {
        cargarPozo();
    }

    private void cargarPozo(){
        try {
            for(int k=0;k<=6;k++){
                for(int j=k;j<=6;j++){
                    Ficha fichaNueva=new Ficha(k,j);
                    this.agregarFicha(fichaNueva);
                }
            }
            Collections.shuffle(getFichasPozo());
        }catch (Exception e){
            System.out.println("Error al cargar pozo: "+e.getMessage());
        }
    }


    public Ficha extraerFicha(int posicion){
        try{
            if(posicion>=0 && posicion<getFichasPozo().size()){
                return getFichasPozo().remove(posicion);
            }
            else{
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al sacar una ficha "+e.getMessage());
            return null;
        }
    }



}
