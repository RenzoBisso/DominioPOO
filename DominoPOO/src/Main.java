import Controlador.Controlador;
import Controlador.IControlador;
import Vista.IVista;
import Vista.VistaConsola.VistaConsola;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                IVista vista = new VistaConsola();

                Thread gameThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IControlador controlador = new Controlador(vista);
                        vista.setControlador(controlador);
                        controlador.iniciar();
                    }
                });

                gameThread.start();
            }
        });



    }
}