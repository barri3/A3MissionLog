/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.barri3.missionlog;

import org.barri3.missionlog.server.Server;

/**
 *
 * @author BARRI3
 */
public class MissionLog {
    public static boolean canContinue = true;
    public static AbstractOutput out = null;
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        out = AbstractOutput.createOutputForEnvironment();
        
        try {
            Settings.load();
        } catch (Exception err) {
            out.handleError(err);
        }
        
        if (Settings.mode == null) {
            if (args.length != 0) {
                Settings.mode = args[0];
            } else {
                out.handleError(new Exception("Not mode was provided"));
                return;
            }
        }
        
        switch (Settings.mode) {
            case Settings.MODE_LOCAL: new SourceLocal().run(); break;
            case Settings.MODE_REMOTE: new SourceRemote().run(); break;
            case Settings.MODE_SERVER: new Server().run(); break;
        }
        
        out.appendSystem("Saving settings");
        try {
            Settings.save();
        } catch (Exception err) {
            out.handleError(err);
        }

        out.close();
        
        out.appendSystem("System Thread has ended");
    }
}
