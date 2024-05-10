/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog.server;

import java.net.ServerSocket;
import java.net.Socket;
import org.barri3.missionlog.MissionLog;
import org.barri3.missionlog.Settings;
import org.barri3.missionlog.SourceLocal;
import uk.co.gamebrewers.bookworm.CBookwormBinarizer;

/**
 *
 * @author GB-BARRI3
 */
public class Server {
    private OutputCapture capture = null;
    private SourceLocal source = null;
    private ServerSocket socket = null;
    private Client[] clients = null;
    
    public void run() {
        MissionLog.out.appendSystemInfo("server");
        
        capture = new OutputCapture();
        source = new SourceLocal();
        
        new Thread(() -> {
            source.run();
        }).start();
        
        clients = new Client[Settings.maxClients];
        
        int serverLoopFailCounter = 0;

        while (MissionLog.canContinue) {
            if (serverLoopFailCounter >= Settings.maxServerFails) {
                MissionLog.out.handleError(new Exception("Max server failure value reached/exceeded"));
                System.exit(0);
                return;
            }

            try {
                if (socket == null) {
                    createServerSocket();
                }
                
                Socket clientSocket = socket.accept();
                acceptOrRejectClient(clientSocket);
                
                serverLoopFailCounter = 0;
            } catch (Exception err) {
                MissionLog.out.handleError(err);
                serverLoopFailCounter++;
                resetServer();
            }
        }
        
        MissionLog.out.appendSystem("Server shutdown");
    }
    
    private void acceptOrRejectClient(Socket clientSocket) {
        String message = "Unable to read handshake";
        
        try {
            Client slot = getClientSlot();
            boolean isFull = slot == null;
            boolean isPasswordCorrect = (isFull) ? (false) : (isPasswordCorrect(clientSocket));
            
            if (isFull || !isPasswordCorrect) {
                message = "Unknown server rejection";
                
                if (isFull) {
                    message = "Server is full";
                } else if (!isPasswordCorrect) {
                    message = "Password is incorrect";
                }
                
                byte[] response = MessagePacker.pack(MessageType.CONNECTION_REJECTED, message);
                clientSocket.getOutputStream().write(response);
                clientSocket.getOutputStream().flush();
                clientSocket.close();
                
                MissionLog.out.append("NET", "Client rejected: " + message);
            } else {
                slot.bind(clientSocket);
            }
        } catch (Exception err) {
            MissionLog.out.append("NET", "Client rejected: " + message);
            MissionLog.out.handleError(err);
        }
    }
    
    private Client getClientSlot() {
        for (int clientIndex = 0; clientIndex < clients.length; clientIndex++) {
            if (clients[clientIndex] == null || clients[clientIndex].isDormant()) {
                clients[clientIndex] = new Client();
                return clients[clientIndex];
            }
        }
        
        return null;
    }
    
    private boolean isPasswordCorrect(Socket clientSocket) {
        try {
            byte[] passwordLength = clientSocket.getInputStream().readNBytes(4);
            int length = CBookwormBinarizer.intFromBytes(passwordLength);

            byte[] passwordData = clientSocket.getInputStream().readNBytes(length);
            String password = new String(passwordData);
            
            return password.contentEquals(Settings.serverPassword);
        } catch (Exception err) {
            MissionLog.out.append("NET", "Password comparison failed");
            MissionLog.out.handleError(err);
        }
        
        return false;
    }
    
    private void createServerSocket() throws Exception {
        socket = new ServerSocket(Settings.serverPort);
        socket.setSoTimeout(250);
    }
    
    private void resetServer() {
        destroyServerSocket();
        disconnectAllClients();
    }
    
    private void destroyServerSocket() {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception err) {
                MissionLog.out.handleError(err);
            }
            
            socket = null;
        }
    }
    
    private void disconnectAllClients() {
        if (clients != null) {
            for (int clientIndex = 0; clientIndex < clients.length; clientIndex++) {
                Client client = clients[clientIndex];
                
                if (client != null) {
                    client.disconnect();
                    client = null;
                }
            }
        }
    }
}
