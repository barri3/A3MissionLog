/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog.server;

/**
 *
 * @author GB-BARRI3
 */
public class BinaryMessage {
    public MessageType type;
    public byte[] data;
    
    public BinaryMessage(MessageType type, byte[] data) {
        this.type = type;
        this.data = data;
    }
}
