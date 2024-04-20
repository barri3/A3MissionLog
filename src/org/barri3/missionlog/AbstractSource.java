/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog;

import java.io.File;

/**
 *
 * @author GB-BARRI3
 */
public abstract class AbstractSource {
    public abstract void run();
    
    public File askForRoot() {
        File detection = detectRoot();
        
        if (detection == null) {
            String answer = MissionLog.out.ask("Where are the log files stored?");
            
            if (answer != null) {
                return check(new File(answer), true);
            }
        }
        
        return detection;
    }
    
    private File detectRoot() {
        String os = System.getProperty("os.name");
        String username = System.getProperty("user.name");
        
        if (os.startsWith("Windows")) {
            return detectRootWindows(username);
        } else if (os.startsWith("Linux")) {
            return detectRootLinux(username);
        } else {
            MissionLog.out.handleError(new Exception("Unrecognised os name " + os));
        }
        
        return null;
    }

    private File detectRootWindows(String username) {
        // C:\Users\<user>\AppData\Local\Arma 3
        return testRoot("C:\\Users\\" + username + "\\AppData\\Local\\Arma 3");
    }
    
    private File detectRootLinux(String username) {
        // home/<user>/.local/share/Arma 3/
        return testRoot("home/" + username + "/.local/share/Arma 3/");
    }
    
    private File testRoot(String dir) {
        return check(new File(dir), false);
    }
    
    private File check(File potential, boolean errorOnNonExistence) {
        if (potential.exists()) {
            if (!potential.isDirectory()) {
                MissionLog.out.handleError(new Exception(potential + " is not a directory"));
            } else if (!potential.canRead()) {
                MissionLog.out.handleError(new Exception("Can not read " + potential));
            } else {
                return potential;
            }
        } else if (errorOnNonExistence) {
            MissionLog.out.handleError(new Exception(potential + " does not exist"));
        }
        
        return null;
    }
}
