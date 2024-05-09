
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog.server;

/**
 *
 * @author GB-BARRI3
 */
public enum MessageType {
    CONNECTION_REJECTED (0);
    
    private byte code;
    
    private MessageType(int code) {
        this.code = (byte) code;
    }
    
    public byte getCode() {
        return code;
    }
    
    public static MessageType forCode(byte code) {
        for (MessageType mt : values()) {
            if (mt.getCode() == code) {
                return mt;
            }
        }
        
        return null;
    }
}
