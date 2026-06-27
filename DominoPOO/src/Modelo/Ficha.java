package Modelo;

public class Ficha {

    private Integer ladoA;
    private Integer ladoB;
    private Boolean doble;

    public Ficha(Integer ladoA, Integer ladoB) {
        this.ladoA = ladoA;
        this.ladoB = ladoB;
        doble = ladoA.equals(ladoB);
    }

    public Integer getLadoA() {
        return ladoA;
    }

    public void setLadoA(Integer ladoA) {
        this.ladoA = ladoA;
    }

    public Boolean getDoble() {
        return doble;
    }

    public void setDoble(Boolean doble) {
        this.doble = doble;
    }

    public Integer getLadoB() {
        return ladoB;
    }

    public void setLadoB(Integer ladoB) {
        this.ladoB = ladoB;
    }


    public void rotar(){

        if(!getDoble()){
            Integer aux=getLadoA();
            setLadoA(getLadoB());
            setLadoB(aux);
        }
    }
    @Override
    public String toString() {
        return "Ficha{" +
                "ladoA=" + ladoA +
                ", ladoB=" + ladoB +
                ", doble=" + doble +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Ficha otraFicha = (Ficha) obj;

        boolean mismoOrden = this.ladoA.equals(otraFicha.getLadoA()) && this.ladoB.equals(otraFicha.getLadoB());

        boolean ordenInvertido = this.ladoA.equals(otraFicha.getLadoB()) && this.ladoB.equals(otraFicha.getLadoA());

        return mismoOrden || ordenInvertido;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.ladoA + this.ladoB);
    }

}
