package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Data {
	
	public static String interfaceFinder() {
        // Liste des adresses IP externes à tester pour prevoir le cas où une ou plusieurs ne fonctionnent pas
    	//Google, Cloudflare, Quad9, Cisco OpenDNS Home, NextDNS
        String[] testAddresses = {"8.8.8.8", "1.1.1.1", "9.9.9.9", "208.67.222.222", "45.90.28.0"};

        for (String address : testAddresses) {
            try {
                // Adresse IP externe à tester pour déterminer l'interface réseau utilisée
                InetAddress externalAddress = InetAddress.getByName(address);

                // Créer une socket pour simuler une connexion à cette adresse
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress(externalAddress, 80), 2000);
                    
                    // Récupérer l'adresse locale utilisée pour cette connexion
                    InetAddress localAddress = socket.getLocalAddress();

                    // Trouver l'interface réseau associée à l'adresse locale
                    NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localAddress);

                    // Si l'interface réseau est trouvée, retourne son nom
                    if (networkInterface != null) {
                        return networkInterface.getName();
                    }
                }

            } catch (UnknownHostException e) {
                System.err.println("Adresse inconnue : " + address);
            } catch (SocketTimeoutException e) {
                System.err.println("Timeout lors de la connexion à l'adresse IP : " + address);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Retourne null si aucune interface n'a été trouvée
        return null;
    }
	
	//***********************************************Emetteur**********************************************

	public static String writeMessage(String key, LinkedHashMap<String, String> attributs) {
        StringBuilder message = new StringBuilder();
        message.append("<").append(key);
        
        for (String i : attributs.keySet()) {
            message.append(" ").append(i).append("=\"").append(attributs.get(i)).append("\"");
        }
        
        message.append("/>");
        return message.toString();
    }
    
    public static void tcpSender(String serverAddress, int serverPort, String message) {
    	// Validation des entrées
        if (serverAddress == null || serverAddress.isEmpty()) {
            throw new IllegalArgumentException("L'adresse du serveur ne doit pas être null ou vide.");
        }
        
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Le message ne doit pas être null ou vide.");
        }
        
        try (Socket socket = new Socket(serverAddress, serverPort);
                OutputStream outputStream = socket.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {

        	// Timeout pour éviter un blocage
            socket.setSoTimeout(5000);

            // Envoyer le message
            writer.write(message);
            writer.newLine();
            
            // Assure que toutes les données sont envoyées
            writer.flush(); 
            
        }catch (UnknownHostException e) {
            System.err.println("Hôte inconnu : " + e.getMessage());
        } catch (SocketTimeoutException e) {
            System.err.println("Le socket a expiré avant l'envoi des données.");
        } catch (ConnectException e) {
            System.err.println("Impossible de se connecter au serveur : " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erreur d'E/S : " + e.getMessage());
        }
    }
    
    
    public static void udpEmitter(String message, String localInterface) {
    	// Validation des entrées
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Le message ne doit pas être null ou vide.");
        }

        if (localInterface == null || localInterface.isEmpty()) {
            throw new IllegalArgumentException("L'interface réseau ne doit pas être null ou vide.");
        }

        //Création du socket
        try (MulticastSocket socket = new MulticastSocket()) {

            // Adresse du groupe multicast
            InetAddress group = InetAddress.getByName("224.7.7.7");

            // Préparer le message à envoyer
            byte[] buf = message.getBytes();

            // Spécifier l'interface réseau
            NetworkInterface networkInterface = NetworkInterface.getByName(localInterface);
            
            if (networkInterface == null) {
                throw new SocketException("Interface réseau introuvable : " + localInterface);
            }

            // Associer le socket à l'interface spécifiée
            socket.setNetworkInterface(networkInterface);
            
            // Ajouter un timeout pour éviter le blocage
            socket.setSoTimeout(5000);

            // Créer un paquet
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 7777);

            // Envoyer le paquet
            socket.send(packet);

        } catch (UnknownHostException e) {
            System.err.println("Adresse de groupe multicast invalide : " + e.getMessage());
        } catch (SocketTimeoutException e) {
            System.err.println("Le socket a expiré avant d'envoyer le paquet.");
        } catch (SocketException e) {
            System.err.println("Erreur liée à l'interface réseau ou au socket : " + e.getMessage());
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    //***********************************************Receveur**********************************************
    
 // File d'attente pour stocker les messages reçus
    private static ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();

    public static void udpReceiver(String localInterface) {
        // Validation des entrées
        if (localInterface == null || localInterface.isEmpty()) {
            throw new IllegalArgumentException("L'interface réseau ne doit pas être null ou vide.");
        }
        
        new Thread(() -> {
            // Création d'un MulticastSocket pour recevoir des messages sur le port 7777
            try (MulticastSocket socket = new MulticastSocket(7777)) {

                // Adresse du groupe multicast
                InetAddress group = InetAddress.getByName("224.7.7.7");

                // Crée une adresse socket avec le groupe multicast et le port
                InetSocketAddress groupAddress = new InetSocketAddress(group, 7777);

                // Sélection de l'interface réseau
                NetworkInterface networkInterface = NetworkInterface.getByName(localInterface);

                if (networkInterface == null) {
                    throw new SocketException("Interface réseau introuvable : " + localInterface);
                }

                // Joindre le groupe multicast en utilisant l'adresse socket et l'interface réseau
                socket.joinGroup(groupAddress, networkInterface);

                // Taille du buffer pour recevoir les messages
                byte[] buf = new byte[1024];

                // Réception des messages
                while (true) {
                    // Création du paquet de réception
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);

                    // Attente d'un paquet
                    socket.setSoTimeout(5000);
                    try {
                        socket.receive(packet);
                    } catch (SocketTimeoutException e) {
                        continue;
                    }

                    // Transforme le paquet reçu en chaîne de caractères
                    String received = new String(packet.getData(), 0, packet.getLength());

                    // Ajouter le message à la file d'attente
                    messageQueue.add(received);

                    // Condition de sortie si le message reçu est "exit"
                    if ("exit".equalsIgnoreCase(received.trim())) {
                        break;
                    }
                }

            } catch (UnknownHostException e) {
                System.err.println("Adresse de groupe multicast invalide : " + e.getMessage());
            } catch (SocketException e) {
                System.err.println("Erreur liée au socket ou à l'interface réseau : " + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start(); // Lancer la réception dans un nouveau thread
    }
    

    public static String getMessage() {
        // Retourne et retire le message le plus ancien, ou null si vide
        return messageQueue.poll();
    }
    

    public static void tcpReceiver(int serverPort) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
                while (true) {
                    // Attendre une connexion d'un client
                    try (Socket clientSocket = serverSocket.accept()) {
                        // Obtenir le flux d'entrée pour recevoir les données du client
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                            String receivedMessage;
                            while ((receivedMessage = reader.readLine()) != null) {
                                messageQueue.add(receivedMessage); // Ajouter le message à la file d'attente

                                // Si le message est "exit", sortir de la boucle
                                /*if (receivedMessage.equalsIgnoreCase("exit")) {
                                    break;
                                }*/
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Erreur lors de la connexion ou de la réception : " + e.getMessage());
                    }
                }
            } catch (BindException e) {
                System.err.println("Erreur : le port " + serverPort + " est déjà utilisé ou inaccessible.");
            } catch (SocketException e) {
                System.err.println("Erreur de socket : " + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public static Object[] readMessage(String message) {
        String key = "";
        LinkedHashMap<String, String> attributs = new LinkedHashMap<>();
        
        // Supprimer les caractères "<" et "/>"
        String content = message.substring(1, message.length() - 2).trim();

        // Séparer la clé des attributs
        String[] split = content.split(" ");
        if (split.length > 0) {
            key = split[0];

            // Parcourir les attributs
            for (int i = 1; i < split.length; i++) {
                String[] attribute = split[i].split("=");
                if (attribute.length == 2) {
                    // Supprimer les guillemets autour des valeurs
                    String attrName = attribute[0].trim();
                    String attrValue = attribute[1].replace("\"", "").trim();
                    attributs.put(attrName, attrValue);
                }
            }
        }
        
        return new Object[] { key, attributs }; 
    }

}