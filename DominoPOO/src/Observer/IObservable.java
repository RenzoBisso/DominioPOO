package Observer;

public interface IObservable {
    void agregarObservador(IObservador observador);
    void quitarObservador(IObservador observador);
    void notificarObservadores();

}
