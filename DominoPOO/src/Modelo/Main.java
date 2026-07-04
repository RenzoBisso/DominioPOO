package Modelo;

import Controlador.ControladorConsola;
import Vista.VistaConsola.VistaConsola;



public class Main {
    public static void main(String[] args) {
        VistaConsola vista = new VistaConsola();
        Partida partida = null;
        ControladorConsola controlador = new ControladorConsola(vista,partida);

        controlador.iniciar();
    }
}