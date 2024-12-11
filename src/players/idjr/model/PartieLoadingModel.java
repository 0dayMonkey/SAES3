package players.idjr.model;

import javafx.application.Platform;
import players.data.Data;

import java.util.LinkedHashMap;
import java.util.function.BiConsumer;

public class PartieLoadingModel {
    private volatile boolean running = true;
    private Thread tcpReceiverThread;

    public void recevoirMessagesTCP(int port, BiConsumer<Object[], String> messageHandler) {
        tcpReceiverThread = new Thread(() -> {
            Data.tcpReceiver(port, (message, playerIP) -> {
                Object[] messageList = Data.readMessage(message);

                @SuppressWarnings("unchecked")
                LinkedHashMap<String, String> attributs = (LinkedHashMap<String, String>) messageList[1];

                // Exécuter le gestionnaire de messages sur le thread JavaFX
                Platform.runLater(() -> {
                    messageHandler.accept(messageList, playerIP);
                });
            });

            // Maintenir le thread actif tant que 'running' est vrai
            while (running) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        tcpReceiverThread.setDaemon(true); // Permet de fermer le thread automatiquement avec l'application
        tcpReceiverThread.start();
    }

    public void stopReceiving() {
        running = false;
        if (tcpReceiverThread != null) {
            tcpReceiverThread.interrupt();
        }
    }
}
