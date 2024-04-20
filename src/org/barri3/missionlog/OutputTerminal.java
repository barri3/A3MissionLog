/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog;

import uk.co.gamebrewers.common.terminal.CTerminal;

/**
 *
 * @author GB-BARRI3
 */
public class OutputTerminal extends AbstractOutput {
    private CTerminal terminal = null;
    
    public OutputTerminal() {
        terminal = new CTerminal();
        terminal.setTitle("A3MissionLog - Barri3.org");
        terminal.getCloseRequestedEvent().add((e) -> {
            terminal.close();
            MissionLog.canContinue = false;
            MissionLog.out.appendSystem("Terminal thread has ended");
        });
    }

    @Override
    public void appendSystemInfo(String mode) {
        super.appendSystemInfo(mode);
        
        appendSystem("Powered by GameBrewers Terminal");
        appendSystem("http://gamebrewers.co.uk/");
        appendSystem("");
    }

    @Override
    public void append(String level, String line) {
        if (level.contentEquals("SYS") || level.contentEquals("ERR")) {
            System.out.println("[" + level + "] " + line);
        }
        
        terminal.append("[" + level + "] " + line);
    }

    @Override
    public void clear() {
        terminal.clear();
    }

    @Override
    public String ask(String question) {
        return terminal.ask(question);
    }

    @Override
    public void handleError(Exception exception) {
        terminal.alertError(exception);
        appendException(exception);
    }
    
    private void appendException(Throwable exception) {
        append("ERR", exception.getClass().getName() + ": " + exception.getMessage());
        
        // For each stack trace element...
        for (StackTraceElement element : exception.getStackTrace()) {
            // Print the element.
            append("ERR", element.toString());
        }
        
        append("ERR", "");
        
        if (exception.getCause() != null) {
            append("ERR", "Caused by...");
            
            appendException(exception.getCause());
        }
    }

    @Override
    public void close() {
        terminal.close();
    }
}
