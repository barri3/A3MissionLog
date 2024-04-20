/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 *
 * @author GB-BARRI3
 */
public class OutputSystem extends AbstractOutput {
    private PrintStream out = null;
    private BufferedReader in = null;
    
    public OutputSystem() {
        out = System.out;
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void append(String level, String line) {
        out.println("[" + level + "] " + line);
    }

    @Override
    public String ask(String question) {
        try {
            out.println(question + "?");
            return in.readLine();
        } catch (Exception err) {
            err.printStackTrace(out);
        }
        
        return null;
    }

    @Override
    public void handleError(Exception err) {
        err.printStackTrace(out);
    }

    @Override
    public void close() { /** Not supported */ }
    
    @Override
    public void clear() { /** Not supported */ }
}
