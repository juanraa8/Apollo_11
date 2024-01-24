import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class LaunchModel {
    // Variable que indica si el lanzamiento está en progreso
    private AtomicBoolean launchInProgress = new AtomicBoolean(false);
    // Servicio de ejecutor para manejar los hilos
    private ExecutorService executorService;
    // Callback para comunicarse con la vista
    private LaunchCallback callback;

    // Interfaz para los callbacks. Permite actualizar la vista desde el modelo
    public interface LaunchCallback {
        void launchCompleted(boolean wasSuccessful);
        void updateCountdownClock(int seconds, int totalDuration);
        void updateLaunchStatus(String status, Color color);
        JFrame getFrame();
    }

    // Establecer el callback
    public void setCallback(LaunchCallback callback) {
        this.callback = callback;
    }

    // Iniciar la secuencia de lanzamiento
    public void startLaunchSequence(int totalSeconds) {
        launchInProgress.set(true);
        // Iniciamos un pool de hilos: uno para la cuenta atrás y otro para el monitoreo
        executorService = Executors.newFixedThreadPool(2);

        // Hilo para la cuenta regresiva y ejecución de fases
        executorService.submit(() -> {
            countdownPhase(totalSeconds);
            executeLaunchPhases();
        });

        // Hilo para monitorear el estado del lanzamiento
        executorService.submit(this::monitoringPhase);

        // Registrar inicio de la secuencia de lanzamiento
        log("Launch sequence started.");
    }

    // Método para la fase de cuenta regresiva
    private void countdownPhase(int totalSeconds) {
        try {
            log("Countdown phase started.");
            for (int i = totalSeconds; i >= 0; i--) {
                if (!launchInProgress.get()) {
                    callback.launchCompleted(false);
                    log("Countdown phase cancelled.");
                    return;
                }
                callback.updateCountdownClock(i, totalSeconds);
                Thread.sleep(1000); // Espera 1 segundo entre cada actualización
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            launchInProgress.set(false);
            callback.launchCompleted(false);
            log("Countdown phase interrupted and cancelled.");
        }
    }

    // Método para ejecutar las fases críticas del lanzamiento
    private void executeLaunchPhases() {
        for (int phase = 1; phase <= 4; phase++) {
            if (!launchInProgress.get()) {
                log("Launch phase " + phase + " cancelled.");
                callback.launchCompleted(false);
                return;
            }
            executeCriticalPhase(phase);
        }
        launchInProgress.set(false);
        callback.launchCompleted(true);
        log("All launch phases completed successfully.");
    }

    // Método para ejecutar una fase crítica
    private void executeCriticalPhase(int phaseNumber) {
        try {
            log("Critical phase " + phaseNumber + " started.");
            Thread.sleep(2000); // Simulando trabajo en una fase crítica
            callback.updateLaunchStatus("Phase " + phaseNumber + " completed", Color.GREEN);
            log("Critical phase " + phaseNumber + " completed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log("Critical phase " + phaseNumber + " interrupted and cancelled.");
        }
    }

    // Método para monitorear el estado del lanzamiento
    private void monitoringPhase() {
        log("Monitoring phase started.");
        while (launchInProgress.get()) {
            try {
                Thread.sleep(1000); // Simulando actividad de monitoreo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log("Monitoring phase interrupted and cancelled.");
            }
        }
        log("Monitoring phase ended.");
    }

    // Método para cancelar la secuencia de lanzamiento
    public void cancelLaunchSequence() {
        launchInProgress.set(false);
        if (executorService != null) {
            executorService.shutdownNow();
            log("Launch sequence cancelled.");
        }
    }

    // Método para escribir en el archivo de logs
    private void log(String message) {
        try (PrintWriter out = new PrintWriter(new FileWriter("logs.txt", true))) {
            out.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}