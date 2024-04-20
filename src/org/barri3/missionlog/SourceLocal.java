/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import uk.co.gamebrewers.common.commentator.Commentator;
import uk.co.gamebrewers.common.threading.ThreadingUtil;

/**
 *
 * @author GB-BARRI3
 */
public class SourceLocal extends AbstractSource {
    private LogDirectoryChecker logDirectoryChecker = null;
    
    public SourceLocal() {
        logDirectoryChecker = new LogDirectoryChecker();
    }
    
    @Override
    public void run() {
        MissionLog.out.appendSystemInfo("local");
        
        try {
            File root = askForRoot();

            if (root == null) {
                return;
            }
            
            MissionLog.out.append("SYS", "Using root directory: " + root);
            
            FileReader reader = null;
            BufferedReader in = null;

            while (MissionLog.canContinue) {
                if (logDirectoryChecker.isTimeToCheck(Settings.fileRefresh)) {
                    File newFile = logDirectoryChecker.scan(root);
                    
                    if (newFile != null) {
                        MissionLog.out.append("SYS", "Openning " + newFile);
                        
                        if (in != null) {
                            in.close();
                        }
                        
                        if (reader != null) {
                            reader.close();
                        }
                        
                        reader = new FileReader(newFile);
                        in = new BufferedReader(reader);
                    }
                } else if (in != null) {
                    while (in.ready()) {
                        String line = in.readLine();
                        
                        if (line.length() >= 9 && line.substring(9).contentEquals("\"###### ###### ###### ###### FRESH STARTUP ###### ###### ###### ######\"")) {
                            MissionLog.out.clear();
                        } else {
                            MissionLog.out.append(line);
                        }
                    }
                } else {
                    ThreadingUtil.join(Settings.tickRate);
                }
                
                logDirectoryChecker.tick();
            }
        } catch (Exception err) {
            MissionLog.out.handleError(err);
        }
        
        MissionLog.out.appendSystem("Scanning thread has ended");
    }
}
