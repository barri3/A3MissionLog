/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog;

import java.awt.GraphicsEnvironment;

/**
 *
 * @author GB-BARRI3
 */
public abstract class AbstractOutput {
    public abstract void handleError(Exception err);
    public abstract void clear();
    public abstract void append(String level, String line);
    public abstract String ask(String question);
    public abstract void close();
    
    public void appendSystemInfo(String mode) {
        appendSystem("Barri3.org A3MissionLog 2404000");
        appendSystem("Copyright © barri3.org, All rights reserved");
        appendSystem("");
        appendSystem("Running in " + mode + " mode");
        appendSystem("");
    }
    
    public void append(String message) {
        append("MSG", message);
    }
    
    public void appendSystem(String message) {
        append("SYS", message);
    }
    
    public static AbstractOutput createOutputForEnvironment() {
        if (!Settings.forceTerminal && (Settings.forceSystem || GraphicsEnvironment.isHeadless())) {
            return new OutputSystem();
        }
        
        return new OutputTerminal();
    }
}
