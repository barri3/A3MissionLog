/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import uk.co.gamebrewers.bookworm.CBookwormBinarizer;

/**
 *
 * @author GB-BARRI3
 */
public class Connection {
    private Socket socket = null;
    private InputStream in = null;
    private OutputStream out = null;
    
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    public void send(BinaryMessage message) throws IOException {
        send(MessagePacker.pack(message));
    }
    
    public void send(StringMessage message) throws IOException {
        send(MessagePacker.pack(message));
    }
    
    public void send(byte[] data) throws IOException {
        out.write(data);
        out.flush();
    }
    
    public BinaryMessage readBinary() throws IOException {
        return MessagePacker.unpackBinary(read());
    }
    
    public StringMessage readString() throws IOException {
        return MessagePacker.unpackString(read());
    }
    
    public byte[] read() throws IOException {
        byte[] lenBytes = in.readNBytes(4);
        int length = CBookwormBinarizer.intFromBytes(lenBytes);
        return in.readNBytes(length);
    }
}
