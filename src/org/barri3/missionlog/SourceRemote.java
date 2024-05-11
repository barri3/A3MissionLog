/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog;

import java.net.Socket;
import org.barri3.missionlog.server.Connection;

/**
 *
 * @author GB-BARRI3
 */
public class SourceRemote extends AbstractSource {
    private Connection conn = null;
    private String serverAddress = null;
    private int serverPort = -1;
    private String serverPassword = null;
    
    @Override
    public void run() {
        MissionLog.out.appendSystemInfo("remote");
        
        getServerAddressAndPort();
        getServerPassword();
        
        connect();
        doHandshake();
        runLoop();
        dispose();
    }
    
    private void getServerAddressAndPort() {
        //use settings as default value for fields
    }
    
    private void getServerPassword() {
        // dont use settings
    }
    
    private void connect() {
        
    }
    
    private void doHandshake() {
        
    }
    
    private void runLoop() {
        
    }
    
    private void dispose() {
        
    }
}
