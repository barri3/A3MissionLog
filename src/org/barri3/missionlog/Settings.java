/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

/**
 *
 * @author GB-BARRI3
 */
public class Settings {
    public static final File SETTINGS_FILE = new File("settings.cfg");
    
    public static final String MODE_SERVER = "server";
    public static final String MODE_LOCAL = "local";
    public static final String MODE_REMOTE = "remote";
    
    public static final int DEFAULT_PORT = 23235;
    
    public static String mode = MODE_LOCAL;
    public static String logDir = null;
    public static String serverAddr = "localhost";
    public static int serverPort = DEFAULT_PORT;
    public static String serverPassword = "admin123";
    public static int fileRefresh = 1200;
    public static int tickRate = 25;
    public static boolean forceSystem = false;
    public static boolean forceTerminal = false;
    public static int maxClients = 32;
    public static int maxClientFails = 5;
    public static int maxServerFails = 5;
    
    public static void load() throws Exception {
        if (!SETTINGS_FILE.exists()) {
            return;
        }
        
        FileReader reader = new FileReader(SETTINGS_FILE);
        BufferedReader in = new BufferedReader(reader);
        
        Exception throwMe = null;
        
        try {
            reader = new FileReader(SETTINGS_FILE);
            in = new BufferedReader(reader);
            
            while (in.ready()) {
                String line = in.readLine();
                
                if (!line.startsWith("#")) {
                    if (!line.contains(": ")) {
                        throw new Exception("Malformed configuration line: " + line);
                    }
                    
                    int separator = line.indexOf(":");
                    String key = line.substring(0, separator);
                    String value = line.substring(separator + 2, line.length());
                    
                    switch (key) {
                        case "mode": mode = getString(value); break;
                        case "logDir": logDir = getString(value); break;
                        case "serverAddr": serverAddr = getString(value); break;
                        case "serverPort": serverPort = getInteger(value, serverPort); break;
                        case "serverPassword": serverPassword = getString(value); break;
                        case "fileRefresh": fileRefresh = getInteger(value, fileRefresh); break;
                        case "tickRate": tickRate = getInteger(value, tickRate); break;
                        case "forceSystem": forceSystem = getBoolean(value); break;
                        case "forceTerminal": forceTerminal = getBoolean(value); break;
                        case "maxClients": maxClients = getInteger(value, maxClients); break;
                        case "maxClientFails": maxClientFails = getInteger(value, maxClientFails); break;
                        case "maxServerFails": maxServerFails = getInteger(value, maxServerFails); break;
                        
                        default: throw new Exception("Unrecognised key/value: " + line);
                    }
                }
            }
        } catch (Exception err) {
            throwMe = err;
        }
        
        try {
            in.close();
        } catch (Exception err) { /* Ignored exception */ }
        
        try {
            reader.close();
        } catch (Exception err) { /* Ignored exception */ }
        
        if (throwMe != null) {
            throw throwMe;
        }
    }
    
    private static String getString(String value) {
        if (value.isBlank() || value.isEmpty() || value.contentEquals("null")) {
            return null;
        }
        
        return value;
    }
    
    private static boolean getBoolean(String value) {
        return value.contentEquals("true") || value.contentEquals("1") || value.contentEquals("y");
    }
    
    private static int getInteger(String value, int def) {
        try {
            return Integer.parseInt(value);
        } catch (Exception err) {
            MissionLog.out.handleError(err);
            return def;
        }
    }
    
    public static void save() throws Exception {
        PrintStream out = new PrintStream(SETTINGS_FILE);
        
        out.println("mode: " + mode);
        out.println("logDir: " + logDir);
        out.println("serverAddr: " + serverAddr);
        out.println("serverPort: " + serverPort);
        out.println("serverPassword" + serverPassword);
        out.println("fileRefresh: " + fileRefresh);
        out.println("tickRate: " + tickRate);
        out.println("forceSystem: " + forceSystem);
        out.println("forceTerminal: " + forceTerminal);
        out.println("maxClients: " + maxClients);
        out.println("maxClientFails: " + maxClientFails);
        out.println("maxServerFails: " + maxServerFails);
        
        out.flush();
        out.close();
    }
}
