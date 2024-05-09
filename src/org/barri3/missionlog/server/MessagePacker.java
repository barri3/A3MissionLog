/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog.server;

import uk.co.gamebrewers.bookworm.CBookwormBinarizer;

/**
 *
 * @author GB-BARRI3
 */
public class MessagePacker {
    public static byte[] pack(MessageType type, byte[] data) {
        byte[] out  = new byte[data.length + 5];
        byte[] len = CBookwormBinarizer.intToBytes(data.length + 1);

        System.arraycopy(len, 0, out, 0, len.length);
        out[4] = type.getCode();
        System.arraycopy(data, 0, out, 5, data.length);
        
        return out;
    }
    
    public static byte[] pack(BinaryMessage message) {
        return pack(message.type, message.data);
    }
    
    public static byte[] pack(MessageType type, String data) {
        return pack(type, data.getBytes());
    }
    
    public static byte[] pack(StringMessage message) {
        return pack(message.type, message.data);
    }
    
    public static BinaryMessage unpackBinary(byte[] raw) {
        byte type = raw[0];
        byte[] data = new byte[raw.length - 1];
        
        System.arraycopy(raw, 1, data, 0, data.length);
        
        return new BinaryMessage(MessageType.forCode(type), data);
    }
    
    public static StringMessage unpackString(byte[] raw) {
        BinaryMessage bin = unpackBinary(raw);
        
        return new StringMessage(bin.type, new String(bin.data));
    }
}
