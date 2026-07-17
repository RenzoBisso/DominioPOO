package Vista.VistaConsola;

import Vista.IVentanaJugador;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class VentanaJugadorConsola implements IVentanaJugador {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private BlockingQueue<String> inputQueue;




    public VentanaJugadorConsola(String tituloVentana) {
        inputQueue = new ArrayBlockingQueue<>(1);
        inicializarGUI(tituloVentana);
    }

    void inicializarGUI(String titulo) {
        frame = new JFrame(titulo);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(new Color(0, 255, 0));
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        textField = new JTextField();
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Monospaced", Font.BOLD, 14));

        textField.addActionListener(e -> {
            String input = textField.getText();
            textField.setText("");
            imprimirMensaje("> " + input);
            inputQueue.offer(input);
        });

        frame.add(textField, BorderLayout.SOUTH);
        frame.setVisible(true);
    }



    @Override
    public void imprimirMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(mensaje + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }

    @Override
    public String obtenerInput() throws InterruptedException {
        return inputQueue.take();
    }

    @Override
    public void setInputHabilitado(boolean habilitado) {
        textField.setEnabled(habilitado);
        if(habilitado) textField.requestFocusInWindow();
    }
    @Override
    public void cerrarVentana() {
        frame.dispose();
    }


}