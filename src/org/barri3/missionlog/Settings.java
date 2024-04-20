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
    public static String serverAddr = null;
    public static int serverPort = DEFAULT_PORT;
    public static int fileRefresh = 40;
    public static int tickRate = 25;
    public static boolean forceSystem = false;
    public static boolean forceTerminal = false;
    
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
                        case "serverPort": serverPort = Integer.parseInt(value); break;
                        case "fileRefresh": fileRefresh = Integer.parseInt(value); break;
                        case "tickRate": tickRate = Integer.parseInt(value); break;
                        case "forceSystem": forceSystem = getBoolean(value); break;
                        case "forceTerminal": forceTerminal = getBoolean(value); break;
                        
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
    
    public static void save() throws Exception {
        PrintStream out = new PrintStream(SETTINGS_FILE);
        
        out.println("mode: " + mode);
        out.println("logDir: " + logDir);
        out.println("serverAddr: " + serverAddr);
        out.println("serverPort: " + serverPort);
        out.println("fileRefresh: " + fileRefresh);
        out.println("tickRate: " + tickRate);
        out.println("forceSystem: " + forceSystem);
        out.println("forceTerminal: " + forceTerminal);
        
        out.flush();
        out.close();
    }
}
