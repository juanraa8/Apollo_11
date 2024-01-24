import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LaunchView {
    // Componentes de la interfaz de usuario
    private JFrame frame;
    private JPanel panel;
    private JTextField secondsField; // Campo para introducir los segundos
    private JProgressBar progressBar; // Barra de progreso para la cuenta atrás
    private JLabel countdownLabel; // Etiqueta para mostrar el tiempo restante
    private JLabel launchStatusLabel; // Etiqueta para mostrar el estado del lanzamiento
    private boolean isCountingDown = false; // Indica si la cuenta atrás está activa

    // Constructor que inicializa la interfaz
    public LaunchView() {
        initializeUI();
    }

    // Método para inicializar la interfaz de usuario
    private void initializeUI() {
        frame = new JFrame("Apollo 11 Launch Simulation"); // Creación de la ventana principal
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Comportamiento al cerrar

        panel = new JPanel(new GridLayout(6, 1, 5, 5)); // Panel con un diseño de cuadrícula
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Borde del panel
        panel.setBackground(Color.BLACK); // Color de fondo

        secondsField = new JTextField(10); // Campo de texto para los segundos
        Font terminalFont = new Font("Courier", Font.PLAIN, 14); // Fuente para el estilo de terminal
        secondsField.setFont(terminalFont); // Establece la fuente del campo de texto

        // Botón para iniciar la cuenta atrás
        JButton startButton = new JButton("Start Countdown");
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.GREEN);
        startButton.setFont(terminalFont);

        // Botón para cancelar la cuenta atrás
        JButton cancelButton = new JButton("Cancel Countdown");
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.RED);
        cancelButton.setFont(terminalFont);

        // Etiqueta para mostrar el estado del lanzamiento
        launchStatusLabel = new JLabel("Launch Status: Inactive", SwingConstants.CENTER);
        launchStatusLabel.setForeground(Color.WHITE);
        launchStatusLabel.setFont(terminalFont);

        // Configuración de la barra de progreso
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        // Etiqueta para mostrar el tiempo restante
        countdownLabel = new JLabel("00:00", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Courier", Font.PLAIN, 20));
        countdownLabel.setForeground(Color.GREEN);

        // Añadiendo componentes al panel
        panel.add(new JLabel("Enter seconds:", SwingConstants.CENTER)).setForeground(Color.GREEN);
        panel.add(secondsField);
        panel.add(startButton);
        panel.add(cancelButton);
        panel.add(launchStatusLabel);
        panel.add(progressBar);
        panel.add(countdownLabel);

        // Añadiendo el panel a la ventana y configuración final
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    // Métodos para asignar los listeners a los botones
    public void setStartButtonListener(ActionListener listener) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton && ((JButton) comp).getText().equals("Start Countdown")) {
                ((JButton) comp).addActionListener(listener);
            }
        }
    }

    public void setCancelButtonListener(ActionListener listener) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton && ((JButton) comp).getText().equals("Cancel Countdown")) {
                ((JButton) comp).addActionListener(listener);
            }
        }
    }

    // Getters y métodos para actualizar la interfaz
    public String getSecondsFieldText() {
        return secondsField.getText();
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JLabel getCountdownLabel() {
        return countdownLabel;
    }

    public JLabel getLaunchStatusLabel() {
        return launchStatusLabel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setCountingDown(boolean countingDown) {
        isCountingDown = countingDown;
    }

    public boolean isCountingDown() {
        return isCountingDown;
    }

    // Método para actualizar el reloj de cuenta atrás y la barra de progreso
    public void updateCountdownClock(int seconds, int totalDuration) {
        SwingUtilities.invokeLater(() -> {
            int minutes = seconds / 60;
            int secs = seconds % 60;
            countdownLabel.setText(String.format("%02d:%02d", minutes, secs));
            progressBar.setValue((int) (((double) totalDuration - seconds) / totalDuration * 100));
        });
    }

    // Método para actualizar el estado del lanzamiento en la interfaz
    public void updateLaunchStatus(String status, Color color) {
        SwingUtilities.invokeLater(() -> {
            launchStatusLabel.setText(status);
            launchStatusLabel.setForeground(color);
        });
    }
}
