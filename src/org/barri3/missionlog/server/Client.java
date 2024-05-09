/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog.server;

import java.net.Socket;

/**
 *
 * @author GB-BARRI3
 */
public class Client {
    private boolean isDormant = false;
    
    public void bind(Socket socket) {
        // send OK response here
    }
    
    public void disconnect() {
        
        isDormant = true;
    }
    
    public void send(byte[] b) {
        
    }
    
    public boolean isDormant() {
        return isDormant;
    }
}
