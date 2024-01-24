import javax.swing.*;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class LaunchController {
    private LaunchModel model;
    private LaunchView view;
    private Clip audioClip;
    private ImageIcon explosionIcon;

    // Constructor que conecta el modelo y la vista
    public LaunchController(LaunchModel model, LaunchView view) {
        this.model = model;
        this.view = view;

        // Carga el audio y la imagen para la simulación
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\Usuario\\Documents\\UAX\\Concurrencia\\Apollo_11\\fanfare-triumphal.wav"));
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        explosionIcon = new ImageIcon("C:\\Users\\Usuario\\Documents\\UAX\\Concurrencia\\Apollo_11\\despegue-de-apolo-13-1546439.png"); // Imagen de la explosión

        // Configura el callback del modelo
        model.setCallback(new LaunchModel.LaunchCallback() {
            @Override
            public void launchCompleted(boolean wasSuccessful) {
                SwingUtilities.invokeLater(() -> {
                    view.setCountingDown(false);
                    String message = wasSuccessful ? "¡Lanzamiento exitoso!" : "¡Lanzamiento cancelado o fallido!";
                    JOptionPane.showMessageDialog(view.getFrame(), message);

                    // Reproduce el sonido de fanfarria o triunfo
                    audioClip.setFramePosition(0);
                    audioClip.start();

                    // Muestra la imagen de la explosión
                    JOptionPane.showMessageDialog(view.getFrame(), "¡Explosión!", "Explosión", JOptionPane.INFORMATION_MESSAGE, explosionIcon);
                });
            }

            @Override
            public void updateCountdownClock(int seconds, int totalDuration) {
                view.updateCountdownClock(seconds, totalDuration);
            }

            @Override
            public void updateLaunchStatus(String status, Color color) {
                view.updateLaunchStatus(status, color);
            }

            @Override
            public JFrame getFrame() {
                return view.getFrame();
            }
        });

        // Establece el listener para el botón de inicio
        view.setStartButtonListener(e -> {
            if (!view.isCountingDown()) {
                try {
                    int totalSeconds = Integer.parseInt(view.getSecondsFieldText());
                    view.setCountingDown(true);
                    model.startLaunchSequence(totalSeconds);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view.getFrame(), "Formato de número inválido");
                }
            }
        });

        // Establece el listener para el botón de cancelar
        view.setCancelButtonListener(e -> {
            if (view.isCountingDown()) {
                model.cancelLaunchSequence();
            }
        });
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LaunchModel model = new LaunchModel();
            LaunchView view = new LaunchView();
            new LaunchController(model, view);
        });
    }
}
