/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.barri3.missionlog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import uk.co.gamebrewers.common.threading.ThreadingUtil;

/**
 *
 * @author GB-BARRI3
 */
public class SourceLocal extends AbstractSource {
    private AbstractOutput out = null;
    private LogDirectoryChecker logDirectoryChecker = null;
    
    public SourceLocal(AbstractOutput out) {
        this.out = out;
        logDirectoryChecker = new LogDirectoryChecker();
    }
    
    public SourceLocal() {
        this(MissionLog.out);
    }
    
    @Override
    public void run() {
        MissionLog.out.appendSystemInfo("local");
        
        try {
            File root = askForRoot();

            if (root == null) {
                return;
            }
            
            out.appendSystem("Using root directory: " + root);
            
            FileReader reader = null;
            BufferedReader in = null;

            while (MissionLog.canContinue) {
                if (logDirectoryChecker.isTimeToCheck(Settings.fileRefresh)) {
                    File newFile = logDirectoryChecker.scan(root);
                    
                    if (newFile != null) {
                        MissionLog.out.appendSystem("Openning " + newFile);
                        
                        if (in != null) {
                            in.close();
                        }
                        
                        if (reader != null) {
                            reader.close();
                        }
                        
                        reader = new FileReader(newFile);
                        in = new BufferedReader(reader);
                    }
                } else if (in != null && in.ready()) {
                    while (in.ready()) {
                        String line = in.readLine();
                        
                        if (line.length() >= 9 && line.substring(9).contentEquals("\"###### ###### ###### ###### FRESH STARTUP ###### ###### ###### ######\"")) {
                            out.clear();
                        } else {
                            out.append(line);
                        }
                    }
                } else {
                    ThreadingUtil.join(Settings.tickRate);
                }
                
                logDirectoryChecker.tick();
            }
        } catch (Exception err) {
            out.handleError(err);
        }
        
        out.appendSystem("Scanning thread has ended");
    }
}
