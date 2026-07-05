package Modelo; // O el paquete donde tengas tu Main actualmente

import Controlador.ControladorConsola;
import Vista.VistaConsola.VistaConsola;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            VistaConsola vista = new VistaConsola();

            Thread gameThread = new Thread(() -> {

                ControladorConsola controlador = new ControladorConsola(vista);
                vista.setControlador(controlador);

                controlador.iniciar();

            });

            gameThread.start();
        });
    }
}