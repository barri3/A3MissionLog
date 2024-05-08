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
                
                // check capacity
                
                // check password
                
                // add to clients
                
                serverLoopFailCounter = 0;
            } catch (Exception err) {
                MissionLog.out.handleError(err);
                serverLoopFailCounter++;
                resetServer();
            }
        }
        
        MissionLog.out.appendSystem("Server shutdown");
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
